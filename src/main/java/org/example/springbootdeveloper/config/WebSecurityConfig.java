//package org.example.springbootdeveloper.config;
//
//import lombok.RequiredArgsConstructor;
//import org.example.springbootdeveloper.config.jwt.TokenProvider;
//import org.example.springbootdeveloper.repository.RefreshTokenRepository;
//import org.example.springbootdeveloper.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//
//    private final UserDetailService userService;
//
//    // 기존 Security 설정
//    // 스프링 시큐리티 기능 비활성화(스프링 시큐리티의 모든 기능 사용하지 않게 설정. 인증,인가 서비스를 모든 곳에 적용하지 않음)
//    // 일반적으로 정적 리소스(이미지, HTML 파일)에 설정
//    // static 하위 경로에 있는 리소스와 h2의 데이터를 확인하는 데 사용하는 h2-console 하위 url을 대상으로 ignoring() 메소드를 사용
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    // 이 메소드에서 인증/인가 및 로그인, 로그아웃 관련 설정이 가능
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.httpBasic().and()
//                .authorizeHttpRequests()    // 특정 경로에 대한 액세스(인증, 인가) 설정
//                // permitAll() -> 누구나 접근이 가능하게 설정
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                // anyRequest() -> 위에서 설정한 url 이외에 요청에 대해서 설정
//                // authenticated() -> 별도의 인가는 필요하지 않지만 인증이 돼야 접근할 수 있다.
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()    // 폼 기반 로그인 설정
//                .loginPage("/login")    // loginPage() -> 로그인 페이지 경로를 설정
//                .defaultSuccessUrl("/articles")    // defaultSuccessUrl() -> 로그인이 완료되었을 때 이동할 경로를 설정
//                .and()
//                .logout()   // 로그아웃 설정
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)    // invalidateHttpSession() -> 로그아웃 이후에 세션을 전체 삭제할 지 여부를 설정
//                .and()
//                .csrf().disable()   // csrf 비활성화, CSRF 공격을 방지하기 위해서는 활성화하는 게 좋다.
//                .build();
//    }
//
//    // 인증 관리자 관련 설정
//    // 사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법, 예를 들어 LDAP, JDBC 기반 인증 등을 설정할 때 사용
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
//                                                       UserDetailService userDetailService) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                // 사용자 정보 서비스 설정
//                // userDetailsService() -> 사용자 정보를 가져올 서비스를 설정
//                // 이때 설정하는 서비스 클래스는 반드시 UserDetailsService를 상속받은 클래스여야 한다.
//                // passwordEncoder() -> 비밀번호를 암호화하기 위한 인코더를 설정
//                .userDetailsService(userService)
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
//
//    // 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
