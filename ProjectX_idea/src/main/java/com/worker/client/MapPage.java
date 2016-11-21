package com.worker.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;

/**
 * Created by AsmodeusX on 16.11.2016.
 */
public class MapPage {

    private void draw() {
        Geolocation geo = Geolocation.getIfSupported();
        if (geo == null)
        {
            Window.alert("GEO NOT SUPPORTED");
        }
        geo.getCurrentPosition(new Callback<Position, PositionError>()
        {

            public void onSuccess(Position result)
            {
                BasicMapWidget wMap = new BasicMapWidget(Point.newInstance(result.getCoordinates().getLatitude(), result.getCoordinates().getLongitude()));
                MenuWidget Menu = new MenuWidget();
                VerticalPanel Wrapper = new VerticalPanel();
                Wrapper.addStyleName("map-page");
                Wrapper.add(Menu.Build("Map"));
                Wrapper.add(wMap);

                RootPanel.get("root-div").clear();
                RootPanel.get("root-div").add(Wrapper);
            }

            public void onFailure(PositionError reason)
            {
                Window.alert(reason.getMessage());
            }
        });
    }

    public MapPage()
    {

    }

    public void Build()
    {
        boolean sensor = true;

        /*HTML key = new HTML("<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDR4Z_yTOp-uo5IjtQhQrkna_Zb9SrMWu0\" type=\"text/javascript\"></script>");
        RootPanel.get().add(key);*/

        ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();

        loadLibraries.add(LoadLibrary.ADSENSE);
        loadLibraries.add(LoadLibrary.DRAWING);
        loadLibraries.add(LoadLibrary.GEOMETRY);
        loadLibraries.add(LoadLibrary.PANORAMIO);
        loadLibraries.add(LoadLibrary.PLACES);

        Runnable onLoad = new Runnable() {
            public void run() {
                draw();
            }
        };

        LoadApi.go(onLoad, loadLibraries, sensor, "key=AIzaSyDR4Z_yTOp-uo5IjtQhQrkna_Zb9SrMWu0");
    }

}
