package src.main.java.com.minhojang.ilikethispagebackend.repositories;

import com.minhojang.ilikethispagebackend.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
