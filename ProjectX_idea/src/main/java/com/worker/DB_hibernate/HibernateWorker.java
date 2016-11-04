package com.worker.DB_hibernate;

import org.hibernate.*;

import java.util.List;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker {
    private Session session = null;
    private SessionFactory factory = HibUtil.getSessionFactory();

    public HibernateWorker () {
        session = factory.openSession();
    }

    public boolean Auth(String login, String passwd) {
        String req = "SELECT password FROM com.worker.UserEntity WHERE com.worker.UserEntity.login= 'root'";
        List ans = session.createQuery(req)./*setParameter("login_field", login).*/list();
        return !(ans.isEmpty() || ans.size() > 1) && (ans.get(0).equals(passwd));
    }

    public void shutdown ()
    {
        session.close();
        HibUtil.shutdown();
    }
}