package com.djunderworld.stm.common.utils;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

/**
 * AWS S3 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 */
public interface AwsS3Util {

	/**
	 * 파일 inputStream을 AWS S3 파일 업로드하고 파일이름을 리턴하는 함수
	 * 
	 * @author dongjooKim
	 * @param location
	 * @param inputStream
	 * @param ext
	 * 
	 * @return String
	 * 
	 * @throws AmazonServiceException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	String selectFileNameByUploadingFileAndExtToTheLocation(String location, InputStream inputStream, String ext)
			throws AmazonServiceException, IllegalStateException, IOException;

	/**
	 * AWS S3 버켓에서 파일이름으로 파일을 삭제하는 함수
	 * 
	 * @author dongjooKim
	 * @param location
	 * @param fileName
	 * @param ext
	 * 
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 */
	void deleteFileByLocationAndFileNameAndExt(String location, String fileName, String ext)
			throws AmazonServiceException, AmazonClientException;

	/**
	 * AWS S3 버켓에서 파일이름으로 파일을 삭제하는 함수
	 * 
	 * @author dongjooKim
	 * @param location
	 * @param fileName
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 */
	void deleteFileByLocationAndFileName(String location, String fileName)
			throws AmazonServiceException, AmazonClientException;

	/**
	 * 파일이름을 통해 파일 inputStream을 AWS S3 파일 업로드하고 파일이름을 리턴하는 함수
	 * 
	 * @author dongjooKim
	 * @param location
	 * @param inputStream
	 * @param fileName
	 * @param ext
	 * 
	 * @throws AmazonServiceException
	 * @throws IllegalStateException
	 * @throws IOException
	 * 
	 * @return String
	 */
	String selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(String location, InputStream inputStream,
			String fileName, String ext) throws AmazonServiceException, IllegalStateException, IOException;

}
