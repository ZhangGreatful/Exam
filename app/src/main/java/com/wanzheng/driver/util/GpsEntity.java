package com.wanzheng.driver.util;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/2 0002.
 */

public class GpsEntity implements Serializable {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
