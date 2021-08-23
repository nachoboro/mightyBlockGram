package com.mightyblockgram.mightyblockgram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDto {

    private String description;
    @JsonProperty("image_path")
    private String imagePath;
    @JsonProperty("upload_date")
    private String uploadDate;
    @JsonProperty("account_id")
    private int accountId;
    private int likes;

    public PostDto(String description, String imagePath, String uploadDate, int accountId){
        this.description = description;
        this.imagePath = imagePath;
        this.uploadDate = uploadDate;
        this.accountId = accountId;
    }
}
