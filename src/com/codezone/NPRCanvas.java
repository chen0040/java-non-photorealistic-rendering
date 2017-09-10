/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;

import java.awt.Color;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Xianshun
 */

public class NPRCanvas extends JComponent implements MouseListener, MouseMotionListener
{
    private BufferedImage mImgSource;
    private BufferedImage mImgCanvas;

    public static final int VIEW_SOURCE=1;
    public static final int VIEW_CANVAS=2;
    private int mView;

    private ArrayList<Painter> mPainters=new ArrayList<Painter>();
   
    public NPRCanvas()
    {
        mView=VIEW_SOURCE;
        mImgSource=null;
        mImgCanvas=null;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public boolean hasSource()
    {
        return mImgSource != null;
    }

    public void addPainter(Painter painter)
    {
        mPainters.add(painter);
    }

    @Override public void mouseDragged(MouseEvent me)
    {
        onPainterlyMouseDrag(me);
    }

    @Override public void mouseMoved(MouseEvent e)
    {

    }

    @Override public void mousePressed(MouseEvent me)
    {
        onPainterlyMouseDrag(me);
    }

    private void onPainterlyMouseDrag(MouseEvent me)
    {
        //javax.swing.JOptionPane.showMessageDialog(null, new javax.swing.JLabel("ehll"));
        if(!this.hasSource())
        {
            return;
        }

        int width=this.getWidth();
        int height=this.getHeight();

        int imgWidth=mImgCanvas.getWidth();
        int imgHeight=mImgCanvas.getHeight();

        float r1=(float)height / width;
        float r2=(float)imgHeight / imgWidth;
        float ratio=1;

        int x=0, y=0;

        if(r1 > r2)
        {
            int height1=(int)(r2 * width);
            y=(height - height1) / 2;
            height=height1;
            ratio=(float)imgWidth / width;
        }
        else
        {
            int width1=(int)(height / r2);
            x=(width-width1) / 2;
            width=width1;
            ratio=(float)imgHeight / height;
        }

        x=me.getX() - x;
        y=me.getY() - y;

        Point pt=new Point(x * ratio, y * ratio);

//        System.out.print("Ratio: "+ratio);
//        System.out.print("Mouse: ("+me.getX()+", "+me.getY()+")");
//        System.out.print("Point: ("+pt.x+", "+pt.y+")");

        Iterator<Painter> iter=mPainters.iterator();
        while(iter.hasNext())
        {
            Painter painter=iter.next();
            painter.mousePressed(pt);
        }
    }

    @Override public void mouseReleased(MouseEvent me)
    {
        Iterator<Painter> iter=mPainters.iterator();
        while(iter.hasNext())
        {
            Painter painter=iter.next();
            painter.mouseReleased(null);
        }
    }

    @Override public void mouseEntered(MouseEvent e)
    {
         
    }

    
    @Override public void mouseExited(MouseEvent e)
    {
        
    }

    @Override public void mouseClicked(MouseEvent e)
    {
        
    }

    public void clear()
    {
        if(mImgCanvas != null)
        {
            int width=mImgCanvas.getWidth();
            int height=mImgCanvas.getHeight();
            Graphics g=mImgCanvas.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            repaint();
        }
    }
    
    @Override public void paint(Graphics g)
    {
        int width=this.getWidth();
        int height=this.getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        
        if(mView==VIEW_SOURCE)
        {
            if(mImgSource != null)
            {
                int imgWidth=mImgSource.getWidth();
                int imgHeight=mImgSource.getHeight();

                float r1=(float)height / width;
                float r2=(float)imgHeight / imgWidth;

                int x=0, y=0;
                
                if(r1 > r2)
                {
                    int height1=(int)(r2 * width);
                    y=(height - height1) / 2;
                    height=height1;
                }
                else
                {
                    int width1=(int)(height / r2);
                    x=(width-width1) / 2;
                    width=width1;
                }


                
                g.drawImage(mImgSource, x, y, width, height, this);
            }
        }
        else if(mView==VIEW_CANVAS)
        {
            if(this.mImgCanvas != null)
            {
                int imgWidth=mImgCanvas.getWidth();
                int imgHeight=mImgCanvas.getHeight();

                float r1=(float)height / width;
                float r2=(float)imgHeight / imgWidth;

                int x=0, y=0;

                if(r1 > r2)
                {
                    int height1=(int)(r2 * width);
                    y=(height - height1) / 2;
                    height=height1;
                }
                else
                {
                    int width1=(int)(height / r2);
                    x=(width-width1) / 2;
                    width=width1;
                }
                
                g.drawImage(mImgCanvas, x, y, width, height, this);
            }
        }
    }

    public void setSource(File file)
    {
        try{
            mImgSource=ImageIO.read(file);
        }catch(IOException ie)
        {
            ie.printStackTrace();
        }
        int width=mImgSource.getWidth();
        int height=mImgSource.getHeight();
        mImgCanvas=new BufferedImage(width, height, mImgSource.getType());
        Graphics g=mImgCanvas.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    public BufferedImage getSource()
    {
        return mImgSource;
    }

    public BufferedImage getView()
    {
        if(mView==VIEW_SOURCE)
        {
            return getSource();
        }
        else if(mView==VIEW_CANVAS)
        {
            return getCanvas();
        }
        return null;
    }

    public BufferedImage getCanvas()
    {
        return mImgCanvas;
    }

    public boolean setViewState(int view)
    {
        if(mView != view)
        {
            mView=view;
            repaint();
            return true;
        }
        return false;
    }

    public int getViewState()
    {
        return mView;
    }
}
