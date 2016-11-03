package com.worker.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.DB_hibernate.HibernateWorker;
import com.worker.client.Worker;
import com.worker.client.WorkerService;

import java.util.List;

public class WorkerServiceImpl extends RemoteServiceServlet implements WorkerService {
    // Implementation of sample interface method
    private HibernateWorker HW = new HibernateWorker();

    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    public String ShowMsg(String msg) {
        return msg;
    }

    public List makeRequest(String req) {
        return HW.makeRequest(req);
    }
}