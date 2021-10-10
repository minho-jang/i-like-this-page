package link.iltp;

import link.iltp.common.util.StringUtils;
import link.iltp.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenService 테스트")
@SpringBootTest
public class TokenServiceTest {

	@Autowired
	TokenService tokenService;

	@DisplayName("새로운 토큰 생성한다")
	@Test
	public void generateNewToken() {
		String newToken = tokenService.generateNewToken();

		assertNotNull(newToken);
	}

	@DisplayName("토큰 생성 후 UUID를 확인한다")
	@Test
	public void generateNewTokenAndVerify() {
		String newToken = tokenService.generateNewToken();
		String uuid = tokenService.getUuidFromToken(newToken);

		assertTrue(StringUtils.isNotEmpty(uuid));
	}
}
