package com.example.genovagreen;

public class ItemPericolosi {

    private String titles;
    private String desc;
    private boolean expanded;

    public ItemPericolosi(){
    }

    public ItemPericolosi(String titles, String desc) {
        this.titles = titles;
        this.desc = desc;
        this.expanded=false;
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

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
