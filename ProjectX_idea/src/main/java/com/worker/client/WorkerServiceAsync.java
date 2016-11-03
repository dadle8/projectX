package com.worker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface WorkerServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void ShowMsg(String msg, AsyncCallback<String> async);
    void makeRequest(String req, AsyncCallback<List<String>> async);
}
