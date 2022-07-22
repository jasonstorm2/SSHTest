package SSHTest.jdbc;//package SSHTest.jdbc;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.DatabaseMetaData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connect {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:9005/game_1010007?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";
    static final String TABLE_NAME = "activity_silkfight_cross_common_logic_cs";
    static final String USER = "root";
    static final String PASS = "Lj@20220722$$$##";

    public static Connect getInstance(){
        return new Connect();
    }

    private List<String> databaseNameList = new ArrayList<>();


    public void connectMysql(String databaseName){
        String DB_URL = "jdbc:mysql://172.17.16.4:3306/chatLog?characterEncoding=utf-8&useSSL=false&rewriteBatchedStatements=true&allowMultiQueries=true";

        Connection conn = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 先查询连接的所有数据库名字
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            com.mysql.cj.jdbc.DatabaseMetaData data = (DatabaseMetaData) conn.getMetaData();
            System.out.println("statements:"+data.getMaxStatements());
            queryDatabaseName(conn);
            // 再逐个查询数据库
            if(!databaseNameList.isEmpty()){
                for (String name : databaseNameList) {
//                    System.out.println("当前计算的数据库:"+name);
                    String url = getURL(name);
                    conn = DriverManager.getConnection(url,USER,PASS);
                    queryTotalMoney(conn);
                }
            }
            com.mysql.cj.jdbc.DatabaseMetaData data1 = (DatabaseMetaData) conn.getMetaData();
            System.out.println("活跃statements:"+data1.getMaxStatements());

            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
//        System.out.println("Goodbye!");
    }

    public String getURL(String databaseName) {
        String DB_URL = "jdbc:mysql://localhost:9005/" + databaseName + "?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&failOverReadOnly=false&serverTimezone=UTC";
        return DB_URL;
    }

    private void queryDatabaseName(Connection conn) {
        try {
            if(databaseNameList.size()>0){
                return;
            }
            Statement stmt = null;
            stmt = conn.createStatement();
            com.mysql.cj.jdbc.DatabaseMetaData data = (DatabaseMetaData) conn.getMetaData();
            System.out.println("statements1:"+data.getMaxStatements());
            String sql;
            sql = "select SCHEMA_NAME as baseName from information_schema.schemata";
            ResultSet rs = stmt.executeQuery(sql);
            com.mysql.cj.jdbc.DatabaseMetaData data2 = (DatabaseMetaData) conn.getMetaData();
            System.out.println("statements2:"+data2.getMaxStatements());
            // 展开结果集数据库
            while (rs.next()) {
                String baseName = rs.getString("baseName");
                System.out.println("baseName:"+baseName);
                String[] nameArr = baseName.split("_");
                if(!nameArr[0].equals("game")){
                    continue;
                }
                if(nameArr[1].equals("log")){
                    continue;
                }
//                System.out.println("baseName：" + baseName);
                databaseNameList.add(baseName);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void queryTotalMoney(Connection conn) {
        try {
            Statement stmt = null;
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT SUM(rmb) AS money FROM charge_order WHERE state=2";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                // 通过字段检索
                String money  = rs.getString("money");
                System.out.println(money);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public static  Map<String, Integer> StringToMap(String str){
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        str = str.substring(1,str.length());
        str = str.substring(0,str.length()-1);

        String[] pairs = str.split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            String keyValue0 = keyValue[0];
            myMap.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }
        return myMap;
    }

    public static Gson strToGson(String str){
        Gson gson = new Gson();
        gson.fromJson(str,JsonObj.class);
        return gson;
    }



}