package com.example.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @ApiOperation(value = "name대로 나옴")
    @GetMapping("name")
    public String getName(@RequestParam @ApiParam(value = "이름") String name){
        return name;
    }

}
