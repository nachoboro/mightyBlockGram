package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.data_sources.MySqlDataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.models.Account;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import com.mightyblockgram.mightyblockgram.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private LikeRepository likeRepository = new LikeRepository(new MySqlDataSourceFactory());
    private AccountRepository accountRepository = new AccountRepository(new MySqlDataSourceFactory());


    public LikeDto getLike(String username, int postId) {
        Account account = accountRepository.getAccountByName(username);
        return likeRepository.getLike(account.getAccountId(), postId);
    }

    public LikeDto createLike(String username, int postId) {
        Account account = accountRepository.getAccountByName(username);
        likeRepository.saveLike(account.getAccountId(), postId);
        return likeRepository.getLike(account.getAccountId(), postId);
    }

    public LikeDto likePost(String username, int postId) {
        Account account = accountRepository.getAccountByName(username);
        likeRepository.likePost(account.getAccountId(), postId);
        return likeRepository.getLike(account.getAccountId(), postId);
    }

    public LikeDto unlikePost(String username, int postId) {
        Account account = accountRepository.getAccountByName(username);
        likeRepository.unlikePost(account.getAccountId(), postId);
        return likeRepository.getLike(account.getAccountId(), postId);
    }
}
