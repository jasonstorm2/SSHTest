package SSHTest;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ExecuteShellCommand {

    String user;//SSH连接用户名
    String password;//SSH连接密码
    String host;//SSH服务器
    int port;//SSH访问端口

    public void execute() throws JDOMException, IOException {
        user = "root";
        host = "124.220.154.162";
        port = 22;
        password = "Lj@20220429#@@123";
        ChannelShell channelShell = null;

        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            // step1：建立ssh连接
            session.connect();


            //获取shell终端
            channelShell = (ChannelShell) session.openChannel("shell");
            channelShell.setPty(true);
            channelShell.connect();


            try (InputStream in = channelShell.getInputStream();
                 OutputStream out = channelShell.getOutputStream();
                 PrintWriter pw = new PrintWriter(out);) {
                //写入要执行的语句
                pw.println("cd /");
                pw.println("mkdir xxxxxxxx");
//                for (String cmd : commands) {
//                    pw.println(cmd);
//                }
                pw.println("exit");
                //刷出
                pw.flush();

                //日志返回输出
                byte[] buffer = new byte[1024];
                for (; ; ) {
                    while (in.available() > 0) {
                        int i = in.read(buffer, 0, 1024);
                        if (i < 0) {
                            break;
                        }
                        String s = new String(buffer, 0, i);
                        System.out.println("获得输出："+s);
                    }
                    if (channelShell.isClosed()) {
                        System.out.println("SSH2 Exit: " + channelShell.getExitStatus());
                        break;
                    }

                }
            }


        } catch (Exception e) {
            if (null != session) {
                //关闭ssh连接
                session.disconnect();
            }
            e.printStackTrace();
        } finally {
            if (null != session) {
                //关闭ssh连接
                session.disconnect();
            }
        }
    }

    public static void main(String[] args) throws JDOMException, IOException {
        new ExecuteShellCommand().execute();
    }

}
