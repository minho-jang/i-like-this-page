package com.minhojang.ilovethispagebackend.repositories;

import com.minhojang.ilovethispagebackend.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
