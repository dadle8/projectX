package com.worker.client;

import java.io.Serializable;

/**
 * Created by AsmodeusX on 17.11.2016.
 */
public class DoublePoint implements Serializable {
    private Double X = 0.0;
    private Double Y = 0.0;

    public DoublePoint()
    {

    }

    public void Set(Double x, Double y)
    {
        this.X = x;
        this.Y = y;
    }

    public Double getX()
    {
        return X;
    }

    public Double getY()
    {
        return Y;
    }

    public String toString()
    {
        return X + " " + Y;
    }
}
