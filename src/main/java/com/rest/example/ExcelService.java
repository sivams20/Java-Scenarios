package com.rest.example;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private final ProductRepository productRepository;

    public ExcelService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int saveExcelData(MultipartFile file) {
        List<Product> products = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet

            for (Row row : sheet) {
                // Skip the header row (Row index 0)
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Extract cell values safely
                Cell nameCell = row.getCell(0);
                Cell priceCell = row.getCell(1);
                Cell stockCell = row.getCell(2);

                if (nameCell != null) {
                    String name = nameCell.getStringCellValue();
                    double price = priceCell != null ? priceCell.getNumericCellValue() : 0.0;
                    int stock = stockCell != null ? (int) stockCell.getNumericCellValue() : 0;

                    products.add(new Product(name, price, stock));
                }
            }

            // Save all extracted rows to H2 Database in bulk
            productRepository.saveAll(products);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage(), e);
        }

        return products.size();
    }
}