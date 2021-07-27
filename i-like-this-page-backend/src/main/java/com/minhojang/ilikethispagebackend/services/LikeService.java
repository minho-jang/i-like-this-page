package com.minhojang.ilikethispagebackend.services;

import com.minhojang.ilikethispagebackend.api.UserLikeVo;
import com.minhojang.ilikethispagebackend.models.Like;
import com.minhojang.ilikethispagebackend.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {
  @Autowired
  private LikeRepository likeRepository;

  public Long countLike(String url) {
    return likeRepository.countByUrl(url);
  }

  public Boolean isLikeUrl(String url, String ipAddress) {
    // TODO: apiAddress가 ipv4인지 ipv6인지
    List<Like> result = likeRepository.findByUrlAndIpAddressV4(url, 0L/* TODO: ipAddress to ipv4 */);
    // or
     List<Like> other = likeRepository.findByUrlAndIpAddressV6(url, new byte[] {}/* TODO: ipAddress to ipv6 */);

    return result.size() > 0;
  }

  public UserLikeVo getLike(String url, String ipAddress) {
//    return new UserLikeVo(countLike(url), isLikeUrl(url, ipAddress));
    return new UserLikeVo(23, false);
  }

  @Transactional
  public UserLikeVo save(String url, String ipAddress) {
    Like saved = likeRepository.save(new Like(
            null,
            0L/* TODO: ipAddress to ipv4 */,
            new byte[] {}/* TODO: ipAddress to ipv6 */,
            url,
            null)
    );

    return new UserLikeVo(countLike(url), isLikeUrl(url, ipAddress));
  }
}
