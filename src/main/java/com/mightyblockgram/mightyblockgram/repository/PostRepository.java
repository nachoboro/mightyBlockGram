package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.data_sources.DataSourceFactory;
import com.mightyblockgram.mightyblockgram.data_sources.MySqlDataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {

    private static final String GET_POST_BY_ID_QUERY = "SELECT post_id, description, image_path, upload_date, account_id from posts where post_id = ?";
    private static final String GET_ALL_POSTS_QUERY = "SELECT posts.post_id, posts.description, posts.image_path, posts.upload_date, posts.account_id, COUNT(like_id) as likes FROM posts LEFT JOIN (select like_id, post_id from likes where active = 1) as likes ON likes.post_id = posts.post_id GROUP BY posts.post_id order by posts.upload_date desc";
    private static final String INSERT_NEW_POST_QUERY = "INSERT INTO posts (description, image_path, upload_date, account_id) values(?,?,?,?)";

    private DataSourceFactory dataSourceFactory;

    public PostRepository(DataSourceFactory dataSourceFactory){
        this.dataSourceFactory = dataSourceFactory;
    }

    public List<PostDto> getAllPosts() {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_ALL_POSTS_QUERY);

            List<PostDto> postList = new ArrayList<>();
            while (resultSet.next()) {
                postList.add(new PostDto(resultSet.getString("description"), resultSet.getString("image_path"), resultSet.getString("upload_date"), resultSet.getInt("account_id"), resultSet.getInt("likes")));
            }

            connection.close();
            return postList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public PostDto savePost(String filePath, int accountId, String description, String uploadDate) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_POST_QUERY);

            statement.setString(1, description);
            statement.setString(2, filePath);
            statement.setString(3, uploadDate);
            statement.setInt(4, accountId);
            statement.executeUpdate();

            PostDto createdPost = new PostDto(description, filePath, uploadDate, accountId, 0);

            connection.close();
            return createdPost;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PostDto getPostById(int postId) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_POST_BY_ID_QUERY);

            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();

            PostDto postDto = null;
            while (resultSet.next()) {
                postDto = new PostDto(resultSet.getString("description"), resultSet.getString("image_path"), resultSet.getString("upload_date"), resultSet.getInt("account_id"));
            }

            connection.close();
            return postDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
