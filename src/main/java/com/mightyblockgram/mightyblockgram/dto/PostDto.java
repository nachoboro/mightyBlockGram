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
    public String  uploadDate;
    @JsonProperty("account_id")
    public int idAccount;
    public int likes;
}
