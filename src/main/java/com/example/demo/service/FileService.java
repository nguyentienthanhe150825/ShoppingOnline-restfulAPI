package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

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

    public InputStreamResource getResource(String fileName, String baseURI, String folder) throws URISyntaxException, FileNotFoundException {
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
    
}
