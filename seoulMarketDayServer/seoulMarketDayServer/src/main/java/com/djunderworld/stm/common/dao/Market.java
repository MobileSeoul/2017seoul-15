package com.djunderworld.stm.common.dao;

/**
 * 
 * 시장 정보 엔티티 클래스
 * 
 * 시장 분류 정보(1.일반시장, 2.전문시장), 지역구 정보(1.강남구,...etc), 시장이름, 도로명 주소, 지번주소, 경도, 위도,
 * 전화번호, 홈페이지, 아바타사진, 시장 부제목, 점포개수, 대표상품, 조회수, 떨어진거리, 상인 수, 팔로워 수, 팔로워 유무
 * 
 * @author dongjooKim
 */
public class Market extends Base {
	private MarketCategory marketCategory;
	private RegionCategory regionCategory;
	private String name;
	private String roadAddress;
	private String lotNumberAddress;
	private Double longitude;
	private Double latitude;
	private String phone;
	private String homepage;
	private String avatar;
	private String subName;
	private int storeCount;
	private String titleItems;
	private int hits;

	private double distance;
	private int userCount;
	private int followerCount;

	private Boolean isFollowed;

	public Market() {
		super();
	}

	public Market(long id, String createdAt, String updatedAt) {
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

	public MarketCategory getMarketCategory() {
		return marketCategory;
	}

	public void setMarketCategory(MarketCategory marketCategory) {
		this.marketCategory = marketCategory;
	}

	public RegionCategory getRegionCategory() {
		return regionCategory;
	}

	public void setRegionCategory(RegionCategory regionCategory) {
		this.regionCategory = regionCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoadAddress() {
		return roadAddress;
	}

	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}

	public String getLotNumberAddress() {
		return lotNumberAddress;
	}

	public void setLotNumberAddress(String lotNumberAddress) {
		this.lotNumberAddress = lotNumberAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(int storeCount) {
		this.storeCount = storeCount;
	}

	public String getTitleItems() {
		return titleItems;
	}

	public void setTitleItems(String titleItems) {
		this.titleItems = titleItems;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
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
