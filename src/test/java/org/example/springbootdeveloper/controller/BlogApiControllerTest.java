package org.example.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootdeveloper.entity.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.dto.UpdateArticleRequest;
import org.example.springbootdeveloper.entity.User;
import org.example.springbootdeveloper.repository.BlogRepository;
import org.example.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest             // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc       // MockMvc 생성
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    // 자바 객체를 JSON데이터로 직렬화, 반대로 JSON데이터를 자바에서 사용하기 위해 자바 객체로 변환하는 역직렬화
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach // 테스트 실행 전 매번 실행하는 메소드
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    // OAuth2 구현 후 사용자 확인 인증을 위한 로직
    // 인증 객체를 저장하는 시큐리티 콘텍스트에 setAuthentication() 메서드를 통해 테스트 유저를 저장
    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    // 블로그 글 생성하는 api 구현
    @DisplayName("addArticle: 블로그 글 추가에 성공")
    @Test
    public void addArticle() throws Exception {
        // given(테스트 준비)
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // -OAuth2
        // 글을 생성하는 API에서는 파라미터로 Principal 객체를 받고 있는데
        // 이 객체에 테스트 유저가 들어가도록 모킹함
        // Principal 객체를 모킹해서 getName() 메서드를 호출하면 userName이라는 값을 반환
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when(테스트 실행)
        // 설정한 내용을 바탕으로 요청 전송
        // HTTP 메소드, URL, 요청 본문, 요청 타입 등 설정 후 테스트 요청을 보냄
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)   // -OAuth2
                .content(requestBody));

        // then(테스트 결과 반환)
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        // AssertJ 검증문 사용
        assertThat(articles.size()).isEqualTo(1);   // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공")
    @Test
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        Article savedArticle = createDefaultArticle();   // -OAuth2
//        final String title = "title";
//        final String content = "content";

//        blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                // - OAuth2
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()))
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()));
//                .andExpect(jsonPath("$[0].title").value(title))
//                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("findArticle: 블로그 특정 글 조회 성공")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();
//        final String title = "title";
//        final String content = "content";

//        Article savedArticle = blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                // - OAuth2
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()))
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()));
//                .andExpect(jsonPath("$.title").value(title))
//                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제 성공")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();
//        final String title = "title";
//        final String content = "content";

//        Article savedArticle = blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then(해당 글이 삭제가 되었는지 List형의 articles 객체를 생성하여 글 목록을 불러와
        // AssertJ 검증문의 isEmpty() 메소드를 통해 확인)
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정 성공")
    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();
//        final String title = "title";
//        final String content = "content";

//        Article savedArticle = blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
                .title("title")
                .author("author")
                .content("content")
                .build());
    }
}