package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.LoginResponseDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountDto accountDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountDto.getUsername(), accountDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = accountService.loadUserByUsername(accountDto.getUsername());
        final String jsonWebToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(jsonWebToken));
    }

    @PostMapping("accounts")
    public ResponseEntity createAccount(@RequestBody AccountDto accountDto) {
        try {
            String username = accountDto.getUsername();
            String password = accountDto.getPassword();
            if (username == null || username.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username cannot be null or empty");
            }
            if (password == null || password.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be null or empty");
            }
            AccountDto existingAccount = accountService.getAccountByName(username);
            if (existingAccount != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
            }
            accountService.createUser(username, password);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully, please login");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(String.format("An error ocurred while creating user: %s", e));
        }

    }
}
