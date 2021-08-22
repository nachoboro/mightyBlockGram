package com.mightyblockgram.mightyblockgram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LikeDto {
    @JsonProperty("post_id")
    public int postId;
    @JsonProperty("account_id")
    public int account_id;
    public boolean active;

}
