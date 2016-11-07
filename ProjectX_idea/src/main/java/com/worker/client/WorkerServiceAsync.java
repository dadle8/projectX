package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.worker.DB_classes.UserEntity;
import java.util.List;

import javax.servlet.http.HttpSession;

public interface WorkerServiceAsync {
    void Auth(String login, String passwd, AsyncCallback<Boolean> async);
    void loginServer(String login, String passwd, AsyncCallback<UserEntity> async);
    void registerNewUser(String login, String passwd, String eMail, AsyncCallback<Boolean> async);
    void loginFromSessionServer(AsyncCallback<UserEntity> async);
    void changePassword(String name, String newPassword, AsyncCallback<Boolean> async);
    void logout(AsyncCallback<Void> async);
    void getUserFromCurrentSession(AsyncCallback<UserEntity> async);

    void getAllUsers(String login, AsyncCallback<List> async);
}
