package com.mightyblockgram.mightyblockgram.service;

import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public LikeDto getLike(int accountId, int postId) {
        return likeRepository.getLike(accountId, postId);
    }

    public LikeDto createLike(int accountId, int postId) {
        return likeRepository.createLike(accountId, postId);
    }

    public LikeDto updateLike(int accountId, int postId) {
        likeRepository.updateLike(accountId, postId);
        return likeRepository.getLike(accountId, postId);
    }
}
