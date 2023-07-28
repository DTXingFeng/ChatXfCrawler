package xyz.xingfeng.tieba;

public class Property {

    public static void setProperty(String Host,String Port){
        System.setProperty("http.proxyHost", Host);
        System.setProperty("http.proxyPort", Port);

    }
}
