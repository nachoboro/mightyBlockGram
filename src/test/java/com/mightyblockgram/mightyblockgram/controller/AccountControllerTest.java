package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.LoginResponseDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.util.JwtUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountService accountService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @SneakyThrows
    @Test
    public void whenLoggingInThrowsBadCredentialsExceptionThenExceptionShouldBeThrown(){
        AccountDto loginAccountDto = new AccountDto("user", "pass");
        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any());
        assertThrows(Exception.class, () -> accountController.login(loginAccountDto));
    }

    @SneakyThrows
    @Test
    public void whenLoggingInCorrectlyThenJwtShouldBeReturned(){
        AccountDto loginAccountDto = new AccountDto("user", "pass");
        User userDetails = new User("user", "pass", new ArrayList<>());
        doReturn(userDetails).when(accountService).loadUserByUsername(eq("user"));
        doReturn("token").when(jwtUtil).generateToken(userDetails);
        ResponseEntity result = accountController.login(loginAccountDto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("token", ((LoginResponseDto) result.getBody()).getToken());
    }

    @Test
    public void whenCreatingAccountWithInvalidUserThenBadRequestShouldBeReturned(){
        AccountDto loginAccountDto = new AccountDto("", "pass");
        ResponseEntity result = accountController.createAccount(loginAccountDto);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingAccountWithInvalidPassThenBadRequestShouldBeReturned(){
        AccountDto loginAccountDto = new AccountDto("user", "");
        ResponseEntity result = accountController.createAccount(loginAccountDto);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingAccountWithNullUserThenBadRequestShouldBeReturned(){
        AccountDto loginAccountDto = new AccountDto(null, "pass");
        ResponseEntity result = accountController.createAccount(loginAccountDto);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingAccountWithNullPassThenBadRequestShouldBeReturned(){
        AccountDto loginAccountDto = new AccountDto("user", null);
        ResponseEntity result = accountController.createAccount(loginAccountDto);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingUserSuccessfullyThenLoginMessageShouldBeReturned(){
        AccountDto loginAccountDto = new AccountDto("user", "pass");
        ResponseEntity result = accountController.createAccount(loginAccountDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void whenCreatingUserThrowsExceptionThenInternalServerErrorShouldBeReturned(){
        AccountDto loginAccountDto = null;
        ResponseEntity result = accountController.createAccount(loginAccountDto);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
