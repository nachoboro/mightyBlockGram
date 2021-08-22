package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class LikeRepository {

    private static final String GET_LIKE_BY_POST_AND_ACCOUNT_ID_QUERY = "SELECT * FROM likes WHERE account_id = ? and post_id = ? ";
    private static final String CREATE_LIKE_QUERY = "INSERT INTO likes (account_id, post_id, active) VALUES (?, ?, 1)";
    private static final String UPDATE_LIKE_QUERY = "UPDATE likes set active = 1 - active where account_id = ? and post_id = ?";

    public LikeDto getLike(int accountId, int postId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            PreparedStatement statement = connection.prepareStatement(GET_LIKE_BY_POST_AND_ACCOUNT_ID_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            ResultSet resultSet = statement.executeQuery();

            LikeDto likeDto = null;
            while (resultSet.next()) {
                likeDto = new LikeDto(resultSet.getInt("account_id"), resultSet.getInt("post_id"), resultSet.getBoolean("active"));
            }

            connection.close();
            return likeDto;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LikeDto createLike(int accountId, int postId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            PreparedStatement statement = connection.prepareStatement(CREATE_LIKE_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            statement.executeUpdate();

            LikeDto likeDto = new LikeDto(postId, accountId, true);

            connection.close();
            return likeDto;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateLike(int accountId, int postId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            PreparedStatement statement = connection.prepareStatement(UPDATE_LIKE_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
