package tech.qijin.util4j.practice.model;

import tech.qijin.util4j.practice.pojo.ColorEnm;

public class UserBag {
    private Integer id;

    private Integer userId;

    private String name;

    private ColorEnm color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public ColorEnm getColor() {
        return color;
    }

    public void setColor(ColorEnm color) {
        this.color = color;
    }
}