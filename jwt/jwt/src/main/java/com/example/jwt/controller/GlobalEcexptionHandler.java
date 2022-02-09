// package com.example.jwt.controller;
//
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
//
// import java.util.Arrays;
// import java.util.LinkedHashMap;
// import java.util.Map;
//
//
// @RestControllerAdvice
// public class GlobalEcexptionHandler {
//
//     @ExceptionHandler(value = Exception.class)
//     public Object handleException(Exception e) {
//
//         Map<String, Object> res = new LinkedHashMap<>();
//         res.put("msg", e.getMessage());
//         res.put("trace", Arrays.toString(e.getStackTrace()));
//         return res;
//     }
//
// }
//
