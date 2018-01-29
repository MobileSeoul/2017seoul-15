package com.djunderworld.stm.merchantfollower.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.djunderworld.stm.merchantfollower.service.MerchantFollowerService;


/**
 * 상인 팔로우 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/merchantfollowers")
public class MerchantFollowerController {
	
	@Autowired
	private MerchantFollowerService merchantFollowerService;
	
}
