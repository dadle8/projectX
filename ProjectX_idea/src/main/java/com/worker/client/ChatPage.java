package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.util.List;

/**
 * Created by Слава on 07.11.2016.
 */
public class ChatPage {
    private VerticalPanel usersPanel = null;
    ListBox users = null;
    private VerticalPanel chat = null;
    private HTML messages = null;
    private TextBox message = null;
    private Button btn = null;
    private Button btnpp = null;
    private static UserEntity CurrentUser = null;

    public ChatPage () {}

    private void generateNextPage(Integer id)
    {
        Cookies.setCookie("NextPage", id.toString());
        Window.Location.reload();
    }

    public void Build()
    {

        WorkerService.App.getInstance().getUserFromCurrentSession(new AsyncCallback<UserEntity>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH IS WRONG WITH SESSION");
            }

            public void onSuccess(UserEntity user) {
                setUsers();

                CurrentUser = user;
                if (CurrentUser != null) {
                    setElements();
                    setHandlers();
                    RootPanel.get().clear();
                    RootPanel.get().add(MakeWrapper());
                }
            }
        });

    }

    private void setUsers()
    {
        WorkerService.App.getInstance().getAllUsers(new AsyncCallback<List>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH IS WRONG");
            }

            public void onSuccess(List result) {
                for(int i = 0; i<result.size(); i++)
                {
                    users.addItem((String) result.get(i));
                }
                users.setVisibleItemCount(users.getItemCount());
            }
        });
    }

    private void setElements()
    {
        usersPanel = new VerticalPanel();
        users = new ListBox();
        btnpp = new Button("Profile");

        usersPanel.add(users);
        usersPanel.add(btnpp);

        chat = new VerticalPanel();
        messages = new HTML("");
        message = new TextBox();
        btn = new Button("Send");


        chat.add(messages);
        chat.add(message);
        chat.add(btn);


        chat.setVisible(false);
    }

    private void setHandlers()
    {
        users.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                messages.setHTML(CurrentUser.getLogin() + "<br>" + users.getSelectedItemText());
                chat.setVisible(true);
            }
        });

        btnpp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(2);
            }
        });
    }

    private HorizontalPanel MakeWrapper()
    {
        HorizontalPanel Wrapper = new HorizontalPanel();
        Wrapper.add(this.usersPanel);
        Wrapper.add(this.chat);
        return Wrapper;
    }
}
