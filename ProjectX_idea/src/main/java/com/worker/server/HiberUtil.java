package com.worker.server;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Слава on 01.11.2016.
 */
public class HiberUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(com.worker.shared.PersonsEntity.class);
            cfg.addAnnotatedClass(com.worker.shared.MessagesEntity.class);
            cfg.addAnnotatedClass(com.worker.shared.GeolocationsEntity.class);
            cfg.configure();

            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    cfg.getProperties()). build();
            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
