package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface WorkerServiceAsync {
    void Auth(String login, String passwd, AsyncCallback<Boolean> async);
}
