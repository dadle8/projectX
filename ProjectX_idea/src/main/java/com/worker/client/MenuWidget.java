package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.util.Date;

/**
 * Created by AsmodeusX on 08.11.2016.
 */
public class MenuWidget {
    private Button toProfilePage = null;
    private Button toChatPage = null;
    private Button logOut = null;

    public MenuWidget()
    {
    }

    private void generateNextPage(Integer id)
    {
        Cookies.setCookie("NextPage", id.toString());
        Window.Location.reload();
    }

    public VerticalPanel Build()
    {
        this.setElements();
        this.setHandlers();
        return this.MakeWrapper();
    }

    private void setElements()
    {
        this.toProfilePage = new Button("Profile");
        this.toChatPage = new Button("Chat");
        this.logOut = new Button("Logout");
    }

    private void setHandlers()
    {
        this.toProfilePage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(2);
            }
        });
        this.toChatPage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(3);
            }
        });
        this.logOut.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                WorkerService.App.getInstance().logout(new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Logout failed.");
                    }

                    public void onSuccess(Void result) {
                        Cookies.removeCookie("longSID");
                        Window.alert("Logout successful!");
                        generateNextPage(0);
                    }
                });
            }
        });
    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(this.toChatPage);
        Wrapper.add(this.toProfilePage);
        Wrapper.add(this.logOut);
        return Wrapper;
    }
}
