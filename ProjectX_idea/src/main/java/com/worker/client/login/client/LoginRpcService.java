package com.worker.client.login.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * Created by Слава on 03.11.2016.
 */
@RemoteServiceRelativePath("LoginRpcService")
public interface LoginRpcService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use LoginRpcService.App.getInstance() to access static instance of LoginRpcServiceAsync
     */
    public static class App {
        private static final LoginRpcServiceAsync ourInstance = (LoginRpcServiceAsync) GWT.create(LoginRpcService.class);

        public static LoginRpcServiceAsync getInstance() {
            return ourInstance;
        }
    }


}
