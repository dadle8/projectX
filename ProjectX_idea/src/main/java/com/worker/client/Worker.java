package com.worker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import javax.enterprise.inject.New;
import javax.servlet.http.Cookie;
import java.util.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */

public class Worker implements EntryPoint {
    /**
     * This is the entry point method.
     */

    ArrayList<Integer> AllowedUnAuth = new ArrayList<Integer>() {{
        add(0);
        add(1);
    }};

    private void displayLoginWindow()
    {
        LoginPage NewPage = new LoginPage();
        NewPage.Build();
    }

    private void displayProfileWindow()
    {
        ProfilePage NewPage = new ProfilePage();
        NewPage.Build();
    }

    private void displayRegisterWindow()
    {
        RegisterPage NewPage = new RegisterPage();
        NewPage.Build();
    }

    private void displayChatWindow()
    {
        ChatPage NewPage = new ChatPage();
        NewPage.Build();
    }

    private void buildNewPage(Integer id)
    {
        Cookies.setCookie("NextPage", id.toString());
        switch (id)
        {
            // 0: login page
            // 1: register page
            // 2: profile page
            // 3: chat page
            case 0:
                displayLoginWindow();
                break;
            case 1:
                displayRegisterWindow();
                break;
            case 2:
                displayProfileWindow();
                break;
            case 3:
                displayChatWindow();
                break;
            default:
                displayLoginWindow();
                Cookies.setCookie("NextPage", "0");
        }
    }

    private Boolean isAllowedForUnAuth(Integer id)
    {
        for(Integer ID : AllowedUnAuth)
            if (id.equals(ID))
                return true;
        return false;
    }

    private void BuildPage(String sessionID) {

        if (Cookies.getCookie("NextPage") == null)
        {
            buildNewPage(0);
        }

        WorkerService.App.getInstance().loginFromSessionServer(new AsyncCallback<UserEntity>() {
            public void onFailure(Throwable caught) {
                buildNewPage(0);
            }

            public void onSuccess(UserEntity result) {
                Integer NextPage = Integer.parseInt(Cookies.getCookie("NextPage"));

                if (result == null) { //empty session
                    if (isAllowedForUnAuth(NextPage)) {
                        buildNewPage(NextPage);
                    } else {
                        buildNewPage(0);
                    }
                } else {
                    if (result.getLoggedIn() == 1) { //logged in
                        buildNewPage(NextPage);
                    } else { // not logged in
                        if (isAllowedForUnAuth(NextPage)) {
                            buildNewPage(NextPage);
                        } else {
                            buildNewPage(0);
                        }
                    }
                }

            }
        });

    }

    public void onModuleLoad() {
        String sessionID = Cookies.getCookie("longSID");
        /*if (sessionID != null)
        {
            checkWithServerIfSessionIdIsStillLegal(sessionID);
        } else {
            displayLoginWindow();
        }*/
        BuildPage(sessionID);
    }
}
