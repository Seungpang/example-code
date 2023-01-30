package com.example.productorderservice.product;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProductService {

    private final ProductPort productPort;

    ProductService(final ProductPort productPort) {
        this.productPort = productPort;
    }

    @Transactional
    public void addProduct(final AddProductRequest request) {
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());

        productPort.save(product);
    }
}
