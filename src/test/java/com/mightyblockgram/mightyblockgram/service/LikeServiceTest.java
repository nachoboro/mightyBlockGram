package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import com.mightyblockgram.mightyblockgram.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenGettingLikeThenLikeRepositoryShouldBeCalledCorrectly(){
        likeService.getLike(2,3);
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

    @Test
    public void whenCreatingLikeThenLikeRepositoryShouldBeCalledCorrectly(){
        likeService.createLike(2,3);
        verify(likeRepository, times(1)).saveLike(eq(2),eq(3));
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

    @Test
    public void whenUpdatingLikeThenLikeRepositoryShouldBeCalledCorrectly(){
        likeService.updateLike(2,3);
        verify(likeRepository, times(1)).updateLike(eq(2),eq(3));
        verify(likeRepository, times(1)).getLike(eq(2),eq(3));
    }

}
