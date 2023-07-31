package com.att.productservice.service;

import com.att.productservice.dto.ProductDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id) throws ChangeSetPersister.NotFoundException;

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto) throws ChangeSetPersister.NotFoundException;

    void deleteProduct(Long id) throws ChangeSetPersister.NotFoundException;
}
