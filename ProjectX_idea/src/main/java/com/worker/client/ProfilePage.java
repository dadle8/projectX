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
    private static UserEntity CurrentUser = null;
    private MenuWidget Menu = new MenuWidget();
    private HTML content = null;

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

                    RootPanel.get("root-div").get().clear();
                    RootPanel.get("root-div").add(MakeWrapper());
                }
            }
        });
    }

    private void setElements()
    {
        this.content = new HTML(
                "<p><strong>Login:</strong> " + CurrentUser.getLogin() + "</p>" +
                "<strong>Password:</strong> " + CurrentUser.getPassword() + "</p>" +
                "<strong>eMail:</strong> " + CurrentUser.getEmail() + "</p>"
        );
    }

    private void setHandlers()
    {
    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(this.Menu.Build("Profile"));
        Wrapper.add(this.content);
        return Wrapper;
    }
}
