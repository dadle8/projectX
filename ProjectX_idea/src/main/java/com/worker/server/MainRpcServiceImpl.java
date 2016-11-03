package com.worker.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.client.MainRpcService;
import com.worker.shared.PersonsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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

    public PersonsEntity save(PersonsEntity user) {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        Query query = session.createQuery("from PersonsEntity where login = :paramName");
        query.setParameter("paramName", user.getLogin());
        List list = query.list();
        session.close();

        return (PersonsEntity) list.get(0);
    }
}