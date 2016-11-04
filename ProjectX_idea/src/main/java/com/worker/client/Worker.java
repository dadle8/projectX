package com.worker.client;

import com.google.gwt.core.client.GWT;
import com.worker.DB_hibernate.HibernateWorker;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */

public class Worker implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
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
                label.setText(loginBox.getText() + "</br>" + passwordBox.getValue() + "</br>" + passwordBox.getText() + "<hr>" + new MD5hashing().getMD5(passwordBox.getText()));
                WorkerService.App.getInstance().Auth(loginBox.getText(), new MD5hashing().getMD5(passwordBox.getText()), new AuthAsyncCallBack(label));
            }
        });

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                //label.setText(event.getResults());
            }
        });

        RootPanel.get("auth_form").add(form);
    }

    private static class AuthAsyncCallBack implements AsyncCallback<Boolean> {

        private Label label;

        AuthAsyncCallBack(Label label) {
            this.label = label;
        }

        public void onSuccess(Boolean ans) {
            if (ans)
            {
                label.setText("Accepted!");
            }
            else
            {
                label.setText("Refused!");
            }
            return;
        }

        public void onFailure(Throwable throwable) {
            {
                label.setText("SMTH goes wrong!");
            }
            return;
        }
    }
}
