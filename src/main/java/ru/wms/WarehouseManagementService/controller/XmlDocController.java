package ru.wms.WarehouseManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.wms.WarehouseManagementService.service.XmlDocParserService;

@Controller
public class XmlDocController {

    /**
     *  Контроллер для локальной загрузки XML-файлов
     */

    private XmlDocParserService xmlDocParserService;

    @GetMapping("/upload")
    public String showUploadForm() {

        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(required = true) Long warehouseId, Model model) {
        try {
            String content = new String(file.getBytes());
            xmlDocParserService.parseAndSaveDate(content,warehouseId);

            boolean isLoadSuccess = true;
            if (isLoadSuccess) {
                model.addAttribute("uploadStatus", "Файл успешно загружен");
            } else {
                model.addAttribute("uploadStatus", "Произошла ошибка при сохранении данных");
            }

            return "redirect:/products";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
