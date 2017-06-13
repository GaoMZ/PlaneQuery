package com.graduate.gaomingzhu.planequery.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by GaoMingzhu on 2017/4/17.
 * email:2713940331@qq.com
 */

public class Flight extends BmobObject {
    private String flight;
    private String startCity;
    private String endCity;
    private BmobDate startDate;
    private BmobDate endDate;
    private Integer remaining;
    private Integer price;
    private Integer vector = 100;

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public BmobDate getStartDate() {
        return startDate;
    }

    public void setStartDate(BmobDate startDate) {
        this.startDate = startDate;
    }

    public BmobDate getEndDate() {
        return endDate;
    }

    public void setEndDate(BmobDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVector() {
        return vector;
    }

    public void setVector(Integer vector) {
        this.vector = vector;
    }
}

