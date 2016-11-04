package com.worker.DB_hibernate;

import org.hibernate.*;

import java.util.List;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker {
    private SessionFactory factory = HibUtil.getSessionFactory();

    public HibernateWorker () {
    }

    public boolean Auth(String login, String passwd) {
        Session session = factory.openSession();
        String req = "SELECT U.password FROM com.worker.DB_classes.UserEntity U WHERE U.login= :login";
        Query q = session.createQuery(req);
        q.setParameter("login", login);
        List ans = q.list();
        return !(ans.isEmpty() || ans.size() > 1) && (ans.get(0).equals(passwd));
    }

    public void shutdown ()
    {
        HibUtil.shutdown();
    }
}