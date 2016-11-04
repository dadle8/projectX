package com.worker.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.DB_hibernate.HibernateWorker;
import com.worker.client.Worker;
import com.worker.client.WorkerService;

import java.util.List;

public class WorkerServiceImpl extends RemoteServiceServlet implements WorkerService {
    // Implementation of sample interface method
    private HibernateWorker HW = new HibernateWorker();

    public boolean Auth(String login, String passwd)
    {
       return HW.Auth(login, passwd);
    }
}