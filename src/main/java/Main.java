import xyz.xingfeng.tieba.GetShuJu;

public class Main {
    public static void main(String[] args) {
        //设置本地代理
//        setProperty("127.0.0.1","7890");
        String name1 = "孙笑川";
        String name2 = "bilibili";

        Thread t1 = new Thread(new GetShuJu(name1));
        t1.setName(name1);
        t1.start();

        Thread t2 = new Thread(new GetShuJu(name2));
        t2.setName(name2);
        t2.start();
    }
    /**
     * 设置代理
     */
    public static void setProperty(String Host,String Port){
        System.setProperty("http.proxyHost", Host);
        System.setProperty("http.proxyPort", Port);

        // 对https也开启代理
        System.setProperty("https.proxyHost", Host);
        System.setProperty("https.proxyPort", Port);
    }
}
