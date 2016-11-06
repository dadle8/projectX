package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

/**
 * Created by AsmodeusX on 06.11.2016.
 */
public class ProfilePage {
    private Label Loginlabel = null;
    private Label Passwordlabel = null;
    private Label Emaillabel = null;
    private Button btn = null;
    private static UserEntity CurrentUser = null;

    public ProfilePage()
    {

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
                    setHandlers();
                    RootPanel.get().clear();
                    RootPanel.get().add(MakeWrapper());
                }
            }
        });
    }

    private void setElements()
    {
        this.Loginlabel =    new Label("Login:    " + CurrentUser.getLogin());
        this.Passwordlabel = new Label("Password: " + CurrentUser.getPassword());
        this.Emaillabel =    new Label("eMail:    " + CurrentUser.getEmail());
        this.btn =           new Button("Logout");
    }

    private void setHandlers()
    {
        this.btn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                WorkerService.App.getInstance().logout(new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Logout failed.");
                    }

                    public void onSuccess(Void result) {
                        Cookies.removeCookie("longSID");
                        Window.alert("Logout successful!");
                        Window.Location.reload();
                    }
                });
            }
        });
    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(this.Loginlabel);
        Wrapper.add(this.Passwordlabel);
        Wrapper.add(this.Emaillabel);
        Wrapper.add(this.btn);
        return Wrapper;
    }
}
