package com.example.my_api_server;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러로 저장하겠다는 뜻
@RequestMapping("/test") //api 서버 주소
@RequiredArgsConstructor //DI를 자동으로 해주는 어노테이션
public class IOC_TEST {

    //final: 불변성 객체를 변경할 수 없음
    private final IOC ioc; //개발자가 만든게 아닌 스프링이 객체(bean)를 주입해줌(DI) -> IOC임
    //1. 필드주입(잘안씀)
    @Autowired
    private IOC ioc2;

    //2. Setter(수정자) 주입 방식 (잘안씀)
    public IOC setIoc(IOC ioc) {
        ioc2 = ioc;
        return ioc2;
    }

    //3.생성자 주입방식(생성할 때 자동으로 주입받음) - 주로 많이 사용
    //생성자에 @Autowired 어노테이션을 사용하여 DI를 받음
    public void IOC(IOC ioc) {
        ioc2 = ioc;
    }

    @GetMapping
    public void iocTest() {
        ioc.func1();
    }

}
