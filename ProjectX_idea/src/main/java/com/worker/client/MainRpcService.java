package com.worker.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import com.worker.shared.UsersEntity;

/**
 * Created by Слава on 01.11.2016.
 */
@RemoteServiceRelativePath("MainRpcService")
public interface MainRpcService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use MainRpcService.App.getInstance() to access static instance of MainRpcServiceAsync
     */
    public static class App {
        private static final MainRpcServiceAsync ourInstance = (MainRpcServiceAsync) GWT.create(MainRpcService.class);

        public static MainRpcServiceAsync getInstance() {
            return ourInstance;
        }
    }

    UsersEntity save(UsersEntity user);
}
