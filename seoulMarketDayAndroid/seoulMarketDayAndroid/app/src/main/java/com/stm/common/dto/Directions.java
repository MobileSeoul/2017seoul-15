package com.stm.common.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class Directions {
    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }
}
