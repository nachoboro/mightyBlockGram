package com.mightyblockgram.mightyblockgram.repository;
import com.mightyblockgram.mightyblockgram.data_sources.DataSourceFactory;
import com.mightyblockgram.mightyblockgram.data_sources.MySqlDataSourceFactory;
import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class AccountRepository {

    private static final String GET_ACCOUNT_BY_NAME_QUERY = "SELECT name, pass FROM accounts WHERE name = ? ";
    private static final String GET_ACCOUNT_BY_ID_QUERY = "SELECT name, pass FROM accounts WHERE account_id = ? ";
    private static final String CREATE_ACCOUNT_QUERY = "INSERT INTO accounts (name, pass) values (?, ?)";

    protected DataSourceFactory dataSourceFactory;

    public AccountRepository(DataSourceFactory dataSourceFactory){
        this.dataSourceFactory = dataSourceFactory;
    }

    public AccountDto getAccountByName(String userName) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_NAME_QUERY);

            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            AccountDto accountDto = null;
            if (resultSet.next()) {
                accountDto = new AccountDto(resultSet.getString("name"), resultSet.getString("pass"));
            }

            connection.close();
            return accountDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AccountDto getAccountById(Integer accountId) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID_QUERY);

            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            AccountDto accountDto = null;
            if (resultSet.next()) {
                accountDto = new AccountDto(resultSet.getString("name"), resultSet.getString("pass"));
            }

            connection.close();
            return accountDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveUser(String user, String hashedPass) {
        try {
            DataSource ds = dataSourceFactory.getDataSource();
            if (ds == null){
                ds = dataSourceFactory.initDataSource();
            }
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT_QUERY);

            statement.setString(1, user);
            statement.setString(2, hashedPass);
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
