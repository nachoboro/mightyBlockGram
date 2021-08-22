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

    private static final String GET_ALL_POSTS_QUERY = "SELECT * FROM post order by uploadDate desc";
    private static final String INSERT_NEW_POST_QUERY = "INSERT INTO post (description, imagePath, uploadDate, idAccount) values(?,?,?,?)";

    public List<PostDto> getAllPosts() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_ALL_POSTS_QUERY);

            List<PostDto> postList = new ArrayList<>();
            while (resultSet.next()) {
                postList.add(new PostDto(resultSet.getString("description"), resultSet.getString("imagePath"), resultSet.getString("uploadDate"), resultSet.getInt("idAccount")));
            }

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

            PostDto createdPost = new PostDto(description, filePath, uploadDate, accountId);

            connection.close();
            return createdPost;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
