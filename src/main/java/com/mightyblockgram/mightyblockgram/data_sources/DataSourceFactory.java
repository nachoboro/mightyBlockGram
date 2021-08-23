package com.mightyblockgram.mightyblockgram.data_sources;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Getter
@Setter(AccessLevel.PROTECTED)
@Component
public abstract class DataSourceFactory {
    private DataSource dataSource;

    public abstract DataSource initDataSource();
}
