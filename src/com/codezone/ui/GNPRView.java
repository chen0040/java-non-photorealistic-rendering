/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author AB
 */
public class GNPRView extends JFrame implements ActionListener
{
    private JPanel mPanel;
    private JMenuBar mMenuBar;
    private com.codezone.NPRCanvas mCanvas;
    private com.codezone.Painterly mPainterly;
    private com.codezone.Sketcher mSketcher;
    private com.codezone.Painter mCurrentPainter;

    private JMenu mFileMenu;
    private JMenuItem mFileOpenMenuItem;
    private JMenuItem mExitMenuItem;
    private JMenu mCanvasMenu;
    private JRadioButtonMenuItem mCanvasSourceViewMenuItem;
    private JRadioButtonMenuItem mCanvasPaintViewMenuItem;
    private JMenuItem mCanvasEraseMenuItem;
    private JFileChooser mFileChooser;
    private JToolBar mToolBar;
    private JButton mBtnFileOpen;
    private JButton mBtnExit;
    private JToggleButton mBtnCanvasSourceView;
    private JToggleButton mBtnCanvasPaintView;
    private JButton mBtnCanvasErase;
    private JPanel mStatusBar;
    private JLabel mStatusBarLabel;
    private JLabel mStatusBarCanvasViewIndicator;
    private ButtonGroup mButtonGroupCanvasMenuBar;
    private JMenu mToolMenu;
    private JRadioButtonMenuItem mImpressionistPaintMenuItem;
    private JToggleButton mBtnImpressionistPaint;
    private JRadioButtonMenuItem mSketcherPaintMenuItem;
    private JToggleButton mBtnSketcherPaint;

    private JPanel mToolBoxPanel;
    private CardLayout mToolBoxCardLayout;
    private JToolBar mSketcherToolBox;
    private JToolBar mPainterlyToolBox;

    private JToggleButton mBtnBrushOval;
    private JToggleButton mBtnBrushGuidedLine;
    private JToggleButton mBtnBrushBSlanted;
    private JToggleButton mBtnBrushBSlantedRain;
    private JToggleButton mBtnBrushPoint;
    private JButton mBtnPaintImpressionist;
    private JButton mBtnPaintSketcher;
    private JToggleButton mBtnBrushRectPin;
    private JToggleButton mBtnBrushRoundedPin;
    private JToggleButton mBtnBrushWaterDrop;

    private JToggleButton mBtnSketchGuidedLine;
    private JToggleButton mBtnSketchPoint;

    private ArrayList<JToggleButton> mBrushButtons=new ArrayList<JToggleButton>();

    @Override public void actionPerformed(ActionEvent args)
    {
        Object source=args.getSource();
        if(source==mExitMenuItem || source==mBtnExit)
        {
            ExitMenuItem_onClick(args);           
        }
        else if(source==mFileOpenMenuItem || source==mBtnFileOpen)
        {
            FileOpenMenuItem_onClick(args);
        }
        else if(source==mCanvasSourceViewMenuItem || source==mBtnCanvasSourceView)
        {
            CanvasSourceViewMenuItem_onClick(args);
        }
        else if(source==mCanvasPaintViewMenuItem || source==mBtnCanvasPaintView)
        {
            CanvasPaintViewMenuItem_onClick(args);
        }
        else if(source==mImpressionistPaintMenuItem || source==mBtnImpressionistPaint)
        {
            ImpressionistPaint_onClick(args);
        }
        else if(source==mSketcherPaintMenuItem || source==mBtnSketcherPaint)
        {
            SketcherPaint_onClick(args);
        }
        else if(source==mCanvasEraseMenuItem || source==mBtnCanvasErase)
        {
            CanvasErase_onClick(args);
        }
        else if(source==mBtnBrushOval)
        {
            setBrush(com.codezone.BrushManager.BRUSH_OVAL, source);
        }
        else if(source==mBtnBrushBSlanted)
        {
            setBrush(com.codezone.BrushManager.BRUSH_BSLANTED, source);
        }
        else if(source==mBtnBrushBSlantedRain)
        {
            setBrush(com.codezone.BrushManager.BRUSH_BSLANTEDRAIN, source);
        }
        else if(source==mBtnBrushPoint || source==mBtnSketchPoint)
        {
            setBrush(com.codezone.BrushManager.BRUSH_POINT, source);
        }
        else if(source==mBtnBrushWaterDrop)
        {
            setBrush(com.codezone.BrushManager.BRUSH_WATERDROP, source);
        }
        else if(source==mBtnBrushGuidedLine || source==mBtnSketchGuidedLine)
        {
            setBrush(com.codezone.BrushManager.BRUSH_GUIDEDLINES, source);
        }
        else if(source==mBtnBrushRectPin)
        {
            setBrush(com.codezone.BrushManager.BRUSH_RECTPIN, source);
        }
        else if(source==mBtnBrushRoundedPin)
        {
            setBrush(com.codezone.BrushManager.BRUSH_ROUNDEDPIN, source);
        }
        else if(source==mBtnPaintImpressionist || source==mBtnPaintSketcher)
        {
            if(mCanvas.hasSource())
            {
                mCurrentPainter.paint();
            }
        }
    }

