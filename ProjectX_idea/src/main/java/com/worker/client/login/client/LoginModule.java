package com.worker.client.login.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Слава on 03.11.2016.
 */
public class LoginModule extends Composite {

    TabPanel welcomePanel = new TabPanel();
    HorizontalPanel loginPanel = new HorizontalPanel();
    VerticalPanel loginrightPanel = new VerticalPanel();
    public final TextBox logintb = new TextBox();
    final TextBox passwordlogintb = new TextBox();
    public final Button loginbt = new Button("Log In");

    VerticalPanel loginleftPanel = new VerticalPanel();
    final Label loginlb = new Label("Login");
    final Label passwordlb = new Label("Password");

    HorizontalPanel singupPanel = new HorizontalPanel();
    VerticalPanel singuprightPanel = new VerticalPanel();
    final TextBox nametb = new TextBox();
    final TextBox surnametb = new TextBox();
    final TextBox newlogintb = new TextBox();
    final TextBox passwordtb = new TextBox();
    final Button createbt = new Button("Log In");

    VerticalPanel singupleftPanel = new VerticalPanel();
    final Label namelb = new Label("Name");
    final Label surnamelb = new Label("Surname");
    final Label newloginlb = new Label("Login");
    final Label newpasswordlb = new Label("Password");

    public LoginModule() {

        /* Start create LoginPanel */
        loginrightPanel.add(logintb);
        loginrightPanel.add(passwordlogintb);
        loginrightPanel.add(loginbt);

        loginleftPanel.add(loginlb);
        loginleftPanel.add(passwordlb);

        loginPanel.add(loginleftPanel);
        loginPanel.add(loginrightPanel);
        /* End create LoginPanel */


        /* Start create singupPanel */
        singuprightPanel.add(nametb);
        singuprightPanel.add(surnametb);
        singuprightPanel.add(newlogintb);
        singuprightPanel.add(passwordtb);
        singuprightPanel.add(createbt);

        singupleftPanel.add(namelb);
        singupleftPanel.add(surnamelb);
        singupleftPanel.add(newloginlb);
        singupleftPanel.add(newpasswordlb);

        singupPanel.add(singupleftPanel);
        singupPanel.add(singuprightPanel);
        /* End create singupPanel */

        welcomePanel.add(loginPanel, "LOG IN");
        welcomePanel.add(singupPanel, "SING UP");

        welcomePanel.selectTab(0);
        welcomePanel.setSize("500px","250px");

        initWidget(welcomePanel);
        //RootPanel.get("welcome").add(welcomePanel);
        // final UsersEntity user = new UsersEntity();


      /*  loginbt.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                Window.alert("Work?");
                user.setName(nametb.getText());
                user.setPassword(passwordtb.getText());

                rpcAsync.save(user, new AsyncCallback<UsersEntity>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Faild");
                    }

                    public void onSuccess(UsersEntity result) {
                        Window.alert("Success");
                    }
                });
            }
        });

        createbt.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                Window.alert("Work?");
               /* user.setName(nametb.getText());
                user.setPassword(passwordtb.getText());

                rpcAsync.save(user, new AsyncCallback<UsersEntity>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Faild");
                    }

                    public void onSuccess(UsersEntity result) {
                        Window.alert("Success");
                    }
                });
            }
        }); */
    }

}
