package com.worker.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.worker.DB_classes.UserEntity;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */

public class Worker implements EntryPoint {
    /**
     * This is the entry point method.
     */
    int delayMills = 60000; //per 1 minute
    Timer tmr = new Timer() {
        @Override
        public void run() {
            WorkerService.App.getInstance().getUserFromCurrentSession(new AsyncCallback<UserEntity>() {
                public void onFailure(Throwable caught) {
                    Window.alert("SMTH GOES WRONG WITH GETTINGUSERFROMSESSION");
                }

                public void onSuccess(UserEntity result) {
                    if (result == null)
                    {
                        return;
                    }
                    Geolocation geo = Geolocation.getIfSupported();
                    if (geo == null)
                    {
                        Window.alert("GEO NOT SUPPORTED");
                    }
                    geo.getCurrentPosition(new Callback<Position, PositionError>()
                    {

                        public void onSuccess(Position result)
                        {
                            WorkerService.App.getInstance().addCurrentGeo(result.getCoordinates().getLatitude(), result.getCoordinates().getLongitude(), Window.Navigator.getUserAgent(), new AsyncCallback<Boolean>() {
                                public void onFailure(Throwable caught) {
                                    Window.alert("SMTH GOES WRONG!");
                                }

                                public void onSuccess(Boolean result) {
                                    if (result)
                                    {
                                        //Window.alert("GEO SET SUCCESSFUL");
                                    } else {
                                        //Window.alert("SAME GEO");
                                    }
                                }
                            });
                        }

                        public void onFailure(PositionError reason)
                        {
                            Window.alert(reason.getMessage());
                        }
                    });
                }
            });
        }
    };

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
            // 4: map page
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
            case 4:
                displayMapWindow();
                break;
            default:
                displayLoginWindow();
                Cookies.setCookie("NextPage", "0");
        }
    }

    private void displayMapWindow()
    {
        MapPage NewPage = new MapPage();
        NewPage.Build();
    }

    private Boolean isAllowedForUnAuth(Integer id)
    {
        for(Integer ID : AllowedUnAuth)
            if (id.equals(ID))
                return true;
        return false;
    }

    private void BuildPage() {

        if (Cookies.getCookie("NextPage") == null) {
            buildNewPage(0);
            return;
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
        tmr.run();
        tmr.scheduleRepeating(delayMills);
        //Window.alert(Window.Navigator.getUserAgent());
        /*if (sessionID != null)
        {
            checkWithServerIfSessionIdIsStillLegal(sessionID);
        } else {
            displayLoginWindow();
        }*/
        BuildPage();
    }
}
