package xyz.xingfeng.tieba;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class TieBa {
    /**
     * 贴吧名称
     */
    String tieBaName;

    /**
     * 贴吧页面
     */
    String tieBaHtml;

    /**
     * 储存获取到的帖子id，移交给TieZi类处理
     */
    private ArrayList<String> tieZiId =new ArrayList<>();

    /**
     * 帖子列表
     */
    private Elements tieZiList;

    /**
     * 当前页数
     */
    private Integer pages = 0;

    public TieBa(String tieBaName){
        this.tieBaName = tieBaName;
    }

    public void getHtml(){
        //获得页面
        //构建url
        String url = "https://tieba.baidu.com/f?kw="+ tieBaName +"&ie=utf-8&pn="+pages;
        GetTiebaHtml getTiebaHtml = new GetTiebaHtml(url);
        this.tieBaHtml = getTiebaHtml.getHtmlString().replaceAll("-->","").replaceAll("<!--","");
    }

    private void getTieZiList() {
        getHtml();
        //获得帖子列表
        Document tieBaDoc = Jsoup.parse(this.tieBaHtml);
        tieZiList = tieBaDoc.select("li.j_thread_list.clearfix.thread_item_box");
    }

    public ArrayList<String> getTieZiId() {
        getTieZiList();
        for (Element tieZi: tieZiList){
            if (tieZi.className().contains("top")){
                continue;
            }
            tieZiId.add(tieZi.attr("data-tid"));
        }
        pages += 50;
        return tieZiId;
    }
}
