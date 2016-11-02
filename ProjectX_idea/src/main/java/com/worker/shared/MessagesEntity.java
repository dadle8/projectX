package com.worker.shared;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Слава on 03.11.2016.
 */
@Entity
@Table(name = "messages", schema = "projectx")
public class MessagesEntity {
    private int idMessage;
    private String message;
    private Date dateMessage;
    private int isRead;
    private Integer idFrom;
    private Integer idTo;
    private PersonsEntity personsByIdFrom;
    private PersonsEntity personsByIdTo;

    @Id
    @Column(name = "id_message", nullable = false)
    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
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
    @Column(name = "date_message", nullable = false)
    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    @Basic
    @Column(name = "is_read", nullable = false)
    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Basic
    @Column(name = "id_from", nullable = true)
    public Integer getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    @Basic
    @Column(name = "id_to", nullable = true)
    public Integer getIdTo() {
        return idTo;
    }

    public void setIdTo(Integer idTo) {
        this.idTo = idTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagesEntity that = (MessagesEntity) o;

        if (idMessage != that.idMessage) return false;
        if (isRead != that.isRead) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (dateMessage != null ? !dateMessage.equals(that.dateMessage) : that.dateMessage != null) return false;
        if (idFrom != null ? !idFrom.equals(that.idFrom) : that.idFrom != null) return false;
        if (idTo != null ? !idTo.equals(that.idTo) : that.idTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMessage;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (dateMessage != null ? dateMessage.hashCode() : 0);
        result = 31 * result + isRead;
        result = 31 * result + (idFrom != null ? idFrom.hashCode() : 0);
        result = 31 * result + (idTo != null ? idTo.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_from", referencedColumnName = "id_person")
    public PersonsEntity getPersonsByIdFrom() {
        return personsByIdFrom;
    }

    public void setPersonsByIdFrom(PersonsEntity personsByIdFrom) {
        this.personsByIdFrom = personsByIdFrom;
    }

    @ManyToOne
    @JoinColumn(name = "id_to", referencedColumnName = "id_person")
    public PersonsEntity getPersonsByIdTo() {
        return personsByIdTo;
    }

    public void setPersonsByIdTo(PersonsEntity personsByIdTo) {
        this.personsByIdTo = personsByIdTo;
    }
}
