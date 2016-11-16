package com.worker.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.worker.DB_classes.UserEntity;

/**
 * Created by Слава on 16.11.2016.
 */
public class UnreadMessagesWidget {
    private VerticalPanel panel = null;
    private ListBox users = null;
    private Label label = null;

    private static UserEntity CurrentUser = null;

    public UnreadMessagesWidget() {}

    public UnreadMessagesWidget(UserEntity CurrentUser) {
        this.CurrentUser = CurrentUser;
    }

    int delayMillis  =  2000;

    Timer tm = new Timer() {
        @Override
        public void run() {
            WorkerService.App.getInstance().getCountOfUnreadMessages(CurrentUser.getId(), new AsyncCallback<String[]>() {
                public void onFailure(Throwable caught) {

                }

                public void onSuccess(String[] result) {
                    if(result != null) {
                        users.clear();
                        for(String str : result) {
                            users.addItem(str);
                        }
                        users.setVisibleItemCount(result.length);
                        panel.setVisible(true);
                    }
                    else {
                        panel.setVisible(false);
                    }
                }
            });
        }
    };

    public VerticalPanel Build() {
        initElements();
        setDependences();
        setStyle();
        setHandlers();

        tm.scheduleRepeating(delayMillis);

        return panel;
    }

    private void initElements() {
        panel = new VerticalPanel();
        users = new ListBox();
        label = new Label("Unread messages");
    }

    private void setDependences() {
        panel.add(label);
        panel.add(users);

        panel.setVisible(false);
    }

    private void setStyle() {

    }

    private  void setHandlers() {

    }
}
