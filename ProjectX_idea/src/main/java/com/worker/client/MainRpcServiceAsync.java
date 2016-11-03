package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.worker.shared.PersonsEntity;
import com.worker.shared.UsersEntity;

/**
 * Created by Слава on 01.11.2016.
 */
public interface MainRpcServiceAsync {

    void save(PersonsEntity user, AsyncCallback<PersonsEntity> async);
}
