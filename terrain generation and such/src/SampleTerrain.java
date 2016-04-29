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
import java.awt.Color;
public class SampleTerrain extends Terrain
{
  private int terrainChance = 0;
  private int spreadChance = 0;
  private Color terrainColor = Color.PINK;
  private String terrainName = "EMPTY";
  public SampleTerrain(int chance,int spread, Color color,String name)
  {
    terrainChance = chance;
    terrainColor = color;
    terrainName = name;
    spreadChance = spread;
  }
  public Color getColor()
  {
    return terrainColor;
  }
  public int getChance()
  {
    return terrainChance;
  }
  public String getName()
  {
    return terrainName;
  }
  public int getSpread()
  {
    return spreadChance;
  }
}