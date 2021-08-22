package com.mightyblockgram.mightyblockgram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    public String description;
    @JsonProperty("image_path")
    public String imagePath;
    @JsonProperty("upload_date")
    public String uploadDate;
    @JsonProperty("account_id")
    public int accountId;
    public int likes;

    public PostDto(String description, String imagePath, String uploadDate, int accountId){
        this.description = description;
        this.imagePath = imagePath;
        this.uploadDate = uploadDate;
        this.accountId = accountId;
    }
}
