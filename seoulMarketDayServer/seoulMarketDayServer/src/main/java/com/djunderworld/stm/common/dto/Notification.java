package com.djunderworld.stm.common.dto;
/**
 * 알림 메시지 포멧 알림 클래스
 * 
 * 메시지 정보
 * 
 * @author dongjooKim
 */
public class Notification {
	private String body;

	public Notification() {
		super();
	}

	public Notification(String body) {
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
