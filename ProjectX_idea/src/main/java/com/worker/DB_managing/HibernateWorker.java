package com.worker.DB_managing;

import com.worker.DB_classes.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker implements Serializable {
    private HibUtil HibUtils = new HibUtil();
    private SessionFactory factory = HibUtil.getSessionFactory();
    public HibernateWorker () {
    }

    /*public boolean Auth(String login, String passwd) {
        Session session = factory.openSession();
        String req = "SELECT U.password FROM com.worker.DB_classes.UserEntity U WHERE U.login= :login";
        Query q = session.createQuery(req);
        q.setParameter("login", login);
        List ans = q.list();
        session.close();
        if (ans.isEmpty())
            return false;
        System.err.println("In:  " + ans.get(0));
        System.err.println("Out: " + passwd);
        return (ans.size() == 1 && ans.get(0).equals(passwd));
    }*/

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

    public void shutdown ()
    {
        HibUtil.shutdown();
    }
}