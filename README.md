# 🗨 간단한 블로그 with Spring Boot 3

Spring Boot 3을 활용한 간단한 블로그 프로젝트입니다.<br>
자체 회원가입 & 로그인 기능과 OAuth 2.0(JWT)을 이용한 Google 소셜 로그인 기능을 제공합니다.<br>
블로그 글 작성, 수정, 삭제가 가능하며, 보안은 Spring Security를 활용하였습니다.  
<br><br>
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
