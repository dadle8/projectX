package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.worker.shared.UsersEntity;

/**
 * Created by Слава on 01.11.2016.
 */
public interface MainRpcServiceAsync {

    void save(UsersEntity user, AsyncCallback<UsersEntity> async);
}
