package com.worker.DB_managing; /**
 * Created by AsmodeusX on 02.11.2016.
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public class HibUtil implements Serializable {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            //Properties properties = new Properties();
            //properties.load(new FileInputStream("src//main//resources//app.properties"));
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            //configuration.addProperties(properties);
            sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new Error("Smth Goes wrong in sessionfactory!");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
