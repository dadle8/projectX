package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Created by AsmodeusX on 06.11.2016.
 */
public class RegisterPage {

    private FlowPanel content = null;

    private FlowPanel headerPanel = null;
    private HTMLPanel headerTitle = null;
    private FlowPanel logo = null;

    private FormPanel regForm = null;
    private FlowPanel formContainer = null;

    private HTMLPanel loginLabel = null;
    private TextBox loginBox = null;

    private HTMLPanel nameLabel = null;
    private TextBox nameBox = null;

    private HTMLPanel surnameLabel = null;
    private TextBox surnameBox = null;

    private HTMLPanel passwordLabel = null;
    private PasswordTextBox passwordBox = null;

    private HTMLPanel confirmPasswordLabel = null;
    private PasswordTextBox confirmPasswordBox = null;

    private HTMLPanel emailLabel = null;
    private TextBox emailBox = null;

    private Button submit = null;
    private Button linkToLogin = null;

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

        RootPanel.get("root-div").clear();
        RootPanel.get("root-div").add(this.content);
    }

    private void setElements()
    {
        content = new FlowPanel();
        content.addStyleName("register-page");

        headerPanel = new FlowPanel();
        headerPanel.addStyleName("header-panel");
        headerTitle = new HTMLPanel("h1", "PartyCoon");
        logo = new FlowPanel();
        logo.addStyleName("logo-header");

        regForm = new FormPanel();
        regForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        regForm.setMethod(FormPanel.METHOD_POST);

        formContainer = new FlowPanel();

        loginLabel = new HTMLPanel("label", "Login");
        loginBox = new TextBox();
        loginBox.getElement().setAttribute("placeholder", "Login");

        nameLabel = new HTMLPanel("label", "Your name");
        nameBox = new TextBox();
        nameBox.getElement().setAttribute("placeholder", "Name");

        surnameLabel = new HTMLPanel("label","Surname");
        surnameBox = new TextBox();
        surnameBox.getElement().setAttribute("placeholder", "Surname");

        passwordLabel = new HTMLPanel("label", "Password");
        passwordBox = new PasswordTextBox();
        passwordBox.getElement().setAttribute("placeholder", "Password");

        confirmPasswordLabel = new HTMLPanel("label", "Confirm password");
        confirmPasswordBox = new PasswordTextBox();
        confirmPasswordBox.getElement().setAttribute("placeholder", "Password confirm");

        emailLabel = new HTMLPanel("label", "EMail:");
        emailBox = new TextBox();
        emailBox.getElement().setAttribute("placeholder", "Email");

        submit = new Button("Submit");
        linkToLogin = new Button("Login");

        // Dependencies

        content.add(headerPanel);
        content.add(regForm);

        headerPanel.add(logo);
        headerPanel.add(headerTitle);

        regForm.setWidget(formContainer);

        formContainer.add(loginLabel);
        formContainer.add(nameLabel);
        formContainer.add(surnameLabel);
        formContainer.add(passwordLabel);
        formContainer.add(confirmPasswordLabel);
        formContainer.add(emailLabel);
        formContainer.add(submit);
        formContainer.add(linkToLogin);

        loginLabel.add(loginBox);
        nameLabel.add(nameBox);
        surnameLabel.add(surnameBox);
        passwordLabel.add(passwordBox);
        confirmPasswordLabel.add(confirmPasswordBox);
        emailLabel.add(emailBox);
    }

    private void setHandlers(){
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
                WorkerService.App.getInstance().registerNewUser(loginBox.getText(), nameBox.getText(), surnameBox.getText(), hash, emailBox.getText(), new AsyncCallback<Boolean>() {
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

        linkToLogin.addClickHandler(new ClickHandler() {
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

}
