package Ex7_136;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainApplication extends JFrame implements KeyListener
{    
    private JLabel          contentpane;
    private CharacterLabel  carLabel, birdLabel, activeLabel;
    private ItemLabel       hatLabel, portalLabel;
    
    protected int framewidth  = MyConstants.FRAMEWIDTH;
    protected int frameheight = MyConstants.FRAMEHEIGHT;
    private int groundY     = MyConstants.GROUND_Y;
    private int skyY        = MyConstants.SKY_Y;

    public static void main(String[] args) 
    {
	new MainApplication();
    }	    
    
    public MainApplication()
    {      
	setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

	// set background image by using JLabel as contentpane
	setContentPane(contentpane = new JLabel());
	MyImageIcon background = new MyImageIcon(MyConstants.FILE_BG).resize(framewidth, frameheight);
	contentpane.setIcon( background );
	contentpane.setLayout( null );

	carLabel = new CharacterLabel(MyConstants.FILE_CAR, 130, 100, this,false); 
        carLabel.setMoveConditions(700, groundY, true, false);
        
	birdLabel = new CharacterLabel(MyConstants.FILE_BIRD, 110, 100, this,true); 
        birdLabel.setMoveConditions(700, skyY, true, true);
                    
        hatLabel = new ItemLabel(MyConstants.FILE_HAT, 80, 80, this);
        hatLabel.setMoveConditions(50, skyY, true, true);
        hatLabel.setHitEffect(true);
        
        portalLabel = new ItemLabel(MyConstants.FILE_PORTAL, 120, 150, this);  
        portalLabel.setMoveConditions(50, groundY, true, true);
        birdLabel.setPortal(portalLabel);

        // first added label is at the front, last added label is at the back
        contentpane.add(hatLabel);
        contentpane.add(carLabel);
        contentpane.add(birdLabel); 
        contentpane.add(portalLabel); 
        //add the keylistener to the method
        
        addKeyListener(this);
        setCar();
	repaint();
    }
        
    public CharacterLabel getActiveLabel()   { return activeLabel; }    
    public void setCar()                     { activeLabel = carLabel;  setTitle("Car is active"); }
    
    //use this method to write the conditions 
    @Override
    public void keyPressed(KeyEvent e) {
        
        switch(e.getKeyCode()) {
            
            case KeyEvent.VK_UP : activeLabel.moveUp();
              break;
              
            case KeyEvent.VK_DOWN : activeLabel.moveDown();
              break;
              
            case KeyEvent.VK_LEFT : activeLabel.moveLeft();
              break;
              
            case KeyEvent.VK_RIGHT : activeLabel.moveRight();
              break; 
            
            case KeyEvent.VK_C :
              setTitle("Car is active");
              activeLabel = carLabel;
              break; 
              
            case KeyEvent.VK_B :
              setTitle("Bird is active");
              activeLabel = birdLabel;
              break; 

        }
        activeLabel.updateLocation();
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

////////////////////////////////////////////////////////////////////////////////
abstract class BaseLabel extends JLabel
{
    protected MyImageIcon      icon;
    protected int              curX, curY, width, height;
    protected boolean          horizontalMove, verticalMove;
    protected MainApplication  parentFrame;   
    
    public BaseLabel() { }
    
    public BaseLabel(String file, int w, int h, MainApplication pf)				
    { 
        width = w; height = h;
        icon = new MyImageIcon(file).resize(width, height);
	setHorizontalAlignment(JLabel.CENTER);
	setIcon(icon);
        parentFrame = pf;
    }   
    
    public void setMoveConditions(int x, int y, boolean hm, boolean vm)
    {
        curX = x; curY = y; 
        setBounds(curX, curY, width, height);
        horizontalMove = hm; 
        verticalMove = vm;
    }  
    
    abstract public void updateLocation(); 
}

////////////////////////////////////////////////////////////////////////////////
class CharacterLabel extends BaseLabel 
{
    private boolean   portalMove  = false;
    private ItemLabel portalLabel = null;    
    
    public CharacterLabel(String file, int w, int h, MainApplication pf,boolean portalMove)
    { 
        super(file, w, h, pf);
        this.portalMove = portalMove;
    }

    public void setPortal(ItemLabel pt)  
    { 
        // If portalMove = true, it can change location to portalLabel's location
        // If portalMove = false, it can only disappear/reappear at its own location
        
        if (pt != null)  { portalLabel = pt; portalMove = true; }
    }
    
    public void isHatOverlap(boolean isLap) {
        
        setIcon(icon);
        
        if(!isLap) return; 
        
        if(portalMove) {
            curX = portalLabel.curX;
            curY = portalLabel.curY + height/2;
        }
        
        else {
            setIcon(null);
        }
        updateLocation();
        
    }
    
    public void updateLocation()  { 
        setBounds(curX,curY,width,height);
        parentFrame.repaint();
    }        
    
    public void moveUp()          { 
        if(verticalMove) {
            if(curY >= 10) curY -= 10; 
        }
    }
    
    public void moveDown()        { 
        if(verticalMove) {
            if(curY + 30 <= parentFrame.frameheight-height) curY += 10; 
        }
    }
    
    public void moveLeft()        { 
        if(horizontalMove) {
            if(curX <= 0) {
                curX = parentFrame.framewidth-width;
            }
            else curX -= 10;
        }
    }
    
    public void moveRight()       { 
        if(horizontalMove) {
            if(curX >= parentFrame.framewidth-width) {
                curX = 0;
            }
            curX += 10;
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
class ItemLabel extends BaseLabel implements MouseMotionListener  
{
    private boolean hitEffect = false;
   
    public ItemLabel(String file, int w, int h, MainApplication pf)				
    { 
        super(file, w, h, pf);
        addMouseMotionListener(this);
        
    }   
    
    public void setHitEffect(boolean he)  
    { 
        // If hitEffect = true, activeLabel will do something upon overlapping
        // If hitEffect = false, activeLabel won't do anyting upon overlapping
        hitEffect = he;
    }

    public void updateLocation()  { 
        setBounds(curX,curY,width,height);
        repaint();
    }
    
    public void isIntersected(CharacterLabel MyLabel) {
        if(!hitEffect) return;

        MyLabel.isHatOverlap(this.getBounds().intersects(MyLabel.getBounds()));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
            curX = curX - width/2 + e.getX();
            curY = curY - height/2 + e.getY();

            Container p = getParent();
            if (curX < 0)  curX = 0;
            if (curY < 0)  curY = 0;
            if (curX + width  > p.getWidth())   curX = p.getWidth() - width;
            if (curY + height > p.getHeight())  curY = p.getHeight() - height;
            
            setLocation(curX, curY);
            isIntersected(parentFrame.getActiveLabel());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
