package com.stm.common.dao;

/**
 * 고객 탈퇴 정보 엔티티 클래스
 * <p>
 * 고객 정보, 고객 탈퇴 카테고리 정보, 내용 정보
 */
public class Exit extends Base {

    private User user;
    private ExitCategory exitCategory;
    private String content;

    public Exit() {
        super();
    }

    public Exit(long id, String createdAt, String updatedAt) {
        super(id, createdAt, updatedAt);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExitCategory getExitCategory() {
        return exitCategory;
    }

    public void setExitCategory(ExitCategory exitCategory) {
        this.exitCategory = exitCategory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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