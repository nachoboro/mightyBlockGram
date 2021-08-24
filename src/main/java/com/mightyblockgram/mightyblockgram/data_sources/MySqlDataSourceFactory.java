package com.mightyblockgram.mightyblockgram.data_sources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MySqlDataSourceFactory extends DataSourceFactory{

    @Override
    public DataSource initDataSource() {
        final HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setJdbcUrl("jdbc:mysql://db:3306/MightyBlockGram");
        config.setUsername("root");
        config.setPassword("root");

        final HikariDataSource dataSource = new HikariDataSource(config);
        setDataSource(dataSource);
        return dataSource;
    }
}
