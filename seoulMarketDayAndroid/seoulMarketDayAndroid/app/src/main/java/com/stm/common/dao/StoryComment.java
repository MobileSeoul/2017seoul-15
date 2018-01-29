package com.stm.common.dao;

/**
 *
 * 스토리 댓글 정보 엔티티 클래스
 *
 * 스토리 정보, 작성자 정보, 그룹 정보, 답글 깊이, 답글 위치, 댓글 내용 정보
 *
 * @author dongjooKim
 */
public class StoryComment extends Base {
    private Story story;
    private User user;
    private long groupId;
    private long depth;
    private long position;
    private String content;

    private File file;

    private int replyCommentCount;

    public StoryComment() {
        super();
    }

    public StoryComment(long id, String createdAt, String updatedAt) {
        super(id, createdAt, updatedAt);
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

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyCommentCount() {
        return replyCommentCount;
    }

    public void setReplyCommentCount(int replyCommentCount) {
        this.replyCommentCount = replyCommentCount;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}