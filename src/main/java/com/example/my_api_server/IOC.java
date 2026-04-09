package com.example.my_api_server;

import org.springframework.stereotype.Component;

//스프링에게 빈(객체)으로 등록하게 해주는 설정
//IOC 컨테이너에 등록이 됨(객체=물건, 단 하나만 생성됨. 싱글톤 패턴)
@Component
public class IOC {

    public void func1(){
        System.out.println("func1 실행");
    }

    public static void main(String[] args){
        //객체 생성
        //메모리, JVM heap 메모리에 사용함

        //객체를 개발자가 생성하지 않고,
        //spring 프레임워크한테 IOC 객체를 만들어 하나로만 재사용하게 함 - 필요할 때 스프링이 주입(DI)
        IOC ioc = new IOC();

        //객체 매서드 호출
        ioc.func1();
    }

}