    private void setBrush(int brushId, Object source)
    {
        com.codezone.BrushManager.getSingleton().setCurrentBrush(brushId);
        Iterator<JToggleButton> iter=mBrushButtons.iterator();
        while(iter.hasNext())
        {
            JToggleButton button=iter.next();
            button.setSelected(button == source);
        }
    }

    private void CanvasErase_onClick(ActionEvent args)
    {
        if(mCanvas.hasSource())
        {
            mCanvas.clear();
        }
    }
    
    private void ImpressionistPaint_onClick(ActionEvent args)
    {
        mToolBoxCardLayout.show(mToolBoxPanel, "Painterly ToolBox");
        mCurrentPainter=mPainterly;
        mBtnImpressionistPaint.setSelected(true);
        mBtnSketcherPaint.setSelected(false);
        mImpressionistPaintMenuItem.setSelected(true);
    }

    private void SketcherPaint_onClick(ActionEvent args)
    {
        mToolBoxCardLayout.show(mToolBoxPanel, "Sketcher ToolBox");
        mCurrentPainter=mSketcher;
        mBtnImpressionistPaint.setSelected(false);
        mBtnSketcherPaint.setSelected(true);
        mSketcherPaintMenuItem.setSelected(true);
    }

    private void CanvasSourceViewMenuItem_onClick(ActionEvent args)
    {
        mCanvas.setViewState(com.codezone.NPRCanvas.VIEW_SOURCE);
        mBtnCanvasSourceView.setSelected(true);
        mBtnCanvasPaintView.setSelected(false);
        mCanvasSourceViewMenuItem.setSelected(true);
        mStatusBarCanvasViewIndicator.setText("Source View");
    }

    private void CanvasPaintViewMenuItem_onClick(ActionEvent args)
    {
        mCanvas.setViewState(com.codezone.NPRCanvas.VIEW_CANVAS);
        mBtnCanvasSourceView.setSelected(false);
        mBtnCanvasPaintView.setSelected(true);
        mCanvasPaintViewMenuItem.setSelected(true);
        mStatusBarCanvasViewIndicator.setText("Canvas View");
    }

