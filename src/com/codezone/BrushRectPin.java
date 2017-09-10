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
public class BrushRectPin extends Brush
{
    public BrushRectPin()
    {
        //
        // TODO: Add constructor logic here
        //
    }

    @Override public void PaintStroke(Graphics g, BufferedImage source, Point stroke_start, Point stroke_end)
    {
        Point pt=new Point();
        pt.x = (stroke_start.x + stroke_end.x) / 2.0;
        pt.y = (stroke_start.y + stroke_end.y) / 2.0;

        int height=source.getHeight();
        int width=source.getWidth();

        Dim dim=new Dim(width, height);

        ReinforceBoundaryConstraints(pt, dim);
        Color color = new Color(source.getRGB(pt.getIntX(), pt.getIntY()));

        Point dir = IPUtils.GetOrientation(source, pt);

        double vx = -dir.y / 255.0;
        double vy = dir.x / 255.0;

        double rad = Math.atan2(vy, vx);
        vx = Math.cos(rad);
        vy = Math.sin(rad);

        /*
        double l = Math.Sqrt(vx * vx + vy * vy);

        vx /= l;
        vy /= l;
         * */

        float bsize2 = getActualBrushSizeHalf();
        float bsize = bsize2 * 2 + 1;
        float advLen = bsize + 2;

        int vxl = (int)(vx * advLen);
        int vyl = (int)(vy * advLen);
        int vxb = (int)(vx * bsize / 2);
        int vyb = (int)(vy * bsize / 2);

        int sx = pt.getIntX() - vxl;
        int sy = pt.getIntY() - vyl;
        int ex = pt.getIntX() + vxl;
        int ey = pt.getIntY() + vyl;

        g.setColor(color);


        int ib2=(int)bsize2;
        int ib=(int)bsize;
        g.fillRect(sx - ib2, sy - ib2, ib, ib);
        g.fillRect(ex - ib2, ey - ib2, ib, ib);

        g.drawLine(sx - vyb, sy + vxb, ex - vyb, ey + vxb);
        g.drawLine(sx + vyb, sy - vxb, ex + vyb, ey - vxb);
    }
}