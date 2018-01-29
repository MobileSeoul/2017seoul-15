package com.stm.common.dao;

/**
 * 지역구 정보 엔티티 클래스
 * <p>
 * 지역구 이름
 */
public class RegionCategory extends Base {
    private String name;

    public RegionCategory(long id, String createdAt, String updatedAt) {
        super(id, createdAt, updatedAt);
    }

    public RegionCategory(long id, String createdAt, String updatedAt, String name) {
        super(id, createdAt, updatedAt);
        this.name = name;
    }

    public RegionCategory() {
        super();
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

        RegionCategory that = (RegionCategory) o;

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
        return "RegionCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
