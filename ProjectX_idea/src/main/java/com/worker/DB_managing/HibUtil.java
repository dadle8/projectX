package com.worker.DB_managing; /**
 * Created by AsmodeusX on 02.11.2016.
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static ServiceRegistry serviceRegistry;

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
