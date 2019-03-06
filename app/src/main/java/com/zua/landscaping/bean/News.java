package com.zua.landscaping.bean;

/**
 * Created by roy on 4/26/16.
 */
public class News {
    private int newsId;
    private String newsName;
    private String newsPicUrl;
    private String newsDescription;
    private String newsInformation;

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsInformation() {
        return newsInformation;
    }

    public void setNewsInformation(String newsInformation) {
        this.newsInformation = newsInformation;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsPicUrl() {
        return newsPicUrl;
    }

    public void setNewsPicUrl(String newsPicUrl) {
        this.newsPicUrl = newsPicUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsDescription='" + newsDescription + '\'' +
                ", newsId=" + newsId +
                ", newsName='" + newsName + '\'' +
                ", newsPicUrl='" + newsPicUrl + '\'' +
                ", newsInformation='" + newsInformation + '\'' +
                '}';
    }
}
