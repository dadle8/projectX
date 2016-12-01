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
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.requestfactory.server.Pair;
import com.worker.DB_classes.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class BasicMapWidget extends Composite {

    private final FlowPanel pWidget;
    private MapWidget mapWidget;
    private Point centerPoint;

    //private Marker markerBasic;

    public BasicMapWidget(Point pt) {
        pWidget = new FlowPanel();
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
        drawBasicMarker(centerPoint.getX(), centerPoint.getY(), "Me");
        WorkerService.App.getInstance().getUserFromCurrentSession(new AsyncCallback<UserEntity>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG WHEN DRAWING SELF TRAJECTORY!");
            }

            public void onSuccess(final UserEntity usr) {
                WorkerService.App.getInstance().getPath(usr.getId(), new AsyncCallback<ArrayList<DoublePoint>>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("SMTH GOES WRONG WHEN GETTING SELF PATH!" + caught.toString());
                    }

                    public void onSuccess(ArrayList<DoublePoint> result) {
                        drawPath(usr.getColor(), result);
                    }
                });
            }
        });
        WorkerService.App.getInstance().getFriends(new AsyncCallback<List<UserEntity>>() {
            public void onFailure(Throwable caught) {
                Window.alert("SMTH GOES WRONG!");
            }

            public void onSuccess(List<UserEntity> result) {
                for(final UserEntity usr : result) {
                    WorkerService.App.getInstance().getPath(usr.getId(), new AsyncCallback<ArrayList<DoublePoint>>() {
                        public void onFailure(Throwable caught) {
                            Window.alert("SMTH GOES WRONG!" + caught.toString());
                        }

                        public void onSuccess(ArrayList<DoublePoint> result) {
                            drawBasicMarker(result.get(result.size() - 1).getX(), result.get(result.size() - 1).getY(), usr.getName() + " " + usr.getSurname());
                            drawPath(usr.getColor(), result);
                        }
                    });
                }
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

    private void drawPath(String color, ArrayList<DoublePoint> points) {
        PolylineOptions pathOpts = PolylineOptions.newInstance();

        LatLng[] coords = arrayListToLatLngArray(points);
        JsArray<LatLng> pathPoints = ArrayHelper.toJsArray(coords);
        pathOpts.setPath(pathPoints);
        pathOpts.setMap(mapWidget);
        pathOpts.setStrokeColor("#" + color);

        Polyline path = Polyline.newInstance(pathOpts);
    }


    private void drawBasicMarker(double x, double y, String caption) {
        LatLng center = LatLng.newInstance(x, y);
        MarkerOptions options = MarkerOptions.newInstance();
        options.setPosition(center);
        //options.setAnimation(Animation.BOUNCE);
        //options.setIcon("./images/user.png");
        options.setTitle(caption);
        if (caption != "Me")
        {
            options.setIcon("./images/blue-dot.png");
        }

        //options.setAnimation(Animation.BOUNCE|DROP);

        final Marker markerBasic = Marker.newInstance(options);
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

        HTML html = new HTML(marker.getTitle() + " is on " + mouseEvent.getLatLng().getToString());

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
        mapWidget.addStyleName("basic-map-widget");
        pWidget.add(mapWidget);

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