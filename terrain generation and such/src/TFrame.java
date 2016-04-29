import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.Point;
import java.awt.Color;
public class TFrame extends JFrame implements MouseListener, MouseMotionListener, ActionListener
{
  TPanel mainPanel;
  World myWorld;
  JButton genButton;
  JButton exitButton;
  
  Point lastClick = null;
  Point draggedTo = null;
  Point frameAt = null;
  
  String actionCode;
  public TFrame(int wid,int hei,TPanel mainPanel,World myWorld)
  {
    setUndecorated(true);
    setBounds(new Rectangle(wid,hei));
    this.mainPanel = mainPanel;
    this.myWorld = myWorld;
    this.myWorld.addFrame(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    add(mainPanel);
    setBackground(new Color(0,0,0,0));
    setVisible(true);
    setLocation(50,50);
    addDecor();
    requestFocus();
  }
  private void addDecor()
  {
    exitButton = new JButton("EXIT");
    mainPanel.add(exitButton);
    exitButton.setBounds(651,560,200,50);
    exitButton.addActionListener(this);
    exitButton.setFocusable(false);
    
    genButton = new JButton("NEW WORLD");
    mainPanel.add(genButton);
    genButton.setBounds(651,460,200,50);
    genButton.addActionListener(this);
    genButton.setFocusable(false);
  }
  public void mouseClicked(MouseEvent e)
  {
  }
  public void mousePressed(MouseEvent e)
  {
    frameAt = getLocationOnScreen();
    lastClick = e.getLocationOnScreen();
  }
  public void mouseReleased(MouseEvent e)
  {
  }
  public void mouseEntered(MouseEvent e)
  {
  }
  public void mouseExited(MouseEvent e)
  {
  }
  public void mouseDragged(MouseEvent e)
  {
    if(lastClick.getY()-frameAt.getY()<30)
    {
      draggedTo = e.getLocationOnScreen();
      setLocation((int)(frameAt.getX()+draggedTo.getX()-lastClick.getX()),(int)(frameAt.getY()+draggedTo.getY()-lastClick.getY()));
    }
  }
  public void mouseMoved(MouseEvent e)
  {
  }
  public void actionPerformed(ActionEvent e)
  {
    actionCode = e.getActionCommand();
    if(actionCode.equals("EXIT"))
    {
      System.exit(0);
    }
    else if(actionCode.equals("NEW WORLD"))
    {
      new Thread(myWorld).start();
    }
  }
}