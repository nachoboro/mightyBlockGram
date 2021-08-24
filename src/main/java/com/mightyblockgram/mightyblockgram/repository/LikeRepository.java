package com.mightyblockgram.mightyblockgram.repository;

import com.mightyblockgram.mightyblockgram.data_sources.DataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class LikeRepository {

    private static final String GET_LIKE_BY_POST_AND_ACCOUNT_ID_QUERY = "SELECT * FROM likes WHERE account_id = ? and post_id = ? ";
    private static final String CREATE_LIKE_QUERY = "INSERT INTO likes (account_id, post_id, active) VALUES (?, ?, 1)";
    private static final String LIKE_POST_QUERY = "UPDATE likes set active = 1 where account_id = ? and post_id = ?";
    private static final String UNLIKE_POST_QUERY = "UPDATE likes set active = 0 where account_id = ? and post_id = ?";

    private DataSourceFactory dataSourceFactory;

    public LikeRepository(DataSourceFactory dataSourceFactory){
        this.dataSourceFactory = dataSourceFactory;
    }

    public LikeDto getLike(int accountId, int postId) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_LIKE_BY_POST_AND_ACCOUNT_ID_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            ResultSet resultSet = statement.executeQuery();

            LikeDto likeDto = null;
            if (resultSet.next()) {
                likeDto = new LikeDto(resultSet.getInt("account_id"), resultSet.getInt("post_id"), resultSet.getBoolean("active"));
            }

            connection.close();
            return likeDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveLike(int accountId, int postId) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_LIKE_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void likePost(int accountId, int postId) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(LIKE_POST_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unlikePost(int accountId, int postId) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(UNLIKE_POST_QUERY);

            statement.setInt(1, accountId);
            statement.setInt(2, postId);
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
