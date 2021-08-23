package com.mightyblockgram.mightyblockgram.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenValidatingCorrectTokenThenTrueShouldBeReturned(){
        Boolean result = jwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciJ9.qJJ5nQJ-XEB94daB24Jqa2lZ20_nIwqEk9VWT5i3dSs", new User("testuser", "test", new ArrayList<>()));
        assertEquals(true, result);
    }

    @Test
    public void whenValidatingIncorrectTokenThenFalseShouldBeReturned(){
        Boolean result = jwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciJ9.qJJ5nQJ-XEB94daB24Jqa2lZ20_nIwqEk9VWT5i3dSs", new User("test", "test", new ArrayList<>()));
        assertEquals(false, result);
    }

    @Test
    public void whenCreatingTokenThenCorrectTokenShouldBeReturned(){
        String result = jwtUtil.generateToken(new User("test", "test", new ArrayList<>()));
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0In0.ZBrzN9QyT95aMeNOESFZyZmdzb3Pv-VPcwcf1zLc4gM", result);
    }

}
