package com.worker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.client.friend_tab.ChatModule;
import com.worker.client.login.LoginModule;
import com.worker.shared.PersonsEntity;

import java.sql.Date;
/**
 * Created by Слава on 01.11.2016.
 */
public class MainModule implements EntryPoint {

    private int idperson;

    public void onModuleLoad() {
        final MainRpcServiceAsync rpcAsync = GWT.create(MainRpcService.class);
        final TabPanel coreapp = new TabPanel();
        final LoginModule loginModule = new LoginModule();
        final ChatModule chatModule = new ChatModule();

        RootPanel.get("welcome").add(loginModule);

        coreapp.add(new Label("Map"),"Map");
        coreapp.add(chatModule,"Message");
        coreapp.add(new Label("My profile"),"My profile");



        loginModule.createbt.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PersonsEntity person = new PersonsEntity();

                person.setIsOnline(1);

                person.setLastdatelogin(new Date(0));
                person.setLogin(loginModule.newlogintb.getText());
                person.setPassword(loginModule.passwordtb.getText());
                person.setName(loginModule.nametb.getText());
                person.setSurname(loginModule.surnametb.getText());

                rpcAsync.save(person, new AsyncCallback<PersonsEntity>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Faild");
                    }

                    public void onSuccess(PersonsEntity result) {
                        RootPanel.get("welcome").clear();
                        RootPanel.get("coreapp").add(coreapp);
                        idperson = result.getIdPerson();
                        Window.alert(Integer.toString(idperson));
                    }
                });

            }
        });
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
