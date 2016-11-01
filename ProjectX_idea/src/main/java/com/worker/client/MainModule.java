package com.worker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.shared.UsersEntity;

/**
 * Created by Слава on 01.11.2016.
 */
public class MainModule implements EntryPoint {
    public void onModuleLoad() {
        final MainRpcServiceAsync rpcAsync = GWT.create(MainRpcService.class);
        final TextBox nametb = new TextBox();
        final TextBox passwordtb = new TextBox();
        Button savebt = new Button("save");

        RootPanel.get("name").add(nametb);
        RootPanel.get("password").add(passwordtb);
        RootPanel.get("save").add(savebt);

        final UsersEntity user = new UsersEntity();

        savebt.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                user.setName(nametb.getText());
                user.setPassword(passwordtb.getText());

                rpcAsync.save(user, new AsyncCallback<UsersEntity>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Faild");
                    }

                    public void onSuccess(UsersEntity result) {
                        Window.alert("Success");
                    }
                });
            }
        });
    }
}
