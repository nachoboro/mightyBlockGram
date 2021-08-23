package com.mightyblockgram.mightyblockgram.data_sources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2DataSourceFactory extends DataSourceFactory{

    @Override
    public DataSource initDataSource() {
        final HikariConfig config = new HikariConfig();

        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setConnectionTestQuery("VALUES 1");
        config.addDataSourceProperty("URL", "jdbc:h2:mem:clicks;MODE=mysql");
        config.addDataSourceProperty("user", "test");
        config.addDataSourceProperty("password", "test");
        config.setPoolName("H2 Primary Connection (MySQL)");

        final HikariDataSource dataSource = new HikariDataSource(config);
        setDataSource(dataSource);

        try (Connection connection = this.getDataSource().getConnection()) {
            String sqlDropTables = new String(Files.readAllBytes(new ClassPathResource("sql/drop_tables.sql").getFile().toPath()));
            PreparedStatement ps = connection.prepareStatement(sqlDropTables);
            ps.executeUpdate();
            String sqlCreateTables = new String(Files.readAllBytes(new ClassPathResource("sql/create_tables.sql").getFile().toPath()));
            PreparedStatement ps2 = connection.prepareStatement(sqlCreateTables);
            ps2.executeUpdate();
            String sqlInsertRecords = new String(Files.readAllBytes(new ClassPathResource("sql/insert_records.sql").getFile().toPath()));
            PreparedStatement ps3 = connection.prepareStatement(sqlInsertRecords);
            ps3.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
}
