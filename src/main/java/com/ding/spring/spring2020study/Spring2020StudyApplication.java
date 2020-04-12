package com.ding.spring.spring2020study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class Spring2020StudyApplication implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Spring2020StudyApplication.class);
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Spring2020StudyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showData();
        showConnection();

    }

    private void showConnection() throws SQLException {
        log.info("dataSource1:"+dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info("conn1："+conn.toString());
        conn.close();
    }

    private void showData() {
        log.info("showData11：");
        jdbcTemplate.queryForList("SELECT * FROM FOO")
                .forEach(row -> log.info("1111:"+row.toString()));
    }
}
