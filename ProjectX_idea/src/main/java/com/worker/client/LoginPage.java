package com.worker.client;

        import com.google.gwt.dom.client.Style;
        import com.google.gwt.event.dom.client.ClickEvent;
        import com.google.gwt.event.dom.client.ClickHandler;
        import com.google.gwt.user.client.Cookies;
        import com.google.gwt.user.client.Window;
        import com.google.gwt.user.client.rpc.AsyncCallback;
        import com.google.gwt.user.client.ui.*;
        import com.worker.DB_classes.UserEntity;

        import javax.persistence.criteria.Root;
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
    private HTML logo = null;
    private VerticalPanel contentPanel = null;
    private HorizontalPanel buttonsPanel = null;

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
        this.initElements();
        this.setDependences();
        this.setHandlers();

        RootPanel.get("root-div").clear();
        RootPanel.get("root-div").add(this.MakeWrapper());
    }

    private void initElements()
    {
        authForm = new FormPanel();
        authForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        authForm.setMethod(FormPanel.METHOD_POST);

        contentPanel = new VerticalPanel();

        loginBox = new TextBox();
        loginBox.getElement().setAttribute("placeholder","login");
        passwordBox = new PasswordTextBox();
        passwordBox.getElement().setAttribute("placeholder","password");
        label = new Label("SMTH is here.");
        submit = new Button("Submit");
        register = new Button("Register");

        buttonsPanel = new HorizontalPanel();

        logo = new HTML("<div class='logo'></div>");
    }

    private void setDependences()
    {
        contentPanel.add(loginBox);
        contentPanel.add(passwordBox);
        contentPanel.add(label);

        buttonsPanel.add(submit);
        buttonsPanel.add(register);
        contentPanel.add(buttonsPanel);

        authForm.setWidget(contentPanel);
    }

    private void setHandlers() {

        authForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                if (loginBox.getText().length() == 0) {
                    label.setText("Login field is empty!");
                    event.cancel();
                }
                String hash = new MD5hashing().getMD5(passwordBox.getText());
                label.setText(loginBox.getText() + "   " + passwordBox.getValue() + "   " + passwordBox.getText() + "   " + hash);
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
        Wrapper.addStyleName("login-page");
        Wrapper.add(this.authForm);
        Wrapper.add(this.logo);
        return Wrapper;
    }
}