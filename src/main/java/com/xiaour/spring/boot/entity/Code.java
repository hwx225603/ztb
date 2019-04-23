package com.xiaour.spring.boot.entity;

import java.util.Date;

public class Code {
    private String code;

    private String machine;

    private Date time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine == null ? null : machine.trim();
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}