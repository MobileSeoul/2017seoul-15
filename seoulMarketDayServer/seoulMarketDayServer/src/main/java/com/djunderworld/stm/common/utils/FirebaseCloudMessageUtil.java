package com.djunderworld.stm.common.utils;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.djunderworld.stm.common.dto.DataMessage;
import com.djunderworld.stm.common.dto.FirebaseResponse;
import com.djunderworld.stm.common.dto.NotificationMessage;

/**
 * firebase cloud messaging service 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 */
public interface FirebaseCloudMessageUtil {

	/**
	 * firebase cloud messaging service 데이터 메시지 전송 함수
	 * 
	 * @author dongjooKim
	 * @param httpEntity
	 * @return CompletableFuture<FirebaseResponse>
	 */
	CompletableFuture<FirebaseResponse> postDataMessage(HttpEntity<DataMessage> httpEntity);

	/**
	 * firebase cloud messaging service 알림 메시지 전송 함수
	 * 
	 * @author dongjooKim
	 * @param httpEntity
	 * @return CompletableFuture<FirebaseResponse>
	 */
	CompletableFuture<FirebaseResponse> postNotificationMessage(HttpEntity<NotificationMessage> httpEntity);

	/**
	 * firebase cloud messaging service HttpHeaer 생성 함수
	 * 
	 * @author dongjooKim
	 * @return HttpHeaders
	 */
	HttpHeaders getHttpHeaders();
}
