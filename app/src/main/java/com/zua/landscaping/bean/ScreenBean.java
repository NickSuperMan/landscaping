package com.zua.landscaping.bean;

/**
 * Created by 123 on 2016/2/19.
 */
public class ScreenBean {

    private int sWidth;
    private int sHeight;

    public ScreenBean(int sWidth, int sHeight) {
        this.sWidth = sWidth;
        this.sHeight = sHeight;
    }

    public int getsWidth() {
        return sWidth;
    }

    public void setsWidth(int sWidth) {
        this.sWidth = sWidth;
    }

    public int getsHeight() {
        return sHeight;
    }

    public void setsHeight(int sHeight) {
        this.sHeight = sHeight;
    }
}
