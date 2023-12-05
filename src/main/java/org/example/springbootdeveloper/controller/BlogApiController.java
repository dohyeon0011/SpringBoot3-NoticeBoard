package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.entity.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.dto.ArticleResponse;
import org.example.springbootdeveloper.dto.UpdateArticleRequest;
import org.example.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor    // final이나 @NotNull이 붙은 필드의 생성자 추가
@RestController             // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {
    
    private final BlogService blogService;

    // HTTP 메소드가 POST일 때 전달받은 URL과 동일하면 메소드로 매핑
    @PostMapping("/api/articles")
    // 요청 본문 값 매핑
    // @RequestBody : HTTP를 요청할 때 응답에 해당하는 값을 @RequestBody 어노테이션이 붙은 객체인 AddArticleRequest에 매핑
    // Principal : 컨트롤러 처리기 메소드에서 자동 파라미터로 주입받을 수 있는 타입 중 하나(구현체 가장 최상위 인터페이스라 사실상 ID 정보만 가져다 사용 가능)
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        // 201 Created : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // GET요청이 들어오면 글 전체 조회 findAllArticles() 메소드 호출
    // 응답용 객체인 ArticleResponse로 파싱해 body에 담아 클라이언트에 전송
    // stream은 자바 8의 기능으로 여러 데이터가 모여있는 컬렉션을 간편하게 처리하기 위한 기능
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    // URL 경로에서 값 추출    
    // @PathVariable -> URL에서 값을 가져오는 어노테이션
    // body에 글 정보를 담아 웹 브라우저로 전송
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // /api/articles/{id} DELETE 요청이 오면 {id}에 해당하는 값이 @PathValueable 어노테이션을 통해 들어옴
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updateArticle);
    }

}
