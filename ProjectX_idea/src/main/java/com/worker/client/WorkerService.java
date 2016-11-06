package com.worker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.worker.DB_classes.UserEntity;

import javax.servlet.http.HttpSession;

@RemoteServiceRelativePath("WorkerService")
public interface WorkerService extends RemoteService {
    boolean Auth(String login, String passwd);
    UserEntity loginServer(String login ,String passwd);
    UserEntity loginFromSessionServer();
    boolean changePassword(String name, String newPassword);
    UserEntity getUserFromCurrentSession();
    void logout();

    public static class App {
        private static WorkerServiceAsync ourInstance = GWT.create(WorkerService.class);
        public static synchronized WorkerServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
