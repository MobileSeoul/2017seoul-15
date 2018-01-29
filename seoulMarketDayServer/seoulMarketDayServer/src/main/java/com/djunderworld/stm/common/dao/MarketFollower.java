package com.djunderworld.stm.common.dao;


/**
*
* 시장 팔로우 정보 엔티티 클래스
* 
* 시장 정보, 팔로우 정보
* 
* @author dongjooKim
*/
public class MarketFollower extends Base {
	private Market market;
	private User follower;

	public MarketFollower() {
		super();
	}

	public MarketFollower(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
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
