package org.example.springbootdeveloper.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 접근 제어자가 protected가 기본 생성자를 생성
@Entity
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 'title'이라는 not null 컬럼과 매핑
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate    // 엔티티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate   // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 빌더 패턴으로 객체 생성
    // 롬복에서 지원하는 어노테이션
    // 생성자 위에 입력하면 빌더 패턴 방식으로 객체 생성이 가능하여 편리
    // 빌더 패턴을 사용하면 객체를 유연하고 직관적으로 생성할 수 있어 애용하는 디자인 패턴
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /* 롬복 어노테이션을 사용하여 기본 생성자와, 게터 메서드를 쓰지 않아도 됨
    // 기본 생성자
    protected Article() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
     */
}
