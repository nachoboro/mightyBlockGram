package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.data_sources.MySqlDataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.models.Account;
import com.mightyblockgram.mightyblockgram.repository.AccountRepository;
import com.mightyblockgram.mightyblockgram.repository.LikeRepository;
import com.mightyblockgram.mightyblockgram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository = new PostRepository(new MySqlDataSourceFactory());
    private AccountRepository accountRepository = new AccountRepository(new MySqlDataSourceFactory());

    protected SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

    public List<PostDto> getPosts(int offset, int limit){
        List<PostDto> postList = postRepository.getAllPosts();
        if (postList.size() > 0){
            return postList.stream().skip(offset).limit(limit).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public PostDto savePost(MultipartFile image, String description, String username) {
        Account account = accountRepository.getAccountByName(username);
        String currentDirectory = System.getProperty("user.dir");
        String formattedDate = formatter.format(new Date());
        String filePath = String.format("%s/%s/%s_%s", currentDirectory, "images", account.getAccountId(), formattedDate);
        File fileToSave = new File(filePath);
        PostDto createdPost = postRepository.savePost(filePath, account.getAccountId(), description, formattedDate);
        try {
            image.transferTo(fileToSave);
        } catch (IOException e){
            e.printStackTrace();
        }
        return createdPost;
    }

    public PostDto getPost(int postId) {
        return postRepository.getPostById(postId);
    }
}
