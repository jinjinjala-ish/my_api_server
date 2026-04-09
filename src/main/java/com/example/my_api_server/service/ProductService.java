package com.example.my_api_server.service;


import com.example.my_api_server.entity.Product;
import com.example.my_api_server.repo.ProductRepo;
import com.example.my_api_server.service.dto.ProductCreateDto;
import com.example.my_api_server.service.dto.ProductResDto;
import com.example.my_api_server.service.dto.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;


    //상품등록
    public ProductResDto createProduct(ProductCreateDto dto) {

        Product product = Product.builder()
                .productName(dto.getProductName())
                .productType(dto.getProductType())
                .productNumber(dto.getProductNumber())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        Product savedProduct = productRepo.save(product);

        ProductResDto resDto = ProductResDto.builder()
                .productNumber(savedProduct.getProductNumber())
                .stock(savedProduct.getStock())
                .price(savedProduct.getPrice())
                .build();

        return resDto;
    }


    //상품조회
    public ProductResDto findProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow();

        ProductResDto resDto = ProductResDto.builder()
                .productNumber(product.getProductNumber())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();

        return resDto;
    }


    //상품수정
    @Transactional
    public ProductResDto updateProduct(ProductUpdateDto dto) {
        //1.조회
        Product product = productRepo.findById(dto.productId()).orElseThrow();

        //2.필요한것만 수정(상품명, 재고수량)

        product.changeProductName(dto.changeProductName());
        product.increaseStock(dto.changeStock());

        //3.리턴
        ProductResDto resDto = ProductResDto.builder()
                .productNumber(product.getProductNumber())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();

        return resDto;
    }
}

