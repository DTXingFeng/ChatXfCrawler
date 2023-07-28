package xyz.xingfeng.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import xyz.xingfeng.pojo.Answers;

import java.util.List;
@Mapper
public interface AnswersDao {

    @Select("select * from answers")
    List<Answers> getAll();

    @Select("select * from answers where id = #{id}")
    Answers getById(int id);

    @Update("update answers set weight = #{weight} where id = #{id}")
    int updateWeight(Answers answers);

    @Select("select * from answers where answer_text like #{text};")
    List<Answers> getIdByText(String text);

    @Insert("INSERT INTO answers (question_id, answer_text, weight) VALUES (#{question_id}, #{answer_text},#{weight});")
    int add(Answers answers);
}
