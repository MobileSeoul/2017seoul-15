package com.djunderworld.stm.common.flag;

/**
 * 파일 플래그 클래스
 * 
 * 유저 디폴트 사진 이름 정보(아바타,커버), 비디오 및 비디오 썸네일 확장자 정보, 파일 게시분류 정보(1.스토리, 2.댓글), 파일 타입
 * 정보(1.사진, 2.비디오, 3.VR360, 4.비디오 썸네일) , 파일 별 컨텐츠 타입(사진, 비디오, VR360, 비디오 썸네일, 유저
 * 아바타 사진, 유저 커버 사진)
 * 
 * @author dongjooKim
 */
public class FileFlag {
	/**
	 * 유저 디폴트 사진 이름 정보(아바타,커버)
	 * 
	 * @author dongjooKim
	 */
	public static final String USER_AVATAR_DEFAULT = "avatar.jpg";
	public static final String USER_COVER_DEFAULT = "cover.jpg";

	/**
	 * 비디오 및 비디오 썸네일 확장자 정보
	 * 
	 * @author dongjooKim
	 */
	public static final String VIDEO_THUMBNAIL_EXT = "jpg";
	public static final String VIDEO_EXT = "mp4";

	/**
	 * 파일 게시분류 정보(1.스토리, 2.댓글)
	 * 
	 * @author dongjooKim
	 */
	public static final int STORY_POST = 1;
	public static final int COMMENT_POST = 2;

	/**
	 * 파일 타입 정보(1.사진, 2.비디오, 3.VR360, 4.비디오 썸네일)
	 * 
	 * @author dongjooKim
	 */
	public static final int PHOTO_TYPE = 1;
	public static final int VIDEO_TYPE = 2;
	public static final int VR360_TYPE = 3;
	public static final int VIDEO_THUMBNAIL_TYPE = 4;

	/**
	 * 파일 별 컨텐츠 타입(사진, 비디오, VR360, 비디오 썸네일, 유저 아바타 사진, 유저 커버 사진)
	 * 
	 * @author dongjooKim
	 */
	public static final String PHOTO_CONTENT_TYPE = "image/*";
	public static final String VIDEO_CONTENT_TYPE = "video/*";
	public static final String VR360_CONTENT_TYPE = "vr/*";
	public static final String VIDEO_THUMBNAIL_CONTENT_TYPE = "video_thumbnail/*";
	public static final String USER_AVATAR_CONTENT_TYPE = "avatar/*";
	public static final String USER_COVER_CONTENT_TYPE = "cover/*";

}
