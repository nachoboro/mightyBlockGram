package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.models.Account;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import com.mightyblockgram.mightyblockgram.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenGettingAllPostsReturnsAtLeastOnePostThenTheyShouldBeCorrectlyPaged(){
        List<PostDto> postList = generatePostList(6);
        doReturn(postList).when(postRepository).getAllPosts();

        List<PostDto> result = postService.getPosts(0,4);

        assertEquals(4, result.size());
        assertEquals(postList.get(0), result.get(0));
        assertEquals(postList.get(1), result.get(1));
        assertEquals(postList.get(2), result.get(2));
        assertEquals(postList.get(3), result.get(3));
    }

    @Test
    public void whenGettingAllPostsReturnsAtLeastOnePostWithOffsetThenTheyShouldBeCorrectlyPaged(){
        List<PostDto> postList = generatePostList(15);
        doReturn(postList).when(postRepository).getAllPosts();

        List<PostDto> result = postService.getPosts(5,6);

        assertEquals(6, result.size());
        assertEquals(postList.get(5), result.get(0));
        assertEquals(postList.get(6), result.get(1));
        assertEquals(postList.get(7), result.get(2));
        assertEquals(postList.get(8), result.get(3));
        assertEquals(postList.get(9), result.get(4));
        assertEquals(postList.get(10), result.get(5));
    }

    @Test
    public void whenGettingAllPostsReturnsEmptyListThenEmptyListShouldBeReturned(){
        List<PostDto> postList = generatePostList(0);
        doReturn(postList).when(postRepository).getAllPosts();

        List<PostDto> result = postService.getPosts(0,7);

        assertEquals(0, result.size());
    }

    @Test
    public void whenSavingPostThenPostRepositoryShouldBeCalledCorrectly(){
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test.jpg".getBytes());
        Account account = new Account(2, "test", "test");
        doReturn(account).when(accountRepository).getAccountByName("test");
        postService.savePost(file, "description", "test");
        verify(postRepository, times(1)).savePost(anyString(), eq(2), eq("description"), anyString());
    }

    @Test
    public void whenGettingPostByIdThenPostRepositoryShouldBeCalledCorrectly(){
        postService.getPost(4);
        verify(postRepository, times(1)).getPostById(eq(4));
    }


    private List<PostDto> generatePostList(int postQuantity) {
        List<PostDto> postList = new ArrayList<>();
        for(int i = 1; i <= postQuantity; i++){
            postList.add(new PostDto("description", "imagePath", "2021-08-22", 2, 3));
        }
        return postList;
    }
}
