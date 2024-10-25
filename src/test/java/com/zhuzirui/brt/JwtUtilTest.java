package com.zhuzirui.brt;

import com.zhuzirui.brt.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testJwt() {
        String token = jwtUtil.generateToken(213L);
        Claims claims = jwtUtil.extractAllClaims(token);
        System.out.println(claims);
    }
}
