package xyz.xingfeng.pojo;

import java.sql.Timestamp;

public class CrawlRecords {
    private int id;
    private String post_id;
    private String website;
    private String title;
    private String author;
    private String content;
    private int comments;
    private Timestamp crawled_at;

    @Override
    public String toString() {
        return "CrawlRecords{" +
                "id=" + id +
                ", post_id=" + post_id +
                ", website='" + website + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                ", crawled_at=" + crawled_at +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public Timestamp getCrawled_at() {
        return crawled_at;
    }

    public void setCrawled_at(Timestamp crawled_at) {
        this.crawled_at = crawled_at;
    }
}
