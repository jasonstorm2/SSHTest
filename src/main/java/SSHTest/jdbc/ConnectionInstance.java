package SSHTest.jdbc;
import java.sql.*;

public class ConnectionInstance {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:9005/game_1010007?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";
    static final String TABLE_NAME = "activity_silkfight_cross_common_logic_cs";
    static final String USER = "jb3";
    static final String PASS = "dIsNE3)s&Hii#xK";

    public void connectMysql(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
//            sql = "SELECT SUM(rmb) AS money FROM charge_order WHERE state=2";
            sql = "select SCHEMA_NAME as baseName from information_schema.schemata";
            ResultSet rs = stmt.executeQuery(sql);

            String infoFamily = "";

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
//                String money  = rs.getString("money");
//                System.out.println("价格："+money);
                String baseName  = rs.getString("baseName");
                System.out.println("baseName："+baseName);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

}
