package SSHTest.jdbc;//package SSHTest.jdbc;
import com.mysql.cj.jdbc.DatabaseMetaData;

import java.sql.*;
public class Conn {
    Connection con;
//    static final String DB_URL = "jdbc:mysql://localhost:3306/cross2?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";
    static final String DB_URL = "jdbc:mysql://172.17.16.48:3306/game_1010007?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功");

        } catch( Exception e){
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(DB_URL,"jb3","dIsNE3)s&Hii#xK");
            System.out.println("数据库连接成功");
            DatabaseMetaData data = (DatabaseMetaData) con.getMetaData();
            System.out.println("活跃statements:"+data.getMaxStatements());
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    public static void main(String[] args) {
        Conn c = new Conn();
        c.getConnection();
    }
}