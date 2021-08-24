package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.data_sources.MySqlDataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.models.Account;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService implements UserDetailsService {

    private AccountRepository accountRepository = new AccountRepository(new MySqlDataSourceFactory());

    public AccountDto getAccountById(Integer accountId){
        Account account =  accountRepository.getAccountById(accountId);
        return new AccountDto(account.getUsername(), account.getPassword());
    }

    public AccountDto getAccountByName(String username){
        Account account =  accountRepository.getAccountByName(username);
        AccountDto accountDto = null;
        if (account != null){
            accountDto = new AccountDto(account.getUsername(), account.getPassword());
        }
        return accountDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByName(userName);
        String username = account.getUsername();
        String pass = account.getPassword();
        return new User(username, pass, new ArrayList<>());
    }

    public void createUser(String username, String password) {
        String hashedPass = new BCryptPasswordEncoder().encode(password);
        accountRepository.saveUser(username, hashedPass);
    }
}
