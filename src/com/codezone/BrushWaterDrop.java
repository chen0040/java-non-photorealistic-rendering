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
public class BrushWaterDrop extends Brush
{
    public BrushWaterDrop()
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

        int bsize2 = getActualBrushSizeHalf();
        int bsize = bsize2 * 2 + 1;
        int advLen = bsize + 2;

        g.setColor(color);

        int ib2=(int)bsize2;
        int ib=(int)bsize;

        int x=(int)pt.getIntX();
        int y=(int)pt.getIntY();
        
        g.drawLine(x, y - advLen / 2, x - bsize2, y + advLen / 2);

        g.drawLine(x, y - advLen / 2, x + bsize2, y + advLen / 2);

        g.fillOval(x - ib2, y + advLen / 2 - ib2, ib, ib);

    }
}