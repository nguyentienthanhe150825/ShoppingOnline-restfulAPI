package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FileService;
import com.example.demo.util.exception.StorageException;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    @Value("${tomosia.upload-file.base-uri}")
    private String baseURI;

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files/export")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam(name = "fileName", required = false) String fileName,
            @RequestParam(name = "folder", required = false) String folder) throws StorageException, URISyntaxException, FileNotFoundException {

        // check file or folder exist
        if (fileName == null) {
            throw new StorageException("Missing request param: (fileName or folder not exist)");
        }

        // check file exist (and not a directory)
        long fileLength = this.fileService.getFileLength(fileName, baseURI, folder);
        if (fileLength == 0) {
            throw new StorageException("File with name = " + fileName + " not found");
        }

        // download single file
        InputStreamResource resource = this.fileService.getResource(fileName, baseURI, folder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
