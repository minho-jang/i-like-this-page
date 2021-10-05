package link.iltp;

import link.iltp.common.util.StringUtils;
import link.iltp.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenService 테스트")
@SpringBootTest
@Slf4j
public class TokenServiceTest {

	@Autowired
	TokenService tokenService;

	@DisplayName("새로운 토큰 생성한다")
	@Test
	public void generateNewToken() {
		// given

		// when
		String newToken = tokenService.generateNewToken();

		// then
		log.info("newToken = " + newToken);
		assertNotNull(newToken);
	}

	@DisplayName("토큰 생성 후 UUID를 확인한다")
	@Test
	public void generateNewTokenAndVerify() {
		// given
		String newToken = tokenService.generateNewToken();

		// when
		String uuid = tokenService.getUuidFromToken(newToken);

		// then
		log.info("uuid = " + uuid);
		assertTrue(StringUtils.isNotEmpty(uuid));
	}
}
