package SSHTest.jdbc;//package SSHTest.jdbc;

import SSHTest.config.GameServerConfig;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

/**
 * SSH端口转发
 */
public class SSHService {
    private static final String GAME_SERVER_CONFIG_XML_PATH = "." + File.separator + "resource" + File.separator + "gameServerConfig.xml";
    private GameServerConfig gameServerConfig;


     Integer lport;//本地端口
//     String rhost;//远程数据库服务器
     int rport;//远程数据库服务端口

     String user;//SSH连接用户名
     String password;//SSH连接密码
     String host;//SSH服务器
     int port;//SSH访问端口

    public static SSHService getInstance(){
        return new SSHService();
    }



    public void sshRun(String rhost) throws JDOMException, IOException {
//        //开始读取配置
//        SAXBuilder builder = new SAXBuilder();
//        Document doc = builder.build(new File(GAME_SERVER_CONFIG_XML_PATH));
//        Element root = doc.getRootElement();
//
//        //加载公共配置数据
//        gameServerConfig = new GameServerConfig(root.getChild("commonConfig"));
//
//        lport = gameServerConfig.getLport();
////        rhost = gameServerConfig.getRhost();
//        rport = gameServerConfig.getRport();
//        user = gameServerConfig.getUser();
//        password = gameServerConfig.getPassword();
//        host = gameServerConfig.getHost();
//        port = gameServerConfig.getPort();

        user = "root";
        host = "124.220.154.162";
        port = 22;
        password = "Lj@20220429#@@123";


        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            // step1：建立ssh连接
            session.connect();
            System.out.println("ssh version："+session.getServerVersion());//这里打印SSH服务器版本信息
            //step2： 设置SSH本地端口转发，本地转发到远程  // 设置SSH本地端口转发后，访问本地ip+port就可以访问到远程的ip+port
//            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            int assinged_port = session.setPortForwardingL(22, rhost, 3306);

            Connect.getInstance().connectMysql("");


        } catch (Exception e) {
            if (null != session) {
                //关闭ssh连接
                session.disconnect();
            }
            e.printStackTrace();
        }finally {
            if (null != session) {
                //关闭ssh连接
                session.disconnect();
            }
        }
    }

}

