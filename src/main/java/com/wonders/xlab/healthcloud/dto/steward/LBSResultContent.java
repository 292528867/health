package com.wonders.xlab.healthcloud.dto.steward;

import java.util.ArrayList;

/**
 * Created by lixuanwu on 2015/5/25.
 */
public class LBSResultContent {

    private Integer uid;
    private Integer geotable_id;
    /**
     * poi地址
     */
    private String address;
    /**
     * poi所属省
     */
    private String province;
    /**
     * poi所属城市
     */
    private String city;
    /**
     *
     */
    private Integer create_time;
    /**
     * poi所属区
     */
    private String district;
    /**
     * 经纬度
     */
    private ArrayList location;
    /**
     * 坐标系定义
     */
    private Integer coord_type;
    /**
     * 坐标系定义
     */
    private Integer type;
    /**
     * poi的标签
     */
    private String tags;
    /**
     * 距离
     */
    private Integer distance;
    /**
     * 权重
     */
    private Integer weight;

    public LBSResultContent() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGeotable_id() {
        return geotable_id;
    }

    public void setGeotable_id(Integer geotable_id) {
        this.geotable_id = geotable_id;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(Integer coord_type) {
        this.coord_type = coord_type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ArrayList getLocation() {
        return location;
    }

    public void setLocation(ArrayList location) {
        this.location = location;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
