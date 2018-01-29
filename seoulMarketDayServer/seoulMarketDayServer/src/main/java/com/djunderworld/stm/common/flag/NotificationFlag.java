package com.djunderworld.stm.common.flag;

/**
 * firebase cloud messaging service 플래그 클래스
 * 
 * 수신자 유형, 발신자 행위, 메시지 페이지 이동 경로 정보
 * 
 * @author dongjooKim
 */
public class NotificationFlag {

	/**
	 * 데이터 메시지 수신자 유형 정보
	 * 
	 * 1:자신, 2:작성자(상인), 3:팔로우된 상인, 4:팔로워 전체
	 * 
	 * @author dongjooKim
	 */
	public static final long TO_ME = 1;
	public static final long TO_WRITER = 2;
	public static final long TO_MERCHANT = 3;
	public static final long TO_FOLLOWERS = 4;

	/**
	 * 데이터 메시지 발신자 행위 정보
	 * 
	 * 1:스토리 작성, 2:댓글 작성, 3:스토리 좋아요, 4:팔로잉
	 * 
	 * @author dongjooKim
	 */
	public static final long WRITING_THE_STORY = 1;
	public static final long WRITING_THE_COMMENT = 2;
	public static final long LIKE_THE_STORY = 3;
	public static final long FOLLOWING_THE_MERCHANT = 4;

	/**
	 * 데이터 메시지 페이지 이동 경로 정보
	 * 
	 * 1:스토리 페이지, 2:일반 유저 페이지, 3:상인 유저 페이지
	 * 
	 * @author dongjooKim
	 */
	public static final long NAVIGATE_TO_THE_PAGE_OF_STORY = 1;
	public static final long NAVIGATE_TO_THE_PAGE_OF_USER = 2;
	public static final long NAVIGATE_TO_THE_PAGE_OF_MERCHANT = 3;


	/**
	 * 데이터 메시지 내용 목적어 정보
	 * 
	 * ex.) 스토리, 회원님의 게시글, 회원님의 댓글, 회원님
	 * 
	 * @author dongjooKim
	 */
	public static final String CONTENT_OBJECT_OF_WRITING_STORY = "스토리";
	public static final String CONTENT_OBJECT_OF_WRITING_COMMENT_OR_LIKE = "회원님의 게시글";
	public static final String CONTENT_OBJECT_OF_WRITING_REPLY_COMMENT = "회원님의 댓글";
	public static final String CONTENT_OBJECT_OF_FOLLOWING_MERCHANT = "회원님";
	
	/**
	 * 알림 메시지 내용 
	 * 
	 * ex.) 다른 기기에서 접속되어 강제 로그아웃합니다.
	 * 
	 * @author dongjooKim
	 */
	
	public static final String CONTENT_OF_FORCED_CLOSE_FOR_DUPLICATIVE_LOGIN = "다른 기기에서 접속되어 강제 로그아웃합니다.";
	
	/**
	 * firebase cloud messaging service priority 정보
	 * 
	 * high
	 * 
	 * @author dongjooKim
	 */
	public static final String HIGH_PRIORITY = "high";

}
