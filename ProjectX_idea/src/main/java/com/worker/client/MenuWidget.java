package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Created by AsmodeusX on 08.11.2016.
 */
public class MenuWidget {

    private FlowPanel content = null;

    private FlowPanel logo = null;

    private Button toProfilePage = null;
    private Button toChatPage = null;
    private Button logOut = null;
    private Button toMapPage = null;

    public MenuWidget()
    {
    }

    private void generateNextPage(Integer id)
    {
        Cookies.setCookie("NextPage", id.toString());
        Window.Location.reload();
    }

    public FlowPanel Build(String title)
    {
        this.setElements(title);
        this.setHandlers();
        return this.content;
    }

    private void setElements(String title)
    {
        content = new FlowPanel();
        content.addStyleName("menu-widget");

        logo = new FlowPanel();
        logo.addStyleName("logo-header");
        toProfilePage = new Button("Profile");
        toChatPage = new Button("Chat");
        logOut = new Button("Logout");
        toMapPage = new Button("Map");

        content.add(logo);
        content.add(toProfilePage);
        content.add(toChatPage);
        content.add(toMapPage);
        content.add(logOut);
        content.add(new HTMLPanel("h1", title));
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
        this.toMapPage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(4);
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

}
