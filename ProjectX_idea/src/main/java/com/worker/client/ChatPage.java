package com.worker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.junit.JUnitShell;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.gwt.dom.client.Style.Unit.EM;
import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * Created by Слава on 07.11.2016.
 *
 * This Widget has
 *  1) Menu - page navigation
 *  2) users - contains all users stored in the database
 *  3) messages - contains messages in html format
 *  4) message - field for entering new messages
 *  5) sendMessageBtn - button for send message contained in 'message' field
 *  6) cleanHistoryBtn - button for clean 'messages' field
 *
 */
public class ChatPage {
    private MenuWidget Menu = new MenuWidget();
    private UnreadMessagesWidget UnreadMessages = null;

    private VerticalPanel usersPanel = null;
    private ListBox users = null;

    private VerticalPanel chat = null;
    private ScrollPanel scrollPanel = null;
    private HorizontalPanel buttonsPanel = null;
    private HTML messages = null;
    private TextBox message = null;
    private Button sendMessageBtn = null;
    private Button cleanHistoryBtn = null;

    /**
     *  This ArrayList to store the earliest time displayed message.
     *  timestampList[users.getSelectedIndex()] - time for users.getSelectedItemText().
     */
    private List<Timestamp> timestampList = new ArrayList<Timestamp>();
    private static UserEntity CurrentUser = null;

