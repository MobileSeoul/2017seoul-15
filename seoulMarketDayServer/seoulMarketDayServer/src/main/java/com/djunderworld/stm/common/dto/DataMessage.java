package com.djunderworld.stm.common.dto;

import java.util.List;

import com.djunderworld.stm.common.dao.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 데이터 메시지 포멧 클래스
 * 
 * 수신자(토큰), 우선순위, 메시지 정보
 * 
 * @author dongjooKim
 */
@JsonInclude(Include.NON_NULL)
public class DataMessage {
	private String[] registration_ids;
	private String priority;
	private Data data;

	public DataMessage() {
		super();
	}

	public DataMessage(String[] registration_ids, String priority, Data data) {
		super();
		this.registration_ids = registration_ids;
		this.priority = priority;
		this.data = data;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String[] getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(String[] registration_ids) {
		this.registration_ids = registration_ids;
	}

	public String[] convertToRegistrationIdArrayFromUserList(List<User> users) {
		int userSize = users.size();
		String[] registrationIds = new String[userSize];

		for (int i = 0; i < userSize; i++) {
			registrationIds[i] = users.get(i).getFcmToken();
		}

		return registrationIds;
	}
}
