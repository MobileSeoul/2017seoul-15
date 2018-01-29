package com.stm.common.dto;

import java.io.Serializable;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public abstract class BaseDto implements Serializable {
    private long id;
    private String createdAt;
    private String updatedAt;

    public BaseDto() {
    }

    public BaseDto(long id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
