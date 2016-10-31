package client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import shared.UsersEntity;

/**
 * Created by Слава on 31.10.2016.
 */
@RemoteServiceRelativePath("Rpc")
public interface Rpc extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use Rpc.App.getInstance() to access static instance of RpcAsync
     */
    public static class App {
        private static final RpcAsync ourInstance = (RpcAsync) GWT.create(Rpc.class);

        public static RpcAsync getInstance() {
            return ourInstance;
        }
    }

    UsersEntity save(UsersEntity user);
}
