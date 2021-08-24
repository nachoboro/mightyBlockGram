package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.models.Account;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import com.mightyblockgram.mightyblockgram.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenGettingLikeThenLikeRepositoryShouldBeCalledCorrectly(){
        Account expectedAccount = new Account(2, "test", "test");
        doReturn(expectedAccount).when(accountRepository).getAccountByName("test");
        likeService.getLike("test",3);
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

    @Test
    public void whenCreatingLikeThenLikeRepositoryShouldBeCalledCorrectly(){
        Account expectedAccount = new Account(2, "test", "test");
        doReturn(expectedAccount).when(accountRepository).getAccountByName("test");
        likeService.createLike("test",3);
        verify(likeRepository, times(1)).saveLike(eq(2),eq(3));
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

    @Test
    public void whenLikingPostThenLikeRepositoryShouldBeCalledCorrectly(){
        Account expectedAccount = new Account(2, "test", "test");
        doReturn(expectedAccount).when(accountRepository).getAccountByName("test");
        likeService.likePost("test",3);
        verify(likeRepository, times(1)).likePost(eq(2),eq(3));
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

    @Test
    public void whenUnlikingPostThenLikeRepositoryShouldBeCalledCorrectly(){
        Account expectedAccount = new Account(2, "test", "test");
        doReturn(expectedAccount).when(accountRepository).getAccountByName("test");
        likeService.unlikePost("test",3);
        verify(likeRepository, times(1)).unlikePost(eq(2),eq(3));
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

}
