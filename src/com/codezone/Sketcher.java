/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;

import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 *
 * @author AB
 */
public class Sketcher extends Painter implements Runnable
{
    protected double m_T0 = 80.0; //80.0;
    protected double m_T1 = 20.0; //20.0;
    protected double m_Tgap = 10.0;
    protected double m_Thatch = 10.0;
    protected int m_iStepSize = 5;
    protected int m_iJumpRadius = 10;
    protected int m_iMaxSteps = 200;
    protected int m_iMaxJumps = 7;
    protected double m_dSharpTurnThreshold = 45;
    protected double m_dAlpha = 45;
    protected float m_fMarkLength = 7;
    protected float m_fMarkThickness = 1;
    protected boolean m_bDirectedDraw = true;

    protected Random m_rand = new Random();

    protected int m_iMarkingBehavior=AntMarkingBehavior.EdgeDrawing;
    protected int m_iColorCopyStyle=ColorCopyStyle.RGB;

    protected double[] m_memory;
    
    public Sketcher(NPRCanvas canvas)
    {
        super(canvas);

        m_memory=new double[3];
        for (int i = 0; i < 3; i++)
        {
            m_memory[i]=0;
        }

        m_iPaintRuns=500;
        m_iAnimatePeriod=50;
    }

    private int m_iStepsCounter=0;
    private int m_iJumpsCounter=0;
    private boolean m_bRunning=false;
    private boolean m_bReset=false;
    private Point m_oStrokePos=null;
    private Point m_oStrokeDir=null;

