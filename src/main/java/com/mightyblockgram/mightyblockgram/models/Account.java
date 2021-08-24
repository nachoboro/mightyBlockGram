package com.mightyblockgram.mightyblockgram.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Account {
    private Integer accountId;
    private String username;
    private String password;
}
