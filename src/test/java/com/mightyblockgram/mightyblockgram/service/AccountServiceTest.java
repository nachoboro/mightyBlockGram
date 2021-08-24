package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.models.Account;
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
        Account account = new Account(2, "Nacho", "HashedPass");
        doReturn(account).when(accountRepository).getAccountByName("Nacho");
        UserDetails result = accountService.loadUserByUsername("Nacho");

        User expectedUser = new User(account.getUsername(), account.getPassword(), new ArrayList<>());

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
    public void whenGettingAccountByIdThenPostRepositoryShouldBeCalledCorrectly(){
        Account account = new Account(2, "test", "test");
        doReturn(account).when(accountRepository).getAccountById(anyInt());
        AccountDto result = accountService.getAccountById(2);
        verify(accountRepository, times(1)).getAccountById(eq(2));
        assertEquals("test", result.getUsername());
        assertEquals("test", result.getPassword());
    }

    @Test
    public void whenGettingAccountByNameThenPostRepositoryShouldBeCalledCorrectly(){
        Account account = new Account(2, "test", "test");
        doReturn(account).when(accountRepository).getAccountByName(anyString());
        AccountDto result = accountService.getAccountByName("test");
        verify(accountRepository, times(1)).getAccountByName(eq("test"));
        assertEquals("test", result.getUsername());
        assertEquals("test", result.getPassword());
    }

}
