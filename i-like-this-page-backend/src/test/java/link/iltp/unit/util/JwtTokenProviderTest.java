package link.iltp.unit.util;

import link.iltp.common.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JwtTokenProviderTest {
	private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	private final String NORMAL_UUID = "00000000-0000-0000-0000-000000000000";

	@BeforeEach
	public void prepare() {
		ReflectionTestUtils.setField(jwtTokenProvider, "issuer", "i-like-this-page");
		ReflectionTestUtils.setField(jwtTokenProvider, "secret", "secret");
	}

	@Test
	@DisplayName("정상적인 generateToken(uuid) 테스트")
	public void generateTokenSuccess() {
		final String token = jwtTokenProvider.generateToken(NORMAL_UUID);
		assertThat(token, is(notNullValue()));
		assertThat(token, isA(String.class));
	}

	@Test
	@DisplayName("정상적인 getUuidFromToken(token) 테스트")
	public void generateTokenAndVerifySuccess() {
		final String expectUuid = NORMAL_UUID;
		final String token = jwtTokenProvider.generateToken(expectUuid);
		final String uuid = jwtTokenProvider.getUuidFromToken(token);

		assertThat(uuid, is(expectUuid));
	}

	@Test
	@DisplayName("generateNewToken() 100번 반복해서 서로다른 UUID를 가지는지 테스트")
	public void generateTokenHasDifferentUuid() {
		Set<String> uuidList = new HashSet<>();
		int expectSize = 100;
		IntStream.range(0, expectSize).forEach((i) -> {
			final String token = jwtTokenProvider.generateNewToken();
			final String uuid = jwtTokenProvider.getUuidFromToken(token);

			assertThat(uuid, is(notNullValue()));
			assertThat(uuid, isA(String.class));
			uuidList.add(uuid);
		});

		assertThat(uuidList, hasSize(expectSize));
	}
}
