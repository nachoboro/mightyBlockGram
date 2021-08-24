package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.data_sources.H2DataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LikeRepositoryTest {

    private LikeRepository likeRepository = new LikeRepository(new H2DataSourceFactory());

    @Test
    public void whenGettingLikeThenLikeDtoShouldBeReturned(){
        LikeDto result = likeRepository.getLike(2,2);
        assertNotNull(result);
    }

    @Test
    public void whenGettingNonExistentLikeThenLikeDtoShouldBeReturned(){
        LikeDto result = likeRepository.getLike(27,27);
        assertNull(result);
    }

    @Test
    public void whenSavingLikeThenLikeShouldBeInsertedInDB(){
        likeRepository.saveLike(4, 7);
        LikeDto newLike = likeRepository.getLike(4,7);
        assertTrue(newLike.isActive());
        assertEquals(4, newLike.getAccountId());
        assertEquals(7, newLike.getPostId());
    }

    @Test
    public void whenSavingLikeThrowsExceptionThenNoResultsShouldBeInserted(){
        LikeRepository likeRepositoryMock = new LikeRepository(null);
        likeRepositoryMock.saveLike(4, 7);
        LikeDto newLike = likeRepositoryMock.getLike(4,7);
        assertNull(newLike);
    }

    @Test
    public void whenLikingPostThenActiveShouldBeSetToTrue(){
        likeRepository.likePost(4, 8);
        LikeDto newLike = likeRepository.getLike(4,8);
        assertTrue(newLike.isActive());
    }

    @Test
    public void whenLikingPostThrowsExceptionThenNoResultsShouldBeUpdated(){
        LikeRepository likeRepositoryMock = new LikeRepository(null);
        likeRepositoryMock.likePost(4, 8);
        LikeDto newLike = likeRepository.getLike(4,8);
        assertFalse(newLike.isActive());
    }

    @Test
    public void whenUnlikingPostThenActiveShouldBeSetToFalse(){
        likeRepository.unlikePost(2, 2);
        LikeDto newLike = likeRepository.getLike(2,2);
        assertFalse(newLike.isActive());
    }

    @Test
    public void whenUnlikingPostThrowsExceptionThenNoResultsShouldBeUpdated(){
        LikeRepository likeRepositoryMock = new LikeRepository(null);
        likeRepositoryMock.unlikePost(2, 2);
        LikeDto newLike = likeRepository.getLike(2,2);
        assertTrue(newLike.isActive());
    }
}
