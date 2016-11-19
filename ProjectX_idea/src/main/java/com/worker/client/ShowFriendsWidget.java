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
    private Label caption = null;
    private VerticalPanel friendsList = null;


    public ShowFriendsWidget()
    {
    }

    public VerticalPanel Build()
    {
        this.setElements();
        this.setHandlers();
        return this.MakeWrapper();
    }

    private void setElements()
    {
        this.caption = new Label("Friends:");
        this.friendsList = new VerticalPanel();
        WorkerService.App.getInstance().getFriends(new AsyncCallback<List<UserEntity>>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG!");
            }

            public void onSuccess(List<UserEntity> result) {
                friendsList.clear();
                for(UserEntity usr : result)
                {
                    friendsList.add(new HTML("Name: " + usr.getName() + "</br> Surname: " + usr.getSurname() + "</br> Ref: " + usr.getRef() + "<hr>"));
                }
            }
        });
    }

    private void setHandlers()
    {

    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(caption);
        Wrapper.add(friendsList);
        return Wrapper;
    }
}
