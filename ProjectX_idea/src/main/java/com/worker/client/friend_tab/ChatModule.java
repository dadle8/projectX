package com.worker.client.friend_tab;

import com.google.gwt.user.client.ui.*;

/**
 * Created by Слава on 03.11.2016.
 */
public class ChatModule extends Composite{
    final HorizontalPanel friendsPanel = new HorizontalPanel();
    final VerticalPanel chat = new VerticalPanel();
    final VerticalPanel dialogs = new VerticalPanel();
    final TextBox message = new TextBox();
    final Button sendbt = new Button("Send");

    public ChatModule() {
        message.setText("Input something");
        friendsPanel.add(dialogs);
        friendsPanel.add(chat);
        friendsPanel.setSize("500px", "250px");
        dialogs.add(new Button("person 1"));
        dialogs.add(new Button("person 2"));
        dialogs.add(new Button("person 3"));
        dialogs.add(new Button("person 4"));
        dialogs.add(new Button("person 5"));
        dialogs.add(new Button("person 6"));

        chat.add(new HTMLPanel("This is a <b>HTMLPanel</b> containing\"\n" +
                "      +\" html contents. \"\n" +
                "      +\" <i>By putting some fairly large contents in the middle\"\n" +
                "      +\" and setting its size explicitly, it becomes a scrollable area\"\n" +
                "      +\" within the page, but without requiring the use of an IFRAME.</i>\"\n" +
                "      +\" <u>Here's quite a bit more meaningless text that will serve\"\n" +
                "      +\" to make this thing scroll off the bottom of its visible area.\"\n" +
                "      +\" Otherwise, you might have to make it really, really\"\n" +
                "      +\" small in order to see the nifty scroll bars!</u>"));
        chat.add(message);
        chat.add(sendbt);
        initWidget(friendsPanel);
    }
}
