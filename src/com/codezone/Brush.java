/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 *
 * @author AB
 */
public abstract class Brush {
    private int m_steps = 5;

    public Brush()
    {
        //
        // TODO: Add constructor logic here
        //
    }

    public void Dispose()
    {

    }

    public int Jitter(int val)
    {
        int jsize2 = getJSizeHalf();
        int jsize=2 * jsize2 + 1;
        return val + Tools.getSingleton().randomInt(jsize) - jsize2;
    }

    public int getSteps()
    {
        return m_steps;
    }

    public void setSteps(int value)
    {
        m_steps = value;
    }

    public int getJSizeHalf()
    {
        double size = BrushManager.getSingleton().getBrushSize();
        double diam = 4.0 * Math.pow(1.41, size);
        return (int)(4.0 * diam / 2.0);
    }

    public int getActualBrushSizeHalf()
    {
        double size = BrushManager.getSingleton().getBrushSize();
        double diam = 2.0 * Math.pow(1.41, size);
        return (int)(diam / 2.0);
    }

    public void ReinforceBoundaryConstraints(Point pt, Dim dim)
    {
        if(pt.x < 0) pt.x=0;
        if(pt.x >= dim.width) pt.x=dim.width-1;
        if(pt.y < 0) pt.y=0;
        if(pt.y >= dim.height) pt.y=dim.height-1;
    }

    

    public void Draw(Point pt, Point dir, Color color, Graphics g, int canvas_width, int canvas_height, boolean bDirectedDraw, float fMarkLength, float fMarkThickness, double dMarkAngle)
    {

    }

    public void PaintStroke(Graphics g, BufferedImage source, Point stroke_start, Point stroke_end)
    {
        
    }
}
