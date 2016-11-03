package com.worker.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import com.worker.shared.PersonsEntity;
import com.worker.shared.UsersEntity;

/**
 * Created by Слава on 01.11.2016.
 */
@RemoteServiceRelativePath("MainRpcService")
public interface MainRpcService extends RemoteService {

    PersonsEntity save(PersonsEntity user);
}
