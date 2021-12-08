package link.iltp.unit.datajpatest;

import link.iltp.entity.Like;
import link.iltp.repository.LikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class LikeRepositoryTest {
	private final String NORMAL_URL = "localhost/test/1/";
	private final String NORMAL_UUID = "00000000-0000-0000-0000-000000000000";

	@Autowired
	private LikeRepository likeRepository;

	@Test
	@DisplayName("존재하는 URL에 대한 countByUrl(url) 테스트")
	public void countByExistedUrl() {
		// given
		final String url = NORMAL_URL;

		// when
		Long count = likeRepository.countByUrl(url);

		// then
		assertThat(count, is(equalTo(3L)));
	}

	@Test
	@DisplayName("존재하는 URL, UUID일 때, findByUrlAndUuid(url, uuid) 테스트")
	public void findByExistedUrlAndExistedUuid() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;

		// when
		List<Like> likeList = likeRepository.findByUrlAndUuid(url, uuid);

		// then
		assertThat(likeList, hasSize(1));

		Like like = likeList.get(0);
		assertThat(like.getUuid(), is(equalTo(uuid)));
		assertThat(like.getUrl(), is(equalTo(url)));
	}
}
