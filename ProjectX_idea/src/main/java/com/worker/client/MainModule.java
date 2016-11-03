package com.worker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.client.login.client.LoginModule;
import com.worker.shared.UsersEntity;

/**
 * Created by Слава on 01.11.2016.
 */
public class MainModule implements EntryPoint {

    public void onModuleLoad() {
        final LoginModule loginModule = new LoginModule();
        RootPanel.get("welcome").add(loginModule);
        
        boolean islogin = false;

        if(!islogin) {
            loginModule.loginbt.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    RootPanel.get("welcome").clear();
                    final TabBar coreapp = new TabBar();

                    coreapp.addTab("Map  ");
                    coreapp.addTab("  Message  ");
                    coreapp.addTab("  My profile");

                    RootPanel.get("coreapp").add(coreapp);

                    final VerticalPanel chat = new VerticalPanel();

                    final VerticalPanel dialog = new VerticalPanel();
                    final TextBox message = new TextBox();
                    message.setText("Input something");
                    final Button sendmessage = new Button("Send");

                    chat.add(dialog);
                    chat.add(message);
                    chat.add(sendmessage);

                    RootPanel.get("chat").add(chat);

                    dialog.add(new Label("1"));
                    dialog.add(new Label("2"));
                    dialog.add(new Label("3"));
                    chat.setVisible(false);

                    coreapp.addSelectionHandler(new SelectionHandler<Integer>() {
                        public void onSelection(SelectionEvent<Integer> event) {
                            // Let the user know what they just did.
                            if(event.getSelectedItem() == 1) {
                                chat.setVisible(true);
                            }
                            else {
                                chat.setVisible(false);
                                Window.alert("You clicked tab " + event.getSelectedItem());
                            }
                        }
                    });
                }
            });


        }
        /*final MainRpcServiceAsync rpcAsync = GWT.create(MainRpcService.class);

        final TabBar coreapp = new TabBar();

        coreapp.addTab("Map  ");
        coreapp.addTab("  Message  ");
        coreapp.addTab("  My profile");

        RootPanel.get("coreapp").add(coreapp);

        final VerticalPanel chat = new VerticalPanel();

        final VerticalPanel dialog = new VerticalPanel();
        final TextBox message = new TextBox();
        message.setText("Input something");
        final Button sendmessage = new Button("Send");

        chat.add(dialog);
        chat.add(message);
        chat.add(sendmessage);

        RootPanel.get("chat").add(chat);

        dialog.add(new Label("1"));
        dialog.add(new Label("2"));
        dialog.add(new Label("3"));
        chat.setVisible(false);

        coreapp.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                // Let the user know what they just did.
                if(event.getSelectedItem() == 1) {
                    chat.setVisible(true);
                }
                else {
                    chat.setVisible(false);
                    Window.alert("You clicked tab " + event.getSelectedItem());
                }
            }
        });
        */
    }
}
