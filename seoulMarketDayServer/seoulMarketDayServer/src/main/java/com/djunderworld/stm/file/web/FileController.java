package com.djunderworld.stm.file.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.file.service.FileService;


/**
 * 파일 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 파일 조회수 증가  API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/{id}/hits", method = RequestMethod.PUT)
	@ResponseBody
	public void updateFileByHits(@PathVariable long id) throws Exception {
		fileService.updateFileByHits(id);
	}
}
