package com.minhojang.ilikethispagebackend.repository;

import com.minhojang.ilikethispagebackend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	long countByUrl(String url);

	List<Like> findByUrlAndIpAddress(String url, String ipAddress);

	void deleteByUrlAndIpAddress(String url, String ipAddress);

}
