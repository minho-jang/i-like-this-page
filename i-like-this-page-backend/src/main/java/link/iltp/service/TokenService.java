package link.iltp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

	@Value("${spring.jwt.issuer}")
	private String ISSUER;

	@Value("${spring.jwt.secret}")
	private String SECRET;

	public String generateToken(String uuid) {

		LocalDateTime January1st2100 = LocalDateTime.of(2100, 1, 1, 0, 0, 0);
		Date expirationDate = Date.from(January1st2100.atZone(ZoneId.systemDefault()).toInstant());
		Date now = new Date();

		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setIssuer(ISSUER)
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.claim("uuid", uuid)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public String generateNewToken() {
		return generateToken(UUID.randomUUID().toString());
	}

	public String getUuidFromToken(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody();

		return claims.get("uuid").toString();
	}
}
