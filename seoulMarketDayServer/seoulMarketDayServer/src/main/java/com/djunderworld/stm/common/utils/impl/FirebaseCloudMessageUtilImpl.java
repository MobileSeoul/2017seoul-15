package com.djunderworld.stm.common.utils.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.djunderworld.stm.common.dto.DataMessage;
import com.djunderworld.stm.common.dto.FirebaseResponse;
import com.djunderworld.stm.common.dto.NotificationMessage;
import com.djunderworld.stm.common.utils.FirebaseCloudMessageUtil;

/**
 * firebase cloud messaging service 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 */
@Service
public class FirebaseCloudMessageUtilImpl implements FirebaseCloudMessageUtil {
	@Value("#{fcm['fcm.serverKey']}")
	private String FCM_SERVER_KEY;

	@Value("#{fcm['fcm.endPoint']}")
	private String FCM_ENDPONIT;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * firebase cloud messaging service 데이터 메시지 전송 함수
	 * 
	 * @author dongjooKim
	 * @param httpEntity
	 * @return CompletableFuture<FirebaseResponse>
	 */
	@Override
	public CompletableFuture<FirebaseResponse> postDataMessage(HttpEntity<DataMessage> httpEntity) {
		FirebaseResponse firebaseResponse = restTemplate.postForObject(FCM_ENDPONIT, httpEntity,
				FirebaseResponse.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}

	/**
	 * firebase cloud messaging service 알림 메시지 전송 함수
	 * 
	 * @author dongjooKim
	 * @param httpEntity
	 * @return CompletableFuture<FirebaseResponse>
	 */
	@Override
	public CompletableFuture<FirebaseResponse> postNotificationMessage(HttpEntity<NotificationMessage> httpEntity) {
		FirebaseResponse firebaseResponse = restTemplate.postForObject(FCM_ENDPONIT, httpEntity,
				FirebaseResponse.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}

	/**
	 * firebase cloud messaging service HttpHeaer 생성 함수
	 * 
	 * @author dongjooKim
	 * @return HttpHeaders
	 */
	@Override
	public HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "key=" + FCM_SERVER_KEY);
		httpHeaders.setContentType(new MediaType("application", "json"));

		return httpHeaders;
	}

}
