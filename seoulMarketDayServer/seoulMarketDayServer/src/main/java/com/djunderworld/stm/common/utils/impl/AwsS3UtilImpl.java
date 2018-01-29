package com.djunderworld.stm.common.utils.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.djunderworld.stm.common.utils.AwsS3Util;

/**
 * AWS S3 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 */
@Service
public class AwsS3UtilImpl implements AwsS3Util {

	@Autowired
	private AmazonS3Client amazonS3Client;

	@Value("#{aws['aws.s3.bucket']}")
	private String BUCKET;

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
	@Override
	public String selectFileNameByUploadingFileAndExtToTheLocation(String location, InputStream inputStream, String ext)
			throws AmazonServiceException, IllegalStateException, IOException {
		String fileName = System.currentTimeMillis() + "";

		ObjectMetadata objectMetadata = new ObjectMetadata();
		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName + "." + ext,
				inputStream, objectMetadata);

		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3Client.putObject(putObjectRequest);

		return fileName;
	}

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
	@Override
	public void deleteFileByLocationAndFileNameAndExt(String location, String fileName, String ext)
			throws AmazonServiceException, AmazonClientException {
		amazonS3Client.deleteObject(new DeleteObjectRequest(BUCKET, location + "/" + fileName + "." + ext));
	}

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
	@Override
	public String selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(String location, InputStream inputStream,
			String fileName, String ext) throws AmazonServiceException, IllegalStateException, IOException {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName + "." + ext,
				inputStream, objectMetadata);

		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3Client.putObject(putObjectRequest);

		return fileName;
	}

	/**
	 * AWS S3 버켓에서 파일이름으로 파일을 삭제하는 함수
	 * 
	 * @author dongjooKim
	 * @param location
	 * @param fileName
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 */
	@Override
	public void deleteFileByLocationAndFileName(String location, String fileName)
			throws AmazonServiceException, AmazonClientException {
		amazonS3Client.deleteObject(new DeleteObjectRequest(BUCKET, location + "/" + fileName));
	}

}
