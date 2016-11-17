package com.worker.client;

import com.google.gwt.ajaxloader.client.ArrayHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.adsense.AdFormat;
import com.google.gwt.maps.client.adsense.AdUnitOptions;
import com.google.gwt.maps.client.adsense.AdUnitWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.maps.client.controls.ControlPosition;
import com.google.gwt.maps.client.events.MouseEvent;
import com.google.gwt.maps.client.events.channelnumber.ChannelNumberChangeMapEvent;
import com.google.gwt.maps.client.events.channelnumber.ChannelNumberChangeMapHandler;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.events.format.FormatChangeMapEvent;
import com.google.gwt.maps.client.events.format.FormatChangeMapHandler;
import com.google.gwt.maps.client.events.mapchange.MapChangeMapEvent;
import com.google.gwt.maps.client.events.mapchange.MapChangeMapHandler;
import com.google.gwt.maps.client.events.position.PositionChangeMapEvent;
import com.google.gwt.maps.client.events.position.PositionChangeMapHandler;
import com.google.gwt.maps.client.overlays.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.server.Pair;

import java.util.ArrayList;

public class BasicMapWidget extends Composite {

    private final VerticalPanel pWidget;
    private MapWidget mapWidget;
    private Point centerPoint;

    private Marker markerBasic;

    public BasicMapWidget(Point pt) {
        pWidget = new VerticalPanel();
        initWidget(pWidget);
        centerPoint = pt;

        draw();
    }

    //markerBasic.setAnimation(Animation.STOPANIMATION);
    //markerBasic.setAnimation(Animation.BOUNCE);

    private void draw() {
        drawMap();
        //drawMapAds();

        //loop for friendlist
        WorkerService.App.getInstance().getPath(1, new AsyncCallback<ArrayList<DoublePoint>>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG!" + caught.toString());
            }

            public void onSuccess(ArrayList<DoublePoint> result) {
                drawBasicMarker(centerPoint.getX(), centerPoint.getY());
                drawPath(result);
            }
        });
    }

    private LatLng[] arrayListToLatLngArray(ArrayList<DoublePoint> points)
    {
        LatLng[] coords = new LatLng[points.size()];
        int k = 0;
        for(DoublePoint coord : points)
        {
            coords[k++] = LatLng.newInstance(coord.getX(), coord.getY());
            //drawBasicMarker(coord.getX(), coord.getY());
        }
        return coords;
    }

    private void drawPath(ArrayList<DoublePoint> points) {
        PolylineOptions pathOpts = PolylineOptions.newInstance();

        LatLng[] coords = arrayListToLatLngArray(points);
        JsArray<LatLng> pathPoints = ArrayHelper.toJsArray(coords);
        pathOpts.setPath(pathPoints);
        pathOpts.setMap(mapWidget);
        pathOpts.setStrokeColor("red");

        Polyline path = Polyline.newInstance(pathOpts);
    }


    private void drawBasicMarker(double x, double y) {
        LatLng center = LatLng.newInstance(x, y);
        MarkerOptions options = MarkerOptions.newInstance();
        options.setPosition(center);
        options.setAnimation(Animation.BOUNCE);
        //options.setIcon("./images/user.png");
        options.setTitle("Hello World");

        //options.setAnimation(Animation.BOUNCE|DROP);

        markerBasic = Marker.newInstance(options);
        markerBasic.setMap(mapWidget);

        markerBasic.addClickHandler(new ClickMapHandler() {
            public void onEvent(ClickMapEvent event) {
                drawInfoWindow(markerBasic, event.getMouseEvent());
            }
        });
    }

    protected void drawInfoWindow(Marker marker, MouseEvent mouseEvent) {
        if (marker == null || mouseEvent == null) {
            return;
        }

        HTML html = new HTML("You clicked on: " + mouseEvent.getLatLng().getToString());

        InfoWindowOptions options = InfoWindowOptions.newInstance();
        options.setContent(html);
        InfoWindow iw = InfoWindow.newInstance(options);
        iw.open(mapWidget, marker);
    }

    private void drawMap() {
        LatLng center = LatLng.newInstance(centerPoint.getX(), centerPoint.getY());//get coords from db
        MapOptions opts = MapOptions.newInstance();
        opts.setZoom(12);
        opts.setCenter(center);
        opts.setMapTypeId(MapTypeId.HYBRID);

        mapWidget = new MapWidget(opts);
        pWidget.add(mapWidget);
        mapWidget.setSize("600px", "400px");

        mapWidget.addClickHandler(new ClickMapHandler() {
            public void onEvent(ClickMapEvent event) {
                // TODO fix the event getting, getting ....
                GWT.log("clicked on latlng=" + event.getMouseEvent().getLatLng());
            }
        });
    }

    private void drawMapAds() {
        AdUnitOptions options = AdUnitOptions.newInstance();
        options.setFormat(AdFormat.HALF_BANNER);
        options.setPosition(ControlPosition.RIGHT_CENTER);
        options.setMap(mapWidget);
        options.setPublisherId("pub-0032065764310410");
        options.setChannelNumber("4000893900");

        AdUnitWidget adUnit = new AdUnitWidget(options);

        adUnit.addChannelNumberChangeHandler(new ChannelNumberChangeMapHandler() {
            public void onEvent(ChannelNumberChangeMapEvent event) {
            }
        });

        adUnit.addFormatChangeHandler(new FormatChangeMapHandler() {
            public void onEvent(FormatChangeMapEvent event) {
            }
        });

        adUnit.addMapChangeHandler(new MapChangeMapHandler() {
            public void onEvent(MapChangeMapEvent event) {
            }
        });

        adUnit.addPositionChangeHandler(new PositionChangeMapHandler() {
            public void onEvent(PositionChangeMapEvent event) {
            }
        });
    }

}