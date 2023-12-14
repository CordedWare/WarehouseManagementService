package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.ProductRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class XmlDocParserService {

    /**
     * Обработчик для парсинга полученных данных из GET-запросов, или файлов.
     * */

    public static final String NAME          = "NAME";
    public static final String DESCRIPTION   = "DESCRIPTION";
    public static final String CATEGORY      = "CATEGORY";
    public static final String PRICE         = "PRICE";
    public static final String QUANTITY      = "QUANTITY";
    public static final String CREATION_DATE = "CREATION_DATE";
    public static final String WAREHOUSE     = "WAREHOUSE";
    public static final String USER          = "USER";

    @Autowired
    private ProductService productService;

    public void parseAndSaveDate(String content) throws Exception {
        List<Product> productRows  = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db         = dbf.newDocumentBuilder();
        Document doc               = db.parse(new InputSource(new StringReader(content)));
        NodeList rowList           = doc.getElementsByTagName("row");

        for (int i = 0; i < rowList.getLength(); i++) { // TODO еще доработать
            Element rowElem = (Element) rowList.item(i);

            User userOwner = new User();
            userOwner.setId(Long.parseLong(rowElem.getAttribute(USER)));

            Warehouse warehouse = new Warehouse();
            warehouse.setName(rowElem.getAttribute(WAREHOUSE));

            Product product = new Product();
            product.setId(Long.valueOf(rowElem.getAttribute(String.valueOf(UUID.randomUUID()))));
            product.setName(rowElem.getAttribute(NAME));
            product.setDescription(rowElem.getAttribute(DESCRIPTION));
            product.setCategory(rowElem.getAttribute(CATEGORY));
            product.setPrice(BigDecimal.valueOf(Integer.parseInt(rowElem.getAttribute(PRICE))));
            product.setQuantity(Integer.parseInt(rowElem.getAttribute(QUANTITY)));
            product.setCreationDate(LocalDate.parse(rowElem.getAttribute(CREATION_DATE)));
            product.setWarehouse(warehouse);
            product.setOwner(userOwner);
        }

        productService.saveParserProduct(productRows);
    }
}
