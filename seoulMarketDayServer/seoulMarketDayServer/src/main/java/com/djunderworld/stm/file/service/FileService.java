package com.djunderworld.stm.file.service;

/**
 * 파일 관련 서비스 레이어 인터페이스 
 * 
 * @author dongjooKim
 * 
 */
public interface FileService {

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
	void updateFileByHits(long id) throws Exception;

}
