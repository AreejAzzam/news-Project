package com.example.android.newsprojectstage1;

public class News {
    private String SectionName;
    private String Type;
    private String Url;
    private String WebtTitle;
    private String DateUpdate;


    News(String SectionName, String Type, String WebtTitle, String Date, String url) {
        this.Type = Type;
        this.WebtTitle = WebtTitle;
        this.SectionName = SectionName;
        this.Url = url;
        this.DateUpdate = Date;
    }

    public String getSectionName() {
        return SectionName;
    }

    public String getType() {
        return Type;
    }

    public String getWebtTitle() {
        return WebtTitle;
    }

    public String getUrl() {
        return Url;
    }

    public String getDateUpdate() {
        return DateUpdate;
    }
}
