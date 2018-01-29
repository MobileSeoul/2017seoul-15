package com.stm.common.dao;


/**
 * 유저정보 엔티티 클래스
 * <p>
 * 이메일, 비밀번호, 이름, 전화번호, 아바타사진, 커버사진, 소개, 레벨(0: 일반유저, 1:상인), 성별(1: 남자, 2:여자),
 * Firebase 토큰, 디바이스 기기 ID 정보, 로그인 접근방법 정보, 엑세스 토큰, 성별 공개범위, 전화번호 공개범위
 *
 * @author dongjooKim
 */
public class User extends Base {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String avatar;
    private String cover;
    private String intro;
    private int level;
    private int gender;
    private String fcmToken;
    private String deviceId;
    private int loginCategoryId;
    private RangeCategory phoneRangeCategory;
    private RangeCategory genderRangeCategory;

    private String accessToken;
    private Boolean isFollowed ;
    private int followerCount;
    private Market market;

    public User() {
        super();
    }

    public User(long id, String createdAt, String updatedAt) {
        super(id, createdAt, updatedAt);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getLoginCategoryId() {
        return loginCategoryId;
    }

    public void setLoginCategoryId(int loginCategoryId) {
        this.loginCategoryId = loginCategoryId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public RangeCategory getPhoneRangeCategory() {
        return phoneRangeCategory;
    }

    public void setPhoneRangeCategory(RangeCategory phoneRangeCategory) {
        this.phoneRangeCategory = phoneRangeCategory;
    }

    public RangeCategory getGenderRangeCategory() {
        return genderRangeCategory;
    }

    public void setGenderRangeCategory(RangeCategory genderRangeCategory) {
        this.genderRangeCategory = genderRangeCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (followerCount != user.followerCount) return false;
        if (level != user.level) return false;
        if (gender != user.gender) return false;
        if (loginCategoryId != user.loginCategoryId) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (cover != null ? !cover.equals(user.cover) : user.cover != null) return false;
        if (intro != null ? !intro.equals(user.intro) : user.intro != null) return false;
        if (fcmToken != null ? !fcmToken.equals(user.fcmToken) : user.fcmToken != null)
            return false;
        if (deviceId != null ? !deviceId.equals(user.deviceId) : user.deviceId != null)
            return false;
        if (accessToken != null ? !accessToken.equals(user.accessToken) : user.accessToken != null)
            return false;
        if (market != null ? !market.equals(user.market) : user.market != null) return false;
        if (phoneRangeCategory != null ? !phoneRangeCategory.equals(user.phoneRangeCategory) : user.phoneRangeCategory != null)
            return false;
        if (genderRangeCategory != null ? !genderRangeCategory.equals(user.genderRangeCategory) : user.genderRangeCategory != null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (fcmToken != null ? fcmToken.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (market != null ? market.hashCode() : 0);
        result = 31 * result + (phoneRangeCategory != null ? phoneRangeCategory.hashCode() : 0);
        result = 31 * result + (genderRangeCategory != null ? genderRangeCategory.hashCode() : 0);
        result = 31 * result + level;
        result = 31 * result + gender;
        result = 31 * result + loginCategoryId;
        result = 31 * result + followerCount;
        return result;
    }


    @Override
    public String toString() {
        return "User [email=" + email + ", password=" + password + ", name=" + name + ", phone=" + phone + ", avatar="
                + avatar + ", cover=" + cover + ", intro=" + intro + ", level=" + level + ", gender=" + gender
                + ", fcmToken=" + fcmToken + ", deviceId=" + deviceId + ", loginCategoryId=" + loginCategoryId
                + ",followCount=" + followerCount + ", accessToken=" + accessToken + ", market=" + market
                + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword() + ", getName()=" + getName()
                + ", getPhone()=" + getPhone() + ", getAvatar()=" + getAvatar() + ", getLevel()=" + getLevel()
                + ", getGender()=" + getGender() + ", getFollowerCount=" + getFollowerCount() + ",getFcmToken()=" + getFcmToken()
                + ", getDeviceId()=" + getDeviceId() + ", getLoginCategoryId()=" + getLoginCategoryId() + ", getCover()=" + getCover()
                + ", getId()=" + getId() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt()
                + ", getIntro()=" + getIntro() + ", getMarket()=" + getMarket() + ", getAccessToken()=" + getAccessToken()
                + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }
}