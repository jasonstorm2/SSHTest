package SSHTest;//package SSHTest;

import SSHTest.jdbc.SSHService;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMoneyServer {
    private static final String DATABASE_CONFIG = "." + File.separator + "sshresource" + File.separator + "databaseInfo.xml";

    public static GetMoneyServer getInstance() {
        return new GetMoneyServer();
    }

    public List<String> getIP() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(DATABASE_CONFIG));
        Element root = doc.getRootElement();
        List rowList = root.getChildren();
        List<String> ipString = new ArrayList<>();//row
        for (Object o : rowList) {
            Element element = (Element) o;
            Element host = (Element) element.getContent(1);//row 里面有三个元素
            Content content = host.getContent(0);//game_host里只有一个元素
            String str = content.getValue();
            ipString.add(str);
            System.out.println(str);
        }
        System.out.println("num:" + ipString.size());
        Map<String, Boolean> ipMap = new HashMap<>();
        for (String ip : ipString) {
            ipMap.put(ip, true);
        }
        List<String> ipList = new ArrayList<>();
        for (String ip : ipMap.keySet()) {
            ipList.add(ip);
        }
        return ipList;
    }

    public static void main(String[] args) throws JDOMException, IOException {
//        List<String> ips = GetMoneyServer.getInstance().getIP();
//        for (String ip : ips) {
//            SSHService.getInstance().sshRun(ip);
//        }
        SSHService.getInstance().sshRun("172.17.16.16");


    }


}
