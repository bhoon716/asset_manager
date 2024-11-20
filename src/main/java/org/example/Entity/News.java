package org.example.Entity;

public class News {

    private final String title;
    private final String link;

    public News(String title, String link){
        this.title = title;
        this.link = link;
    }

    public String getTitle() {

        return title;
    }

    public String getLink() {
        return link;
    }
}
