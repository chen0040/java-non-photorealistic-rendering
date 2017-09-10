/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 *
 * @author AB
 */

public class BrushBSlanted extends Brush
{
    public BrushBSlanted()
    {
        //
        // TODO: Add constructor logic here
        //
        this.setSteps(5);
    }

    @Override public void PaintStroke(Graphics g, BufferedImage source, Point stroke_start, Point stroke_end)
    {
        Dim dim=new Dim(source.getWidth(), source.getHeight());
        
        int lx = stroke_start.getIntX();
        int ly = stroke_start.getIntY();

        int cx = stroke_end.getIntX();
        int cy = stroke_end.getIntY();

        int nsteps = getSteps();

        int bsize2 = getActualBrushSizeHalf();
        int bsize = bsize2 * 2 - 1;

        Point pos=new Point();
        for (int i = 0; i < nsteps; ++i)
        {
            int px = (lx * (nsteps - i) + cx * i) / nsteps;
            int py = (ly * (nsteps - i) + cy * i) / nsteps;
            pos.x = Jitter(px);
            pos.y = Jitter(py);

            this.ReinforceBoundaryConstraints(pos, dim);
            java.awt.Color c=new java.awt.Color(source.getRGB(pos.getIntX(), pos.getIntY()));

            int x1=pos.getIntX()-bsize2;
            int y1=pos.getIntY()+bsize2;
            int x2=pos.getIntX()+bsize2;
            int y2=pos.getIntY()-bsize2;

            g.setColor(c);
            g.drawLine(x1, y1, x2, y2);
         }

    }
}
