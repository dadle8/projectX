package com.worker.shared;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Слава on 03.11.2016.
 */
@Entity
@Table(name = "geolocations", schema = "projectx")
public class GeolocationsEntity implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idGeolocation;
    private double geolocationX;
    private double geolocationY;
    private Date dateGeolocation;
    private Integer idPerson;
    private PersonsEntity personsByIdPerson;

    @Column(name = "idgeolocation", nullable = false)
    public int getIdGeolocation() {
        return idGeolocation;
    }

    public void setIdGeolocation(int idGeolocation) {
        this.idGeolocation = idGeolocation;
    }

    @Basic
    @Column(name = "geolocationX", nullable = false, precision = 6)
    public double getGeolocationX() {
        return geolocationX;
    }

    public void setGeolocationX(double geolocationX) {
        this.geolocationX = geolocationX;
    }

    @Basic
    @Column(name = "geolocationY", nullable = false, precision = 6)
    public double getGeolocationY() {
        return geolocationY;
    }

    public void setGeolocationY(double geolocationY) {
        this.geolocationY = geolocationY;
    }

    @Basic
    @Column(name = "dategeolocation", nullable = false)
    public Date getDateGeolocation() {
        return dateGeolocation;
    }

    public void setDateGeolocation(Date dateGeolocation) {
        this.dateGeolocation = dateGeolocation;
    }

    @Basic
    @Column(name = "idperson", nullable = true)
    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeolocationsEntity that = (GeolocationsEntity) o;

        if (idGeolocation != that.idGeolocation) return false;
        if (Double.compare(that.geolocationX, geolocationX) != 0) return false;
        if (Double.compare(that.geolocationY, geolocationY) != 0) return false;
        if (dateGeolocation != null ? !dateGeolocation.equals(that.dateGeolocation) : that.dateGeolocation != null)
            return false;
        if (idPerson != null ? !idPerson.equals(that.idPerson) : that.idPerson != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idGeolocation;
        temp = Double.doubleToLongBits(geolocationX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(geolocationY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateGeolocation != null ? dateGeolocation.hashCode() : 0);
        result = 31 * result + (idPerson != null ? idPerson.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idperson", referencedColumnName = "idperson")
    public PersonsEntity getPersonsByIdPerson() {
        return personsByIdPerson;
    }

    public void setPersonsByIdPerson(PersonsEntity personsByIdPerson) {
        this.personsByIdPerson = personsByIdPerson;
    }
}
