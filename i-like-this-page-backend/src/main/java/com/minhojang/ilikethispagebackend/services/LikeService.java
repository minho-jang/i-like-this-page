package com.minhojang.ilikethispagebackend.services;

import com.minhojang.ilikethispagebackend.models.Like;
import com.minhojang.ilikethispagebackend.repositories.LikeRepository;
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
