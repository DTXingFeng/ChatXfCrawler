package xyz.xingfeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xingfeng.dao.AnswersDao;
import xyz.xingfeng.pojo.Answers;

import java.util.List;

@Service
public class AnswersServiceImpl implements AnswersService{

    @Autowired
    AnswersDao answersDao;

    @Override
    public boolean add(Answers answers) {
        return answersDao.add(answers) > 0;
    }

    @Override
    public List<Answers> getAll() {
        return answersDao.getAll();
    }

    @Override
    public Answers getById(int id) {
        return answersDao.getById(id);
    }

    @Override
    public List<Answers> getIdByText(String text) {
        return answersDao.getIdByText(text);
    }

    @Override
    public Boolean updateWeight(Answers answers) {
        return answersDao.updateWeight(answers) > 0;
    }
}
