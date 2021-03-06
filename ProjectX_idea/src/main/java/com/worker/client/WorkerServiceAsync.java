package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.worker.DB_classes.UserEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface WorkerServiceAsync {
    void addCurrentGeo(double latitude, double longitude, String userAgent, AsyncCallback<Boolean> async);
    void confirmFriendship(UserEntity usr, AsyncCallback<Void> async);
    void getInvites(AsyncCallback<List<UserEntity>> async);
    void addFriend(UserEntity usr, AsyncCallback<Boolean> async);
    void searchUsers(String str, AsyncCallback<ArrayList<UserEntity>> async);
    void getPath(Integer id, AsyncCallback<ArrayList<DoublePoint>> async);
    void Auth(String login, String passwd, AsyncCallback<Boolean> async);
    void loginServer(String login, String passwd, AsyncCallback<UserEntity> async);
    void registerNewUser(String login, String name, String surname, String passwd, String eMail, AsyncCallback<Boolean> async);
    void loginFromSessionServer(AsyncCallback<UserEntity> async);
    void changePassword(String name, String newPassword, AsyncCallback<Boolean> async);
    void logout(AsyncCallback<Void> async);
    void getUserFromCurrentSession(AsyncCallback<UserEntity> async);
    void getAllUsers(String login, AsyncCallback<List> async);
    void saveNewMessage(String message, int idfrom, String loginAddressee, Timestamp DateMessage, AsyncCallback<Boolean> async);
    void getLastUnreadMessage(int idfrom, String loginAddressee, String messages, AsyncCallback<String> async);
    void getMessageHistory(int idfrom, String loginAddressee, Timestamp time, AsyncCallback<String[]> async);
    void getCountOfUnreadMessages(int idto, AsyncCallback<String[][]> async);
    void getFriends(AsyncCallback<List<UserEntity>> async);
}
