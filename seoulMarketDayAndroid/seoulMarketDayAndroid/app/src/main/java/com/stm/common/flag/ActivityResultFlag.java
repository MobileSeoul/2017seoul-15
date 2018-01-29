package com.stm.common.flag;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class ActivityResultFlag {
    /**
     *  request
     *
     *  액티비티 요청 코드
     *
     */
    public static final int STORY_CREATE_REQEUST = 10001;
    public static final int STORY_EDIT_REQEUST = 10010;

    public static final int STORY_COMMENT_REQEUST = 10002;
    public static final int STORY_COMMENT_REPLY_REQEUST = 10003;
    public static final int COMMENT_DIALOG_REQUEST = 10004;
    public static final int COMMENT_EDIT_REQUEST = 10005;
    public static final int STORY_DIALOG_REQUEST = 10006;
    public static final int USER_REQUEST_FROM_MERCHANT_LIST = 10007;
    public static final int SEARCH_TAG_REQUEST = 10008;

    public static final int USER_EDIT_REQUEST = 10011;
    public static final int USER_EDIT_DIALOG_REQUEST = 10012;

    public static final int OPINION_CATEGORY_DIALOG_REQUEST = 10013;
    public static final int EXIT_CATEGORY_DIALOG_REQUEST = 10014;



    /**
     * Media store request code(사진, 비디오, VR360, 아바타 사진, 커버사진)
     */
    public static final int PICK_VR_REQUEST = 20003;
    public static final int PICK_VIDEO_REQUEST = 20002;
    public static final int PICK_PHOTO_REQUEST = 20001;

    public static final int PICK_AVATAR_PHOTO_REQUEST = 20004;
    public static final int PICK_COVER_PHOTO_REQUEST = 20005;
    /**
     * 상인 가입시 시장 선택 결과를 받기 위한 코드
     */
    public static final int SEARCH_MARKET_REQUEST = 20001;



    /**
     * result
     * <p>
     * 액티비티 결과 코드
     */
    public static final int RESULT_OK = -1;
    public static final int RESULT_CANCEL = 0;
    public static final int RESULT_LOGIN = 2;

    public static final int RESULT_EDIT = 3;
    public static final int RESULT_DELETE = 4;

    /**
     *
     *
     */
}
