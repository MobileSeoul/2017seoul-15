package com.djunderworld.stm.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.file.service.FileService;

/**
 * 파일 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {

	@Autowired
	private FileMapper fileMapper;

	/**
	 * 파일 조회수 증가 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public void updateFileByHits(long id) throws Exception {
		fileMapper.updateFileByHits(id);
	}

}
