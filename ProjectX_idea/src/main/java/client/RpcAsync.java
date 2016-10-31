package client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import shared.UsersEntity;

/**
 * Created by Слава on 31.10.2016.
 */
public interface RpcAsync {
    void save(UsersEntity user, AsyncCallback<UsersEntity> async);
}
