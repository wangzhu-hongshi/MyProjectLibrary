package com.wang.domain;

import java.io.Serializable;

/**
 * 账户实体类
 */
public class Account implements Serializable {
    public Integer ID;
    public Integer UID;
    public Double MONEY;
    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getUID() {
        return UID;
    }

    public void setUID(Integer UID) {
        this.UID = UID;
    }

    public Double getMONEY() {
        return MONEY;
    }

    public void setMONEY(Double MONEY) {
        this.MONEY = MONEY;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ID=" + ID +
                ", UID=" + UID +
                ", MONEY=" + MONEY +
                '}';
    }
}
