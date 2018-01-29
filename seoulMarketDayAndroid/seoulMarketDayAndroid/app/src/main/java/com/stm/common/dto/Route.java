package com.stm.common.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;

    private List<Leg> legs;

    public OverviewPolyLine getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public List<Leg> getLegs() {
        return legs;
    }

}
