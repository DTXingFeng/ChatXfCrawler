package xyz.xingfeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xingfeng.dao.CrawlRecordsDao;
import xyz.xingfeng.pojo.CrawlRecords;

import java.util.List;

@Service
public class CrawlRecordsServiceImpl implements CrawlRecordsService{

    @Autowired
    CrawlRecordsDao crawlRecordsDao;

    @Override
    public List<CrawlRecords> getAll() {
        return crawlRecordsDao.getAll();
    }

    @Override
    public int add(CrawlRecords crawlRecords) {
        return crawlRecordsDao.add(crawlRecords);
    }

    @Override
    public CrawlRecords getById(String post_id) {
        return crawlRecordsDao.getById(post_id);
    }
}
