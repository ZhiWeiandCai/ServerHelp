package com.xht.android.serverhelp.model;

/**
 * Created by czw on 2016/10/24.
 * 办证进度标签每步的数据项
 */

public class ProsItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String name;
    private String startTime;
    private String endTime;
}
