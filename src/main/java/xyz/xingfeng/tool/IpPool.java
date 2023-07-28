package xyz.xingfeng.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.xingfeng.tieba.GetTiebaHtml;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IpPool {
    private static final Logger logger = LogManager.getLogger(IpPool.class);
    //存放获得的ip与端口
    Map<String,String> ips = new HashMap<>();
    //国内免费代理网页
    static String ipUrl = "https://www.kuaidaili.com/free/intr/";

    public IpPool(){
        //只获取前五页
        for (int i = 1; i <= 5; i++){
            String htmlString = getHtml(ipUrl + i);
            Document parse = Jsoup.parse(htmlString);
            Elements tbody = parse.getElementsByTag("tr");
            for (Element element : tbody){
                analysis(element);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void analysis(Element element){
        String ip = "";
        String port = "";
        //获取ip
        Elements elements = element.selectXpath("td[@data-title='IP']");
        for (Element e : elements){
            String s = e.text();
            if (!s.equals("")) {
                ip = e.text();
            }
        }
        //获取端口
        elements = element.selectXpath("td[@data-title='PORT']");
        for (Element e : elements){
            String s = e.text();
            if (!s.equals("")) {
                port = e.text();
                ips.put(ip,port);
            }
        }

    }



    //获得免费ip池
    public Map getIp(){
        return this.ips;

    }

    public String getHtml(String url) {
        String htmlString = "";
        int i = 0;
        while (true) {
            if (i>=5){
                return "";
            }
            try {
                URL sUrl = new URL(url);
                sUrl.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) sUrl.openConnection();
                //设置请求头
                httpURLConnection.addRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.183");

//                httpURLConnection.addRequestProperty("Cookie", cookie);

                //发送get请求
                httpURLConnection.setRequestMethod("GET");
                //设置超时
                httpURLConnection.setConnectTimeout(10000);
                InputStream in = httpURLConnection.getInputStream();
                byte[] b = new byte[1024];
                int len;
                //返回状态码
                int codeNum = httpURLConnection.getResponseCode();
                while ((len = in.read(b)) != -1) {
                    htmlString += new String(b, 0, len);
                }
                return htmlString;
            } catch (Exception e) {
                i++;
                logger.error(e.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


}
