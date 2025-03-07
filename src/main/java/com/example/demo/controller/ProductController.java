package com.example.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Product;
import com.example.demo.domain.response.file.ResUploadFileDTO;
import com.example.demo.service.FileService;
import com.example.demo.service.ProductService;
import com.example.demo.util.exception.StorageException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    // Dependency Inject: Constructor
    private final ProductService productService;
    private final FileService fileService;

    @Value("${tomosia.upload-file.base-uri}")
    private String baseURI;

    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @PostMapping(value = "/products", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> createNewProduct(@RequestPart("product") @Valid Product createProduct,
            @RequestPart(name = "file", required = false) MultipartFile file, @RequestParam("folder") String folder)
            throws StorageException, URISyntaxException, IOException {

        // create a directory if not exist
        this.fileService.createDirectory(baseURI + folder);

        // Upload Product Image and Get Image URL
        String uploadImageUrl = this.fileService.uploadFile(baseURI + folder, file);

        // Set URL ảnh vào Product
        createProduct.setImage(uploadImageUrl);

        // Save Product in database
        Product product = this.productService.handleCreateProduct(createProduct);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping(value = "/products/upload/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResUploadFileDTO> upload(@RequestParam("productId") long productId,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder) throws StorageException, URISyntaxException, IOException {

        // Check file is empty
        if (file == null || file.isEmpty()) {
            throw new StorageException("File is empty. Please upload a file.");
        }

        // create a directory if not exist
        this.fileService.createDirectory(baseURI + folder);

        // upload product image
        String uploadImageUrl = this.fileService.uploadFile(baseURI + folder, file);

        // save imageUrl in database
        Product updatedProduct = this.productService.saveProductImage(uploadImageUrl, productId);

        ResUploadFileDTO res = new ResUploadFileDTO();
        res.setFileName(updatedProduct.getImage());
        res.setUploadedAt(Instant.now());

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
