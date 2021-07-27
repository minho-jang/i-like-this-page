package com.minhojang.ilikethispagebackend.repositories;

import com.minhojang.ilikethispagebackend.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
  long countByUrl(String url);
  List<Like> findByUrlAndIpAddressV4(String url, long ipAddressV4);
  List<Like> findByUrlAndIpAddressV6(String url, byte[] ipAddressV6);
}
