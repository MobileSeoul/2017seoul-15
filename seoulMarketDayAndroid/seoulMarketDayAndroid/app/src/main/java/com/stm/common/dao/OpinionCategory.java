package com.stm.common.dao;

/**
 * 고객 의견 카테고리 정보 엔티티 클래스
 * <p>
 * 고객 의견 카테고리 이름정보
 *
 * @author dongjooKim
 */
public class OpinionCategory extends Base {
    private String name;

    public OpinionCategory() {
        super();
    }

    public OpinionCategory(long id, String createdAt, String updatedAt) {
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