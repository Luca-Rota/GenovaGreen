package com.example.genovagreen;

public class ItemPericolosi {

    private String titles;
    private String desc;
    private boolean isShrink = true;

    public ItemPericolosi(){
    }

    public ItemPericolosi(String titles, String desc) {
        this.titles = titles;
        this.desc = desc;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
