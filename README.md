# 🗨 간단한 블로그 with Spring Boot 3  

첫 스프링 프로젝트를 해보는 Spring Boot 3을 활용한 간단한 블로그 프로젝트입니다.<br>
자체 회원가입 & 로그인 기능과 OAuth 2.0(JWT)을 이용한 Google 소셜 로그인 기능을 제공합니다.<br>
블로그 글 작성, 수정, 삭제가 가능하며, 보안은 Spring Security를 활용하였습니다. 

---

⚙️ 기술 스택

<strong>Backend</strong>

<li>Spring Boot 3</li>
<li>Spring Data Jpa</li>
<li>Spring Security</li>
<li>OAuth 2.0, JWT</li>
<li>JAVA</li>  
<br>

Frontend  

<li>Thymeleaf</li>
<li>HTML/CSS</li>
<li>JavaScript</li>
<li>BootStrap</li>  
<br>

개발 환경
<li>H2 DataBase</li>  
<li>IntelliJ IDEA</li>

---

✨ 주요 기능
<br><br>

💭 사용자 인증 & 보안

1. Spring Security를 이용한 자체 회원가입 및 로그인  
2. OAuth 2.0을 이용한 Google 소셜 로그인 (JWT 인증)

<br>

💭 블로그 기능

1. 블로그 글 작성, 수정, 삭제  
2. 게시글 리스트 및 상세보기


---

<strong>서버 아키텍처</strong>

![image](https://github.com/user-attachments/assets/3900c1c5-0c5a-48cc-ad45-b574aa6444b6)

<br><br>

📦 SpringBoot3-NoticeBoard  
 ┣ 📂 src  
 ┃ ┣ 📂 main  
 ┃ ┃ ┣ 📂 java.com.example.springbootdeveloper  
 ┃ ┃ ┃ ┣ 📂 config    # 보안 및 설정 파일  
 ┃ ┃ ┃ ┣ 📂 controller # 컨트롤러 계층  
 ┃ ┃ ┃ ┣ 📂 entity    # 엔티티 및 모델  
 ┃ ┃ ┃ ┣ 📂 dto       # 데이터 전송 객체  
 ┃ ┃ ┃ ┣ 📂 repository # 데이터 액세스 계층  
 ┃ ┃ ┃ ┣ 📂 service   # 비즈니스 로직  
 ┃ ┃ ┃ ┣ 📂 util    # 쿠키 설정  
 ┃ ┃ ┣ 📂 resources  
 ┃ ┃ ┃ ┣ 📂 static   # 정적 리소스 (CSS, JS)  
 ┃ ┃ ┃ ┣ 📂 templates # Thymeleaf 템플릿  
 ┣ 📜 build.gradle  
 ┣ 📜 README.md  

 ---
 <br>

# Login
<img width="870" alt="image" src="https://github.com/user-attachments/assets/53c80f38-b28e-4a2e-827b-1dc1ce6882de" />  
<br>
(로그인 화면)  
<br><br>

# 메인
 <img width="1899" alt="image" src="https://github.com/user-attachments/assets/caa353b6-4a9a-4db1-8c04-fe1ec3bf5fe7" />  
 (메인 화면)  
<br><br>

# 글 상세 조회
<img width="1306" alt="image" src="https://github.com/user-attachments/assets/422045f8-88ae-4415-9d50-74e4514757fd" />
(게시글 상세 조회)

---  
# 📝 간단한 블로그 만들기 프로젝트를 하면서 배운 점  

스프링을 처음 접해보고 스프링 프레임워크와 스프링 부트의 기본적인 기능들을 사용해 봤습니다.  
<br>


📌 1. Spring Security를 활용한 회원가입 및 로그인 기능  
<ol>Spring Security를 이용하여 자체 회원가입 및 로그인 기능을 구현하였습니다.</ol>
<ol>비밀번호 암호화를 위해 BCryptPasswordEncoder를 적용하여 보안성을 강화하였습니다.</ol>
<ol>인증 및 인가의 개념에 대해 알게 되고 이해할 수 있었으며, 이를 실제 프로젝트에 적용하는 경험을 쌓을 수 있었습니다.</ol>
 
 <br>
 
📌 2. OAuth 2.0 + JWT를 활용한 Google 소셜 로그인  
<ol>OAuth 2.0을 활용하여 Google 소셜 로그인 기능을 구현하였습니다.</ol>
<ol>로그인 후 발급된 **JWT(Json Web Token)**를 이용하여 사용자 인증을 처리하고, 보안성을 유지하는 방법을 배웠습니다.</ol>
<ol>Access Token과 Refresh Token의 개념을 학습하면서, 토큰 기반 인증 시스템의 작동 원리를 보다 명확하게 이해할 수 있었습니다.</ol>

 <br>
 
📌 3. 블로그 CRUD 기능 구현
<ol>블로그 글을 작성(Create), 수정(Update), 삭제(Delete), 조회(Read) 하는 기능을 개발하였습니다.</ol>
<ol>@Entity, @Repository, @Service, @Controller 등의 개념을 활용하여 Spring의 계층 구조를 직접 설계하고 적용할 수 있었습니다.</ol>
<ol>CSR 방식인 Thymeleaf를 활용하여 동적인 웹 페이지를 렌더링하는 방식을 익히고, 데이터와 UI를 효과적으로 연결하는 방법을 배웠습니다.</ol><br>

🎯 프로젝트를 진행하며 느낀 점  
<br>
Spring Boot와 Spring Security는 복잡하지만 보안성에 있어서는 좋은 제공하지만, 기본 개념을 충분히 이해하고 적용하는 것이 중요하다는 점을 깨달았습니다.    
OAuth 2.0 및 JWT 기반 인증 방식을 직접 구현하면서, 보안과 관련된 다양한 고려 사항을 학습할 수 있었습니다.    
블로그를 직접 만들어 보면서 스프링 웹 애플리케이션이 동작하는 전체적인 흐름을 이해할 수 있었습니다.
비록 스프링에 있어서 간단한 기능들만을 경험해 보았지만, 이 프로젝트를 진행하면서 Spring Boot를 활용한 웹 애플리케이션 개발과 관련하여 다양한 개념을 직접 적용해 볼 수 있었습니다.



