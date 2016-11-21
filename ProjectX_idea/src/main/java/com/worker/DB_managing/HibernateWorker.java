package com.worker.DB_managing;

import com.google.gwt.user.client.Window;
import com.worker.DB_classes.FriendEntity;
import com.worker.DB_classes.GeoEntity;
import com.worker.DB_classes.MessagesEntity;
import com.worker.DB_classes.UserEntity;
import com.worker.client.DoublePoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker implements Serializable {
    private HibUtil HibUtils = new HibUtil();
    private SessionFactory factory = HibUtil.getSessionFactory();

    public HibernateWorker ()
    {

    }

    public Boolean addGeo(UserEntity usr, double latitude, double longitude, String userAgent)
    {
        Session session = factory.openSession();
        List<Object[]> coords = session.createQuery("SELECT G.latitude, G.longitude FROM com.worker.DB_classes.GeoEntity G ORDER BY G.time DESC").list();

        if (!coords.isEmpty()) {
            double lastLatitude = (Double) coords.get(0)[0];
            double lastLongitude = (Double) coords.get(0)[1];
            if (lastLatitude == latitude && lastLongitude == longitude) {
                return false;
            }
        }
        GeoEntity newGeo = new GeoEntity();
        newGeo.setLatitude(latitude);
        newGeo.setLongitude(longitude);
        newGeo.setTime(new Timestamp(new java.util.Date().getTime()));
        newGeo.setUserid(usr.getId());

        userAgent = userAgent.substring(0, Math.min(userAgent.length(), 511));
        System.err.println(userAgent);
        newGeo.setDevice(userAgent);

        session.beginTransaction();
        session.save(newGeo);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public void setUserSession(UserEntity usr, String sessionId)
    {
        Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE com.worker.DB_classes.UserEntity U SET U.sessionId = :sessionId WHERE U.login = :login").setParameter("sessionId", sessionId).setParameter("login", usr.getLogin()).executeUpdate();
        session.createQuery("UPDATE com.worker.DB_classes.UserEntity U SET U.loggedIn = 1 WHERE U.login = :login").setParameter("login", usr.getLogin()).executeUpdate();
        session.close();
        return;
    }

    public void clearUserSession(UserEntity usr)
    {
        Session session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE com.worker.DB_classes.UserEntity U SET U.sessionId = :sessionId WHERE U.login = :login").setParameter("sessionId", null).setParameter("login", usr.getLogin()).executeUpdate();
        session.createQuery("UPDATE com.worker.DB_classes.UserEntity U SET U.loggedIn = 0 WHERE U.login = :login").setParameter("login", usr.getLogin()).executeUpdate();
        session.close();
        return;
    }

    public ArrayList<UserEntity> searchUsers(String str)
    {
        Session session = factory.openSession();
        str = '%' + str + '%';
        System.err.println(str);
        List <UserEntity> users = session.createQuery("SELECT U FROM com.worker.DB_classes.UserEntity U WHERE U.name like :pattern OR U.surname like :pattern OR U.ref like :pattern").setParameter("pattern", str).list();
        System.err.println(users.size());
        //ArrayList<UserEntity> ans = new ArrayList<UserEntity>();
        session.close();

        return new ArrayList<UserEntity>(users);
    }

    public ArrayList<DoublePoint> getPath(Integer id)
    {
        Session session = factory.openSession();

        List <Object[]> points = session.createQuery("SELECT G.latitude, G.longitude FROM com.worker.DB_classes.GeoEntity G WHERE G.userid = :id ORDER BY G.time").setParameter("id", id).setMaxResults(50).list();
        ArrayList<DoublePoint> ans = new ArrayList<DoublePoint>();
        for(Object[] point: points)
        {
            double latitude = (Double) point[0];
            double longitude = (Double) point[1];
            DoublePoint pt = new DoublePoint();
            pt.Set(latitude, longitude);
            ans.add(pt);
        }

        session.close();
        return ans;
    }

    /**
     * Created by Слава
     *
     * This method return logins of users that are stored in the database except the user with the login 'String login'.
     */
    public List getAllUser(String login) {
        Session session = factory.openSession();
        List users = session.createQuery("SELECT U.login FROM com.worker.DB_classes.UserEntity U WHERE U.login != :login").setParameter("login",login).list();

        if(!users.isEmpty()) {
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

    private String generateNewRef()
    {
        String ans = "";
        Random rnd = new Random();
        char[] available = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '!', '@', '#', ')', '%', '(', '?', ';', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', '+', '_'};
        for(int i = 0; i < 32; i++)
        {
            ans += available[Math.abs(rnd.nextInt() % available.length)];
        }

        return ans;
    }

    private String normal(String s, int len)
    {
        while (s.length() < len)
        {
            s.concat("0");
        }
        return s;
    }

    private String generateRandomColor()
    {
        Random rnd = new Random();
        int val = rnd.nextInt(1000000);
        return normal(Integer.toString(val), 6);
    }

    public Boolean registerNewUser(String login, String name, String surname, String passwd, String eMail)
    {
        if (getUserByLogin(login) != null) {
            return false;
        }

        Session session = factory.openSession();

        UserEntity newUser = new UserEntity();
        newUser.setLogin(login);
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setPassword(passwd);
        newUser.setEmail(eMail);
        newUser.setRef(generateNewRef());
        newUser.setColor(generateRandomColor());

        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    /**
     * Created by Слава
     *
     * This method save in table 'message' new message from 'idfrom' to 'loginAddressee' with content 'message'.
     * New message has flag 'isread' = 0 and 'dateMessage' = now date.
     */
    public Boolean saveNewMessage(String message, int idfrom, String loginAddressee) {
        UserEntity userAddressee = getUserByLogin(loginAddressee);
        Session session = factory.openSession();

        MessagesEntity newMessage = new MessagesEntity();
        newMessage.setMessage(message);
        newMessage.setDateMessage(new Timestamp(new java.util.Date().getTime()));
        newMessage.setIdfrom(idfrom);
        newMessage.setIdto(userAddressee.getId());
        newMessage.setIsread(0);

        session.beginTransaction();
        session.save(newMessage);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    /**
     * Created by Слава
     *
     * This method get 'lengthMessageHistory' messages with dateMessage before 'time' for specific user.
     *
     *  |     idfrom    |      idto     |
     *  ---------------------------------
     *  | specific user |   addressee   |
     *  ---------------------------------
     *  |   addressee   | specific user |
     *
     *  After get messages those messages which were not read by the addressee
     *  be read (call method setIsReadMessage), i.e. flag 'isread' = 1.
     */
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

    /**
     * Created by Слава
     *
     * This method get 'lengthMessageHistory' unread messages from 'loginAddressee'
     * that have a 'dateMessage' before 'dateMessage' last read message.
     *
     */
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

    /**
     * Created by Слава
     *
     * This method set flag 'isread' = 1 from messages
     * which have  'firstDateMessage' <= 'dataMessage' <= 'lastDateMessage' for specific user.
     */
    private void setIsReadMessage(MessagesEntity firstmessage,MessagesEntity lastmessage,
                                         Session session, int idfrom, int idto) {
        int result = session.createQuery("UPDATE com.worker.DB_classes.MessagesEntity M SET M.isread = 1 " +
                " WHERE M.idfrom = :idto AND M.idto = :idfrom" +
                " AND M.dateMessage BETWEEN :firstDateMessage AND :lastDateMessage ")
                .setParameter("idfrom", idfrom)
                .setParameter("idto", idto)
                .setParameter("firstDateMessage", firstmessage.getDateMessage())
                .setParameter("lastDateMessage", lastmessage.getDateMessage())
                .executeUpdate();

        session.getTransaction().commit();
    }

    /**
     * Created by Слава
     *
     * This method return count unread message, i.e. flag 'isread' = 0,
     * and login for all users who send messages specific user.
     */
    public List getCountOfUnreadMessages(int idto) {
        Session session = factory.openSession();

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

    /**
     * Created by Слава
     *
     * This method return all users who have in table 'friend' usersId  = specific userid and accepted = 1.
     */
    public List<UserEntity> getFriends(int userId) {
        Session session = factory.openSession();

        List<UserEntity> friends = session.createQuery("FROM com.worker.DB_classes.UserEntity U " +
                "WHERE U.id IN (SELECT F.friendId FROM com.worker.DB_classes.FriendEntity F " +
                "WHERE F.userId = :userId AND F.accepted = 1)" +
                "OR U.id IN (SELECT F.userId FROM com.worker.DB_classes.FriendEntity F " +
                "WHERE F.friendId = :userId AND F.accepted = 1)")
                .setParameter("userId", userId)
                .list();

        if(!friends.isEmpty()) {
            session.close();
            return friends;
        }

        session.close();
        return  null;
    }

    public List<UserEntity> getInvites(UserEntity usr)
    {
        Session session = factory.openSession();

        List<UserEntity> invites = session.createQuery("FROM com.worker.DB_classes.UserEntity U " +
                "WHERE U.id IN (SELECT F.userId FROM com.worker.DB_classes.FriendEntity F " +
                "WHERE F.friendId = :userId AND F.accepted = 0)")
                .setParameter("userId", usr.getId())
                .list();

        if(!invites.isEmpty()) {
            session.close();
            return invites;
        }

        session.close();
        return  null;
    }

    public void confirmFriendship(UserEntity from, UserEntity to)
    {
        Session session = factory.openSession();
        session.beginTransaction();

        int res = session.createQuery("UPDATE com.worker.DB_classes.FriendEntity F SET F.accepted = 1 WHERE F.userId = :to AND F.friendId = :from").setParameter("to", to.getId()).setParameter("from", from.getId()).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public boolean addFriend(UserEntity from, UserEntity to)
    {
        Session session = factory.openSession();

        List <Byte> ans = session.createQuery("SELECT F.accepted FROM com.worker.DB_classes.FriendEntity F WHERE F.userId = :from AND F.friendId = :to").setParameter("from", from.getId()).setParameter("to", to.getId()).list();

        if (ans.size() != 0) {//Если предложение уже было
            return false;
        }

        FriendEntity friendship = new FriendEntity();
        friendship.setAccepted((byte)0);
        friendship.setUserId(from.getId());
        friendship.setFriendId(to.getId());

        session.beginTransaction();
        session.save(friendship);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public void shutdown ()
    {
        HibUtil.shutdown();
    }
}