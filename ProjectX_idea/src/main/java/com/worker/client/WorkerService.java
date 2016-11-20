package com.worker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.web.bindery.requestfactory.server.Pair;
import com.worker.DB_classes.UserEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

@RemoteServiceRelativePath("WorkerService")
public interface WorkerService extends RemoteService {
    void confirmFriendship(UserEntity usr);
    List<UserEntity> getInvites();
    boolean addFriend(UserEntity usr);
    ArrayList<UserEntity> searchUsers(String str);
    ArrayList<DoublePoint> getPath(Integer id);
    boolean Auth(String login, String passwd);
    UserEntity loginServer(String login ,String passwd);
    Boolean registerNewUser(String login, String name, String surname, String passwd, String eMail);
    UserEntity loginFromSessionServer();
    boolean changePassword(String name, String newPassword);
    UserEntity getUserFromCurrentSession();
    void logout();
    List getAllUsers(String login);
    Boolean saveNewMessage(String message, int idfrom, String loginAddressee);
    String getLastUnreadMessage(int idfrom, String loginAddressee, String messages);
    String[] getMessageHistory(int idfrom, String loginAddressee, Timestamp time, int i);
    String[] getCountOfUnreadMessages(int idto);
    List<UserEntity> getFriends();
    public static class App {
        private static WorkerServiceAsync ourInstance = GWT.create(WorkerService.class);
        public static synchronized WorkerServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
