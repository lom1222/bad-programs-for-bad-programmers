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
import hsa.Console;
import java.awt.Color;
import java.util.Random;
public class MergeSortNikita
{
    //YOU CAN PLAY WITH THESE FINAL VALUES TO CHANGE THE PROGRAM
    private final int VALUEMAX = 1200;
    private final int MAXRAND = 400;
    private final int DELAYVALUE = 1;
    private final int SCREENSIZE = 150;
    private final int VALUEWIDTH = 1;
    private final boolean draw = true;
    private final boolean returnDone = false;
    private final boolean loop = true;
    private final Color colorFront = Color.green;
    private final Color colorBack = Color.black;
    //TRUST ME, ITS FUNNY
    int[] unsorted = new int [VALUEMAX];
    int[] sorted = new int [VALUEMAX];
    Console c;
    public MergeSortNikita ()
    {
	c = new Console (25, SCREENSIZE);
	while(true)
	{
	    setUpScreen ();
	    generateValues ();
	    beginMergeSort ();
	    if(!loop)
	    {
		break;
	    }
	}
	if(returnDone)
	{
	    System.out.println("done: "+VALUEMAX);
	}
    }


    private void setUpScreen ()
    {
	c.setTextColor (colorFront);
	c.setTextBackgroundColor (colorBack);
	c.clear ();
    }


    private void generateValues ()
    {
	Random r = new Random ();
	for (int x = 0 ; x < VALUEMAX ; x++)
	{
	    unsorted [x] = r.nextInt (MAXRAND - 1) + 1;
	}
	if(draw)
	    drawMerge (unsorted, 0);
    }


    private void beginMergeSort ()
    {
	sorted = mergeSort (unsorted, 0);
    }


    private void drawMerge (int[] numbers, int startAt)
    {
	c.setColor (colorFront);
	c.fillRect (VALUEWIDTH * startAt, 480, VALUEWIDTH * numbers.length, 15);
	try
	{
	    Thread.sleep (DELAYVALUE / 5 * 2);
	}
	catch (InterruptedException e)
	{
	}
	for (int x = startAt ; x < startAt + numbers.length ; x++)
	{
	    c.setColor (colorBack);
	    c.fillRect (x * VALUEWIDTH, 250 - MAXRAND, VALUEWIDTH/6*5+1, 215 + MAXRAND);
	    c.setColor (colorFront);
	    c.fillRect (x * VALUEWIDTH, 450 - (numbers [x - startAt] ), VALUEWIDTH/6*5+1, numbers [x - startAt] );
	}
	try
	{
	    Thread.sleep (DELAYVALUE / 5 * 3);
	}
	catch (InterruptedException e)
	{
	}
	c.setColor (colorBack);
	c.fillRect (VALUEWIDTH * startAt, 480, VALUEWIDTH * numbers.length, 15);
    }


    private int[] mergeSort (int[] numbers, int startAt)
    {
	int[] sorted = new int [numbers.length];
	int[] number1 = new int [numbers.length / 2];
	int[] number2 = new int [numbers.length - (numbers.length / 2)];
	int[] sorted1 = new int [numbers.length / 2];
	int[] sorted2 = new int [numbers.length - (numbers.length / 2)];
	if (numbers.length == 1)
	{
	    return numbers;
	}
	else
	{
	    for (int x = 0 ; x < number1.length ; x++)
	    {
		number1 [x] = numbers [x];
	    }
	    for (int x = 0 ; x < number2.length ; x++)
	    {
		number2 [x] = numbers [x + number1.length];
	    }
	    sorted1 = mergeSort (number1, startAt);
	    sorted2 = mergeSort (number2, startAt + number1.length);
	    int x = 0, y = 0;
	    while (true)
	    {
		if (x == sorted1.length)
		{
		    for (y = y ; y < sorted2.length ; y++)
		    {
			sorted [x + y] = sorted2 [y];
		    }
		    break;
		}
		if (y == sorted2.length)
		{
		    for (x = x ; x < sorted1.length ; x++)
		    {
			sorted [x + y] = sorted1 [x];
		    }
		    break;
		}
		if (sorted1 [x] <= sorted2 [y])
		{
		    sorted [x + y] = sorted1 [x];
		    x++;
		}
		else
		{
		    sorted [x + y] = sorted2 [y];
		    y++;
		}
	    }
	}
	if(draw)
	    drawMerge (sorted, startAt);
	return sorted;
    }


    public static void main (String[] args)
    {
	MergeSortNikita msn = new MergeSortNikita ();
    }
}
