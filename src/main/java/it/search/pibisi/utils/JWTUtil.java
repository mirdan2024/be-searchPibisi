package it.search.pibisi.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {

	@Value("${jwt_secret}")
	private String secret;

	Date convertToDateViaInstant(LocalDateTime dateToConvert) {
		return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	public String generateToken(String username, Long id) throws IllegalArgumentException, JWTCreationException {
		LocalDateTime ldt = LocalDateTime.now().plusDays(1);
		return JWT.create().withSubject("User Details").withClaim("username", username).withClaim("id", id)
				.withIssuedAt(new Date()).withIssuer("ANTIMONEY").withExpiresAt(convertToDateViaInstant(ldt))
				.sign(Algorithm.HMAC256(secret));
	}

	public DecodedJWT validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withSubject("User Details")
				.withIssuer("ANTIMONEY").build();
		return verifier.verify(token.replace("Bearer ", ""));
	}

	public HashMap<String, String> getInfoFromJwt(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		DecodedJWT jwtDecoded = validateTokenAndRetrieveSubject(jwt);

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("idUtente", jwtDecoded.getClaim("id").asString());
		hm.put("idIntermediario", jwtDecoded.getClaim("idIntermediario").asString());
		hm.put("accountId", jwtDecoded.getClaim("accountId").asString());
		hm.put("apiKey", jwtDecoded.getClaim("apiKey").asString());

		return hm;
	}
	
}