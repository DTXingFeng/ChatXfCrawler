import xyz.xingfeng.tieba.GetShuJu;
import xyz.xingfeng.tool.IpPool;

import java.util.Map;
import java.util.Set;

public class Main {

    private static Thread t1;

    public static void main(String[] args) {
        String name1 = "孙笑川";

        t1 = new Thread(new GetShuJu(name1));
        t1.setName(name1);
        t1.start();

        System.out.println(new IpPool().getIp());
    }
    /**
     * 设置代理
     */
    public static void setProperty(String Host,String Port){
        System.setProperty("http.proxyHost", Host);
        System.setProperty("http.proxyPort", Port);

    }
    class autoSetIp implements Runnable{

        @Override
        public void run() {
            while (true) {
                Map ip = new IpPool().getIp();
                Set<String> set = ip.keySet();
                for (String s : set) {
                    Main.setProperty(s, (String) ip.get(s));
                    try {
                        Thread.sleep((long) (((int) 3 + Math.random() * (10)) * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
