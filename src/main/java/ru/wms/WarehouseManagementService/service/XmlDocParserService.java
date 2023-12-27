package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.wms.WarehouseManagementService.entity.Company;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.exceptions.WarehouseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class XmlDocParserService {

    /**
     * Обработчик для парсинга полученных данных из GET-запросов, или файлов.
     */

    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String CATEGORY = "CATEGORY";
    public static final String PRICE = "PRICE";
    public static final String QUANTITY = "QUANTITY";
    public static final String CREATION_DATE = "CREATION_DATE";
    public static final String WAREHOUSE_ID = "WAREHOUSE_ID";
    public static final String COMPANY_ID = "COMPANY_ID";
    public static final String COMPANY = "COMPANY";
    public static final String ADRESS = "";


    private final ProductService productService;

    private final WarehouseService warehouseService;

    private final CompanyService companyService;

    public void parseAndSaveDate(String content, Long warehouseId) throws WarehouseException, ParserConfigurationException, IOException, SAXException { // TODO порефакторить для более универсального парсинга + работы с API
        List<Product> productRows = new ArrayList<>();
        List<Warehouse> warehouseRows = new ArrayList<>();
        List<Company> companyRows = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(content)));
        NodeList rowList = doc.getElementsByTagName("row");

        for (int i = 0; i < rowList.getLength(); i++) { // TODO еще доработать
            Element rowElem = (Element) rowList.item(i);

//            Company company = new Company();
//            String companyId = rowElem.getAttribute(COMPANY_ID);
//            company.setId(companyId.isEmpty() || companyId.equals("") ? 1 : Long.parseLong(companyId));
//            company.setName(COMPANY);
//            company.setAddress(ADRESS);
//
//            Warehouse warehouse = new Warehouse();
//            String warehouseId = rowElem.getAttribute(WAREHOUSE_ID);
//            warehouse.setId(warehouseId.isEmpty() ? 1 : Long.parseLong(warehouseId));
//            warehouse.setCompany(company);
//            warehouseRows.add(warehouse);
//
//            Set<Warehouse> warehouseSet = new HashSet<>();
//            warehouseSet.add(warehouse);
//            company.setWarehouses(warehouseSet);
//            companyRows.add(company);

            var product = new Product();
//            var productId = rowElem.getAttribute(WAREHOUSE_ID);
//            product.setId(productId.isEmpty() ? 1 : Long.parseLong(productId));
            product.setName(rowElem.getAttribute(NAME));
            product.setDescription(rowElem.getAttribute(DESCRIPTION));
            product.setCategory(rowElem.getAttribute(CATEGORY));

            var price = rowElem.getAttribute(PRICE);
            product.setPrice(price.isEmpty() ? null : BigDecimal.valueOf(Double.parseDouble(price)));

            var quantity = rowElem.getAttribute(QUANTITY);
            product.setQuantity(quantity.isEmpty() ? null : Integer.parseInt(quantity));

            var creationDate = rowElem.getAttribute(CREATION_DATE);
            product.setCreationDate(creationDate.isEmpty() ? null : LocalDate.parse(creationDate));

            if (!product.getName().isEmpty() && product.getPrice() != null && product.getCreationDate() != null && product.getQuantity() != null)
                productRows.add(product);
        }

//        List<Product> prod = Collections.singletonList(productRows.get(0));
        productService.saveParserProduct(productRows, warehouseId);
    }

}
