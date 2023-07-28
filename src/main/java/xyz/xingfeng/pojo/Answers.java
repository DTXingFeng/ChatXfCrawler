package xyz.xingfeng.pojo;

import java.sql.Timestamp;

public class Answers {
    private int id;
    private int question_id;
    private String answer_text;
    private Timestamp created_at;

    private int weight;

    @Override
    public String toString() {
        return "Answers{" +
                "id=" + id +
                ", question_id=" + question_id +
                ", answer_text='" + answer_text + '\'' +
                ", created_at=" + created_at +
                ", weight=" + weight +
                '}';
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
