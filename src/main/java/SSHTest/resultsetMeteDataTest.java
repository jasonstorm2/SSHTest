package SSHTest;//package SSHTest;

import com.mysql.cj.jdbc.DatabaseMetaData;

import java.sql.*;

public class resultsetMeteDataTest {

    Connection con;
        static final String DB_URL = "jdbc:mysql://localhost:3306/cross2?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";
//    static final String DB_URL = "jdbc:mysql://172.17.16.48:3306/game_1010007?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功");

        } catch( Exception e){
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(DB_URL,"root","23102282");
            System.out.println("数据库连接成功");
            String sql = "select * from cross_mammond";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData data = resultSet.getMetaData();
            int count = data.getColumnCount();//表的列数
            System.out.println("列数："+count);

            while(resultSet.next()){//遍历结果集的，每一行
                for (int i = 0; i < count; i++) {
                    String name = data.getColumnLabel(i+1);//每一个列的名字
                    System.out.println("列名字："+name);
                    System.out.println("name:"+data.getColumnName(i+1));
                    System.out.println("列值:"+resultSet.getObject(name));
                }
                System.out.println("-------------------------------------------------");
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        resultsetMeteDataTest test = new resultsetMeteDataTest();
        test.getConnection();
    }
}
