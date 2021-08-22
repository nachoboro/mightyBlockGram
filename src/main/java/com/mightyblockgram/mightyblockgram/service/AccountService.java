package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDto getAccount(Integer accountId){
        return accountRepository.getAccountById(accountId);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AccountDto accountDto = accountRepository.getAccount(userName);
        String username = accountDto.getUsername();
        String pass = accountDto.getPassword();
        return new User(username, pass, new ArrayList<>());
    }

    public void createUser(String username, String password) {
        String hashedPass = new BCryptPasswordEncoder().encode(password);
        accountRepository.saveUser(username, hashedPass);
    }
}
