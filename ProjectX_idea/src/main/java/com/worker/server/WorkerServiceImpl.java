package com.worker.server;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
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
    private static long serialVersionUID = 1456105400553118L;
    private int lengthMessageHistory = 15;

    public Boolean addCurrentGeo(double latitude, double longitude, String userAgent)
    {
        return HW.addGeo(getUserFromCurrentSession(), latitude, longitude, userAgent);
    }

    public ArrayList<UserEntity> searchUsers(String str)
    {
        return HW.searchUsers(str);
    }

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
            HW.setUserSession(user, SessionID);
        }
        return user;
    }

    public Boolean registerNewUser(String login, String name, String surname, String passwd, String eMail)
    {
        return HW.registerNewUser(login, name, surname, passwd, eMail);
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

    public Boolean saveNewMessage(String message, int idfrom, String loginAddressee, Timestamp DateMessage) {
        return HW.saveNewMessage(message,idfrom,loginAddressee, DateMessage);
    }

    public String getLastUnreadMessage(int idfrom, String loginAddressee, String messages) {
        List lastUnreadMessages =  HW.getLastUnreadMessage(idfrom,loginAddressee, lengthMessageHistory);

        if(lastUnreadMessages != null) {
            StringBuilder history = new StringBuilder();
            MessagesEntity message = null;

            history.append(messages);
            for (int i = lastUnreadMessages.size() - 1; i >= 0; i--) {
                message = (MessagesEntity) lastUnreadMessages.get(i);
                history.append("<p class='message-to-me'>" + message.getMessage()
                        + " | " + formatDate(message.getDateMessage()) + "</p>");
            }
            return history.toString();
        }
        return null;
    }

    public String[] getMessageHistory(int idfrom, String loginAddressee, Timestamp time) {
        List messageHistory =  HW.getMessageHistory(idfrom,loginAddressee, lengthMessageHistory, time);

        if(messageHistory != null) {
            String[] result = new String[2];
            StringBuilder history = new StringBuilder();

            MessagesEntity message = (MessagesEntity) messageHistory.get(messageHistory.size() - 1);
            result[0] = message.getDateMessage().toString();

            for (int j = messageHistory.size() - 1; j >= 0; j--) {
                message = (MessagesEntity) messageHistory.get(j);
                if (message.getIdfrom() != idfrom) history.append("<p class='message-to-me'>" + message.getMessage()
                        + " | " + formatDate(message.getDateMessage()) + "</p>");
                else history.append("<p class='message-from-me'>"
                        + message.getMessage() + " | " + formatDate(message.getDateMessage()) +  "</p>");
               /* if (message.getIdfrom() != idfrom) history.append("<p align='left' style='overflow-wrap:" +
                        " break-word; width: 320px; color: #ff6c36;'>" + message.getMessage()
                        + " | " + formatDate(message.getDateMessage()) + "</p>");
                else history.append("<p align='right' style='overflow-wrap: break-word; width: 320px; color: #4B0082;'>"
                        + message.getMessage() + " | " + formatDate(message.getDateMessage()) +  "</p>");*/
            }
            result[1] = history.toString();

            return result;
        }
        return null;
    }

    public String[][] getCountOfUnreadMessages(int idto) {
        List<Object[]> countUnreadMessages = HW.getCountOfUnreadMessages(idto);

        if(countUnreadMessages != null) {
            String[][] res = new String[countUnreadMessages.size()][2];
            int i = 0;

            for (Object[] obj : countUnreadMessages) {
                res[i][0] = obj[0].toString() + " " + obj[1].toString();
                res[i][1] = ": " + obj[2];
                i++;
            }
            return  res;
        }
        return null;
    }

    public List<UserEntity> getInvites()
    {
        return HW.getInvites(getUserFromCurrentSession());
    }

    public void confirmFriendship(UserEntity usr)
    {
        HW.confirmFriendship(getUserFromCurrentSession(), usr);
    }

    public boolean addFriend(UserEntity usr)
    {
        UserEntity currentUser = this.getUserFromCurrentSession();
        if (currentUser.getId() == usr.getId())
        {
            throw(new Error("Can't send invite to yourself"));
        }
        return HW.addFriend(this.getUserFromCurrentSession(), usr);
    }

    public List<UserEntity> getFriends() {
            return HW.getFriends(getUserFromCurrentSession().getId());
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
        HW.clearUserSession(user);
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