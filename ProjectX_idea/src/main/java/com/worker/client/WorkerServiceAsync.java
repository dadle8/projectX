package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorkerServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
