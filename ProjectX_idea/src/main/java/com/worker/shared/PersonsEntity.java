package com.worker.shared;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Слава on 03.11.2016.
 */
@Entity
@Table(name = "persons", schema = "projectx")
public class PersonsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idPerson;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Date lastdatelogin;
    private Integer isOnline;

    public PersonsEntity() {
    }

    @Column(name = "idperson", nullable = false)
    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 45)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "lastdatelogin", nullable = true)
    public Date getLastdatelogin() {
        return lastdatelogin;
    }

    public void setLastdatelogin(Date lastdatelogin) {
        this.lastdatelogin = lastdatelogin;
    }

    @Basic
    @Column(name = "isonline", nullable = true)
    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonsEntity that = (PersonsEntity) o;

        if (idPerson != that.idPerson) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (lastdatelogin != null ? !lastdatelogin.equals(that.lastdatelogin) : that.lastdatelogin != null)
            return false;
        if (isOnline != null ? !isOnline.equals(that.isOnline) : that.isOnline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPerson;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (lastdatelogin != null ? lastdatelogin.hashCode() : 0);
        result = 31 * result + (isOnline != null ? isOnline.hashCode() : 0);
        return result;
    }
}
