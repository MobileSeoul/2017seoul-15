package com.stm.common.dto;

import com.stm.common.dto.Location;
import com.stm.common.dto.OverviewPolyLine;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class Step {
    private Location start_location;
    private Location end_location;
    private OverviewPolyLine polyline;

    public Location getStart_location() {
        return start_location;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public OverviewPolyLine getPolyline() {
        return polyline;
    }

}
