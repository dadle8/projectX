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
    private static UserEntity CurrentUser = null;
    private MenuWidget Menu = new MenuWidget();

    public ProfilePage()
    {

    }

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
    }

    private void setHandlers()
    {
    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(this.Menu.Build());
        Wrapper.add(this.Loginlabel);
        Wrapper.add(this.Passwordlabel);
        Wrapper.add(this.Emaillabel);
        return Wrapper;
    }
}
