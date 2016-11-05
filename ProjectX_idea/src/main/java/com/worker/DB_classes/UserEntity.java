package com.worker.DB_classes;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;

/**
 * Created by AsmodeusX on 05.11.2016.
 */
@Entity
@Table(name = "user", schema = "projectx", catalog = "")
public class UserEntity implements IsSerializable {
    private int id;
    private String login;
    private String password;
    private String email;
    private byte loggedIn;
    private String sessionId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 32)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 32)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "LoggedIn", nullable = false)
    public byte getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(byte loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Basic
    @Column(name = "SessionId", nullable = true, length = 16)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (loggedIn != that.loggedIn) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (int) loggedIn;
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }
}
