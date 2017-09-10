/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author AB
 */
public class BrushPoint extends Brush
{
    public BrushPoint()
    {
        //
        // TODO: Add constructor logic here
        //
    }

    @Override public void Draw(Point pt, Point dir, Color color, Graphics g, int canvas_width, int canvas_height, boolean bDirectedDraw, float fMarkLength, float fMarkThickness, double dMarkAngle)
    {
        if (fMarkThickness <= 1) fMarkThickness = 2;

        g.setColor(color);
        int width=(int)fMarkThickness;
        g.fillOval(pt.getIntX() - width / 2, pt.getIntY() - width / 2, width, width);

    }

    @Override public void PaintStroke(Graphics g, BufferedImage source, Point stroke_start, Point stroke_end)
    {
        int lx = stroke_start.getIntX();
        int ly = stroke_start.getIntY();

        int cx = stroke_end.getIntX();
        int cy = stroke_end.getIntY();

        int nsteps = getSteps();

        int width=source.getWidth();
        int height=source.getHeight();

        Point pt=new Point();
        Dim dim=new Dim(width, height);

        for (int i = 0; i < nsteps; i++)
        {
            int px = (lx * (nsteps - i) + cx * i) / nsteps;
            int py = (ly * (nsteps - i) + cy * i) / nsteps;
            pt.x = Jitter(px);
            pt.y = Jitter(py);

            ReinforceBoundaryConstraints(pt, dim);

            int x=pt.getIntX();
            int y=pt.getIntY();

            g.setColor(new Color(source.getRGB(x, y)));

            g.fillRect(x-1, y-1, 2, 2);
        }
    }
}
