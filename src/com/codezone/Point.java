/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;

/**
 *
 * @author AB
 */
public class Point
{
    public double x;
    public double y;

    public Point(double _x, double _y)
    {
        x=_x;
        y=_y;
    }

    public int getIntX()
    {
        return (int)x;
    }

    public int getIntY()
    {
        return (int)y;
    }

    public Point()
    {
        x=0;
        y=0;
    }

    public Point clone()
    {
        return new Point(this.x, this.y);
    }

    public double getDegree()
    {
        if(x==0 && y==0)
        {
            return 0;
        }
        return getRadian() * 180 / Math.PI;
    }

    public double getRadian()
    {
        return MathUtils.atan2(y, x);
    }
}
