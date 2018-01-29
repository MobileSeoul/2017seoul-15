package com.stm.common.dao;

/**
 * Created by ㅇㅇ on 2017-08-29.
 */

public class Behavior extends Base {
    private String name;

    public Behavior() {
        super();
    }

    public Behavior(long id, String createdAt, String updatedAt) {
        super(id, createdAt, updatedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public String getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public void setCreatedAt(String createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    public String getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    public void setUpdatedAt(String updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

}
