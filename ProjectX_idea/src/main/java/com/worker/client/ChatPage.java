package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private Button btnrefresh = null;
    private static UserEntity CurrentUser = null;
    private ScrollPanel scrollPanel = null;
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
    private List<Timestamp> timestampList = new ArrayList<Timestamp>();

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
                for(int i = 0; i < result.size(); i++)
                {
                    users.addItem((String) result.get(i));
                    timestampList.add(i, new Timestamp(new java.util.Date().getTime()));
                }
                users.setVisibleItemCount(users.getItemCount());
            }
        });
    }

    private void setElements()
    {
        usersPanel = new VerticalPanel();
        users = new ListBox();
        btnrefresh = new Button("More");

        usersPanel.add(users);

        chat = new VerticalPanel();
        scrollPanel = new ScrollPanel();
        scrollPanel.setSize("200px","250px");
        messages = new HTML("");
        messages.setWidth("180px");
        messages.setWordWrap(true);
        message = new TextBox();
        btn = new Button("Send");

        scrollPanel.add(messages);
        chat.add(btnrefresh);
        chat.add(scrollPanel);
        chat.add(message);
        chat.add(btn);
        chat.setVisible(false);
    }

    private void setHandlers()
    {
        users.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(tm.isRunning()) tm.cancel();
                WorkerService.App.getInstance().getMessageHistory(CurrentUser.getId(), users.getSelectedItemText(), new Timestamp(new java.util.Date().getTime()), users.getSelectedIndex(), new AsyncCallback<String[]>() {
                            public void onFailure(Throwable caught) {
                                Window.alert("SMTH IS WRONG IN getMessageHistory");
                            }

                            public void onSuccess(String[] result) {
                                if(result[0] != null) {
                                    timestampList.set(users.getSelectedIndex(), Timestamp.valueOf(result[0]));
                                    messages.setHTML(result[1]);
                                }
                                else messages.setHTML("");
                            }
                        });

                chat.setVisible(true);
                tm.scheduleRepeating(2000);
            }
        });

        btnrefresh.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                WorkerService.App.getInstance().getMessageHistory(CurrentUser.getId(), users.getSelectedItemText(), timestampList.get(users.getSelectedIndex()), users.getSelectedIndex(), new AsyncCallback<String[]>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("SMTH IS WRONG IN getMessageHistory");
                    }

                    public void onSuccess(String[] result) {
                        if(result[1] != null) {
                            timestampList.set(users.getSelectedIndex(), Timestamp.valueOf(result[0]));
                            messages.setHTML(result[1] + messages.getHTML());
                        }
                    }
                });
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
