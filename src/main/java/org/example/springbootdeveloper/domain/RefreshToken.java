package org.example.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 리프레시 토큰은 데이터베이스에 저장하는 정보이므로 Entity와 Repository를 생성해야 함.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false)
        private Long id;

        @Column(name = "user_id", nullable = false, unique = true)
        private Long userId;

        @Column(name = "refresh_token", nullable = false)
        private String refreshToken;

    public RefreshToken(Long userId, String refreshToken) {
            this.userId = userId;
            this.refreshToken = refreshToken;
        }

        public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        /*  this : 리턴 값을 가지고 다른 행위를 더 수행하기 위해 자기 자신의 참조값을 리턴시킴
            player.setName().getName() 이렇게 사용이 가능함
        */
        return this;
    }

}
