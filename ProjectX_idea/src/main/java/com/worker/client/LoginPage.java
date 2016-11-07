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
 * Created by AsmodeusX on 07.11.2016.
 */
public class LoginPage {
    private FormPanel authForm = null;
    private TextBox loginBox = null;
    private PasswordTextBox passwordBox = null;
    private Label label = null;
    private Button submit = null;
    private Button register = null;

    public LoginPage()
    {
    }

    private void generateNextPage(Integer id)
    {
        Cookies.setCookie("NextPage", id.toString());
        Window.Location.reload();
    }

    public void Build()
    {
        this.setElements();
        this.setHandlers();
        RootPanel.get().clear();
        RootPanel.get().add(this.MakeWrapper());
    }

    private void setElements()
    {
        authForm = new FormPanel();
        authForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        authForm.setMethod(FormPanel.METHOD_POST);

        VerticalPanel panel = new VerticalPanel();
        loginBox = new TextBox();
        passwordBox = new PasswordTextBox();
        label = new Label("SMTH is here.");
        submit = new Button("Submit");
        register = new Button("Register");

        panel.add(loginBox);
        panel.add(passwordBox);
        panel.add(label);
        panel.add(submit);
        panel.add(register);

        authForm.setWidget(panel);
    }

    private void setHandlers() {

        authForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                if (loginBox.getText().length() == 0) {
                    label.setText("Login field is empty!");
                    event.cancel();
                }
                String hash = new MD5hashing().getMD5(passwordBox.getText());
                label.setText(loginBox.getText() + "</br>" + passwordBox.getValue() + "</br>" + passwordBox.getText() + "<hr>" + hash);
                WorkerService.App.getInstance().loginServer(loginBox.getText(), hash, new AsyncCallback<UserEntity>() {
                    public void onSuccess(UserEntity result) {
                        if (result != null && result.getLoggedIn() == 1) {
                            final long DURATION = 1000 * 60 * 60 * 24; //24 hours
                            Date expires = new Date(System.currentTimeMillis() + DURATION);
                            Cookies.setCookie("longSID", result.getSessionId(), expires, null, "/", false);
                            Window.alert("Access Granted. Cookie was set. Cookie = " + Cookies.getCookie("longSID"));
                            generateNextPage(2);
                        } else {
                            Window.alert("Access Denied. Check your user-name and password.");
                        }

                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Access Denied. Failure." + caught.getMessage());
                    }
                });
            }
        });

        submit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                authForm.submit();
            }
        });

        register.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(1);
            }
        });

        authForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                //label.setText(event.getResults());
            }
        });
    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(this.authForm);
        return Wrapper;
    }
}