package com.example.android.worldcupnews;

public class Article {

    // The title of the article
    private String mTitle;

    // The section the article belongs to
    private String mSection;

    // The published time of the article
    private String mTime;

    // The url of the article
    private String mURL;

    // Constructs the Article object
    public Article(String title, String section, String time, String url) {
        mTitle = title;
        mSection = section;
        mTime = time;
        mURL = url;
    }

    // Gets the title of the article
    public String getTitle() {
        return mTitle;
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
