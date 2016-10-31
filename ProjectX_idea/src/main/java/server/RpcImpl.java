package server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import client.Rpc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import shared.UsersEntity;

/**
 * Created by Слава on 31.10.2016.
 */
public class RpcImpl extends RemoteServiceServlet implements Rpc {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;
    public RpcImpl() {
        sessionFactory = Main.getSessionFactory();
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