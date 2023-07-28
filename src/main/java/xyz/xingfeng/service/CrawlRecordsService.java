package xyz.xingfeng.service;

import xyz.xingfeng.pojo.CrawlRecords;

import java.util.List;

public interface CrawlRecordsService {

    public List<CrawlRecords> getAll();

    public int add(CrawlRecords crawlRecords);

    public CrawlRecords getById(String post_id);
}
