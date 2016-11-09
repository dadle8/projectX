package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
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
    private MenuWidget Menu = new MenuWidget();
    Timer tm = new Timer() {
        @Override
        public void run() {
            WorkerService.App.getInstance().getLastUnreadMessage(CurrentUser.getId(), users.getSelectedItemText(), messages.getHTML(), new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    Window.alert("SMTH IS WRONG IN getLastUnreadMessage");
                }

                public void onSuccess(String result) {
                    messages.setHTML(result);
                }
            });
        }
    };

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
                CurrentUser = user;
                if (CurrentUser != null) {
                    setElements();
                    setUsers(CurrentUser.getLogin());
                    setHandlers();
                    RootPanel.get().clear();
                    RootPanel.get().add(MakeWrapper());
                }
            }
        });

    }

    private void setUsers(String login)
    {
        WorkerService.App.getInstance().getAllUsers(login,new AsyncCallback<List>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH IS WRONG IN getAllUsers");
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
                /*
                WorkerService.App.getInstance().getLastUnreadMessage(CurrentUser.getId(), users.getSelectedItemText(), messages.getHTML(), new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("SMTH IS WRONG IN getLastUnreadMessage");
                    }

                    public void onSuccess(String result) {
                        messages.setHTML(result);
                    }
                });
                */
                messages.setHTML("");
                chat.setVisible(true);
                if(tm.isRunning()) tm.cancel();
                tm.scheduleRepeating(1000);
            }
        });

        btnpp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(2);
            }
        });

        btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(!(message.getText().length() == 0))
                    WorkerService.App.getInstance().saveNewMessage(message.getText(), CurrentUser.getId(), users.getSelectedItemText(), new AsyncCallback<Boolean>() {
                        public void onFailure(Throwable caught) {
                            Window.alert("SMTH IS WRONG IN saveNewMessage");
                        }

                        public void onSuccess(Boolean result) {
                            messages.setHTML(messages.getHTML() + "<p align='right'>" + message.getText() + "</p>" );
                        }
                    });
            }
        });
    }

    private HorizontalPanel MakeWrapper()
    {
        HorizontalPanel Wrapper = new HorizontalPanel();
        Wrapper.add(this.Menu.Build());
        Wrapper.add(this.usersPanel);
        Wrapper.add(this.chat);
        return Wrapper;
    }
}
