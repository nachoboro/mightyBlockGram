package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenLoadingUserByUsernameThenUserShouldBeReturned(){
        AccountDto expectedAccountDto = new AccountDto("Nacho", "HashedPass");
        doReturn(expectedAccountDto).when(accountRepository).getAccountByName("Nacho");
        UserDetails result = accountService.loadUserByUsername("Nacho");

        User expectedUser = new User(expectedAccountDto.getUsername(), expectedAccountDto.getPassword(), new ArrayList<>());

        assertEquals(expectedUser.getUsername(), result.getUsername());
        assertEquals(expectedUser.getPassword(), result.getPassword());
        assertEquals(expectedUser.getAuthorities(), result.getAuthorities());
    }

    @Test
    public void whenCreatingUserThenPostRepositoryShouldBeCalledCorrectly(){
        accountService.createUser("test", "test");
        verify(accountRepository, times(1)).saveUser(anyString(), anyString());
    }

    @Test
    public void whenGettingAccountByNameThenPostRepositoryShouldBeCalledCorrectly(){
        accountService.getAccount(2);
        verify(accountRepository, times(1)).getAccountById(eq(2));
    }

}
