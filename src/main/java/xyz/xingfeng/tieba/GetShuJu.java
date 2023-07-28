package xyz.xingfeng.tieba;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import xyz.xingfeng.config.SpringConfig;
import xyz.xingfeng.pojo.Answers;
import xyz.xingfeng.pojo.CrawlRecords;
import xyz.xingfeng.pojo.Questions;
import xyz.xingfeng.service.AnswersService;
import xyz.xingfeng.service.CrawlRecordsService;
import xyz.xingfeng.service.QuestionsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetShuJu implements Runnable{
    AnnotationConfigApplicationContext cxt = new AnnotationConfigApplicationContext(SpringConfig.class);
    AnswersService answersService = cxt.getBean(AnswersService.class);
    QuestionsService questionsService = cxt.getBean(QuestionsService.class);
    CrawlRecordsService crawlRecordsService = cxt.getBean(CrawlRecordsService.class);
    private static final Logger logger = LogManager.getLogger(GetShuJu.class);
    String name;
    public GetShuJu(String name){
        this.name = name;
    }
    @Override
    public void run() {
        TieBa tieBa = new TieBa(name);
        logger.info("准备进行{}吧的爬取",name);
        ArrayList<String> tieZiId = new ArrayList<>();
        while (true) {
            tieZiId = tieBa.getTieZiId();
            if (tieZiId.size() == 0){
                return;
            }
            for (String s : tieZiId) {
                //将爬到的帖子id遍历对比，查看是否已经被爬过了
                if (!isNotOver(s)) {
                    continue;
                }
                logger.info("正在进行{}的爬取", s);
                TieZi tieZi = new TieZi(s);
                logger.info("帖子名称: {}", tieZi.getName());
                tieZi.getLzFloor();
                tieZi.getFloor();
                ArrayList<Map> qAndA = tieZi.getQAndA();
                logger.info("上传到服务器");
                toServer(qAndA);
                toOverServer(s);
                logger.info("完成对id为{}帖子的处理", s);
                try {
                    Thread.sleep((long) (((int) 4+Math.random()*(4)) * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void toServer(ArrayList<Map> maps){

        for (Map map : maps){
            //先提取问题
            String q = (String) map.get("Q");
            if (q.equals("")){
                continue;
            }
            //检查服务器是否有同一个问题
            int idByTextQ = 0;
            idByTextQ = questionsService.getIdByText(q);
            if (idByTextQ == 0){
                //没有同一个问题，写入
                Questions questions = new Questions();
                questions.setQuestion_text(q);
                questionsService.add(questions);
                //写入后再获得id
                idByTextQ = questionsService.getIdByText(q);
            }
            //有同一个问题，放弃写入
            //提取回答
            String a = (String) map.get("A");
            if (a.equals("")){
                continue;
            }
            //检查服务器是否有同一个回答
            List<Answers> idByTextA = answersService.getIdByText(a);
            for (Answers answers : idByTextA){
                int question_id = answers.getQuestion_id();
                //判断这个问题id是否与这次的问题一致
                if (question_id == idByTextQ){
                    //发现一致
                    //更新
                    answers.setWeight(answers.getWeight() + 1);
                    answersService.updateWeight(answers);
                    return;
                }
            }
            //没有同一个回答
            //直接写入一个新的
            Answers answers = new Answers();
            answers.setAnswer_text(a);
            answers.setQuestion_id(idByTextQ);
            answers.setWeight(1);
            answersService.add(answers);

        }
    }

    public void toOverServer(String s){
        //任务已完成，将爬完的帖子写入数据库
        CrawlRecords crawlRecords = new CrawlRecords();
        crawlRecords.setPost_id(s);
        crawlRecordsService.add(crawlRecords);
    }
    public boolean isNotOver(String s){
        //检查是否爬过该帖子
        CrawlRecords byId = crawlRecordsService.getById(s);
        if (byId != null){
            //爬过
            return false;
        }
        return true;
    }
}
