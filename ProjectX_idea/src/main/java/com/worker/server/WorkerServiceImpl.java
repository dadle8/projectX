package com.worker.server;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.DB_classes.UserEntity;
import com.worker.DB_managing.HibernateWorker;
import com.worker.client.WorkerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class WorkerServiceImpl extends RemoteServiceServlet implements WorkerService {
    // Implementation of sample interface method
    private HibernateWorker HW = new HibernateWorker();
    private static long serialVersionUID = 1456105400553118785L;

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

    public UserEntity loginFromSessionServer()
    {
        return getUserAlreadyFromSession();
    }

    public void logout()
    {
        deleteUserFromSession();
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