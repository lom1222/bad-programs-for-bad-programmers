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
import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;
public class TGen extends Random
{
  private ArrayList<SampleTerrain> terrainTypeList = new ArrayList<SampleTerrain>();
  private int totalChance = 0;
  private int totalSpread = 0;
  private int seed;
  public TGen(int seed)
  {
    this.seed = seed;
    setSeed(seed);
  }
  public int getSeed()
  {
    return seed;
  }
  public TGen(SampleTerrain[] tArray,int seed)
  {
    setSeed(seed);
    for(int x=0;x<tArray.length;x++)
    {
      terrainTypeList.add(tArray[x]);
      totalChance += tArray[x].getChance();
      totalSpread += tArray[x].getSpread();
    }
  }
  public void addTerrain(SampleTerrain terrain)
  {
    terrainTypeList.add(terrain);
    totalChance += terrain.getChance();
    totalSpread += terrain.getSpread();
  }
  public int getChance(String terrainName)
  {
    for(int x=0;x<terrainTypeList.size();x++)
    {
      if(terrainTypeList.get(x).getName().equals(terrainName))
        return terrainTypeList.get(x).getChance();
    }
    return 0;
  }
  public int getSpread(String terrainName)
  {
    for(int x=0;x<terrainTypeList.size();x++)
    {
      if(terrainTypeList.get(x).getName().equals(terrainName))
        return terrainTypeList.get(x).getSpread();
    }
    return 0;
  }
  public Color getColor(String terrainName)
  {
    for(int x=0;x<terrainTypeList.size();x++)
    {
      if(terrainTypeList.get(x).getName().equals(terrainName))
        return terrainTypeList.get(x).getColor();
    }
    return Color.PINK;
  }
  public int getTotalChance()
  {
    return totalChance;
  }
  public int getTotalSpread()
  {
    return totalSpread;
  }
  public ArrayList<SampleTerrain> getTerrainList()
  {
    return terrainTypeList;
  }
  public String getRandTerrain()
  {
    int tempRand = nextInt(totalChance);
    int minRand = 0;
    int maxRand = terrainTypeList.get(0).getChance();
    for(int x = 0;x<terrainTypeList.size()-1;x++)
    {
      if(tempRand >= minRand && tempRand < maxRand)
      {
        return terrainTypeList.get(x).getName();
      }
      minRand+=terrainTypeList.get(x).getChance();
      maxRand+=terrainTypeList.get(x+1).getChance();
    }
    return terrainTypeList.get(terrainTypeList.size()-1).getName();
  }
}
