package com.worker.DB_classes;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Слава on 08.11.2016.
 */
@Entity
@Table(name = "messages", schema = "projectx", catalog = "")
public class MessagesEntity implements IsSerializable {
    private int id;
    private String message;
    private Date dateMessage;
    private int isread;
    private int idfrom;
    private int idto;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message", nullable = false, length = 1024)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "datemessage", nullable = false)
    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    @Basic
    @Column(name = "isread", nullable = false)
    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    @Basic
    @Column(name = "idfrom", nullable = false)
    public int getIdfrom() {
        return idfrom;
    }

    public void setIdfrom(int idfrom) {
        this.idfrom = idfrom;
    }

    @Basic
    @Column(name = "idto", nullable = false)
    public int getIdto() {
        return idto;
    }

    public void setIdto(int idto) {
        this.idto = idto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagesEntity that = (MessagesEntity) o;

        if (id != that.id) return false;
        if (isread != that.isread) return false;
        if (idfrom != that.idfrom) return false;
        if (idto != that.idto) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (dateMessage != null ? !dateMessage.equals(that.dateMessage) : that.dateMessage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (dateMessage != null ? dateMessage.hashCode() : 0);
        result = 31 * result + isread;
        result = 31 * result + idfrom;
        result = 31 * result + idto;
        return result;
    }
}
