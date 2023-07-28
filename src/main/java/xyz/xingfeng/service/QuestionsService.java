package xyz.xingfeng.service;

import xyz.xingfeng.pojo.Questions;

public interface QuestionsService {
    public boolean add(Questions questions);

    public int getIdByText(String text);

    public Questions getById(int id);
}
