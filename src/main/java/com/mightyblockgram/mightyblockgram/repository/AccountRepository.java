package com.mightyblockgram.mightyblockgram.repository;
import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class AccountRepository {

    private static final String GET_ACCOUNT_BY_ID_QUERY = "SELECT * FROM accounts WHERE account_id = ? ";

    public AccountDto getAccount(Integer accountId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MightyBlockGram", "nacho", "nacho");
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID_QUERY);

            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            AccountDto accountDto = null;
            while (resultSet.next()) {
                accountDto = new AccountDto(resultSet.getString("name"));
            }

            connection.close();
            return accountDto;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
