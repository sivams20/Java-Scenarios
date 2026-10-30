package com.rest.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ExcelService excelService;
    private final ProductRepository productRepository;

    public ProductController(ExcelService excelService, ProductRepository productRepository){
        this.excelService = excelService;
        this.productRepository = productRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("Please upload a valid Excel file.");
        }
        int savedCount = excelService.saveExcelData(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully uploaded and saved " + savedCount + " records into the database.");
    }
}
