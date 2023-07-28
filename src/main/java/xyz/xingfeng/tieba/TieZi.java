package xyz.xingfeng.tieba;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TieZi {
    /**
     * 最后得到的问答集合
     */
    private ArrayList<Map> QAndA = new ArrayList<>();
    /**
     *帖子id
     */
    private String id;
    /**
     * 楼主名称
     */
    private String LZName;
    /**
     * 页数,默认第一页
     */
    private Integer pages = 1;
    /**
     * 帖子总页数
     */
    private Integer maxPages;

    /**
     * 最大回复
     */
    private Integer maxReply;

    /**
     * 帖子名称
     */
    private String name;
    /**
     * 楼主层内容
     */
    String lzValue;
    //帖子全体内容
    private String tieZiHtml;
    private static final Logger logger = LogManager.getLogger(TieZi.class);
    public TieZi(String id){
        //获得传输过来的帖子id
        this.id = id;
        //发起对页面的请求
        getHtml();
        //获得总页数
        //解析请求的页面
        Document doc = Jsoup.parse(tieZiHtml);
        //获得显示最大页和最大回复的标签
        Elements reply_num = doc.select("li.l_reply_num span.red");
        this.maxPages = 0;
        this.maxReply = 0;
        if (reply_num.size() != 0){
            //如果不为0则表示拥有最大页和最大回复的资料
            this.maxReply = Integer.valueOf(doc.select("li.l_reply_num span.red").get(0).text());
            this.maxPages = Integer.valueOf(doc.select("li.l_reply_num span.red").get(1).text());
        }else {
            //反之则没有，但要怎么处理我暂时还没想好
        }
        this.name = doc.select("h3").text();
        //有些标题不是使用h3标签，要改
    }
    public void getHtml(){
        //构建url
        String url = "https://tieba.baidu.com/p/"+ id +"?pn=" + pages;
        GetTiebaHtml getTiebaHtml = new GetTiebaHtml(url);
        this.tieZiHtml = getTiebaHtml.getHtmlString();
    }

    /**
     * 获得楼层信息
     */
    public void getFloor(){
        if (lzValue.length() == 0){
            //如果一楼没有内容（图片标签不算内容），则将楼主内容变成帖子标题
            lzValue = name;
        }
        if (maxReply == 0){
            //如果最大回复为0，则直接退出
            return;
        }
        for (;pages <= maxPages; pages++) {
            getHtml();
            //获得此页面中除了楼主层的所有楼层
            Document doc = Jsoup.parse(tieZiHtml);
            //获得此页面除了楼中楼的所有回复
            Elements FloorElements = doc.select("div.l_post.j_l_post.l_post_bright");
            logger.info("正在处理"+pages+"/"+maxPages);
            for (org.jsoup.nodes.Element floorElement : FloorElements) {
                //判定是不是楼主
                if (floorElement.select("span.tail-info").size() > 0) {
                    if (floorElement.select("span.tail-info").get(1).text().equals("1楼")) {
                        //System.out.println("楼主层，过滤");
                        continue;
                    }
                }
                //获取id
                //System.out.println(floorElement.select("li.d_name a").get(0).text());
                //获取信息
                if (floorElement.select("div.d_post_content.j_d_post_content").size() == 0){
                    continue;
                }
                String FloorValue = floorElement.select("div.d_post_content.j_d_post_content").get(0).text();
                if (FloorValue.length() == 0){
                    continue;
                }
                //此处的回复一般是回复楼主的，所以直接构造一个map类型
                Map<String, String> qAndA = new HashMap<>();
                qAndA.put("Q", lzValue);
                qAndA.put("A", FloorValue);
                QAndA.add(qAndA);
                getLzL(floorElement, FloorValue);
                //休息一下，防止被检测
                try {

                    Thread.sleep((long) (((int) 2+Math.random()*(3)) * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 获得楼中楼信息
     */
    public void getLzL(Element floorElement,String FloorValue){
        //查看楼中楼
        Integer louPages = 1;
        //获得data_pid
        String data_pid = floorElement.attr("data-pid");
        while (true){
            //构建楼中楼api
            String LouZhongLou = "https://tieba.baidu.com/p/comment?tid=" + id + "&pid=" + data_pid + "&pn=" + louPages;
            GetTiebaHtml getTiebaHtml = new GetTiebaHtml(LouZhongLou);
            String lzlHtml = getTiebaHtml.getHtmlString();
            Document lzlElements = Jsoup.parse(lzlHtml);
            Elements lzlList = lzlElements.select("div.lzl_cnt");
            if (lzlList.size() > 0) {
                for (Element lzl : lzlList) {
                    Elements isHuiFu = lzl.select("span.lzl_content_main a");
                    //是否回复消息？
                    if (isHuiFu.size() != 0) {
                        //是，忽略此结果
                        continue;
                    }else {
                        //不是，进行获取信息
                        Elements lzlBox = lzl.select("span.lzl_content_main");
                        String lzlValue = lzlBox.get(0).text();
                        if (lzlValue.length() == 0){
                            continue;
                        }
                        Map<String, String> qAndA = new HashMap<>();
                        qAndA.put("Q", FloorValue);
                        qAndA.put("A", lzlValue);
                        QAndA.add(qAndA);
                    }
                }
            }else {
                break;
            }
            louPages++;
            try {
                Thread.sleep((long) (((int) 1+Math.random()*(3)) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getLzFloor(){
        //获取楼主消息
        Document doc = Jsoup.parse(tieZiHtml);
        Elements LzFloorElements =doc.select("div.l_post.j_l_post.l_post_bright");
        LZName = "";
        if (LzFloorElements.select("li.d_name a").size() != 0){
            //获取楼主id
            LZName = LzFloorElements.select("li.d_name a").get(0).text();
        }
        lzValue = "";
        if (LzFloorElements.select("div.d_post_content.j_d_post_content").size() != 0){
            //获取楼主信息
            lzValue = LzFloorElements.select("div.d_post_content.j_d_post_content").get(0).text();
        }

    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Map> getQAndA() {
        return QAndA;
    }

    public String getName() {
        return name;
    }
}
