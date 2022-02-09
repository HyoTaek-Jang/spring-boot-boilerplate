package com.example.jwt.controller;

import com.example.jwt.domain.Auth;
import com.example.jwt.dto.LoginRequest;
import com.example.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @Transactional
    @PostMapping("/login")
    public String loginWithEmail(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getEmail().equals("test") && loginRequest.getPwd().equals("123")) {
            // login 성공
            // 1분짜리 Acess
            String accessToken = authService.createToken(loginRequest.getEmail(), (60L * 1000));
            String refreshToken = authService.createToken(loginRequest.getEmail(), (30L * 24 * 60 * 60 * 1000));

            Auth auth = authService.createAuth(accessToken, refreshToken, loginRequest.getEmail());

            authService.deleteAuthByEmail(loginRequest.getEmail());
            authService.saveAuth(auth);


            return "로그인 성공 토큰 : " + accessToken;
        } else {
            // login 실패
            return "로그인 실패";

        }
    }

    @GetMapping("/test")
    public String test() {
        return "토큰 확인 완료";
    }

    @GetMapping
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String loginpage() {
        return "loginpage";
    }

    @GetMapping("/a")
    public String a() {
        return "aaaaaa";
    }
}
