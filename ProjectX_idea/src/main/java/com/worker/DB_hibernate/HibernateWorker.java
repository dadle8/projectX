package com.worker.DB_hibernate;

import org.hibernate.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by AsmodeusX on 29.10.2016.
 */
public class HibernateWorker {
    private Session session = null;
    private SessionFactory Factory = null;
    public HibernateWorker () {
        Factory = HibUtil.getSessionFactory();
        session = Factory.openSession();
    }

    public List<String> makeRequest(String req)
    {
        /*Transaction tx = session.beginTransaction();
        SQLQuery */
        List preans = new ArrayList();
        List<String> ans = new ArrayList<String>();

        try {
            preans = this.session.createSQLQuery(req).list();
        } catch (Throwable e)
        {
            throw new Error(e.toString());
        }

        if (!preans.isEmpty()) {

            for (Object o : preans) {
                ans.add(o.toString());
            }
        }
        return ans;
    }

    public void shutdown ()
    {
        session.close();
        HibUtil.shutdown();
    }
}