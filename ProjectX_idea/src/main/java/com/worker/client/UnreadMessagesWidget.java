package com.worker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.sql.Timestamp;

/**
 * Created by Слава on 16.11.2016.
 */
public class UnreadMessagesWidget {
    private VerticalPanel usersPanel = null;
    private Label label = null;

    private UserEntity CurrentUser = null;
    private VerticalPanel users = null;

    public UnreadMessagesWidget() {}

    public UnreadMessagesWidget(UserEntity CurrentUser, VerticalPanel users){
        this.CurrentUser = CurrentUser;
        this.users = users;
    }

    int delayMillis  =  1500;

    Timer tm = new Timer() {
        @Override
        public void run() {
            WorkerService.App.getInstance().getCountOfUnreadMessages(CurrentUser.getId(), new AsyncCallback<String[][]>() {
                public void onFailure(Throwable caught) {
                    GWT.log("Error in 'tm.run()' in 'getCountOfUnreadMessages'\n" + caught.toString());
                }

                public void onSuccess(String[][] result) {
                    if(result != null) {
                        usersPanel.clear();

                        for(int i = 0;i < result.length; i++) {
                            final Button user = new Button(result[i][0] + result[i][1]);
                            user.setTitle(result[i][0]);
                            user.addClickHandler(new ClickHandler() {
                                //@Override
                                public void onClick(ClickEvent event) {
                                    for(int i = 0; i < users.getWidgetCount(); i++) {
                                        Button bt = (Button) users.getWidget(i);
                                        if(bt.getText() == user.getTitle()) {
                                            bt.click();
                                            break;
                                        }
                                    }
                                }
                            });
                            usersPanel.add(user);
                        }
                        if(!usersPanel.isVisible()) {
                            usersPanel.setVisible(true);
                        }
                    }
                    else {
                        usersPanel.setVisible(false);
                    }
                }
            });
        }
    };

    public VerticalPanel Build() {
        VerticalPanel Wrapper = new VerticalPanel();
        initElements();
        setDependences();
        setStyle();
        setHandlers();

        tm.scheduleRepeating(delayMillis);
        Wrapper.add(label);
        Wrapper.add(usersPanel);
        return Wrapper;
    }

    private void initElements() {
        usersPanel = new VerticalPanel();
        label = new Label("Unread messages");
    }

    private void setDependences() {
        usersPanel.add(label);
        usersPanel.add(label);

        usersPanel.setVisible(false);
    }

    private void setStyle() {

    }

    private  void setHandlers() {

    }
}
