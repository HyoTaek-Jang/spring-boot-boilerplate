package com.example.jwtKakaoSwagger.controller;

import com.example.jwtKakaoSwagger.domain.Auth;
import com.example.jwtKakaoSwagger.dto.LoginRequest;
import com.example.jwtKakaoSwagger.service.AuthService;
import com.example.jwtKakaoSwagger.util.JwtTokenUtil;
import com.example.jwtKakaoSwagger.util.KakaoAPI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    KakaoAPI kakaoAPI = new KakaoAPI();

    @ApiOperation(value = "카카오 로그인 Redirection")
    @GetMapping("/login")
    public Object login(@RequestParam String code) {
//        redirection url 여기로 코드가 넘어옴.
        if (code == null) {
            return "로그인 실패";
        }

        String kakaoToken = kakaoAPI.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(kakaoToken);

        // login 성공
        // 1분짜리 Acess
        String accessToken = authService.createToken((String) userInfo.get("nickname"), (1 * 60L* 1000));
        String refreshToken = authService.createToken((String) userInfo.get("nickname"), (30L * 24 * 60 * 60 * 1000));

        Auth auth = authService.createAuth(accessToken, refreshToken, (String) userInfo.get("nickname"));

        authService.deleteAuthByEmail((String) userInfo.get("nickname"));
        authService.saveAuth(auth);


        return accessToken;

    }

    @ApiOperation(value = "AcessToken Test API")
    @Operation(description = "헤더에 AccessToken을 넣고 해당 메서드를 실행하면 카카오 닉네임이 반환됨. ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
    })
    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        String subject = jwtTokenUtil.getSubject(request);

        return subject;
    }


}
