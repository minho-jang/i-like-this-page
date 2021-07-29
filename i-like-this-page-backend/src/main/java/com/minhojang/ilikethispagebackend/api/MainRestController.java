package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.configures.LikeRequest;
import com.minhojang.ilikethispagebackend.services.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
public class MainRestController {

  @Autowired
  LikeService likeService;

  @GetMapping("/like")
  public ApiResult<UserLikeVo> getLike(LikeRequest param) {
    return ApiResult.success(
            likeService.getLike(param.getUrl(), param.getClientIp())
    );
  }

  @PostMapping("/like")
  public ApiResult<UserLikeVo> setLike(LikeRequest param) {
    return ApiResult.success(
            likeService.saveLike(param.getUrl(), param.getClientIp())
    );
  }
}