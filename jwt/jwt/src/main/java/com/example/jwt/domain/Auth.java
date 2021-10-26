package com.example.jwt.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;
    private String accessToken;

    private String email;


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
