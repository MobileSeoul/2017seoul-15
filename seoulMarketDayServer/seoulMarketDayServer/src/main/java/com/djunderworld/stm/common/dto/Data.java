package com.djunderworld.stm.common.dto;

/**
 * 데이터 메시지 포멧 데이터 클래스
 * 
 * 메시지 정보
 * 
 * @author dongjooKim
 */
public class Data {
	private String body;

	public Data() {
		super();
	}

	public Data(String body) {
		super();
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
