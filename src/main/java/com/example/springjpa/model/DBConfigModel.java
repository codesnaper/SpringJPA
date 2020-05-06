package com.example.springjpa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component("dbDevConfig")
public class DBConfigModel {
    @Value("${dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${dataSource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

}
