package xyz.xingfeng.tieba;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.xingfeng.config.HttpConfig;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetTiebaHtml {
    private static final Logger logger = LogManager.getLogger(GetTiebaHtml.class);
//    String cookie = HttpConfig.COOKIE;
    //创建一个储存结果的string类型
    private String htmlString = "";
    String userAgent = HttpConfig.USER_AGENT;
    public GetTiebaHtml(String url) {
        int i = 0;
        while (true) {
            if (i>=5){
                return;
            }
            try {
                URL sUrl = new URL(url);
                sUrl.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) sUrl.openConnection();
                //设置请求头
                httpURLConnection.addRequestProperty("user-agent", userAgent);
                httpURLConnection.addRequestProperty("Accept-Language", "zh-CN");
                httpURLConnection.addRequestProperty("Charset", "UTF-8");

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
                return;
            } catch (Exception e) {
                i++;
                logger.error(e.toString());
            }
        }
    }

    public String getHtmlString(){
        return htmlString;
    }
}
