package com.graduate.gaomingzhu.planequery.model;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by GaoMing on 2017-04-11.
 */

public class User extends BmobUser implements Serializable {
    private Integer age;
    private Boolean sex;  //true:man     false:woman
    private Boolean isManager;//true:Manager  false:no
    private String idCard;


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean manager) {
        isManager = manager;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
