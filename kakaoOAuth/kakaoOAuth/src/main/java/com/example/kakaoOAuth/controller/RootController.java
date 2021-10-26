package com.example.kakaoOAuth.controller;

import com.example.kakaoOAuth.util.KakaoAPI;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class RootController {

    KakaoAPI kakaoAPI = new KakaoAPI();

    @RequestMapping("/login")
    public Object login(@RequestParam String code) {
//        redirection url 여기로 코드가 넘어옴.
        if (code == null) {
            return "로그인 실패";
        }

        String accessToken = kakaoAPI.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(accessToken);


        return userInfo;
    }
}
