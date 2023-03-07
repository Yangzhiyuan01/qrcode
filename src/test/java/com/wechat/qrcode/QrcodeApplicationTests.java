package com.wechat.qrcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class QrcodeApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

    /**
     * HikariProxyConnection@1471558227 wrapping com.mysql.cj.jdbc.ConnectionImpl@c0013b8
     * @throws SQLException
     */
    @Test
    void testDataBase() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
