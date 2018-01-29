package com.djunderworld.stm.firebasenotification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.firebasenotification.service.FirebaseNotificationService;

/**
 * 파이어베이스 알림 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/notifications")
public class FirebaseNotificationController {

	@Autowired
	private FirebaseNotificationService firebaseNotificationService;

	/**
	 * 파이어베이스 알림 수정 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param firebaseNotification
	 * @throws Exception
	 * 
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateFirebaseNotification(@PathVariable long id,
			@RequestBody FirebaseNotification firebaseNotification) throws Exception {
		firebaseNotificationService.updateFirebaseNotification(firebaseNotification);
	}
}
