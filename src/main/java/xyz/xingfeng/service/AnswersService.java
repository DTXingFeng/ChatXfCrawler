package xyz.xingfeng.service;

import org.apache.ibatis.annotations.Select;
import xyz.xingfeng.dao.AnswersDao;
import xyz.xingfeng.pojo.Answers;

import java.util.List;

public interface AnswersService {

    public boolean add(Answers answers);

    public List<Answers> getAll();

    public Answers getById(int id);

    public List<Answers> getIdByText(String text);

    public Boolean updateWeight(Answers answers);
}
