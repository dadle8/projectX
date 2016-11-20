package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AsmodeusX on 06.11.2016.
 */
public class RegisterPage {
    private FormPanel regForm = null;

    private Label loginLabel = null;
    private TextBox loginBox = null;

    private Label nameLabel = null;
    private TextBox nameBox = null;

    private Label surNameLabel = null;
    private TextBox surNameBox = null;

    private Label passwordLabel = null;
    private PasswordTextBox passwordBox = null;

    private Label confirmPasswordLabel = null;
    private PasswordTextBox confirmPasswordBox = null;

    private Label emailLabel = null;
    private TextBox emailBox = null;

    private Button submit = null;
    private Button loginForm = null;

    public RegisterPage()
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
        regForm = new FormPanel();
        regForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        regForm.setMethod(FormPanel.METHOD_POST);

        VerticalPanel panel = new VerticalPanel();

        loginLabel = new Label("Type a login:");
        loginBox = new TextBox();

        nameLabel = new Label("Type a name:");
        nameBox = new TextBox();

        surNameLabel = new Label("Type a surname:");
        surNameBox = new TextBox();

        passwordLabel = new Label("Type a password:");
        passwordBox = new PasswordTextBox();

        confirmPasswordLabel = new Label("Confirm a password:");
        confirmPasswordBox = new PasswordTextBox();

        emailLabel = new Label("Type an E-Mail:");
        emailBox = new TextBox();

        submit = new Button("Submit");
        loginForm = new Button("LoginForm");

        panel.add(loginLabel);
        panel.add(loginBox);
        panel.add(nameLabel);
        panel.add(nameBox);
        panel.add(surNameLabel);
        panel.add(surNameBox);
        panel.add(passwordLabel);
        panel.add(passwordBox);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordBox);
        panel.add(emailLabel);
        panel.add(emailBox);
        panel.add(submit);
        panel.add(loginForm);

        regForm.setWidget(panel);
    }

    private void setHandlers()
    {
        regForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                if (loginBox.getText().length() == 0) {
                    Window.alert("LoginBox in empty!");
                    event.cancel();
                    return;
                }
                if (!passwordBox.getText().equals(confirmPasswordBox.getText()))
                {
                    Window.alert("Passwords are not equal!");
                    event.cancel();
                    return;
                }

                String hash = new MD5hashing().getMD5(passwordBox.getText());
                WorkerService.App.getInstance().registerNewUser(loginBox.getText(), nameBox.getText(), surNameBox.getText(), hash, emailBox.getText(), new AsyncCallback<Boolean>() {
                    public void onSuccess(Boolean result) {
                        if (result) {
                            Window.alert("Registered successful!");
                            generateNextPage(0);
                        } else {
                            Window.alert("Current login is busy.");
                        }
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Failure." + caught.getMessage());
                    }
                });
            }
        });

        submit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                regForm.submit();
            }
        });

        loginForm.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                generateNextPage(0);
            }
        });

        regForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                //label.setText(event.getResults());
            }
        });
    }

    private VerticalPanel MakeWrapper()
    {
        VerticalPanel Wrapper = new VerticalPanel();
        Wrapper.add(this.regForm);
        return Wrapper;
    }
}
