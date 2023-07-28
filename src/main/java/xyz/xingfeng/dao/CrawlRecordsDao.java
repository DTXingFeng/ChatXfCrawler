package xyz.xingfeng.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.xingfeng.pojo.CrawlRecords;

import java.util.List;

@Mapper
public interface CrawlRecordsDao {
    @Select("select * from crawl_records")
    List<CrawlRecords> getAll();

    @Insert("insert into crawl_records(post_id) values(#{post_id})")
    int add(CrawlRecords crawlRecords);

    @Select("select * from crawl_records where post_id like #{post_id};")
    CrawlRecords getById(String post_id);
}
