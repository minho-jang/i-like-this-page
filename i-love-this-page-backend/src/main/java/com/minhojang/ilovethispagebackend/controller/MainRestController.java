package com.minhojang.ilovethispagebackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class MainRestController {

  @GetMapping("/like")
  public ApiResult like() {
    return new ApiResult(null, "SUCCESS");
  }
}

// TODO 다른 코드 참조하여 ApiResult 클래스 새로 만들기
class ApiResult {
  String error;
  String data;

  public ApiResult(String error, String data) {
    this.error = error;
    this.data = data;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}