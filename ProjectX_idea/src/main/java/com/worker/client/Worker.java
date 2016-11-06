package com.worker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */

public class Worker implements EntryPoint {
    /**
     * This is the entry point method.
     */

    private void checkWithServerIfSessionIdIsStillLegal(String sessionID)
    {
        WorkerService.App.getInstance().loginFromSessionServer(new AsyncCallback<UserEntity>()
        {
            public void onFailure(Throwable caught)
            {
                displayLoginWindow();
            }

            public void onSuccess(UserEntity result)
            {
                if (result == null)
                {
                    displayLoginWindow();
                } else
                {
                    if (result.getLoggedIn() == 1)
                    {
                        displayProfileWindow();
                    } else
                    {
                        displayLoginWindow();
                    }
                }
            }

        });
    }

    private void displayLoginWindow()
    {
        final FormPanel form = new FormPanel();
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);

        VerticalPanel panel = new VerticalPanel();

        final TextBox loginBox = new TextBox();
        final PasswordTextBox passwordBox = new PasswordTextBox();
        final Label label = new Label("SMTH is here.");

        panel.add(loginBox);
        panel.add(passwordBox);
        panel.add(label);

        panel.add(new Button("Submit", new ClickHandler() {
            public void onClick(ClickEvent event) {
                form.submit();
            }
        }));

        form.setWidget(panel);

        form.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                if (loginBox.getText().length() == 0) {
                    label.setText("Login field is empty!");
                    event.cancel();
                }
                String hash = new MD5hashing().getMD5(passwordBox.getText());
                label.setText(loginBox.getText() + "</br>" + passwordBox.getValue() + "</br>" + passwordBox.getText() + "<hr>" + hash);
                WorkerService.App.getInstance().loginServer(loginBox.getText(), hash, new AsyncCallback<UserEntity>()
                {
                    public void onSuccess(UserEntity result)
                    {
                        if (result != null && result.getLoggedIn() == 1)
                        {
                            final long DURATION = 1000 * 60 * 60 * 24; //24 hours
                            Date expires = new Date(System.currentTimeMillis() + DURATION);
                            Cookies.setCookie("longSID", result.getSessionId(), expires, null, "/", false);
                            Window.alert("Access Granted. Cookie was set. Cookie = " + Cookies.getCookie("longSID"));
                            Window.Location.reload();
                        } else
                        {
                            Window.alert("Access Denied. Check your user-name and password.");
                        }

                    }

                    public void onFailure(Throwable caught)
                    {
                        Window.alert("Access Denied. Failure." + caught.getMessage());
                    }
                });
            }
        });

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                //label.setText(event.getResults());
            }
        });

        RootPanel.get("auth_form").add(form);
    }

    private void displayProfileWindow()
    {
        ProfilePage NewPage = new ProfilePage();
        NewPage.Build();
    }

    public void onModuleLoad() {
        String sessionID = Cookies.getCookie("longSID");
        if (sessionID != null)
        {
            checkWithServerIfSessionIdIsStillLegal(sessionID);
        } else {
            displayLoginWindow();
        }
    }
}
