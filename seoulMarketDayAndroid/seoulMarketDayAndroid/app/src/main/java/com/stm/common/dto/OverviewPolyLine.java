package com.stm.common.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class OverviewPolyLine {
    @SerializedName("points")
    public String points;

    public String getPoints() {
        return points;
    }
}
