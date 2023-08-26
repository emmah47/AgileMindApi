package com.javabean.agilemind.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface TokenProvider {

    String generate(Authentication authentication);

    Optional<Jws<Claims>> validateTokenAndGetJws(String token);
}