    public void run()
    {
        /*
        BufferedImage source=this.mCanvas.getSource();
        Graphics g=this.mCanvas.getCanvas().getGraphics();

        int height=source.getHeight()/2;
        int width=source.getWidth() /2;

        for(int i=source.getHeight()-1; i>height; --i)
        {
            for(int j=0; j<width; ++j)
            {
                Color c=IPUtils.getPixel(source, new Point(j, i));
                byte l=IPUtils.GetLuminance(source, new Point(j, i));
                c.R=l;
                c.G=l;
                c.B=l;
                g.setColor(c.getARGB());
                g.drawRect(j, i, 1, 1);
            }
        }
        */

        for(int runs=0; runs < m_iPaintRuns; ++runs)
        {
            m_oStrokePos = getStartPoint(this.mCanvas.getSource());
            m_oStrokeDir = new Point();

            double L0 = IPUtils.GetLuminance(this.mCanvas.getSource(), m_oStrokePos);
            double Lgap = 0;
            double Gnorm = 0;

            Lgap=computeLgap(m_oStrokePos, this.mCanvas.getSource(), L0, Lgap);
            Gnorm=ComputeGnorm(m_oStrokePos, this.mCanvas.getSource(), Gnorm);


            m_iStepsCounter = 10;
            m_iJumpsCounter = 0;

            m_bRunning = true;
            m_bReset = true;

            for(int i = 0; i < m_memory.length; ++i)
            {
                m_memory[i]=0;
            }

            Graphics g=mCanvas.getCanvas().getGraphics();

            int behavior=getMarkingBehavior();

            while(m_bRunning)
            {
                switch(behavior)
                {
                    case AntMarkingBehavior.EdgeDrawing:
                        DrawStroke_EdgeDrawing(g, this.mCanvas.getSource());
                        break;
                    case AntMarkingBehavior.FillingAndHatching:
                        DrawStroke_FillingAndHatching();
                        break;
                }
            }

            if(runs % m_iAnimatePeriod==0)
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
        new Thread(this).run();
    } 

    public boolean getDirectedDraw()
    {
        return m_bDirectedDraw;
    }

    public void setDirectedDraw(boolean value)
    {
        m_bDirectedDraw = value;
    }

    public float getMarkThickness()
    {
        return m_fMarkThickness;
    }

    public void setMarkThickness(float value)
    {
        m_fMarkThickness = value;
    }

    public float getMarkLength()
    {
        return m_fMarkLength;
    }

    public void setMarkLength(float value)
    {
        m_fMarkLength = value;
    }

    public int getMarkingBehavior()
    {
        return m_iMarkingBehavior;
    }

    public void setMarkingBehavior(int value)
    {
        m_iMarkingBehavior = value;
    }

    public int getColorCopy()
    {
        return m_iColorCopyStyle;
    }

    public void setColorCopy(int value)
    {
        m_iColorCopyStyle = value;
    }

    public double getThatch()
    {
        return m_Thatch;
    }

    public void setThatch(double value)
    {
        m_Thatch = value;
    }

    public double getSharpTurnThreshold()
    {
        return m_dSharpTurnThreshold;
    }

    public void setSharpTurnThreshold(double value)
    {
        m_dSharpTurnThreshold = value;
    }

    public int getMaxSteps()
    {
        return m_iMaxSteps;
    }

    public void setMaxSteps(int value)
    {
        m_iMaxSteps = value;
    }

    public double getMarkAngle()
    {
        return m_dAlpha;
    }

    public void setMarkAngle(double value)
    {
        m_dAlpha = value;
    }

    public Random getRandomEngine()
    {
        return m_rand;
    }

    public int getStepSize()
    {
        return m_iStepSize;
    }

    public void setStepSize(int value)
    {
        m_iStepSize = value;
    }

    public double getT0()
    {
        return m_T0;
    }

    public void setT0(double value)
    {
        m_T0 = value;
    }

    public double getT1()
    {
        return m_T1;
    }

    public void setT1(double value)
    {
        m_T1 = value;
    }

    public double getTgap()
    {
        return m_Tgap;
    }

    public void setTgap(double value)
    {
        m_Tgap = value;
    }

    public int getMaxJumps()
    {
        return m_iMaxJumps;
    }

    public void setMaxJumps(int value)
    {
        m_iMaxJumps = value;
    }

    protected void DrawStroke_EdgeDrawing(Graphics g, BufferedImage source)
    {
        if (!m_bRunning) return;

        if (m_iStepsCounter >= getMaxSteps())
        {
            m_bRunning = false;
            return;
        }

        if (Continue_EdgeDrawing(source))
        {
            Mark_EdgeDrawing(g, source);
            Move_EdgeDrawing(source);
        }
        else
        {
            if(GradientTrackVanished(m_oStrokePos, source))
            {
                Jump2LocalMaximum(m_oStrokePos, source);
                m_bReset = true;
                for (int i = 0; i < m_memory.length; ++i)
                {
                    m_memory[i]=0;
                }
                m_iJumpsCounter++;
            }
            else
            {
                m_bRunning = false;
            }

            if (m_iJumpsCounter > getMaxJumps())
            {
                m_bRunning = false;
            }
        }
    }

    protected void Jump2LocalMaximum(Point currentPos, BufferedImage source)
    {
        int canvas_width = source.getWidth();
        int canvas_height = source.getHeight();

        double max_norm = m_T1;

        boolean bLocalPeakFound=false;

        Point pos = currentPos.clone();
        for(int i=-5; i<=5; i++)
        {
            int xx=currentPos.getIntX() + i;
            if(xx < 0)
            {
                continue;
            }
            else if(xx >= canvas_width)
            {
                break;
            }
            for(int j=-5; j<5; j++)
            {
                int yy=currentPos.getIntY() + j;
                if(yy < 0)
                {
                    continue;
                }
                else if(yy >= canvas_height)
                {
                    break;
                }
                if(!WithinJumpRegion(currentPos, xx, yy))
                {
                    continue;
                }
                double norm=IPUtils.GradientNorm(new Point(xx, yy), source);
                if(norm > max_norm)
                {
                    bLocalPeakFound=true;
                    max_norm=norm;
                    pos.x = xx;
                    pos.y = yy;
                }
            }
        }

        if(bLocalPeakFound)
        {
            currentPos.x=pos.x;
            currentPos.y = pos.y;
        }
    }

    public int getJumpRadius()
    {
        return m_iJumpRadius;
    }

    public void setJumpRadius(int value)
    {
        m_iJumpRadius=value;
    }

    protected boolean WithinJumpRegion(Point origin, int x, int y)
    {
        double dSharpTurnThreshold=getSharpTurnThreshold() * 3;
        if(dSharpTurnThreshold > 90)
        {
                dSharpTurnThreshold=90;
        }

        double jump_length=(origin.x-x)*(origin.x-x)+(origin.y -y)*(origin.y-y);
        double rad=MathUtils.atan2(y-origin.y, x-origin.x);
        double degree=rad* 180/Math.PI ;

        if(jump_length <= getJumpRadius() * getJumpRadius())
        {
                if(degree<= dSharpTurnThreshold)
                {
                        return true;
                }
                else
                {
                        return false;
                }
        }

        return false;
    }

    protected void Move_EdgeDrawing(BufferedImage source)
    {
        double prev_angle = m_oStrokeDir.getDegree();

        Point dir = IPUtils.GetOrientation(source, m_oStrokePos);

        double vx = -(double)(dir.y);
        double vy = (double)(dir.x);

        double rad = MathUtils.atan2(vy, vx);

        m_oStrokeDir.x=Math.cos(rad);
        m_oStrokeDir.y=Math.sin(rad);

        double curr_angle = m_oStrokeDir.getDegree();

        m_oStrokeDir.x += (int)(m_oStrokeDir.x * getStepSize());
        m_oStrokeDir.y += (int)(m_oStrokeDir.y * getStepSize());

        m_iStepsCounter++;

        if(m_bReset)
        {
            m_bReset = false;
        }
        else
        {
            for (int i = 0; i < m_memory.length - 1; ++i)
            {
                m_memory[i]=m_memory[i+1];
            }
            m_memory[m_memory.length-1]=Math.abs(curr_angle - prev_angle);
            if (m_memory[m_memory.length-1] > 180)
            {
                m_memory[m_memory.length-1]=360-m_memory[m_memory.length-1];
            }
        }
    }

    public Brush getCurrentBrush()
    {
        return BrushManager.getSingleton().getCurrentBrush();
    }

    protected void Mark_EdgeDrawing(Graphics g, BufferedImage source)
    {
        int canvas_width=source.getWidth();
        int canvas_height=source.getHeight();

        Color color = new Color(source.getRGB(m_oStrokePos.getIntX(), m_oStrokePos.getIntY()));
        if(this.m_iColorCopyStyle==ColorCopyStyle.Luminance)
        {
            byte l=IPUtils.GetLuminance(color);
            int rgb=(color.getAlpha() << 24) | (l << 16) | (l << 8) | l;
            color=new Color(rgb);
        }

        getCurrentBrush().Draw(m_oStrokePos,
                m_oStrokeDir, 
                color,
                g,
                canvas_width,
                canvas_height,
                getDirectedDraw(),
                getMarkLength(),
                getMarkThickness(),
                getMarkAngle());
    }

    protected void DrawStroke_FillingAndHatching()
    {
        //not implemented
    }

    protected boolean Continue_EdgeDrawing(BufferedImage source)
    {
        if(m_iStepsCounter >= m_iMaxSteps)
        {
            return false;
        }

        if (IPUtils.isOutsideImage(m_oStrokePos, source))
        {
            return false;
        }

        if(m_memory[m_memory.length-1] >= m_dSharpTurnThreshold)
        {
            return false;
        }

        boolean bContinue = !GradientTrackVanished(m_oStrokePos, source);

        return bContinue;
    }

    boolean GradientTrackVanished(Point pos, BufferedImage source)
    {
            double norm=IPUtils.GradientNorm(pos, source);
            return norm < m_T1;
    }

    protected double computeLgap(Point pt, BufferedImage source, double L0, double Lgap)
    {
        if(!IPUtils.isOutsideImage(pt, source))
        {
            double Llocal = IPUtils.GetLuminance(source, pt);
            Lgap = Llocal - L0;
        }
        return Lgap;
    }

    protected double ComputeGnorm(Point pt, BufferedImage source, double Gnorm)
    {
        if (!IPUtils.isOutsideImage(pt, source))
        {
            Gnorm = IPUtils.GradientNorm(pt, source);
        }
        return Gnorm;
    }

    protected Point getStartPoint(BufferedImage source)
    {
        double norm = 0;
        int x = 0;
        int y = 0;
        int canvas_width = source.getWidth();
        int canvas_height = source.getHeight();
        do
        {
            x = m_rand.nextInt(canvas_width);
            y = m_rand.nextInt(canvas_height);

            norm = IPUtils.GradientNorm(new Point(x, y), source);
        } while (norm > m_T0);

        return new Point(x, y);
    }
}
