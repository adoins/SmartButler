package com.liuguilin.butlerservice.entity;

/*
 *  项目名: SmartButler_Sunny
 *  包名:   com.example.a67045.smartbutler_sunny.entity
 *  文件名: CourierData
 *  创建者: Sunny
 *  创建时间: 2017/1/21 10:27
 *  描述:   TODO
 */
public class CourierData {

    private String datetime;
    private String remark;
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
