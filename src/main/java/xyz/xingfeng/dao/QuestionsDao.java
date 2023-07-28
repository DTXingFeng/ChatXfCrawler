package xyz.xingfeng.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xyz.xingfeng.pojo.Questions;

import java.util.List;

@Mapper
public interface QuestionsDao {

    @Select("select * from questions")
    List<Questions> getAll();

    @Select("select * from questions where id = #{id}")
    Questions getById(int id);

    @Select("select id from questions where question_text like #{text}")
    Questions getIdByText(String text);

    @Insert("INSERT INTO questions (question_text) VALUES (#{question_text});")
    int add(Questions questions);
}
