package com.djunderworld.stm.common.dao;

/**
 *
 * 팔로우 정보 엔티티 클래스
 * 
 * 상인 정보, 팔로우 정보
 * 
 * @author dongjooKim
 */
public class MerchantFollower extends Base {
	private User merchant;
	private User user;

	public MerchantFollower() {
		super();
	}

	public MerchantFollower(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public User getMerchant() {
		return merchant;
	}

	public void setMerchant(User merchant) {
		this.merchant = merchant;
	}

}
