/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.*;

/**
 *
 * @author AB
 */
public class Painterly extends Painter implements Runnable
{
    private int m_iStrokeCount;
    private int m_iStrokeStepSize;
    private Point m_oPrevStrokePos;
    
    public Painterly(NPRCanvas canvas)
    {
        super(canvas);
        m_iStrokeCount=10;
        m_iStrokeStepSize=10; //50
    }

    @Override public void mousePressed(Point pt)
    {
        //javax.swing.JOptionPane.showMessageDialog(null, new javax.swing.JLabel("ehll"));
         draw(pt);
         mCanvas.repaint();
    }

    @Override public void mouseDragged(Point pt)
    {
        draw(pt);
        mCanvas.repaint();
    }

    @Override public void mouseReleased(Point pt)
    {
        m_oPrevStrokePos=null;
    }

    private void draw(Point stroke_start)
    {
        if(m_oPrevStrokePos==null)
        {
            m_oPrevStrokePos=stroke_start;
        }

        BufferedImage source=mCanvas.getSource();

        Graphics g=mCanvas.getCanvas().getGraphics();

        Brush brush=getCurrentBrush();
        brush.PaintStroke(g, source, stroke_start, m_oPrevStrokePos);

        m_oPrevStrokePos=stroke_start;
        /*
        int width=source.getWidth();
        int height=source.getHeight();

        Point stroke_end = new Point();
        double rad, move_x, move_y;
        for (int i = 0; i < m_iStrokeCount; ++i)
        {
            rad = Tools.getSingleton().random() * Math.PI * 2;
            move_x = Math.cos(rad) * m_iStrokeStepSize;
            move_y = Math.sin(rad) * m_iStrokeStepSize;
            stroke_end.x = stroke_start.x + move_x;
            stroke_end.y = stroke_start.y + move_y;
            if (stroke_end.x >= width) stroke_end.x -= move_x;
            if (stroke_end.y >= height) stroke_end.y -= move_y;

            brush.PaintStroke(g, source, stroke_start, stroke_end);
            stroke_start.x = stroke_end.x;
            stroke_start.y = stroke_end.y;
        }
         * */
    }

    public void run()
    {
        BufferedImage source=mCanvas.getSource();

        int width=source.getWidth();
        int height=source.getHeight();

        Graphics g=mCanvas.getCanvas().getGraphics();
        
        for(int runs=0; runs < m_iPaintRuns; ++runs)
        {            
            Point stroke_start = new Point();
            stroke_start.x = Tools.getSingleton().randomInt(width);
            stroke_start.y = Tools.getSingleton().randomInt(height);
            Point stroke_end = new Point();

            double rad, move_x, move_y;
            Brush brush=getCurrentBrush();
            for (int i = 0; i < m_iStrokeCount; ++i)
            {
                rad = Tools.getSingleton().random() * Math.PI * 2;
                move_x = Math.cos(rad) * m_iStrokeStepSize;
                move_y = Math.sin(rad) * m_iStrokeStepSize;
                stroke_end.x = stroke_start.x + move_x;
                stroke_end.y = stroke_start.y + move_y;
                if (stroke_end.x >= width) stroke_end.x -= move_x;
                if (stroke_end.y >= height) stroke_end.y -= move_y;
                if (stroke_end.x <=0 ) stroke_end.x += move_x;
                if (stroke_end.y <=0 ) stroke_end.y += move_y;

                brush.PaintStroke(g, source, stroke_start, stroke_end);
                stroke_start.x = stroke_end.x;
                stroke_start.y = stroke_end.y;
            }

            if(runs % m_iAnimatePeriod == 0)
            {
                try{
                    Thread.sleep(10);
                    mCanvas.repaint();
                }catch(InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
        }
    }

    public void paint()
    {
        new Thread(this).start();
    }

    public Brush getCurrentBrush()
    {
        return BrushManager.getSingleton().getCurrentBrush();
    }
}
