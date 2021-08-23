package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenGettingAllPostsThenCorrectResponseShouldBeReturned(){
        List<PostDto> postList = generatePostList(2);
        doReturn(postList).when(postService).getPosts(anyInt(), anyInt());
        ResponseEntity result = postController.getAllPosts(0,2);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2,((List<PostDto>) result.getBody()).size());
    }

    @Test
    public void whenCreatingPostWithInvalidAccountThenBadRequestShouldBeReturned(){
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test.jpg".getBytes());
        Integer accountId = 87;
        String description = "desc";
        doReturn(null).when(accountService).getAccount(87);
        ResponseEntity result = postController.createPost(file,accountId,description);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingAPostThenPostShouldBeReturned(){
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test.jpg".getBytes());
        Integer accountId = 87;
        String description = "desc";
        doReturn(new AccountDto("test", "test")).when(accountService).getAccount(87);
        doReturn(new PostDto("desc", "path", "2021-08-22", 87, 3)).when(postService).savePost(any(), anyInt(), anyString());
        ResponseEntity result = postController.createPost(file,accountId,description);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        PostDto bodyPostDto = (PostDto) result.getBody();
        assertEquals("desc", bodyPostDto.getDescription());
        assertEquals("path", bodyPostDto.getImagePath());
        assertEquals("2021-08-22", bodyPostDto.getUploadDate());
        assertEquals(87, bodyPostDto.getAccountId());
        assertEquals(3, bodyPostDto.getLikes());
    }

    private List<PostDto> generatePostList(int postQuantity) {
        List<PostDto> postList = new ArrayList<>();
        for(int i = 1; i <= postQuantity; i++){
            postList.add(new PostDto("description", "imagePath", "2021-08-22", 2, 3));
        }
        return postList;
    }
}
