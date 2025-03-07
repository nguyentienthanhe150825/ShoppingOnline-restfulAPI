package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;

@Service
public class FileService {

    public long getFileLength(String fileName, String baseURI, String folder) throws URISyntaxException {
        String str = "";
        if (folder == null) {
            str = baseURI + fileName;
        } else {
            str = baseURI + folder + "/" + fileName;
        }
        URI uri = new URI(str);
        Path path = Paths.get(uri);

        File tmpDir = new File(path.toString());

        // file not exist or file is 1 directory (thư mục thì sẽ ko có kích thước tệp)
        if (!tmpDir.exists() || tmpDir.isDirectory()) {
            return 0;
        }
        return tmpDir.length();
    }

    public InputStreamResource getResource(String fileName, String baseURI, String folder)
            throws URISyntaxException, FileNotFoundException {
        String str = "";
        if (folder == null) {
            str = baseURI + fileName;
        } else {
            str = baseURI + folder + "/" + fileName;
        }
        URI uri = new URI(str);
        Path path = Paths.get(uri);

        File file = new File(path.toString());
        return new InputStreamResource(new FileInputStream(file));
    }

    public byte[] exportExcelFile(List<User> users) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Users");

            Row headerRow = sheet.createRow(0); // Tạo row đầu tiên chứa tên tiêu đề các cột
            String[] columns = { "ID", "Name", "Phone", "Email", "Gender", "Address", "Avatar", "Created At",
                    "Updated At" };
            for (int i = 0; i < columns.length; i++) {
                // Tạo giá trị cho hàng tiêu đề trong file excel
                headerRow.createCell(i).setCellValue(columns[i]);
            }

            int rowIdx = 1; // Bắt đầu từ hàng thứ 2 với rowIdx = 1 để ghi dữ liệu user vào file excel
            for (User user : users) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getName());
                row.createCell(2).setCellValue(user.getPhone());
                row.createCell(3).setCellValue(user.getEmail());
                row.createCell(4).setCellValue(user.getGender().toString());
                row.createCell(5).setCellValue(user.getAddress());
                row.createCell(6).setCellValue(user.getAvatar());
                row.createCell(7).setCellValue(user.getCreateAt().toString());
                row.createCell(8).setCellValue(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : "");
            }

            workbook.write(out); // Gửi file excel qua API mà không cần lưu vào ổ đĩa
            return out.toByteArray();
        }
    }

}
