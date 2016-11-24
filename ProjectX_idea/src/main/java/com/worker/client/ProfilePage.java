package com.worker.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.worker.DB_classes.UserEntity;

/**
 * Created by AsmodeusX on 06.11.2016.
 */
public class ProfilePage {

    private static UserEntity CurrentUser = null;

    private MenuWidget Menu = new MenuWidget();

    private FriendshipInvitesWidget Invites = new FriendshipInvitesWidget();
    private FindFriendsWidget FindFriends = new FindFriendsWidget();
    private ShowFriendsWidget ShowFriends = new ShowFriendsWidget();

    private FlowPanel content = null;
    private FlowPanel innerContent = null;

    public ProfilePage()
    {

    }

    // never used?
    private void generateNextPage(Integer id)
    {
        Cookies.setCookie("NextPage", id.toString());
        Window.Location.reload();
    }

    public void Build()
    {
        WorkerService.App.getInstance().getUserFromCurrentSession(new AsyncCallback<UserEntity>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH IS WRONG WITH SESSION");
            }

            public void onSuccess(UserEntity user) {
                CurrentUser = user;
                if (CurrentUser != null) {
                    setElements();
                    setHandlers();

                    RootPanel.get("root-div").get().clear();
                    RootPanel.get("root-div").add(content);
                }
            }
        });
    }

    private void setElements()
    {
        content = new FlowPanel();
        innerContent = new FlowPanel();
        innerContent.addStyleName("profile-page");

        content.add(Menu.Build("Profile"));
        content.add(innerContent);

        innerContent.add(new HTMLPanel("p", "<strong>Login:</strong> " + CurrentUser.getLogin()));
        innerContent.add(new HTMLPanel("p", "<strong>Password:</strong> " + CurrentUser.getPassword()));
        innerContent.add(new HTMLPanel("p", "<strong>eMail:</strong> " + CurrentUser.getEmail()));
        innerContent.add(new HTMLPanel("p", "<strong>ref:</strong> " + CurrentUser.getRef()));

        content.add(this.Invites.Build());
        content.add(this.FindFriends.Build());
        content.add(this.ShowFriends.Build());
    }

    private void setHandlers()
    {
    }

}
