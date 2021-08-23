package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.data_sources.H2DataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountRepositoryTest {

    private AccountRepository accountRepository = new AccountRepository(new H2DataSourceFactory());

    @Test
    public void whenGettingAccountByNameThenAccountDtoShouldBeReturned(){
        AccountDto result = accountRepository.getAccountByName("Nacho");
        assertEquals("Nacho", result.getUsername());
        assertEquals("Nacho", result.getPassword());
    }

    @Test
    public void whenGettingAccountByNameThatDoesntExistThenNullAccountDtoShouldBeReturned(){
        AccountDto result = accountRepository.getAccountByName("DoesNotExist");
        assertNull(result);
    }

    @Test
    public void whenGettingAccountByNameThrowsExceptionThenNullShouldBeReturned() {
        AccountRepository accountRepositoryMock = new AccountRepository(null);
        assertNull(accountRepositoryMock.getAccountByName("Nacho"));
    }

    @Test
    public void whenGettingAccountByIdThenAccountDtoShouldBeReturned(){
        AccountDto result = accountRepository.getAccountById(2);
        assertEquals("Nacho", result.getUsername());
        assertEquals("Nacho", result.getPassword());
    }

    @Test
    public void whenGettingAccountByIdThatDoesntExistThenAccountDtoShouldBeReturned(){
        AccountDto result = accountRepository.getAccountById(75);
        assertNull(result);
    }

    @Test
    public void whenGettingAccountByIdThrowsExceptionThenNullShouldBeReturned() {
        AccountRepository accountRepositoryMock = new AccountRepository(null);
        assertNull(accountRepositoryMock.getAccountById(3));
    }

    @Test
    public void whenSavingUserThenUserShouldBeInsertedInDB(){
        accountRepository.saveUser("newUser", "hashedPass");
        AccountDto newUser = accountRepository.getAccountByName("newUser");
        assertEquals("hashedPass", newUser.getPassword());
    }

    @Test
    public void whenSavingUserThrowsExceptionThenNoResultsShouldBeInserted(){
        AccountRepository accountRepositoryMock = new AccountRepository(null);
        accountRepositoryMock.saveUser("newUser", "hashedPass");
        AccountDto newUser = accountRepository.getAccountByName("newUser");
        assertNull(newUser);
    }
}
