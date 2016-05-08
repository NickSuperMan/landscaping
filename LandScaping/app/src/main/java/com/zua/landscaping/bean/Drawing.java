package com.zua.landscaping.bean;

/**
 * Created by roy on 4/26/16.
 */
public class Drawing {

    private int drawd;
    private String drawPicUrl;
    private String drawName;

    public int getDrawd() {
        return drawd;
    }

    public void setDrawd(int drawd) {
        this.drawd = drawd;
    }

    public String getDrawName() {
        return drawName;
    }

    public void setDrawName(String drawName) {
        this.drawName = drawName;
    }

    public String getDrawPicUrl() {
        return drawPicUrl;
    }

    public void setDrawPicUrl(String drawPicUrl) {
        this.drawPicUrl = drawPicUrl;
    }

    @Override
    public String toString() {
        return "Drawing{" +
                "drawd=" + drawd +
                ", drawPicUrl='" + drawPicUrl + '\'' +
                ", drawName='" + drawName + '\'' +
                '}';
    }
}
