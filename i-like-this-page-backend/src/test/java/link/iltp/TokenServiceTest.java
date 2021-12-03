package link.iltp;

import com.google.common.base.Strings;
import link.iltp.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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

		assertThat(Strings.isNullOrEmpty(uuid), is(equalTo(false)));
	}
}