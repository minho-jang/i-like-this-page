package link.iltp.unit.entitytest;

import link.iltp.entity.Like;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LikeTest {
	@Test
	@DisplayName("Like 엔티티 동등성, 동일성 테스트")
	public void hashcodeTest() {
		// given
		final Long tmpLikeId = 1L;
		final Long tmpAnotherLikeId = 2L;
		final String tmpUuid = "TEMP-UUID";
		final String tmpUrl = "TEMP-URL";
		final LocalDateTime tmpCreateAt = LocalDateTime.now();

		// when
		Like like1 = new Like(tmpLikeId, tmpUuid, tmpUrl, tmpCreateAt);
		Like like2 = new Like(tmpLikeId, tmpUuid, tmpUrl, tmpCreateAt);
		Like like3 = new Like(tmpAnotherLikeId, tmpUuid, tmpUrl, tmpCreateAt);

		// then
		assertThat(like1.hashCode() == like2.hashCode(), is(true));
		assertThat(like1.hashCode() == like3.hashCode(), is(false));
		assertThat(like1.equals(like2), is(true));
		assertThat(like1.equals(like3), is(false));
	}
}
