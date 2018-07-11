package com.example.android.worldcupnews;

public class Article {

    // The title of the article
    private String mTitle;

    // The author of the article
    private String mAuthor;

    // The section the article belongs to
    private String mSection;

    // The published time of the article
    private String mTime;

    // The url of the article
    private String mURL;

    // Constructs the Article object
    public Article(String title, String author, String section, String time, String url) {
        mTitle = title;
        mAuthor = author;
        mSection = section;
        mTime = time;
        mURL = url;
    }

    // Gets the title of the article
    public String getTitle() {
        return mTitle;
    }

    // Gets the author of the article
    public String getAuthor() {
        return mAuthor;
    }

    // Gets the section the article belongs to
    public String getSection() {
        return mSection;
    }

    // Gets the published time of the article
    public String getTime() {
        return mTime;
    }

    // Gets the URL of the article
    public String getURL() {
        return mURL;
    }
}
