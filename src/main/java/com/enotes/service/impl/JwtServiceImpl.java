package com.enotes.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
import com.enotes.exception.JwtTokenExpiredException;
import com.enotes.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	private String secretKey="";
	
	public JwtServiceImpl() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGenerator.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public String generateJwtToken(User user) {
		
		Map<String,Object> claims=new HashMap<>();
		claims.put("role", user.getRole());
		claims.put("status", user.getAccountStatus().isActive());
		
		String token = Jwts.builder()
				       .claims()
				       .add(claims) // to pass some extra info
				       .subject(user.getEmail())
				       .issuedAt(new Date(System.currentTimeMillis()))
				       .expiration(new Date(System.currentTimeMillis()+ 1000*60*10))
				       .and()
				       .signWith(getKey())
				       .compact();
		return token;
	}

	private Key getKey() {
	    byte[] keyByte = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyByte);
	}

	@Override
	public String extractUsername(String token) {
		Claims claim=extractAllClaim(token);
		String username = claim.getSubject();
		return username;
	}

	private Claims extractAllClaim(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(token).getPayload();
			return claims;
		} catch (ExpiredJwtException e) {
			throw new JwtTokenExpiredException("Token is expired");
		} catch (JwtException e) {
			throw new JwtTokenExpiredException("invalid jwt token");
		}
		
		catch (Exception e) {
			throw e;
		}
	}
	
	
	public String role(String token) {
		Claims claims = extractAllClaim(token);
		Object object = claims.get("role");
		return (String)object
;	}

	private SecretKey decryptKey(String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		boolean isExpire=isTokenExpired(token);
		if(username.equalsIgnoreCase(userDetails.getUsername()) && !isExpire) {
			return true;
		}
			
		return false;
	}

	private boolean isTokenExpired(String token) {
		Claims claims = extractAllClaim(token);
		Date expiration = claims.getExpiration();
		
		// today date=16  expire=17th
		return expiration.before(new Date()); //17 before 16 no
	}
	
	

}
