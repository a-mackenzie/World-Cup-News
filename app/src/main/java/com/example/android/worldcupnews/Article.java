package com.example.android.worldcupnews;

public class Article {

    // The title of the article
    private String mTitle;

    // The section the article belongs to
    private String mSection;

    // The author of the article
    private String mAuthor;

    // The published date of the article
    private String mDate;

    // The published time of the article
    private String mTime;

    // Constructs the Article object
    public Article(String title, String section, String author, String date, String time) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mDate = date;
        mTime = time;
    }

    // Gets the title of the article
    public String getTitle() {
        return mTitle;
    }

    // Gets the section the article belongs to
    public String getSection() {
        return mSection;
    }

    // Gets the author of the article
    public String getAuthor() {
        return mAuthor
    }

    // Gets the published date of the article
    public String getDate() {
        return mDate;
    }

    // Gets the published time of the article
    public String getTime() {
        return mTime;
    }
}
