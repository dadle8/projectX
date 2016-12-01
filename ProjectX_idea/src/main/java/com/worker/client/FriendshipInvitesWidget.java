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

    private FlowPanel content = null;
    private FlowPanel invites = null;

    public FriendshipInvitesWidget()
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
        content.addStyleName("invites-widget");

        // Dependencies

        content.add(new HTMLPanel("h1", "Invites"));

        invites = new FlowPanel();

        WorkerService.App.getInstance().getInvites(new AsyncCallback<List<UserEntity>>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG!");
            }

            public void onSuccess(List<UserEntity> result) {
                invites.clear();
                for(final UserEntity usr : result)
                {
                    FlowPanel newInvite = new FlowPanel();
                    Button confirmButton = new Button("Confirm");
                    FlowPanel userInfo = new FlowPanel();
                    confirmButton.addClickHandler(new ClickHandler() {
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
                    userInfo.add(new HTMLPanel("p", "<strong>Name:</strong> " + usr.getName()));
                    userInfo.add(new HTMLPanel("p", "<strong>Surname:</strong> " + usr.getSurname()));
                    userInfo.add(new HTMLPanel("p", "<strong>Ref:</strong> " + usr.getRef()));

                    newInvite.add(userInfo);
                    newInvite.add(confirmButton);

                    invites.add(newInvite);
                }
            }
        });

        content.add(invites);
    }

    private void setHandlers()
    {

    }

}
