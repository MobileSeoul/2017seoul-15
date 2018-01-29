package com.djunderworld.stm.common.dao;

import java.io.Serializable;

/**
 * 
 * 엔티티 디폴트 클래스
 * 
 * 아이디, 생성일, 수정일
 * 
 * @author dongjooKim
 */
public abstract class Base implements Serializable{
	private long id;
	private String createdAt;
	private String updatedAt;

	public Base() {
		super();
	}

	public Base(long id, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}
