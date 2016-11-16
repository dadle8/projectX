package com.worker.DB_classes;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by AsmodeusX on 16.11.2016.
 */
@Entity
@Table(name = "geo", schema = "projectx", catalog = "")
public class GeoEntity {
    private int id;
    private Double latitude;
    private Double longitude;
    private Integer userid;
    private Date time;
    private String device;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "latitude", nullable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "userid", nullable = true)
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "device", nullable = true, length = 128)
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoEntity geoEntity = (GeoEntity) o;

        if (id != geoEntity.id) return false;
        if (latitude != null ? !latitude.equals(geoEntity.latitude) : geoEntity.latitude != null) return false;
        if (longitude != null ? !longitude.equals(geoEntity.longitude) : geoEntity.longitude != null) return false;
        if (userid != null ? !userid.equals(geoEntity.userid) : geoEntity.userid != null) return false;
        if (time != null ? !time.equals(geoEntity.time) : geoEntity.time != null) return false;
        if (device != null ? !device.equals(geoEntity.device) : geoEntity.device != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (device != null ? device.hashCode() : 0);
        return result;
    }
}
