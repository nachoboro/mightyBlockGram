package com.mightyblockgram.mightyblockgram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LikeDto {
    @JsonProperty("account_id")
    private int accountId;
    @JsonProperty("post_id")
    private int postId;
    private boolean active;

}
