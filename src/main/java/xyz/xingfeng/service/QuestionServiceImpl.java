package xyz.xingfeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xingfeng.dao.QuestionsDao;
import xyz.xingfeng.pojo.Questions;

@Service
public class QuestionServiceImpl implements QuestionsService{

    @Autowired
    QuestionsDao questionsDao;

    @Override
    public boolean add(Questions questions) {
        return questionsDao.add(questions)>0;
    }

    @Override
    public int getIdByText(String text) {
        Questions idByText = questionsDao.getIdByText(text);
        if (idByText != null){
            return idByText.getId();
        }
        return 0;
    }

    @Override
    public Questions getById(int id) {
        return questionsDao.getById(id);
    }
}
