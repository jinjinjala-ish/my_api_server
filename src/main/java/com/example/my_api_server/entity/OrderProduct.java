package com.example.my_api_server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "order_products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //주문(1) - 주문상품(N) - 상품(1)
    //상품, 주문, 주문수량

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product; //상품

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order; //주문

    private Long number; //주문수량

}
