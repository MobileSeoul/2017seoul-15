package com.stm.common.dao;

import java.io.Serializable;

/**
 * 엔티티 디폴트 클래스
 * <p>
 * 아이디, 생성일, 수정일
 */
public abstract class Base implements Serializable {
    private long id;
    private String createdAt;
    private String updatedAt;

    public Base(long id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Base() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base base = (Base) o;

        if (id != base.id) return false;
        if (createdAt != null ? !createdAt.equals(base.createdAt) : base.createdAt != null)
            return false;
        return updatedAt != null ? updatedAt.equals(base.updatedAt) : base.updatedAt == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
