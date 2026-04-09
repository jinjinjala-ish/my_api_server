package com.example.my_api_server.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Getter
@Builder
public class Product {

    //상품명, 상품번호(Shirt - RED - S - 001), 상품타입(의류, 음식 장신구..), 가격, 재고수량
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk

    private String productName; //상품명

    private String productNumber; //상품번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType; //상품타입

    private Long price; //가격

    private Long stock; //재고
    @Version
    private Long version; //버전

    //필요한것만 바꿀수있게 Setter처럼 변경할 수 있게하고, 네이밍은 의미있는 메서드
    public void changeProductName(String changeProductName) {
        this.productName = changeProductName;
    }

    //재고 빼고 추가 가능
    //재고 추가
    public void increaseStock(Long addStock) {
        this.stock += addStock; //현재 재고 + 추가 할 재고
    }

    //재고 빼기
    public void decreaseStock(Long subStock) {
        this.stock -= subStock; //현재 재고 - 뺄 재고
    }


    //구매 가능 여부 확인
    //캡슐화를하게되면 변경지점이 되게 작아짐. 코드의 유지보수가 적어지게 됨
    public void buyProductWithStock(Long orderCount) {
        if (this.getStock() - orderCount < 0) {
            throw new RuntimeException("재고가 음수이니 주문 할 수 없습니다!");
        }
        this.decreaseStock(orderCount);
    }

}
