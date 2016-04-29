 //Copyright 2016 Nikita Lizogubenko
 //
 //  Licensed under the Apache License, Version 2.0 (the "License");
 //  you may not use this file except in compliance with the License.
 //  You may obtain a copy of the License at
 //
 //      http://www.apache.org/licenses/LICENSE-2.0
 //
 //  Unless required by applicable law or agreed to in writing, software
 //  distributed under the License is distributed on an "AS IS" BASIS,
 //  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 //  See the License for the specific language governing permissions and
 //  limitations under the License.
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
