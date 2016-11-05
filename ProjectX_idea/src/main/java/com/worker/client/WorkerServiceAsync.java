package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.worker.DB_classes.UserEntity;

public interface WorkerServiceAsync {
    void Auth(String login, String passwd, AsyncCallback<Boolean> async);
    void loginServer(String name, String password, AsyncCallback<UserEntity> async);
    void loginFromSessionServer(AsyncCallback<UserEntity> async);
    void changePassword(String name, String newPassword, AsyncCallback<Boolean> async);
    void logout(AsyncCallback<Void> async);
}
