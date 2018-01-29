package com.stm.common.flag;

/**
 * 파일 디폴트 플래그 클래스
 * <p>
 * 마켓 사진, 유저사진 디폴트 파일 정보
 */
public class DefaultFileFlag {

    /**
     * 마켓 사진 디폴트 파일 이름
     */
    public static final String MARKET_AVARTAR_NAME = "market_avatar.jpg";

    /**
     * 유저 사진 (1:아바타, 2:커버)
     */
    public static final int USER_AVATAR_TYPE = 1;
    public static final int USER_COVER_TYPE = 2;

    /**
     * 미디어 파일 확장자 (비디오, 비디오 썸네일)
     */
    public static final String VIDEO_EXT = "mp4";
    public static final String VIDEO_THUMBNAIL_EXT = "jpg";


    /**
     * 미디어 파일 타입 (1:사진, 2:비디오, 3:VR360, 4:비디오 썸네일)
     */
    public static final int PHOTO_TYPE = 1;
    public static final int VIDEO_TYPE = 2;
    public static final int VR360_TYPE = 3;
    public static final int VIDEO_THUMBNAIL_TYPE = 4;


    /**
     * 미디어 파일 content 타입 (1:사진, 2:비디오, 3:VR360, 4:비디오 썸네일)
     */

    public static final String PHOTO_CONTENT_TYPE = "image/*";
    public static final String VIDEO_CONTENT_TYPE = "video/*";
    public static final String VR360_CONTENT_TYPE = "vr/*";
    public static final String VIDEO_THUMBNAIL_CONTENT_TYPE = "video_thumbnail/*";

    /**
     * 유저 사진 파일 content 타입 (아바타, 커버)
     */
    public static final String USER_AVATAR_CONTENT_TYPE = "avatar/*";
    public static final String USER_COVER_CONTENT_TYPE = "cover/*";

    /**
     * 파일 업로드 최대 허용 수 (10), 최대 크기 (20MB)
     */
    public static final int MAX_FILE_COUNT = 10;
    public static final long MAX_FILE_SIZE = 167772160;

    /**
     * 파일 게시 유형
     */
    public static final int STORY_POST_FILE = 1;
    public static final long STORY_COMMENT_POST_FILE = 2;

}
