package SSHTest.config;//package SSHTest.config;

import org.jdom2.Element;

public class GameServerConfig {

    Integer lport;//本地端口
    String rhost;//远程数据库服务器
    int rport;//远程数据库服务端口

    String user;//SSH连接用户名
    String password;//SSH连接密码ResultSet
    String host;//SSH服务器
    int port;//SSH访问端口


    public GameServerConfig(Element gameServiceCommon) {
        this.lport = Integer.parseInt(gameServiceCommon.getChildTextTrim("lport"));
        this.rhost = gameServiceCommon.getChildTextTrim("rhost");
        this.rport = Integer.parseInt(gameServiceCommon.getChildTextTrim("rport"));

        this.user = gameServiceCommon.getChildTextTrim("user");
        this.password = gameServiceCommon.getChildTextTrim("password");
        this.host = gameServiceCommon.getChildTextTrim("host");
        this.port = Integer.parseInt(gameServiceCommon.getChildTextTrim("port"));


    }

    public Integer getLport() {
        return lport;
    }

    public String getRhost() {
        return rhost;
    }

    public int getRport() {
        return rport;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
