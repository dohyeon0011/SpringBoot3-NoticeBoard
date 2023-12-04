package org.example.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
// @ConfigurationProperties : 자바 클래스에 프로퍼티값을 가져와서 사용하는 어노테이션
// properties , *.yml 파일에 있는 property를 자바 클래스에 값을 가져와서(바인딩) 사용할 수 있게 해주는 어노테이션
// Spring boot 에서는 운영에 필요한 설정(DB 정보, LOG설정 등등 )들을 *.properties , *.yml 에 써두고 관리한다.
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey = "fasdfdsa";
}
 