    /**
     * Timer to display unread messages every 'delayMillis' ms.
     */
    int delayMillis  = 2000;
    Timer tm = new Timer() {
        @Override
        public void run() {
            WorkerService.App.getInstance().getLastUnreadMessage(CurrentUser.getId(),
                    users.getSelectedItemText(), messages.getHTML(), new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    GWT.log("Error in 'tm.run' when 'getLastUnreadMessage'.\n" + caught.toString());
                }

                public void onSuccess(String result) {
                    if (result != null) {
                        messages.setHTML(result);
                        scrollPanel.scrollToBottom();
                    }
                }
            });
        }
    };

    /**
     * This timer for blocking sendMessageBtn until RPC returned.
     */
    private  boolean abortFlag = false;
    Timer tmForSendBtn = new Timer() {
        @Override
        public void run() {
            if(abortFlag) {
                sendMessageBtn.setEnabled(true);
                tmForSendBtn.cancel();
            }
        }
    };

    public ChatPage () {}

    public void Build() {
        WorkerService.App.getInstance().getUserFromCurrentSession(new AsyncCallback<UserEntity>() {
            public void onFailure(Throwable caught) {
                GWT.log("Error in 'Build' when 'getUserFromCurrentSession'.\n" + caught.toString());
            }

            public void onSuccess(UserEntity user) {
                if (user != null) {
                    CurrentUser = user;

                    initElements();
                    setDependences();
                    setStyle();
                    setUsers(CurrentUser.getLogin());
                    setHandlers();

                    RootPanel.get("root-div").clear();
                    RootPanel.get("root-div").add(MakeWrapper());
                }
            }
        });
    }

    private void setUsers(String login) {
        WorkerService.App.getInstance().getAllUsers(login,new AsyncCallback<List>() {
            public void onFailure(Throwable caught) {
                GWT.log("Error in 'setUsers' when 'getAllUsers'.\n" + caught.toString());
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

    private void initElements() {
        UnreadMessages = new UnreadMessagesWidget(CurrentUser);

        usersPanel = new VerticalPanel();
        users = new ListBox();

        chat = new VerticalPanel();
        scrollPanel = new ScrollPanel();
        buttonsPanel = new HorizontalPanel();

        messages = new HTML();
        message = new TextBox();
        message.setMaxLength(1024);
        sendMessageBtn = new Button("Send");
        cleanHistoryBtn = new Button("Clean history");
    }

    private void setDependences() {
        usersPanel.add(users);

        buttonsPanel.add(sendMessageBtn);
        buttonsPanel.add(cleanHistoryBtn);
        scrollPanel.add(messages);

        chat.add(scrollPanel);
        chat.add(message);
        chat.add(buttonsPanel);

        chat.setVisible(false);
    }

    private void setStyle() {
        scrollPanel.getElement().getStyle().setProperty("width","350px");
        scrollPanel.getElement().getStyle().setProperty("height","405px");

        messages.getElement().getStyle().setProperty("width","320px");
    }
    private void setHandlers() {
        users.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(tm.isRunning()) {
                    tm.cancel();
                }

                WorkerService.App.getInstance().getMessageHistory(CurrentUser.getId(), users.getSelectedItemText(),
                        new Timestamp(new java.util.Date().getTime()), users.getSelectedIndex(), new AsyncCallback<String[]>() {
                            public void onFailure(Throwable caught) {
                                GWT.log("Error in 'users.addClickHandler' when 'getMessageHistory'.\n" + caught.toString());
                            }

                            public void onSuccess(String[] result) {
                                if(result != null) {
                                    timestampList.set(users.getSelectedIndex(), Timestamp.valueOf(result[0]));
                                    messages.setHTML(result[1]);
                                }
                                else {
                                    messages.setHTML("");
                                }

                                chat.setVisible(true);
                                scrollPanel.scrollToBottom();
                                tm.scheduleRepeating(delayMillis);
                            }
                        });
            }
        });

        sendMessageBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(message.getText().length() != 0 & message.getText().length() <= 1024) {
                    sendMessageBtn.setEnabled(false);
                    tmForSendBtn.scheduleRepeating(delayMillis/4);

                    WorkerService.App.getInstance().saveNewMessage(message.getText(), CurrentUser.getId(),
                            users.getSelectedItemText(), new AsyncCallback<Boolean>() {
                        public void onFailure(Throwable caught) {
                            GWT.log("Error in 'sendMessageBtn.addClickHandler' when 'saveNewMessage'.\n" + caught.toString());
                            abortFlag = true;
                        }

                        public void onSuccess(Boolean result) {
                            messages.setHTML(messages.getHTML() + "<p align='right' style='overflow-wrap: break-word; width: 320px; color: #4B0082;'>"
                                    + message.getText() + " | " + formatDate(new Timestamp(new java.util.Date().getTime())) + "</p>");
                            message.setText("");
                            scrollPanel.scrollToBottom();
                            abortFlag = true;
                        }
                    });
                }
            }
        });

        cleanHistoryBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(!messages.getHTML().equals("")) {
                    WorkerService.App.getInstance().getMessageHistory(CurrentUser.getId(), users.getSelectedItemText(),
                            new Timestamp(new java.util.Date().getTime()), users.getSelectedIndex(), new AsyncCallback<String[]>() {
                                public void onFailure(Throwable caught) {
                                   GWT.log("Error in 'cleanHistoryBtn.addClickHandler' when 'getMessageHistory'" + caught.toString());
                                }

                                public void onSuccess(String[] result) {
                                    if (result != null) {
                                        timestampList.set(users.getSelectedIndex(), Timestamp.valueOf(result[0]));
                                        messages.setHTML(result[1]);
                                        scrollPanel.scrollToBottom();

                                    }
                                }
                            });
                }
            }
        });

        scrollPanel.addScrollHandler(new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                if (scrollPanel.getVerticalScrollPosition() == scrollPanel.getMinimumVerticalScrollPosition()) {
                    final int oldMaxScrollPosition = scrollPanel.getMaximumVerticalScrollPosition();

                    WorkerService.App.getInstance().getMessageHistory(CurrentUser.getId(), users.getSelectedItemText(),
                            timestampList.get(users.getSelectedIndex()), users.getSelectedIndex(), new AsyncCallback<String[]>() {
                                public void onFailure(Throwable caught) {
                                    GWT.log("Error in 'scrollPanel.addScrollHandler' when 'getMessageHistory'" + caught.toString());
                                }

                                public void onSuccess(String[] result) {
                                    if(result != null) {
                                        timestampList.set(users.getSelectedIndex(), Timestamp.valueOf(result[0]));
                                        messages.setHTML(result[1] + messages.getHTML());
                                        scrollPanel.setVerticalScrollPosition(scrollPanel.getMaximumVerticalScrollPosition() - oldMaxScrollPosition);
                                    }
                                }
                            });
                }
            }
        });
    }

    private VerticalPanel MakeWrapper() {
        VerticalPanel Wrapper = new VerticalPanel();
        HorizontalPanel HorizonWrapper = new HorizontalPanel();
        Wrapper.add(this.Menu.Build("Chat"));
        HorizonWrapper.add(this.usersPanel);
        HorizonWrapper.add(this.chat);
        HorizonWrapper.add(this.UnreadMessages.Build());
        Wrapper.add(HorizonWrapper);
        return Wrapper;
    }

    private String formatDate(Timestamp time) {
        String pattern = "HH:mm";
        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
        DateTimeFormat timeFormat = new DateTimeFormat(pattern, info) {}; // <- It is trick.

        return timeFormat.format(time);
    }
}
