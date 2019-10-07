package com.qf.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Oldman 2019/9/10 14:17
 * bug? 不存在的
 */
public class DataSourceUtils {

    private static DruidDataSource dataSource;

    private static ThreadLocal<Connection> threadLocal;

    static {
        try {
            //初始化
            threadLocal = new ThreadLocal<>();
            Properties properties = new Properties();
            InputStream is = DataSourceUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(is);
            is.close();

            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化连接池失败");
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void startTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        Connection conn = getConnection();
        conn.commit();
    }

    public static void rollback()throws SQLException {
        Connection conn = getConnection();
        conn.rollback();
    }

    public static void close() throws SQLException {
        Connection conn = getConnection();
        conn.close();
        //解除绑定
        threadLocal.remove();
    }
}
