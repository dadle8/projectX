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
    private TextBox textBox = null;
    private Button searchButton = null;
    private VerticalPanel searchResult = null;

    public FindFriendsWidget()
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
        this.textBox = new TextBox();
        this.searchButton = new Button("Search");
        this.searchResult = new VerticalPanel();
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
                            HorizontalPanel friendPanel = new HorizontalPanel();
                            Button addFriendButton = new Button("ADD");
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
                            friendPanel.add(new HTML("Name: " + usr.getName() + "</br> Surname: " + usr.getSurname() + "</br> Ref: " + usr.getRef() + "<hr>"));
                            friendPanel.add(addFriendButton);
                            searchResult.add(friendPanel);
                        }
                    }
                });
            }
        });
    }

    private HorizontalPanel MakeWrapper()
    {
        HorizontalPanel Wrapper = new HorizontalPanel();
        Wrapper.add(textBox);
        Wrapper.add(searchButton);
        Wrapper.add(searchResult);
        return Wrapper;
    }
}
