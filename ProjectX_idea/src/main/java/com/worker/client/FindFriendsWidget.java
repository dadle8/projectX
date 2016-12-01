package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.util.ArrayList;

/**
 * Created by AsmodeusX on 19.11.2016.
 */
public class FindFriendsWidget {

    private FlowPanel content = null;
    private TextBox textBox = null;
    private Button searchButton = null;
    private FlowPanel searchResult = null;

    public FindFriendsWidget()
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
        content.addStyleName("find-friends-widget");

        textBox = new TextBox();
        searchButton = new Button("Search");
        searchResult = new FlowPanel();

        // Dependencies

        content.add(new HTMLPanel("h1", "Find friends"));
        content.add(textBox);
        content.add(searchButton);
    }

    private void setHandlers()
    {
        this.searchButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                WorkerService.App.getInstance().searchUsers(textBox.getText(), new AsyncCallback<ArrayList<UserEntity>>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("SMTH GOES WRONG!");
                    }

                    public void onSuccess(ArrayList<UserEntity> result) {
                        searchResult.clear();
                        for(final UserEntity usr : result)
                        {
                            FlowPanel friendPanel = new FlowPanel();
                            Button addFriendButton = new Button("Add");
                            FlowPanel userInfo = new FlowPanel();
                            addFriendButton.addClickHandler(new ClickHandler() {
                                public void onClick(ClickEvent event) {
                                    WorkerService.App.getInstance().addFriend(usr, new AsyncCallback<Boolean>() {
                                        public void onFailure(Throwable caught) {
                                            Window.alert("SMTH GOES WRONG!");
                                        }

                                        public void onSuccess(Boolean result) {
                                            if (result) {
                                                Window.alert("INVITING SENT SUCCESSFUL TO " + usr.getName() + " " + usr.getSurname());
                                            }
                                            else
                                            {
                                                Window.alert("INVITING WAS SENT EARLIER");
                                            }
                                        }
                                    });
                                }
                            });
                            userInfo.add(new HTMLPanel("p", "<strong>Name:</strong> " + usr.getName()));
                            userInfo.add(new HTMLPanel("p", "<strong>Surname:</strong> " + usr.getSurname()));
                            userInfo.add(new HTMLPanel("p", "<strong>Ref:</strong> " + usr.getRef()));

                            friendPanel.add(userInfo);
                            friendPanel.add(addFriendButton);

                            searchResult.add(friendPanel);
                        }
                    }
                });
            }
        });

        content.add(searchResult);
    }
}
