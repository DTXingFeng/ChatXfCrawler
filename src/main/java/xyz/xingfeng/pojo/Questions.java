package xyz.xingfeng.pojo;

import java.sql.Timestamp;

public class Questions {
    private int id;
    private String question_text;
    private Timestamp created_at;

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", question_text='" + question_text + '\'' +
                ", created_at=" + created_at +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
