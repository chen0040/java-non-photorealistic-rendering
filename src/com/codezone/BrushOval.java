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
public class BrushOval extends Brush
{
    public BrushOval()
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

        int bsize2=getActualBrushSizeHalf();
        int bsize=bsize2*2 - 1;

        int width=source.getWidth();
        int height=source.getHeight();

        Dim dim=new Dim(width, height);

        Point pt=new Point();

        for (int i = 0; i < nsteps; i++)
        {
            int px = (lx * (nsteps - i) + cx * i) / nsteps;
            int py = (ly * (nsteps - i) + cy * i) / nsteps;
            pt.x = Jitter(px);
            pt.y = Jitter(py);

            this.ReinforceBoundaryConstraints(pt, dim);

            g.setColor(new Color(source.getRGB(pt.getIntX(), pt.getIntY())));
            g.fillOval(pt.getIntX()-bsize2, pt.getIntY()-bsize2, bsize, bsize);
        }
    }
}
