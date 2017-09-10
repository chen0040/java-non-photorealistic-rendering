/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author AB
 */
public class BrushBSlantedRain extends Brush
{
    public BrushBSlantedRain()
    {
        //
        // TODO: Add constructor logic here
        //
    }

    @Override public void PaintStroke(Graphics g, BufferedImage source, Point stroke_start, Point stroke_end)
    {
        int lx = stroke_start.getIntX();
        int ly = stroke_start.getIntY();

        int cx = stroke_end.getIntX();
        int cy = stroke_end.getIntY();

        int nsteps = getSteps();

        int bsize2 = getActualBrushSizeHalf();
        int bsize = bsize2 * 2 - 1;

        int height=source.getHeight();
        int width=source.getWidth();

        Point pt=new Point();
        Dim dim=new Dim(width, height);

        

        for (int i = 0; i < nsteps; i++)
        {
            int px = (lx * (nsteps - i) + cx * i) / nsteps;
            int py = (ly * (nsteps - i) + cy * i) / nsteps;

            pt.x = Jitter(px);
            pt.y = Jitter(py);

            ReinforceBoundaryConstraints(pt, dim);

            g.setColor(new Color(source.getRGB(pt.getIntX(), pt.getIntY())));
            
            int nmarks = 1 + bsize * bsize / 200;

            for (int m = 0; m < nmarks; m++)
            {
                int rx = Jitter(pt.getIntX());
                int ry = Jitter(pt.getIntY());
                int x1=rx - 1;
                int y1=ry + 1;
                int x2=rx + 1;
                int y2=ry - 1;
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}