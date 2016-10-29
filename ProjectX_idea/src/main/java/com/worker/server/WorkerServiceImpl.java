package com.worker.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.worker.client.WorkerService;

public class WorkerServiceImpl extends RemoteServiceServlet implements WorkerService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}