import java.awt.Color;
public class World implements Runnable
{
  TGen myGen;
  Chunk[][] world = new Chunk[3][3];
  TPanel worldPanel;
  int screenW = 0;
  int screenH = 0;
  int nodeSplitW = 0;
  int nodeSplitH = 0;
  int scale = 0;
  String xysave;
  String [] tempxy;
  TFrame myFrame;
  boolean draw;
  ErrorCatch errorCatch = new ErrorCatch(worldPanel,this);
  boolean errorBool = false;
  public World(int screenW,int screenH,int scale,int nodeSplitW,int nodeSplitH,TGen randGen,TPanel mainPanel,boolean draw)
  {
    myGen = randGen;
    worldPanel =mainPanel;
    this.screenW = screenW;
    this.screenH = screenH;
    this.nodeSplitW = nodeSplitW;
    this.nodeSplitH = nodeSplitH;
    this.scale = scale;
    this.draw = draw;
  }
  public Chunk[][] getWorld()
  {
    return world;
  }
  public void makeWorld()
  {
    for(int x= 0;x<world.length;x++)
    {
      for(int y = 0;y<world.length;y++)
      {
        world[y][x] = new Chunk(screenW/3, screenH/3, scale, nodeSplitW,nodeSplitH, myGen,worldPanel,world,draw);
      }
    }
  }
  public void run()
  {
    try
    {
    makeWorld();
    for(int x= 0;x<world.length;x++)
    {
      for(int y = 0;y<world.length;y++)
      {
        world[y][x].getChunk(x,y);
        worldPanel.drawAgain(world);
        //try{Thread.sleep(2000);}catch(InterruptedException e){}
      }
    }
    worldPanel.drawAgain(world);
    }
    catch(Exception e)
    {
      System.out.println(e);
      if(!errorBool)
      {
        errorBool = true;
        new Thread(errorCatch).start();
      }
      
    }
  }
  public void addFrame(TFrame myFrame)
  {
    this.myFrame = myFrame;
  }
  public void addTerrains()
  {
    myGen.addTerrain(new SampleTerrain(4,1,Color.GREEN,"GRASS"));
    myGen.addTerrain(new SampleTerrain(1,2,Color.BLUE,"WATER"));
    myGen.addTerrain(new SampleTerrain(0,0,Color.YELLOW,"SAND"));
    myGen.addTerrain(new SampleTerrain(0,0,new Color(0,0,192),"DEEP WATER"));
    myGen.addTerrain(new SampleTerrain(0,0,new Color(0,160,0),"FOREST"));
  }
}