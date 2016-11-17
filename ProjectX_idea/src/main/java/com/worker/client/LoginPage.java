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
    private Image logo = null;
    private HTML upperBound = null;
    private HTML lowerBound = null;
    private VerticalPanel contentPanel = null;
    private HorizontalPanel buttonsPanel = null;
    private Integer boundHeight = 20;

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
        this.setStyle();
        this.setDependences();
        this.setHandlers();

        RootPanel.get().clear();
        RootPanel.get().getElement().getStyle().setProperty("backgroundColor", "#fff5fa");
        RootPanel.get().add(this.MakeWrapper());
    }

    private void initElements()
    {
        authForm = new FormPanel();
        authForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        authForm.setMethod(FormPanel.METHOD_POST);

        contentPanel = new VerticalPanel();

        loginBox = new TextBox();
        passwordBox = new PasswordTextBox();
        label = new Label("SMTH is here.");
        submit = new Button("");
        register = new Button("");

        buttonsPanel = new HorizontalPanel();

        logo = new Image("./images/logo.png");
    }

    private void setStyle()
    {
        String authFormBackgroundColor = "#080245";
        upperBound = new HTML("<div style='top: 0;width: 100%;height: " + this.boundHeight + "px;background-color: #080245;'></div>");
        lowerBound = new HTML("<div style='bottom: 0;width: 100%;height: " + this.boundHeight + "px;background-color: #080245;'></div>");

        authForm.getElement().getStyle().setBackgroundColor(authFormBackgroundColor);
        authForm.getElement().getStyle().setProperty("borderRadius", "10px");
        authForm.getElement().getStyle().setProperty("margin", "0 20% 0 20%");
        authForm.getElement().getStyle().setProperty("padding", "20px 0 20px 0");
        authForm.getElement().getStyle().setProperty("minWidth", "500px");
        authForm.getElement().getStyle().setProperty("minHeight", "200px");

        contentPanel.getElement().getStyle().setProperty("width", "100%");
        contentPanel.getElement().getStyle().setProperty("padding", "0 60px 0 60px");

        loginBox.getElement().getStyle().setBackgroundImage("url('./images/user.png')");
        loginBox.getElement().getStyle().setProperty("backgroundRepeat", "no-repeat");
        loginBox.getElement().getStyle().setProperty("backgroundPosition", "10px 3px");
        loginBox.getElement().getStyle().setProperty("paddingLeft", "50px");
        loginBox.getElement().getStyle().setProperty("width", "calc(100% - 60px)"); //50 padding + 10 rounded corners

        passwordBox.getElement().getStyle().setBackgroundImage("url('./images/pass.png')");
        passwordBox.getElement().getStyle().setProperty("backgroundRepeat", "no-repeat");
        passwordBox.getElement().getStyle().setProperty("backgroundPosition", "10px 3px");
        passwordBox.getElement().getStyle().setProperty("paddingLeft", "50px");
        passwordBox.getElement().getStyle().setProperty("width", "calc(100% - 60px)");

        submit.getElement().setClassName("loginButton");

        register.getElement().setClassName("registerButton");

        label.getElement().getStyle().setColor("#FFFFFF");

        buttonsPanel.getElement().getStyle().setProperty("width", "100%");

        logo.getElement().getStyle().setProperty("height", "100%");
        logo.getElement().getStyle().setProperty("minHeight", "214px");
    }

    private void setDependences()
    {
        contentPanel.add(loginBox);
        contentPanel.add(passwordBox);
        contentPanel.add(label);

        buttonsPanel.add(submit);
        buttonsPanel.add(register);
        buttonsPanel.setCellHorizontalAlignment(submit, HasAlignment.ALIGN_LEFT);
        buttonsPanel.setCellHorizontalAlignment(register, HasAlignment.ALIGN_RIGHT);

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
        Wrapper.setHeight(Window.getClientHeight() + "px");
        Wrapper.setWidth(Window.getClientWidth() + "px");
        Wrapper.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
        Wrapper.add(this.upperBound);
        Wrapper.add(this.authForm);
        Wrapper.add(this.logo);
        Wrapper.setVerticalAlignment(HasAlignment.ALIGN_BOTTOM); //прижимаем к низу нижнюю границу
        Wrapper.add(this.lowerBound);
        return Wrapper;
    }
}