    private void FileOpenMenuItem_onClick(ActionEvent args)
    {
        if(mFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
        {
            mCanvas.setSource(mFileChooser.getSelectedFile());
            mCanvas.repaint();
        }
    }
    
    private void ExitMenuItem_onClick(ActionEvent args)
    {
         this.dispose();
         System.exit(0);
    }

    public GNPRView(String title)
    {
        super(title);
        try{
            this.setIconImage(javax.imageio.ImageIO.read(new File("images/nprIcon.png")));
        }catch(IOException ie)
        {
            ie.printStackTrace();
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        initComponents();
    }

    public void initComponents()
    {
        //initialize the main panel
        mPanel=new JPanel(new BorderLayout());
        this.setContentPane(mPanel);

        //intialize menu
        JPanel mnuPanel=new JPanel(new GridLayout(2, 1));
        mPanel.add(mnuPanel, BorderLayout.NORTH);

        mMenuBar=new JMenuBar();
        mnuPanel.add(mMenuBar);

        mFileMenu=new JMenu("File");
        mFileMenu.setIcon(new ImageIcon("images/fileIcon.png"));
        mMenuBar.add(mFileMenu);

        mFileOpenMenuItem=new JMenuItem("Open");
        mFileOpenMenuItem.setIcon(new ImageIcon("images/openIcon.png"));
        mFileOpenMenuItem.addActionListener(this);
        mFileMenu.add(mFileOpenMenuItem);

        mFileMenu.add(new javax.swing.JSeparator());

        mExitMenuItem=new JMenuItem("Exit");
        mExitMenuItem.setIcon(new ImageIcon("images/exitIcon.png"));
        mExitMenuItem.addActionListener(this);
        mFileMenu.add(mExitMenuItem);

        mCanvasMenu=new JMenu("Canvas");
        mCanvasMenu.setIcon(new ImageIcon("images/canvasIcon.png"));
        mMenuBar.add(mCanvasMenu);

        mCanvasSourceViewMenuItem=new JRadioButtonMenuItem("Source View");
        mCanvasSourceViewMenuItem.setIcon(new ImageIcon("images/svIcon.png"));
        mCanvasSourceViewMenuItem.setSelected(true);
        mCanvasSourceViewMenuItem.addActionListener(this);
        mCanvasMenu.add(mCanvasSourceViewMenuItem);

        mCanvasPaintViewMenuItem=new JRadioButtonMenuItem("Canvas View");
        mCanvasPaintViewMenuItem.setIcon(new ImageIcon("images/cvIcon.png"));
        mCanvasPaintViewMenuItem.addActionListener(this);
        mCanvasMenu.add(mCanvasPaintViewMenuItem);

        mCanvasEraseMenuItem=new JRadioButtonMenuItem("Clear");
        mCanvasEraseMenuItem.setIcon(new ImageIcon("images/eraseIcon.png"));
        mCanvasEraseMenuItem.addActionListener(this);
        mCanvasMenu.add(mCanvasEraseMenuItem);

        mButtonGroupCanvasMenuBar=new ButtonGroup();
        mButtonGroupCanvasMenuBar.add(mCanvasSourceViewMenuItem);
        mButtonGroupCanvasMenuBar.add(mCanvasPaintViewMenuItem);

        mToolMenu=new JMenu("Tool");
        mToolMenu.setIcon(new ImageIcon("images/imIcon.png"));
        mMenuBar.add(mToolMenu);

        mImpressionistPaintMenuItem=new JRadioButtonMenuItem("Impressionist");
        mImpressionistPaintMenuItem.setIcon(new ImageIcon("images/impIcon.png"));
        mImpressionistPaintMenuItem.setSelected(true);
        mImpressionistPaintMenuItem.addActionListener(this);
        mToolMenu.add(mImpressionistPaintMenuItem);

        mSketcherPaintMenuItem=new JRadioButtonMenuItem("Sketcher");
        mSketcherPaintMenuItem.setIcon(new ImageIcon("images/skpIcon.png"));
        mSketcherPaintMenuItem.addActionListener(this);
        mToolMenu.add(mSketcherPaintMenuItem);
        
        //initialize toolbar
        mToolBar=new JToolBar();
        mnuPanel.add(mToolBar);
        
        mBtnExit=new JButton();
        mBtnExit.setIcon(new ImageIcon("images/exitIcon.png"));
        mBtnExit.setToolTipText("Exit");
        mBtnExit.addActionListener(this);
        mToolBar.add(mBtnExit);

        mBtnFileOpen=new JButton();
        mBtnFileOpen.setIcon(new ImageIcon("images/fileIcon.png"));
        mBtnFileOpen.setToolTipText("Open");
        mBtnFileOpen.addActionListener(this);
        mToolBar.add(mBtnFileOpen);

        mToolBar.addSeparator();

        mBtnCanvasSourceView=new JToggleButton();
        mBtnCanvasSourceView.setIcon(new ImageIcon("images/svIcon.png"));
        mBtnCanvasSourceView.setToolTipText("Source View");
        mBtnCanvasSourceView.addActionListener(this);
        mBtnCanvasSourceView.setSelected(true);
        mToolBar.add(mBtnCanvasSourceView);
        
        mBtnCanvasPaintView=new JToggleButton();
        mBtnCanvasPaintView.setIcon(new ImageIcon("images/cvIcon.png"));
        mBtnCanvasPaintView.setToolTipText("Canvas View");
        mBtnCanvasPaintView.addActionListener(this);
        mToolBar.add(mBtnCanvasPaintView);

        mBtnCanvasErase=new JButton();
        mBtnCanvasErase.setIcon(new ImageIcon("images/eraseIcon.png"));
        mBtnCanvasErase.setToolTipText("Clear Canvas");
        mBtnCanvasErase.addActionListener(this);
        mToolBar.add(mBtnCanvasErase);

        mToolBar.addSeparator();

        mBtnImpressionistPaint=new JToggleButton();
        mBtnImpressionistPaint.setIcon(new ImageIcon("images/impIcon.png"));
        mBtnImpressionistPaint.setToolTipText("Impresionist");
        mBtnImpressionistPaint.addActionListener(this);
        mBtnImpressionistPaint.setSelected(true);
        mToolBar.add(mBtnImpressionistPaint);

        mBtnSketcherPaint=new JToggleButton();
        mBtnSketcherPaint.setIcon(new ImageIcon("images/skpIcon.png"));
        mBtnSketcherPaint.setToolTipText("Sketcher");
        mBtnSketcherPaint.addActionListener(this);
        mToolBar.add(mBtnSketcherPaint);

        //initialize canvas
        mCanvas=new com.codezone.NPRCanvas();
        mPanel.add(mCanvas, BorderLayout.CENTER);
        mPainterly=new com.codezone.Painterly(mCanvas);
        mSketcher=new com.codezone.Sketcher(mCanvas);
        mCurrentPainter=mPainterly;

        //initialize status bar
        mStatusBar=new JPanel(new GridLayout(1, 2));
        mPanel.add(mStatusBar, BorderLayout.SOUTH);
        
        mStatusBarLabel=new JLabel("Status Bar");
        mStatusBarLabel.setIcon(new ImageIcon("images/sbIcon.png"));
        mStatusBar.add(mStatusBarLabel);

        mStatusBarCanvasViewIndicator=new JLabel("Source View");
        mStatusBarCanvasViewIndicator.setIcon(new ImageIcon("images/sciIcon.png"));
        mStatusBar.add(mStatusBarCanvasViewIndicator);

        //initialize file chooser
        mFileChooser=new JFileChooser();
        FilePreviewer previewer = new FilePreviewer(mFileChooser);

        GNPRFileFilter jpgFilter = new GNPRFileFilter("jpg", "JPEG Compressed Image Files");
	GNPRFileFilter gifFilter = new GNPRFileFilter("gif", "GIF Image Files");
        GNPRFileFilter bmpFilter = new GNPRFileFilter("bmp", "BMP Image Files");
        GNPRFileFilter pngFilter = new GNPRFileFilter("png", "PNG Image Files");
	mFileChooser.addChoosableFileFilter(jpgFilter);
        mFileChooser.addChoosableFileFilter(gifFilter);
        mFileChooser.addChoosableFileFilter(bmpFilter);
        mFileChooser.addChoosableFileFilter(pngFilter);

	GNPRFileView fileView= new GNPRFileView();
	fileView.putIcon("bmp", new ImageIcon("images/bmpIcon.png"));
	fileView.putIcon("gif", new ImageIcon("images/gifIcon.png"));
        fileView.putIcon("jpg", new ImageIcon("images/jpgIcon.png"));
        fileView.putIcon("png", new ImageIcon("images/pngIcon.png"));

	mFileChooser.setAccessory(previewer);
	mFileChooser.setFileView(fileView);

        //initialize toolbar
        mToolBoxPanel=new JPanel();
        mToolBoxCardLayout=new CardLayout();
        mToolBoxPanel.setLayout(mToolBoxCardLayout);
        mPanel.add(mToolBoxPanel, BorderLayout.EAST);

        mSketcherToolBox=new JToolBar();
        mSketcherToolBox.setToolTipText("Sketch ToolBox");
        mSketcherToolBox.setOrientation(JToolBar.VERTICAL);
        mToolBoxPanel.add(mSketcherToolBox, "Sketcher ToolBox");

        mPainterlyToolBox=new JToolBar();
        mPainterlyToolBox.setToolTipText("Painterly ToolBox");
        mPainterlyToolBox.setOrientation(JToolBar.VERTICAL);
        mToolBoxPanel.add(mPainterlyToolBox, "Painterly ToolBox");

        mBtnPaintImpressionist=new JButton();
        mBtnPaintImpressionist.setIcon(new ImageIcon("images/pIcon.png"));
        mBtnPaintImpressionist.setToolTipText("Paint");
        mBtnPaintImpressionist.addActionListener(this);
        mPainterlyToolBox.add(mBtnPaintImpressionist);
        mPainterlyToolBox.addSeparator();
        
        mBtnBrushOval=new JToggleButton();
        mBtnBrushOval.setIcon(new ImageIcon("images/bIcon_Oval.png"));
        mBtnBrushOval.setToolTipText("Oval Brush");
        mBtnBrushOval.addActionListener(this);
        mBrushButtons.add(mBtnBrushOval);
        mPainterlyToolBox.add(mBtnBrushOval);
        
        mBtnBrushBSlanted=new JToggleButton();
        mBtnBrushBSlanted.setIcon(new ImageIcon("images/bIcon_BSlanted.png"));
        mBtnBrushBSlanted.setToolTipText("BSlanted Brush");
        mBtnBrushBSlanted.addActionListener(this);
        mBrushButtons.add(mBtnBrushBSlanted);
        mPainterlyToolBox.add(mBtnBrushBSlanted);

        mBtnBrushBSlantedRain=new JToggleButton();
        mBtnBrushBSlantedRain.setIcon(new ImageIcon("images/bIcon_BSlantedRain.png"));
        mBtnBrushBSlantedRain.setToolTipText("BSlanted Rain Brush");
        mBtnBrushBSlantedRain.addActionListener(this);
        mBrushButtons.add(mBtnBrushBSlantedRain);
        mPainterlyToolBox.add(mBtnBrushBSlantedRain);

        mBtnBrushPoint=new JToggleButton();
        mBtnBrushPoint.setIcon(new ImageIcon("images/bIcon_Point.png"));
        mBtnBrushPoint.setToolTipText("Point Brush");
        mBtnBrushPoint.addActionListener(this);
        mBrushButtons.add(mBtnBrushPoint);
        mPainterlyToolBox.add(mBtnBrushPoint);

        mBtnBrushWaterDrop=new JToggleButton();
        mBtnBrushWaterDrop.setIcon(new ImageIcon("images/bIcon_WaterDrop.png"));
        mBtnBrushWaterDrop.setToolTipText("Water Drop Brush");
        mBtnBrushWaterDrop.addActionListener(this);
        mBrushButtons.add(mBtnBrushWaterDrop);
        mPainterlyToolBox.add(mBtnBrushWaterDrop);

        mBtnBrushRectPin=new JToggleButton();
        mBtnBrushRectPin.setIcon(new ImageIcon("images/bIcon_RectPin.png"));
        mBtnBrushRectPin.setToolTipText("Rect Pin Brush");
        mBtnBrushRectPin.addActionListener(this);
        mBrushButtons.add(mBtnBrushRectPin);
        mPainterlyToolBox.add(mBtnBrushRectPin);

        mBtnBrushRoundedPin=new JToggleButton();
        mBtnBrushRoundedPin.setIcon(new ImageIcon("images/bIcon_RoundedPin.png"));
        mBtnBrushRoundedPin.setToolTipText("Rounded Pin Brush");
        mBtnBrushRoundedPin.addActionListener(this);
        mBrushButtons.add(mBtnBrushRoundedPin);
        mPainterlyToolBox.add(mBtnBrushRoundedPin);

        mBtnBrushGuidedLine=new JToggleButton();
        mBtnBrushGuidedLine.setIcon(new ImageIcon("images/bIcon_GuidedLine.png"));
        mBtnBrushGuidedLine.setToolTipText("Guided Line Brush");
        mBtnBrushGuidedLine.addActionListener(this);
        mBrushButtons.add(mBtnBrushGuidedLine);
        mPainterlyToolBox.add(mBtnBrushGuidedLine);
        
        mBtnPaintSketcher=new JButton();
        mBtnPaintSketcher.setIcon(new ImageIcon("images/pIcon.png"));
        mBtnPaintSketcher.setToolTipText("Paint");
        mBtnPaintSketcher.addActionListener(this);
        mSketcherToolBox.add(mBtnPaintSketcher);

        mSketcherToolBox.addSeparator();

        mBtnSketchPoint=new JToggleButton();
        mBtnSketchPoint.setIcon(new ImageIcon("images/bIcon_Point.png"));
        mBtnSketchPoint.setToolTipText("Point Brush");
        mBtnSketchPoint.addActionListener(this);
        mBrushButtons.add(mBtnSketchPoint);
        mSketcherToolBox.add(mBtnSketchPoint);
        
        mBtnSketchGuidedLine=new JToggleButton();
        mBtnSketchGuidedLine.setIcon(new ImageIcon("images/bIcon_GuidedLine.png"));
        mBtnSketchGuidedLine.setToolTipText("Guided Line Brush");
        mBtnSketchGuidedLine.addActionListener(this);
        mBrushButtons.add(mBtnSketchGuidedLine);
        mSketcherToolBox.add(mBtnSketchGuidedLine);

        mToolBoxCardLayout.show(mToolBoxPanel, "Painterly ToolBox");
    }

    public void run()
    {
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception exc) {
	    System.err.println("Error loading  Look and Feel: " + exc);
	}

        GNPRView app=new GNPRView("Non Photo-realistic Rendering");
        app.run();
    }
}
