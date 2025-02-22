package com.haifan.forum.utils;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.exception.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Slf4j
public class JWTUtil {

    private static final long expiration = 1000 * 60 * 60 * 24;

    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("haifan123asd123123qwezxcqweasdzxcasdfasdfasdf"));

    public static String getToken(Map<String,Object> claim) {

        String token = Jwts.builder()
                .setClaims(claim)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();

        return token;
    }

    public static Claims parseToken(String token) {
        if (token == null) {
            log.info("token is null");
            return null;
        }

        JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
        Claims claims = null;
        try {
            claims = build.parseClaimsJws(token).getBody();
        } catch (ApplicationException e) {
            e.printStackTrace();
            log.warn(ResultCode.FAILED_PARSE_TOKEN.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARSE_TOKEN));
        }

        return claims;
    }

    public static boolean verifyTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())) {
            log.warn(ResultCode.FAILED_TOKEN_EXPIRED.getMessage());
            return true;
        }
        return false;
    }

    public static Object getParam(Claims claims, String str) {
        Object o = claims.get(str);
        return o;
    }
}
