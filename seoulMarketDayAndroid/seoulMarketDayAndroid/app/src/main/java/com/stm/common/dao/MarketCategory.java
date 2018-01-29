package com.stm.common.dao;

/**
 * 지역구 정보 엔티티 클래스
 * <p>
 * 시장 분류 이름
 */
public class MarketCategory extends Base {
    private String name;

    public MarketCategory() {
    }

    public MarketCategory(long id, String createdAt, String updatedAt) {
        super(id, createdAt, updatedAt);
    }

    public MarketCategory(long id, String createdAt, String updatedAt, String name) {
        super(id, createdAt, updatedAt);
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MarketCategory that = (MarketCategory) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MarketCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
