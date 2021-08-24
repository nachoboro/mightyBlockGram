package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.data_sources.H2DataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostRepositoryTest {

    private PostRepository postRepository = new PostRepository(new H2DataSourceFactory());

    @Test
    public void whenGettingAllPostsThenTheyShouldBeOrderedByDate(){
        List<PostDto> postList = postRepository.getAllPosts();
        assertTrue(postList.get(0).getUploadDate().compareTo(postList.get(1).getUploadDate()) >= 0);
        assertTrue(postList.get(1).getUploadDate().compareTo(postList.get(2).getUploadDate()) >= 0);
        assertTrue(postList.get(2).getUploadDate().compareTo(postList.get(3).getUploadDate()) >= 0);
        assertTrue(postList.get(3).getUploadDate().compareTo(postList.get(4).getUploadDate()) >= 0);
    }

    @Test
    public void whenGettingAllPostsThrowsExceptionThenEmptyListShouldBeReturned(){
        PostRepository postRepositoryMock = new PostRepository(null);
        List<PostDto> result = postRepositoryMock.getAllPosts();
        assertEquals(0, result.size());
    }

    @Test
    public void whenSavingPostThenPostShouldBeInsertedInDB(){
        int previousSize = postRepository.getAllPosts().size();
        postRepository.savePost("path", 4, "description", "2021-08-22");
        int actualSize = postRepository.getAllPosts().size();
        assertEquals(actualSize, previousSize+1);
    }

    @Test
    public void whenSavingPostsThrowsExceptionThenNoPostShouldBeInserted(){
        PostRepository postRepositoryMock = new PostRepository(null);
        postRepositoryMock.savePost("path", 4, "description", "2021-08-22");
        int actualSize = postRepository.getAllPosts().size();
        assertEquals(9, actualSize);
    }

    @Test
    public void whenGettingPostByIdThenCorrectPostShouldBeReturned(){
        PostDto post = postRepository.getPostById(4);
        assertEquals("desc2", post.getDescription());
        assertEquals("/images/test2", post.getImagePath());
        assertEquals(2, post.getAccountId());
        assertEquals("2021-08-21 00:00:00", post.getUploadDate());
    }

    @Test
    public void whenGettingPostByIdThrowsExceptionThenNullShouldBeReturned() {
        PostRepository postRepositoryMock = new PostRepository(null);
        PostDto post = postRepositoryMock.getPostById(4);
        assertNull(post);
    }

}
