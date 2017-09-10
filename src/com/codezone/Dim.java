/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;

/**
 *
 * @author AB
 */
public class Dim {
    public double width;
    public double height;

    public Dim(double w, double h)
    {
        width=w;
        height=h;
    }

    public Dim()
    {
        width=0;
        height=0;
    }

    public Dim clone()
    {
        return new Dim(width, height);
    }

}
