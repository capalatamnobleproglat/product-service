package com.att.productservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.att.productservice.model.Product;
import com.att.productservice.dto.ProductDto;
import com.att.productservice.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        product = productRepository.save(product);
        return convertToDto(product);
    }

    @Override
    public ProductDto getProductById(Long id) throws ChangeSetPersister.NotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) throws ChangeSetPersister.NotFoundException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos del producto existente con la información del DTO
        BeanUtils.copyProperties(productDto, existingProduct, "id");

        existingProduct = productRepository.save(existingProduct);
        return convertToDto(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) throws ChangeSetPersister.NotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        productRepository.delete(product);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
