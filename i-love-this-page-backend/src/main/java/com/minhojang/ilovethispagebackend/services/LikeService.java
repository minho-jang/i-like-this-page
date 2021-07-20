package com.minhojang.ilovethispagebackend.services;

import com.minhojang.ilovethispagebackend.models.Like;
import com.minhojang.ilovethispagebackend.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
  @Autowired
  private LikeRepository likeRepository;

  public Like save(Like like) {
    likeRepository.save(like);
    return like;
  }
}
