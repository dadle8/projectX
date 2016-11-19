package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsmodeusX on 19.11.2016.
 */
public class FriendshipInvitesWidget {
    private Label caption = null;
    private VerticalPanel invites = null;

    public FriendshipInvitesWidget()
    {
    }

    public HorizontalPanel Build()
    {
        this.setElements();
        this.setHandlers();
        return this.MakeWrapper();
    }

    private void setElements()
    {
        this.caption = new Label("Invites:");
        this.invites = new VerticalPanel();
        WorkerService.App.getInstance().getInvites(new AsyncCallback<List<UserEntity>>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG!");
            }

            public void onSuccess(List<UserEntity> result) {
                invites.clear();
                for(final UserEntity usr : result)
                {
                    HorizontalPanel friendPanel = new HorizontalPanel();
                    Button addFriendButton = new Button("CONFIRM");
                    addFriendButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            WorkerService.App.getInstance().confirmFriendship(usr, new AsyncCallback<Void>() {
                                public void onFailure(Throwable caught) {
                                    Window.alert("SMTH GOES WRONG!");
                                }

                                public void onSuccess(Void result) {
                                    Window.alert("CONFIRMED REQUEST FROM " + usr.getName() + " " + usr.getSurname());
                                }
                            });
                        }
                    });
                    friendPanel.add(new HTML("Name: " + usr.getName() + "</br> Surname: " + usr.getSurname() + "</br> Ref: " + usr.getRef() + "<hr>"));
                    friendPanel.add(addFriendButton);
                    invites.add(friendPanel);
                }
            }
        });
    }

    private void setHandlers()
    {

    }

    private HorizontalPanel MakeWrapper()
    {
        HorizontalPanel Wrapper = new HorizontalPanel();
        Wrapper.add(caption);
        Wrapper.add(invites);
        return Wrapper;
    }
}
