package org.example.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

// 스프링 부트는 컨트롤러의 @Controller 어노테이션을 보고 반환하는 값의 이름을 가진 뷰의 파일을 찾음
@Controller
public class ExampleController {

    // Model 객체는 뷰, 즉 HTML 쪽으로 값을 넘겨주는 객체
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));
        
        model.addAttribute("person", examplePerson);    // Person 객체에 저장
        model.addAttribute("today", LocalDate.now());
        
        return "example";   // example.html라는 뷰 조회
    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
