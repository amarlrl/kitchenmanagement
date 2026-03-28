package com.smartkitchen.kitchenmanagement.config;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	private String SECRET = "9AUG2001";
	
	//generate token method 
	
	public String generateToken(String username) {
		return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
		
	}

	public String extractUsername(String token) {

        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
	
	  public boolean validateToken(String token, String username) {

	        String extractedUsername = extractUsername(token);
	        return extractedUsername.equals(username);
	    }
	
	
}
