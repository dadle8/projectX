package com.worker.DB_classes;

import javax.persistence.*;

/**
 * Created by Слава on 17.11.2016.
 */
@Entity
@Table(name = "friend", schema = "projectx", catalog = "")
public class FriendEntity {
    private int id;
    private int userId;
    private int friendId;
    private byte accepted;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "friendId", nullable = false)
    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Basic
    @Column(name = "accepted", nullable = false)
    public byte getAccepted() {
        return accepted;
    }

    public void setAccepted(byte accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendEntity that = (FriendEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (friendId != that.friendId) return false;
        if (accepted != that.accepted) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + friendId;
        result = 31 * result + (int) accepted;
        return result;
    }
}
