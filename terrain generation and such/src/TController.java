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
import javax.swing.JButton;
public class TController
{
  private final int SCREENW = 300;
  private final int SCREENH = SCREENW;
  private final int SCALE = 600/SCREENW;
  private final int NODESPLITW = SCREENW/9;
  private final int NODESPLITH = SCREENH/9;
  private final int SEED = new Random().nextInt();
  private final boolean DRAW = true;
  TGen myGen = new TGen(SEED);
  TPanel mainPanel = new TPanel(SCALE,myGen);
  World myWorld = new World(SCREENW,SCREENH,SCALE,NODESPLITW,NODESPLITH,myGen,mainPanel,DRAW);
  TFrame mainFrame = new TFrame(SCREENW*SCALE+304,SCREENH*SCALE+34,mainPanel,myWorld);
  JButton exitButton = new JButton("Exit");
  SplashScreen loadScreen = new SplashScreen();
  public TController()
  {
    myWorld.addTerrains();
    new Thread(myWorld).start();
  }
}