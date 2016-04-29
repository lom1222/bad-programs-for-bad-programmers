import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
public class TPanel extends JPanel
{
  private int worldScale = 0;
  Chunk[][] world = {{new Chunk()}};
  Terrain[][] chunk;
  TGen myGen;
  private int chunkx = 0;
  private int chunky = 0;
  public TPanel(int scale,TGen myGen)
  {
    setLayout(null);
    this.myGen = myGen;
    setWorldScale(scale);
  }
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.GRAY);
    g.fillRect(2,2,900,28);
    g.setColor(Color.BLACK);
    g.drawRect(0,0,(int)getBounds().getWidth()-2,(int)getBounds().getHeight()-2);
    g.drawRect(1,1,(int)getBounds().getWidth()-2,(int)getBounds().getHeight()-2);
    g.drawRect(602,32,1,599);
    g.drawRect(2,30,899,1);
    for(int wx = 0;wx<world.length;wx++)
    {
      for(int wy = 0;wy<world[wx].length;wy++)
      {
        chunk = world[wy][wx].getChunk();
        chunkx =wx;
        chunky =wy;
        for(int x = 0;x<chunk.length;x++)
        {
          for(int y = 0;y<chunk[x].length;y++)
          {
            g.setColor(myGen.getColor(chunk[y][x].getName()));
            g.fillRect(x*worldScale+2+chunkx*chunk.length*worldScale,y*worldScale+32+chunky*chunk.length*worldScale,worldScale,worldScale);
          }
        }
      }
    }
  }
  public void drawAgain(Chunk[][] world)
  {
    this.world = world;
    repaint();
  }
  public void setWorldScale(int scale)
  {
    worldScale = scale;
  }
}