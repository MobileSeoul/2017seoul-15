package com.djunderworld.stm.common.dao;

/**
 * 
 * 유저정보 엔티티 클래스
 * 
 * 이메일, 비밀번호, 이름, 전화번호, 아바타사진, 커버사진, 소개, 레벨(1.일반유저, 2.상인), 성별(1.남자, 2.여자),
 * Firebase 토큰, 디바이스 기기 ID 정보, 로그인 접근방법 정보, 엑세스 토큰, 성별 공개범위, 전화번호 공개범위 , 팔로워
 * 수(상인의 경우), 팔로워 유무(상인의 경우), 시장정보(상인의 경우)
 * 
 * @author dongjooKim
 * 
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
	private int followerCount;
	private Boolean isFollowed;

	private String accessToken;

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

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public Boolean getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(Boolean isFollowed) {
		this.isFollowed = isFollowed;
	}

}
