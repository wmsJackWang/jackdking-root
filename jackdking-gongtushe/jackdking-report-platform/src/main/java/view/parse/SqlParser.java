package view.parse;


import java.sql.*;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName SqlPraser
 * @Description TODO
 * @Author jackdking
 * @Date 06/07/2022 2:49 下午
 * @Version 2.0
 **/
public class SqlParser {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //1.数据库连接的4个基本要素：
        String url = "jdbc:mysql://bittechblog.com:3306/ry?useSSL=false";
        String user = "root";
        String password = "wanyang@wms";
        String driverName = "com.mysql.jdbc.Driver";
        //2.实例化Driver
        Class clazz = Class.forName(driverName);
        Driver driver = (Driver) clazz.newInstance();
        //3.注册驱动
        DriverManager.registerDriver(driver);
        //4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);

        PreparedStatement sqlStatement = conn.prepareStatement("select * from sys_config where config_id = -1");
        ResultSet resultSet = sqlStatement.executeQuery();
        System.out.println(resultSet.getMetaData());
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.printf("%-15s\t",resultSet.getMetaData().getColumnName(i));
        }
    }

}
