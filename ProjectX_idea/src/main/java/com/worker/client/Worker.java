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
        final Button button = new Button("Click me");
        final Label label = new Label("Text area");
        final TextBox txtBox = new TextBox();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (txtBox.getText().equals("")) {
                    label.setText("NULLable request.");
                } else {
                    WorkerService.App.getInstance().makeRequest(txtBox.getText(), new ListedAsyncCallback(label));
                }
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot1").add(txtBox);
        RootPanel.get("slot2").add(button);
        RootPanel.get("slot3").add(label);
    }

    private static class ListedAsyncCallback implements AsyncCallback<List<String>> {
        private Label label;

        //Can't catch syntax errors :: TODO

        public ListedAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(List<String> ans) {
            String formatedAns = "";
            if (ans.isEmpty())
            {
                label.setText("NULLable answer.");
                return;
            }
            for(Object line : ans)
            {
                System.out.println(line.toString());
                formatedAns = formatedAns.concat(line.toString());
                formatedAns = formatedAns.concat("</br>");
            }
            label.getElement().setInnerHTML(formatedAns);
            return;
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!" + throwable.getCause().toString());
            return;
        }
    }
}
