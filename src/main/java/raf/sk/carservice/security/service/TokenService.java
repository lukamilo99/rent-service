package raf.sk.carservice.security.service;

import io.jsonwebtoken.Claims;

public interface TokenService {
    String generateToken(Claims claims);
    Claims parseToken(String jwt);

}
