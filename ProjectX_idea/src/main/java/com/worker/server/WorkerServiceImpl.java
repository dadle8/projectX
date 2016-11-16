package com.worker.server;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.web.bindery.requestfactory.server.Pair;
import com.worker.DB_classes.MessagesEntity;
import com.worker.DB_classes.UserEntity;
import com.worker.DB_managing.HibernateWorker;
import com.worker.client.DoublePoint;
import com.worker.client.WorkerService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WorkerServiceImpl extends RemoteServiceServlet implements WorkerService {
    // Implementation of sample interface method
    private HibernateWorker HW = new HibernateWorker();
    private static long serialVersionUID = 1456105400553118785L;
    private int lengthMessageHistory = 15;

    public ArrayList<DoublePoint> getPath(Integer id)
    {
        ArrayList<DoublePoint> ans = new ArrayList<DoublePoint>();
        ans = HW.getPath(id);
        System.err.println(id + " " + ans);
        return ans;
    }

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
        StringBuilder history = null;
        MessagesEntity message = null;

        if(!messageHistory.isEmpty()) {
            history = new StringBuilder();
            history.append(messages);
            for (int i = messageHistory.size() - 1; i >= 0; i--) {
                message = (MessagesEntity) messageHistory.get(i);
                history.append("<p align='left' style='overflow-wrap:" +
                        " break-word; width: 320px; color: #ff6c36;'>" + message.getMessage()
                        + " | " + formatDate(message.getDateMessage()) + "</p>");
            }
            return history.toString();
        }
        return null;
    }

    public String[] getMessageHistory(int idfrom, String loginAddressee, Timestamp time, int i) {
        List messageHistory =  HW.getMessageHistory(idfrom,loginAddressee, lengthMessageHistory, time);
        String[] result = null;

        StringBuilder history = new StringBuilder();

        if(!messageHistory.isEmpty()) {
            result = new String[2];
            MessagesEntity message = (MessagesEntity) messageHistory.get(messageHistory.size() - 1);
            result[0] = message.getDateMessage().toString();

            for (int j = messageHistory.size() - 1; j >= 0; j--) {
                message = (MessagesEntity) messageHistory.get(j);
                if (message.getIdfrom() != idfrom) history.append("<p align='left' style='overflow-wrap:" +
                        " break-word; width: 320px; color: #ff6c36;'>" + message.getMessage()
                        + " | " + formatDate(message.getDateMessage()) + "</p>");
                else history.append("<p align='right' style='overflow-wrap: break-word; width: 320px; color: #4B0082;'>"
                        + message.getMessage() + " | " + formatDate(message.getDateMessage()) +  "</p>");
            }
            result[1] = history.toString();
        }

        return result;
    }

    public String[] getCountOfUnreadMessages(int idto) {
        List<Object[]> countUnreadMessages = HW.getCountOfUnreadMessages(idto);

        String[] res = null;
        if(countUnreadMessages != null) {
            res = new String[countUnreadMessages.size()];
            int i = 0;
            for (Object[] obj : countUnreadMessages) {
                res[i] = obj[0] + ": " + obj[1];
                i++;
            }

        }

        return res;
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

    private String formatDate(Timestamp time)
    {
        String pattern = "HH:mm";
        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
        DateTimeFormat timeFormat = new DateTimeFormat(pattern, info) {}; // <- It is trick.

        return timeFormat.format(time);
    }
}