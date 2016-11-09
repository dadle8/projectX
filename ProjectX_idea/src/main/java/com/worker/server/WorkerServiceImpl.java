package com.worker.server;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.DB_classes.MessagesEntity;
import com.worker.DB_classes.UserEntity;
import com.worker.DB_managing.HibernateWorker;
import com.worker.client.ChatPage;
import com.worker.client.WorkerService;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class WorkerServiceImpl extends RemoteServiceServlet implements WorkerService {
    // Implementation of sample interface method
    private HibernateWorker HW = new HibernateWorker();
    private static long serialVersionUID = 1456105400553118785L;
    private int lengthMessageHistory = 10;

    public boolean Auth(String login, String passwd) {
        return false;
    }

    public UserEntity getUserFromCurrentSession()
    {
        return (UserEntity)this.getThreadLocalRequest().getSession().getAttribute("user");
    }

    private String generateNewSessionId()
    {
        serialVersionUID++;
        return Long.toString(serialVersionUID);
    }

    public UserEntity loginServer(String login, String passwd)
    {
        UserEntity user = HW.getUserByLogin(login);
        if (user == null)
            return null;
        System.err.println("Login:     " + user.getLogin());
        System.err.println("InPasswd:  " + passwd);
        System.err.println("CmpPasswd: " + user.getPassword());
        if (user.getPassword().equals(passwd)) {
            user.setLoggedIn((byte) 1);
            storeUserInSession(user);

            String SessionID = generateNewSessionId();
            System.err.println("Session:   " + SessionID);
            user.setSessionId(SessionID);
        }
        return user;
    }

    public Boolean registerNewUser(String login, String passwd, String eMail)
    {
        return HW.registerNewUser(login, passwd, eMail);
    }

    public UserEntity loginFromSessionServer()
    {
        return getUserAlreadyFromSession();
    }

    public void logout()
    {
        deleteUserFromSession();
    }

    public List getAllUsers(String login) {
        return HW.getAllUser(login);
    }

    public Boolean saveNewMessage(String message, int idfrom, String loginAddressee) {
        return HW.saveNewMessage(message,idfrom,loginAddressee);
    }

    public String getLastUnreadMessage(int idfrom, String loginAddressee, String messages) {
        List messageHistory =  HW.getLastUnreadMessage(idfrom,loginAddressee, lengthMessageHistory);
        StringBuilder history = new StringBuilder();
        MessagesEntity message = null;

        history.append(messages);
        if(!messageHistory.isEmpty()) {
            for (int i = messageHistory.size() - 1; i >= 0; i--) {
                message = (MessagesEntity) messageHistory.get(i);
                history.append("<p align='left'>" + message.getMessage() + "</p>");
            }

        }
        return history.toString();
    }

    public String[] getMessageHistory(int idfrom, String loginAddressee, Timestamp time, int i) {
        List messageHistory =  HW.getMessageHistory(idfrom,loginAddressee, lengthMessageHistory, time);
        String[] result = new String[2];

        StringBuilder history = new StringBuilder();
        if(!messageHistory.isEmpty()) {

            MessagesEntity message = (MessagesEntity) messageHistory.get(0);
            System.err.println( messageHistory.get(0)+"   " + message.getDateMessage().toString());
            System.err.println( messageHistory.get(messageHistory.size() - 1));
            result[0] = message.getDateMessage().toString();

            for (int j = messageHistory.size() - 1; j >= 0; j--) {
                message = (MessagesEntity) messageHistory.get(j);
                if (message.getIdfrom() != idfrom) history.append("<p align='left'>" + message.getMessage() + "</p>");
                else history.append("<p align='right'>" + message.getMessage() + "</p>");
            }
            result[1] = history.toString();
        }

        return result;
    }

    public boolean changePassword(String name, String newPassword)
    {
        // change password logic
        return false;
    }

    private UserEntity getUserAlreadyFromSession()
    {
        UserEntity user = null;
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof UserEntity)
        {
            user = (UserEntity) userObj;
        }
        return user;
    }

    private void storeUserInSession(UserEntity user)
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
        user.setSessionId(session.getId());
    }

    private void deleteUserFromSession()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        UserEntity user = (UserEntity)session.getAttribute("user");
        user.setLoggedIn((byte)0);
        user.setSessionId("");
        session.removeAttribute("user");
    }
}