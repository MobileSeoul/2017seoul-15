package com.djunderworld.stm.common.dao;

/**
 * 
 * 시장 분류 정보 엔티티 클래스
 * 
 * 시장 분류 이름정보(1.일반시장, 2.전문시장)
 * 
 * @author dongjooKim
 */
public class MarketCategory extends Base  {
	private String name;

	public MarketCategory() {
		super();
	}

	public MarketCategory(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public MarketCategory(String name) {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketCategory other = (MarketCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MarketCategory [name=" + name + ", getId()=" + getId() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getUpdatedAt()=" + getUpdatedAt() + ", getName()=" + getName() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

}
