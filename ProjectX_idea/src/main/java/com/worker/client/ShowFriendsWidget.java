package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Label;
import com.worker.DB_classes.UserEntity;

import java.util.*;

/**
 * Created by AsmodeusX on 19.11.2016.
 */
public class ShowFriendsWidget {

    private FlowPanel content = null;
    private FlowPanel friendsList = null;


    public ShowFriendsWidget()
    {
    }

    public FlowPanel Build()
    {
        this.setElements();
        this.setHandlers();
        return content;
    }

    private void setElements()
    {
        content = new FlowPanel();
        content.addStyleName("profile-widget");
        content.addStyleName("friends-list-widget");

        friendsList = new FlowPanel();

        // Dependencies

        content.add(new HTMLPanel("h1", "Friends"));

        WorkerService.App.getInstance().getFriends(new AsyncCallback<List<UserEntity>>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG!");
            }

            public void onSuccess(List<UserEntity> result) {
                friendsList.clear();
                for(UserEntity usr : result)
                {
                    FlowPanel userInfo = new FlowPanel();
                    userInfo.add(new HTMLPanel("p", "<strong>Name:</strong> " + usr.getName()));
                    userInfo.add(new HTMLPanel("p", "<strong>Surname:</strong> " + usr.getSurname()));
                    userInfo.add(new HTMLPanel("p", "<strong>Ref:</strong> " + usr.getRef()));

                    FlowPanel buffer = new FlowPanel();
                    buffer.add(userInfo);

                    friendsList.add(buffer);
                }
            }
        });

        content.add(friendsList);
    }

    private void setHandlers()
    {

    }
}
