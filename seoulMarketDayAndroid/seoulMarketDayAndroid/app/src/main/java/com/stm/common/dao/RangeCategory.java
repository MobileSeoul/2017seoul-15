package com.stm.common.dao;

/**
 * 공개범위 정보 엔티티 클래스
 * <p>
 * 공개 분류 이름정보
 */
public class RangeCategory extends Base  {
    private String name;

    public RangeCategory() {
        super();
    }

    public RangeCategory(long id, String createdAt, String updatedAt) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RangeCategory rangeCategory = (RangeCategory) o;

        return name != null ? name.equals(rangeCategory.name) : rangeCategory.name == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RangeCategory{" +
                "name='" + name + '\'' +
                '}';
    }

}