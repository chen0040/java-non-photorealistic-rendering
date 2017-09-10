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
public class BrushGuidedLines extends Brush
{
    public BrushGuidedLines()
    {
        //
        // TODO: Add constructor logic here
        //
    }

    @Override public void Draw(Point pt, Point dir, Color color, Graphics g, int canvas_width, int canvas_height, boolean bDirectedDraw, float fMarkLength, float fMarkThickness, double dMarkAngle)
    {
        double rad = dMarkAngle * Math.PI / 180;

        int dx = 0;
        int dy = 0;
        if (bDirectedDraw)
        {
            dx = (int)(dir.x * fMarkLength / 2);
            dy = (int)(dir.y * fMarkLength / 2);
        }
        else
        {
            dx = (int)(fMarkLength * Math.cos(rad) / 2);
            dy = (int)(fMarkLength * Math.sin(rad) / 2);
        }

        int sx = pt.getIntX() + dx;
        int sy = pt.getIntY() + dy;

        int ex = pt.getIntX() - dx;
        int ey = pt.getIntY() - dy;

        if (sx < 0) sx = 0;
        if (sx >= canvas_width) sx = canvas_width-1;
        if (sy < 0) sy = 0;
        if (sy >= canvas_height) sy = canvas_height-1;

        if (ex < 0) ex = 0;
        if (ex >= canvas_width) ex = canvas_width-1;
        if (ey < 0) ey = 0;
        if (ey >= canvas_height) ey = canvas_height-1;

        g.setColor(color);
        g.drawLine(sx, sy, ex, ey);
    }

    @Override public void PaintStroke(Graphics g, BufferedImage source, Point stroke_start, Point stroke_end)
    {
        Point pos=new Point();
        pos.x = (stroke_start.x + stroke_end.x) / 2.0;
        pos.y = (stroke_start.y + stroke_end.y) / 2.0;

        Dim dim=new Dim(source.getWidth(), source.getHeight());


        this.ReinforceBoundaryConstraints(pos, dim);
        java.awt.Color c=new java.awt.Color(source.getRGB(pos.getIntX(), pos.getIntY()));

        this.ReinforceBoundaryConstraints(pos, dim);
        Point dir = IPUtils.GetOrientation(source, pos);

        double vx = -(double)(dir.y) / 255.0;
        double vy = (double)(dir.x) / 255.0;

        double rad = MathUtils.atan2(vy, vx);
        vx = Math.cos(rad);
        vy = Math.sin(rad);

        float bsize2 = getActualBrushSizeHalf();
        float bsize = bsize2 * 2 + 1;
        float advLen = bsize + 2;

        int vxl = (int)(vx * advLen);
        int vyl = (int)(vy * advLen);

        int[] vxb=new int[2];
        int[] vyb=new int[2];

        vxb[0] = (int)(vx * bsize / 2);
        vyb[0] = (int)(vy * bsize / 2);

        vxb[1] = (int)(vx * bsize / 4);
        vyb[1] = (int)(vy * bsize / 4);

        int sx = pos.getIntX() - vxl;
        int sy = pos.getIntY() - vyl;
        int ex = pos.getIntX() + vxl;
        int ey = pos.getIntY() + vyl;

        g.setColor(c);

        for (int i = 0; i < 2; ++i)
        {
            g.drawLine(sx - vyb[i], sy + vxb[i], ex - vyb[i], ey + vxb[i]);
            g.drawLine(sx + vyb[i], sy - vxb[i], ex + vyb[i], ey - vxb[i]);
        }
        g.drawLine(sx, sy, ex, ey);
    }
}