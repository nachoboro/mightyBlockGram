package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.dto.PostDto;
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

    @Autowired
    PostRepository postRepository;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

    public List<PostDto> getPosts(int offset, int limit){
        List<PostDto> postList = postRepository.getAllPosts();
        if (postList != null){
            return postList.stream().skip(offset).limit(limit).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public PostDto savePost(MultipartFile image, int accountId, String description) {
        String currentDirectory = System.getProperty("user.dir");
        String formattedDate = formatter.format(new Date());
        String filePath = String.format("%s/%s/%s_%s", currentDirectory, "images", accountId, formattedDate);
        File fileToSave = new File(filePath);
        PostDto createdPost = postRepository.savePost(filePath, accountId, description, formattedDate);
        if (!fileToSave.exists()){
            new File(filePath).mkdir();
        }
        try {
            image.transferTo(fileToSave);
        } catch (IOException e){
            e.printStackTrace();
        }
        return createdPost;
    }
}
