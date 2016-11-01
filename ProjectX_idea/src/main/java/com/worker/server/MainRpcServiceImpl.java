package com.worker.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.client.MainRpcService;
import com.worker.shared.UsersEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by Слава on 01.11.2016.
 */
public class MainRpcServiceImpl extends RemoteServiceServlet implements MainRpcService {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;
    public MainRpcServiceImpl() {
        sessionFactory = HiberUtil.getSessionFactory();
    }

    public UsersEntity save(UsersEntity user) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
        return null;
    }
}