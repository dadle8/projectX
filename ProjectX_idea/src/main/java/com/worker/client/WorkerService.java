package com.worker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

@RemoteServiceRelativePath("WorkerService")
public interface WorkerService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);
    String ShowMsg(String msg);
    List<String> makeRequest(String req);

    /**
     * Utility/Convenience class.
     * Use WorkerService.App.getInstance() to access static instance of WorkerServiceAsync
     */
    public static class App {
        private static WorkerServiceAsync ourInstance = GWT.create(WorkerService.class);
        public static synchronized WorkerServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
