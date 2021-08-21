package com.mightyblockgram.mightyblockgram.repository;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PostRepository {
    public String getAllPosts() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from post");

            String message = "";
            while (resultSet.next()) {
                message += "postId: " + resultSet.getInt("idPost") + ", description: " + resultSet.getString("description") + ", imagePath: " + resultSet.getString("imagePath") + ", uploadDate: " + resultSet.getString("uploadDate") + ", idAccount: " + resultSet.getString("idAccount");
            }

            connection.close();
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
