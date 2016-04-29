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
import java.io.*;
public class Chunk
{
  TGen myGen;
  Chunk[][] myWorld;
  Terrain[][] chunk;
  int screenW = 0;
  int screenH = 0;
  int nodeSplitW = 0;
  int nodeSplitH = 0;
  int scale = 0;
  TPanel myPanel;
  String xysave;
  String [] tempxy;
  Terrain[][] tempChunk;
  boolean draw;
  
  BufferedReader in;
  
  int chunkx = 0;
  int chunky = 0;
  public Chunk()
  {
    chunk = emptyChunk();
  }
  public Chunk(int screenW,int screenH,int scale,int nodeSplitW,int nodeSplitH,TGen randGen,TPanel mainPanel,Chunk[][] myWorld,boolean draw)
  {
    myGen = randGen;
    chunk = new Terrain[screenW][screenH];
    this.screenW = screenW;
    this.screenH = screenH;
    this.myWorld =myWorld;
    this.nodeSplitW = nodeSplitW;
    this.nodeSplitH = nodeSplitH;
    this.scale = scale;
    this.draw = draw;
    myPanel = mainPanel;
    setEmptyChunk();
  }
  public void setEmptyChunk()
  {
    chunk = emptyChunk();
  }
  public Terrain[][] getChunk()
  {
    return chunk;
  }
  public Terrain[][] getChunk(int x,int y)
  {
    chunkx = x;
    chunky = y;
    try
    {
      in = new BufferedReader(new FileReader(myGen.getSeed()+"_"+x+"_"+y+".chunk"));
      for(int xe= 0;xe<chunk.length;xe++)
      {
        tempxy = in.readLine().split(" ");
        for(int ye = 0;ye<chunk[0].length;ye++)
        {
          chunk[ye][xe].setName(tempxy[ye]);
        }
      }
    }
    catch(IOException e)
    {
      generateNewChunk();
    }
    return chunk;
  }
  private Terrain[][] emptyChunk()
  {
    Terrain[][] tempWorld = new Terrain[screenW][screenH];
    for(int x = 0;x<screenW;x++)
    {
      for(int y = 0;y<screenH;y++)
      {
        tempWorld[y][x]=new Terrain();
      }
    }
    return tempWorld;
  }
  private void getBorderChunks()
  {
    if(chunkx>0)
    {
      tempChunk = myWorld[chunky][chunkx-1].getChunk();
      for(int x= 0;x<chunk[0].length;x++)
      {
        xysave = tempChunk[x][tempChunk.length-1].getName();
        if(xysave.equals("WATER")||xysave.equals("DEEP WATER"))
        {
          chunk[x][0].setName("WATER");
        }
        else if(xysave.equals("GRASS")||xysave.equals("FOREST")||xysave.equals("SAND"))
        {
          chunk[x][0].setName("GRASS");
        }
      }
    }
    if(draw)myPanel.drawAgain(myWorld);
    if(chunkx<2)
    {
      tempChunk = myWorld[chunky][chunkx+1].getChunk();
      for(int x= 0;x<chunk[0].length;x++)
      {
        xysave = tempChunk[x][0].getName();
        if(xysave.equals("WATER")||xysave.equals("DEEP WATER"))
        {
        chunk[x][chunk.length-1].setName("WATER");
        }
        else if(xysave.equals("GRASS")||xysave.equals("FOREST")||xysave.equals("SAND"))
        {
        chunk[x][chunk.length-1].setName("GRASS");
        }
      }
    }
    if(draw)myPanel.drawAgain(myWorld);
    if(chunky>0)
    {
      tempChunk = myWorld[chunky-1][chunkx].getChunk();
      for(int x= 0;x<chunk[0].length;x++)
      {
        xysave = tempChunk[tempChunk.length-1][x].getName();
        if(xysave.equals("WATER")||xysave.equals("DEEP WATER"))
        {
          chunk[0][x].setName("WATER");
        }
        else if(xysave.equals("GRASS")||xysave.equals("FOREST")||xysave.equals("SAND"))
        {
          chunk[0][x].setName("GRASS");
        }
      }
    }
    if(draw)myPanel.drawAgain(myWorld);
    if(chunky<2)
    {
      tempChunk = myWorld[chunky+1][chunkx].getChunk();
      for(int x= 0;x<chunk[0].length;x++)
      {
        xysave = tempChunk[0][x].getName();
        if(xysave.equals("WATER")||xysave.equals("DEEP WATER"))
        {
          chunk[chunk.length-1][x].setName("WATER");
        }
        else if(xysave.equals("GRASS")||xysave.equals("FOREST")||xysave.equals("SAND"))
        {
          chunk[chunk.length-1][x].setName("GRASS");
        }
      }
    }
    if(draw)myPanel.drawAgain(myWorld);
  }
  public void generateNewChunk()
  {
    setEmptyChunk();
    getBorderChunks();
    generateBaseTerrain();
    addSand();
    addDeepWater();
    smooth();
    addForest(screenW/20*3+1);
  }
  public void smooth()
  {
    for(int x = 0;x<screenW;x++)
    {
      for(int y = 0;y<screenH;y++)
      {
        if(x>0&&x<screenW-1&&chunk[y][x-1].getName().equals(chunk[y][x+1].getName()))
        {
          chunk[y][x].setName(chunk[y][x+1].getName());
        }
        else if(y>0&&y<screenH-1&&chunk[y-1][x].getName().equals(chunk[y+1][x].getName()))
        {
          chunk[y][x].setName(chunk[y+1][x].getName());
        }
      }
    }
    if(draw)myPanel.drawAgain(myWorld);
  }
  public void addSand()
  {
    int sandLayers = screenW/20;
    for(int z = 0;z<sandLayers;z++)
    {
      xysave = "";
      for(int x = 0;x<screenW;x++)
      {
        for(int y = 0;y<screenH;y++)
        {
          if(chunk[y][x].getName().equals("GRASS"))
          {
            if((x>0&&chunk[y][x-1].getName().equals("WATER"))||(x<screenW-1&&chunk[y][x+1].getName().equals("WATER"))||(y>0&&chunk[y-1][x].getName().equals("WATER"))||(y<screenH-1&&chunk[y+1][x].getName().equals("WATER"))||(x>0&&chunk[y][x-1].getName().equals("SAND"))||(x<screenW-1&&chunk[y][x+1].getName().equals("SAND"))||(y>0&&chunk[y-1][x].getName().equals("SAND"))||(y<screenH-1&&chunk[y+1][x].getName().equals("SAND")))
            {
              xysave+=y+" "+x+" ";
            }
          }
        }
      }
      tempxy = xysave.split(" ");
      for(int y = 0;y<tempxy.length-1;y+=2)
      {
        if(myGen.nextInt(2)==1)
        {
          chunk[Integer.parseInt(tempxy[0+y])][Integer.parseInt(tempxy[1+y])].setName("SAND");
        }
      }
    if(draw)myPanel.drawAgain(myWorld);
    }
  }
  public void addDeepWater()
  {
    int shallowLength = nodeSplitW/5;
    xysave = "";
    for(int x = 0;x<screenW;x++)
    {
      for(int y = 0;y<screenH;y++)
      {
        if((chunk[y][x].getName().equals("WATER"))&&(x<shallowLength||chunk[y][x-shallowLength].getName().equals("WATER"))&&(y<shallowLength||chunk[y-shallowLength][x].getName().equals("WATER"))&&(x>=screenW-shallowLength-1||chunk[y][x+shallowLength].getName().equals("WATER"))&&(y>=screenH-shallowLength-1||chunk[y+shallowLength][x].getName().equals("WATER")))
        {
          xysave+= x+" "+y+" ";
        }
      }
    }
    tempxy = xysave.split(" ");
    for(int x=0;x<tempxy.length-1;x+=2)
    {
      chunk[Integer.parseInt(tempxy[1+x])][Integer.parseInt(tempxy[0+x])].setName("DEEP WATER");
    }
    if(draw)myPanel.drawAgain(myWorld);
  }
  public void addForest(int forestRad)
  {
    int forestChance = (int)Math.sqrt(screenW*screenH)*15;
    xysave = "";
    for(int x = 0;x<screenW;x++)
    {
      for(int y = 0;y<screenH;y++)
      {
        if((myGen.nextInt(forestChance)==forestChance-1)&&chunk[y][x].getName().equals("GRASS")&&(x<(forestRad/2+1)||chunk[y][x-(forestRad/2+1)].getName().equals("GRASS"))&&(y<(forestRad/2+1)||chunk[y-(forestRad/2+1)][x].getName().equals("GRASS"))&&(x>screenW-(forestRad/2+1)-1||chunk[y][x+(forestRad/2+1)].getName().equals("GRASS"))&&(y>screenH-(forestRad/2+1)-1||chunk[y+(forestRad/2+1)][x].getName().equals("GRASS")))
        {
          xysave+=y+" "+x+" ";
          for(int ex = 0;ex<forestRad;ex++)
          {
            for(int ey = 0;ey<forestRad;ey++)
            {
              if((myGen.nextInt((forestRad/2+1-ex)*(forestRad/2+1-ex)+(forestRad/2+1-ey)*(forestRad/2+1-ey)+3))>=((forestRad/2+1-ex)*(forestRad/2+1-ex)+(forestRad/2+1-ey)*(forestRad/2+1-ey))&&((y-forestRad/2+ey)>0&&(x-forestRad/2+ex)>0&&(y-forestRad/2-1+ey)<screenH&&(x-forestRad/2-1+ex)<screenW)&&(chunk[(y-forestRad/2-1+ey)][(x-forestRad/2-1+ex)].getName().equals("GRASS")))
              {
                xysave+=(y-forestRad/2-1+ey)+" "+(x-forestRad/2-1+ex)+" ";
              }
            }
          }
        }
      }
    }
    tempxy = xysave.split(" ");
    for(int x = 0;x<tempxy.length-1;x+=2)
    {
      chunk[Integer.parseInt(tempxy[0+x])][Integer.parseInt(tempxy[1+x])].setName("FOREST");
    }
    if(draw)myPanel.drawAgain(myWorld);
  }
  public void generateBaseTerrain()
  {
    boolean worldBuilding = false;
    int totalSpread = myGen.getTotalChance();
    int currentChance = 0;
    for(int x =0;x<screenW/nodeSplitW;x++)
    {
      for(int y =0;y<screenH/nodeSplitH;y++)
      {
        chunk[y*nodeSplitH+myGen.nextInt(nodeSplitH)][x*nodeSplitW+myGen.nextInt(nodeSplitW)].setName(myGen.getRandTerrain());
      }
    }
    while(true)
    {
      xysave = "";
      worldBuilding = false;
      for(int x = 0;x<screenW;x++)
      {
        for(int y =0;y<screenH;y++)
        {
          if(myGen.getChance(chunk[y][x].getName())!=0)
          {
            if((x>0)&&(myGen.getChance(chunk[y][x-1].getName())==0))
            {
              xysave+= y+" "+x+" "+(y)+" "+(x-1)+" ";
              worldBuilding = true;
            }
            if((y>0)&&(myGen.getChance(chunk[y-1][x].getName())==0))
            {
              xysave+= y+" "+x+" "+(y-1)+" "+(x)+" ";
              worldBuilding = true;
            }
            if((x<screenW-1)&&(myGen.getChance(chunk[y][x+1].getName())==0))
            {
              xysave+= y+" "+x+" "+(y)+" "+(x+1)+" ";
              worldBuilding = true;
            }
            if((y<screenH-1)&&(myGen.getChance(chunk[y+1][x].getName())==0))
            {
              xysave+= y+" "+x+" "+(y+1)+" "+(x)+" ";
              worldBuilding = true;
            }
          }
        }
      }
      tempxy = xysave.split(" ");
      for(int x = 0;x<tempxy.length-1;x+=4)
      {
        currentChance = myGen.getSpread(chunk[Integer.parseInt(tempxy[0+x])][Integer.parseInt(tempxy[1+x])].getName());
        if(myGen.nextInt(totalSpread)>=((totalSpread-currentChance)))
        {
          chunk[Integer.parseInt(tempxy[2+x])][Integer.parseInt(tempxy[3+x])].setName(chunk[Integer.parseInt(tempxy[0+x])][Integer.parseInt(tempxy[1+x])].getName());
        }
      }
    if(draw)myPanel.drawAgain(myWorld);
      if(!worldBuilding)
      {
        break;
      }
    }
  }
}
