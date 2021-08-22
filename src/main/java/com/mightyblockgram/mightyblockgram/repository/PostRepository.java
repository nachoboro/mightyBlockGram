package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.dto.PostDto;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PostRepository {

    private static final String GET_ALL_POSTS_QUERY = "SELECT posts.post_id, posts.description, posts.image_path, posts.upload_date, posts.account_id, COUNT(like_id) as likes FROM posts LEFT JOIN (select like_id, post_id from likes where active = 1) as likes ON likes.post_id = posts.post_id GROUP BY post_id order by posts.upload_date desc";
    private static final String INSERT_NEW_POST_QUERY = "INSERT INTO posts (description, image_path, upload_date, account_id) values(?,?,?,?)";

    public List<PostDto> getAllPosts() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_ALL_POSTS_QUERY);

            List<PostDto> postList = new ArrayList<>();
            while (resultSet.next()) {
                System.out.println("inside result set");
                postList.add(new PostDto(resultSet.getString("description"), resultSet.getString("image_path"), resultSet.getString("upload_date"), resultSet.getInt("account_id"), resultSet.getInt("likes")));
            }

            System.out.println(postList.size());
            connection.close();
            return postList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PostDto savePost(String filePath, int accountId, String description, String uploadDate) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_POST_QUERY);

            statement.setString(1, description);
            statement.setString(2, filePath);
            statement.setString(3, uploadDate);
            statement.setInt(4, accountId);
            statement.executeUpdate();

            PostDto createdPost = new PostDto(description, filePath, uploadDate, accountId, 0);

            connection.close();
            return createdPost;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
