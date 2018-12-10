package tech.qijin.util4j.practice.model;

import tech.qijin.util4j.trace.pojo.EnvEnum;

public class User {
    private Integer id;

    private String name;

    private String userName;

    private Byte valid;

    private EnvEnum env;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public EnvEnum getEnv() {
        return env;
    }

    public void setEnv(EnvEnum env) {
        this.env = env;
    }
}