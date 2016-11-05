package com.worker.DB_managing;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.impl.AsyncFragmentLoader;
import com.google.gwt.user.client.Window;
import com.worker.DB_classes.UserEntity;
import org.hibernate.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker implements Serializable {
    private SessionFactory factory = HibUtil.getSessionFactory();
    public HibernateWorker () {
    }

    public boolean Auth(String login, String passwd) {
        Session session = factory.openSession();
        String req = "SELECT U.password FROM com.worker.DB_classes.UserEntity U WHERE U.login= :login";
        Query q = session.createQuery(req);
        q.setParameter("login", login);
        List ans = q.list();
        session.close();
        System.err.println("In:  " + ans.get(0));
        System.err.println("Out: " + passwd);
        return !(ans.isEmpty() || ans.size() > 1) && (ans.get(0).equals(passwd));
    }

    public UserEntity getUserByLogin(String login) {
        Session session = factory.openSession();
        UserEntity user = new UserEntity();
        //Костыль
        List ids = session.createQuery("SELECT U.id FROM com.worker.DB_classes.UserEntity U WHERE U.login= :login").setParameter("login", login).list();
        System.err.println(ids.size());
        //Костыль
        if (!ids.isEmpty())
        {
            System.err.println(ids.get(0).toString());
            Integer currentId = (Integer)ids.get(0);
            user = session.get(UserEntity.class, currentId);
            System.err.println(user.getLogin());
        }
        session.close();
        return user;
    }

    public void shutdown ()
    {
        HibUtil.shutdown();
    }
}