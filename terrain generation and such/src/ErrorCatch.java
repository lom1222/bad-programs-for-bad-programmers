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
import java.util.Set;
public class ErrorCatch implements Runnable
{
  TPanel worldPanel;
  World world;
  Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
  Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
  int basicThreads = threadArray.length+1;
  public ErrorCatch(TPanel worldPanel,World world)
  {
    this.worldPanel=worldPanel;
    this.world=world;
  }
  public void run()
  {
    while(true)
    {
      threadSet = Thread.getAllStackTraces().keySet();
      threadArray = threadSet.toArray(new Thread[threadSet.size()]);
      System.out.println("Errors Detected, waiting to recreate, threads running:"+(threadArray.length-basicThreads));
      //System.out.println(threadSet);
      if(threadArray.length<=basicThreads)
      {
        System.out.println("Errors Detected, recreating world");
        new Thread(world).start();
        world.errorBool = false;
        break;
      }
      try{Thread.sleep(1000);}catch(InterruptedException e){}
    }
  }
}
