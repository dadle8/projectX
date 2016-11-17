package com.worker.DB_managing;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;

import com.google.web.bindery.requestfactory.server.Pair;
import com.worker.DB_classes.MessagesEntity;
import com.worker.DB_classes.UserEntity;
import com.worker.client.DoublePoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker implements Serializable {
    private HibUtil HibUtils = new HibUtil();
    private SessionFactory factory = HibUtil.getSessionFactory();

    public HibernateWorker ()
    {

    }

    public ArrayList<DoublePoint> getPath(Integer id)
    {
        Session session = factory.openSession();

        List <Object[]> points = session.createQuery("SELECT G.latitude, G.longitude FROM com.worker.DB_classes.GeoEntity G WHERE G.userid = :id").setParameter("id", id).list();
        ArrayList<DoublePoint> ans = new ArrayList<DoublePoint>();
        for(Object[] point: points)
        {
            double latitude = (Double) point[0];
            double longitude = (Double) point[1];
            DoublePoint pt = new DoublePoint();
            pt.Set(latitude, longitude);
            ans.add(pt);
        }
        return ans;
    }

    public List getAllUser(String login)
    {
        Session session = factory.openSession();
        List users = session.createQuery("SELECT U.login FROM com.worker.DB_classes.UserEntity U WHERE U.login!= :login").setParameter("login",login).list();

        if(!users.isEmpty())
        {
            session.close();
            return users;
        }

        session.close();
        return null;
    }

    public UserEntity getUserByLogin(String login) {
        //Костыль
        Session session = factory.openSession();
        List ids = session.createQuery("SELECT U.id FROM com.worker.DB_classes.UserEntity U WHERE U.login= :login").setParameter("login", login).list();
        System.err.println(ids.size());
        //Костыль
        if (!ids.isEmpty() || ids.size() > 1)
        {
            Integer currentId = (Integer) ids.get(0);
            UserEntity user = session.get(UserEntity.class, currentId);
            session.close();
            return user;
        }
        session.close();
        return null;
    }

    public Boolean registerNewUser(String login, String passwd, String eMail)
    {
        if (getUserByLogin(login) != null) {
            return false;
        }

        Session session = factory.openSession();
        session.beginTransaction();

        UserEntity newUser = new UserEntity();
        newUser.setLogin(login);
        newUser.setPassword(passwd);
        newUser.setEmail(eMail);

        session.save(newUser);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Boolean saveNewMessage(String message, int idfrom, String loginAddressee) {
        UserEntity userAddressee = getUserByLogin(loginAddressee);
        Session session = factory.openSession();
        session.beginTransaction();

        MessagesEntity newMessage = new MessagesEntity();
        newMessage.setMessage(message);
        newMessage.setDateMessage(new Timestamp(new java.util.Date().getTime()));
        newMessage.setIdfrom(idfrom);
        newMessage.setIdto(userAddressee.getId());
        newMessage.setIsread(0);

        session.save(newMessage);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List getMessageHistory(int idfrom, String loginAddressee,int lengthMessageHistory, Timestamp time) {
        UserEntity userAddressee = getUserByLogin(loginAddressee);
        Session session = factory.openSession();
        session.beginTransaction();
        List MessageHistory = session.createQuery("FROM com.worker.DB_classes.MessagesEntity M " +
                "WHERE ((M.idfrom = :idto AND M.idto = :idfrom) OR (M.idfrom = :idfrom AND M.idto = :idto)) " +
                "AND :time > M.dateMessage ORDER BY M.dateMessage DESC")
                .setParameter("idfrom", idfrom)
                .setParameter("idto",userAddressee.getId())
                .setParameter("time", time)
                .setMaxResults(lengthMessageHistory)
                .list();

        if(!MessageHistory.isEmpty()) {
            setIsReadMessage((MessagesEntity) MessageHistory.get(MessageHistory.size() - 1),
                    (MessagesEntity) MessageHistory.get(0),
                    session, idfrom, userAddressee.getId());
            session.close();
            return MessageHistory;
        }

        session.close();
        return null;
    }

    public List getLastUnreadMessage(int idfrom, String loginAddressee, int lengthMessageHistory) {
        UserEntity userAddressee = getUserByLogin(loginAddressee);
        Session session = factory.openSession();
        session.beginTransaction();


        List lastmessage = session.createQuery("SELECT M.dateMessage FROM com.worker.DB_classes.MessagesEntity M " +
                "WHERE ((M.idfrom = :idfrom AND M.idto = :idto) OR (M.idfrom = :idto AND M.idto = :idfrom AND M.isread = 1)) " +
                "ORDER BY M.dateMessage DESC")
                .setParameter("idfrom", idfrom)
                .setParameter("idto",userAddressee.getId())
                .setMaxResults(1)
                .list();

        List MessageHistory;

        if(lastmessage.isEmpty()) {
            MessageHistory = session.createQuery("FROM com.worker.DB_classes.MessagesEntity M " +
                    "WHERE M.idfrom = :idto AND M.idto = :idfrom " +
                    "AND M.isread = 0" +
                    "ORDER BY M.dateMessage DESC")
                    .setParameter("idfrom", idfrom)
                    .setParameter("idto", userAddressee.getId())
                    .setMaxResults(lengthMessageHistory)
                    .list();
        }
        else {
            MessageHistory = session.createQuery("FROM com.worker.DB_classes.MessagesEntity M " +
                    "WHERE M.idfrom = :idto AND M.idto = :idfrom " +
                    "AND M.isread = 0 AND M.dateMessage > :dateMessage " +
                    "ORDER BY M.dateMessage DESC")
                    .setParameter("idfrom", idfrom)
                    .setParameter("idto", userAddressee.getId())
                    .setParameter("dateMessage", lastmessage.get(0))
                    .setMaxResults(lengthMessageHistory)
                    .list();
        }

        if(!MessageHistory.isEmpty()) {
            setIsReadMessage((MessagesEntity) MessageHistory.get(MessageHistory.size() - 1),
                    (MessagesEntity) MessageHistory.get(0),
                    session, idfrom, userAddressee.getId());
            session.close();
            return  MessageHistory;
        }

        session.close();
        return  null;
    }

    private void setIsReadMessage(MessagesEntity firstmessage,MessagesEntity lastmessage,
                                         Session session, int idfrom, int idto) {
        int result = session.createQuery("UPDATE com.worker.DB_classes.MessagesEntity M SET M.isread = 1 " +
                " WHERE M.idfrom = :idto AND M.idto = :idfrom" +
                " AND M.dateMessage BETWEEN :firstid AND :lastid ")
                .setParameter("idfrom", idfrom)
                .setParameter("idto", idto)
                .setParameter("firstid", firstmessage.getDateMessage())
                .setParameter("lastid", lastmessage.getDateMessage())
                .executeUpdate();

        session.getTransaction().commit();
    }


    public List getCountOfUnreadMessages(int idto) {
        Session session = factory.openSession();
        session.beginTransaction();

        List countIfUnreadMessages = session.createQuery("SELECT U.login, COUNT(*) AS C FROM " +
                "com.worker.DB_classes.MessagesEntity M, com.worker.DB_classes.UserEntity U " +
                "WHERE M.idfrom = U.id AND M.idto= :idto AND M.isread = 0 GROUP BY M.idfrom")
                .setParameter("idto", idto)
                .list();

        if(!countIfUnreadMessages.isEmpty()) {
            session.close();
            return countIfUnreadMessages;
        }

        session.close();
        return  null;
    }

    public List getFriends(int userId) {
        Session session = factory.openSession();
        session.beginTransaction();

        List friends = session.createQuery("FROM com.worker.DB_classes.UserEntity U " +
                "WHERE U.id IN (SELECT F.friendId FROM com.worker.DB_classes.FriendEntity F " +
                "WHERE F.userId = :userId AND F.accepted = 1)")
                .setParameter("userId", userId)
                .list();

        if(!friends.isEmpty()) {
            session.close();
            return friends;
        }

        session.close();
        return  null;
    }

    public void shutdown ()
    {
        HibUtil.shutdown();
    }
}