package mysnake7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


class key extends KeyAdapter
{

    public void keyReleased(KeyEvent e)
    {

        //boolean k1=false, k2=false;

        if(e.getKeyCode()==KeyEvent.VK_CONTROL)
            Game.k1=true;
        if(e.getKeyCode()==KeyEvent.VK_T)
            Game.k2=true;




    }


    @Override
    public void keyPressed(KeyEvent e) {






        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            if(!Game.game_over)
            {
                if (Game.pause==false) Game.pause=true;
                else Game.pause=false;
            }
            else Game.start_the_game=true;

        }


        if(e.getKeyCode()==KeyEvent.VK_RIGHT && Game.xy[0][0] ==  Game.xy[1][0] ) Game.direction=1;
        else  if(e.getKeyCode()==KeyEvent.VK_LEFT &&  Game.xy[0][0] ==  Game.xy[1][0]) Game.direction=2;
        else  if(e.getKeyCode()==KeyEvent.VK_DOWN && Game.xy[0][1] ==  Game.xy[1][1]) Game.direction=3;
        else  if(e.getKeyCode()==KeyEvent.VK_UP && Game.xy[0][1] ==  Game.xy[1][1]) Game.direction=4;

    }
}

class Game extends JPanel implements ActionListener
{
    int x=10,y=10,width=10, applex,appley,score=0,speed=300,the_best_score=0;
    static int direction=1;
    static boolean pause =false,game_over=false,start_the_game=false, filing=false;

    static int xy[][]= new int [3][2];
    String pause_continue;
    Timer tm;

    static boolean k1=false,k2= false;


    Game()
    {
        setBackground(Color.BLACK);
        for(int i=0;i<xy.length;i++)
        {
           xy[i][0]=width;
            xy[i][1]=width;

        }


        applex=3*width;
        appley=4*width;

        tm = new Timer(speed,this);
        tm.start();

    }


    public void start()
    {
        x=10;y=10;width=10; applex=3*width;;appley=4*width;;score=0;speed=300;the_best_score=0;
       direction=1;
       pause =false;game_over=false;
      filing=false;
        int delete_array[][]= new int [3][2];
        xy= delete_array;
        for(int i=0;i<xy.length;i++)
        {
            xy[i][0]=width;
            xy[i][1]=width;

        }
        //tm.setDelay(speed);
        start_the_game=false;
    }


    public void write(String line)
    {
        try
        {
            FileWriter fl2 = new FileWriter("score.txt");
            BufferedWriter br = new BufferedWriter(fl2);

           // String line="0";


            br.write(line);


            br.close();

        }
        catch (Exception e2)
        {
            System.out.println(e2);
        }
    }

   public int read()
   {
       String line=null;
       try
       {
           FileReader fl = new FileReader("score.txt");
           BufferedReader br = new BufferedReader(fl);



           /*
           while ((line= br.readLine()) !=null)
           {
               line= br.readLine();
           }

            */

           line= br.readLine();

           br.close();

       }
       catch (Exception e)
       {
           write("0");


       }


       return Integer.parseInt(line);

   }


    public void actionPerformed(ActionEvent ae)
    {
        if(k1 && k2)
        {
            if(Game.game_over) Game.start_the_game=true;
            write("0");
       k1=false;
       k2=false;

        }


        if (start_the_game)
        {
            speed=300;
            tm.setDelay(speed);
            start();
        }

        if(!game_over)
        {
            if(!pause)
            {
                for (int i=xy.length-1;i>0;i--)
                {
                    xy[i][0]=xy[i-1][0];
                    xy[i][1]=xy[i-1][1];
                }


                switch (direction)
                {
                    case 1:
                        if(x>25*width) x=0;
                        x+=10;

                        break;

                    case 2:
                        if(x<2*width) x=27*width;
                        x-=10;

                        break;

                    case 3:

                        if(y>23*width) y=0;
                        y+=10;

                        break;


                    case 4:
                        if(y<2*width) y=25*width;
                        y-=10;

                        break;

                }


                xy[0][0] =x;
                xy[0][1]=y;

                for(int i=1;i<xy.length;i++)
                {
                    if(xy[0][0]==xy[i][0] && xy[0][1]==xy[i][1]) game_over=true;
                }


                if(xy[0][0]==applex && xy[0][1]==appley)
                {
                    score++;

                    boolean same=false;

                    while (!same)
                    {
                        same=true;
                        applex=(int)(Math.random()*25)*width+width;
                        appley=(int)(Math.random()*24)*width+width;
                        for (int i=0;i<xy.length;i++)
                        {
                            if(applex==xy[i][0] && appley==xy[i][1]) same=false;
                        }

                    }
                    //same=false;


                    int resize_array [][] = new int [xy.length+1][2];

                    for(int i=0;i<xy.length;i++)
                    {
                        resize_array[i][0]=xy[i][0];
                        resize_array[i][1]=xy[i][1];
                    }
                    resize_array[resize_array.length-1][0]=resize_array[resize_array.length-2][0];
                    resize_array[resize_array.length-1][1]=resize_array[resize_array.length-2][1];

                    xy=resize_array;

                    speed-=5;
                    if(speed<25) speed=25;
                    tm.setDelay(speed);

                }



            }

            if(pause==false) pause_continue=" To pause press space";
            else pause_continue=" To continue press space";
        }
        else
        {
            //if(game_over==true)
                pause_continue=" To restart press space";


            if(filing==false)
            {

                Integer score2 =score;

                if(score>read())
                {
                    write(score2.toString());
                }

             the_best_score= read();
                filing=true;

            }


        }


        repaint();

    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);




        g.setColor(Color.BLUE);
        for(int i=1;i<27;i++)
        {
            for(int z=1;z<25;z++)
            {
                g.drawOval(i*width,z*width,width,width);
            }
        }


        g.setColor(Color.RED);

        g.fillOval(applex,appley,width,width);

        g.setColor(Color.GREEN);

        for(int i=0;i<xy.length;i++)
            g.fillOval(xy[i][0],xy[i][1],width,width);


        g.setColor(Color.YELLOW);

      if(game_over==true)

      {

          g.setFont(new Font("Calibri",Font.BOLD,33));
          g.drawString("Game Over",60,130);
          g.setFont(new Font("Calibri",Font.BOLD,20));
          g.drawString("The Best Score: "+the_best_score,60,170);
          g.setFont(new Font("Calibri",Font.BOLD,14));
          g.drawString("If you want to remove the the best score,",18,190);
          g.drawString("press ctrl+t",105,210);




      }


        g.setFont(new Font("Calibri",Font.PLAIN,12));
        g.drawString("Score: "+score+pause_continue,10,263);
    }


}

public class mysnake7 {

    public static void main(String [] args)
    {
        JFrame jf = new JFrame();
       jf.setVisible(true);
       jf.setSize(300,310);
       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       jf.add(new Game());
       jf.setResizable(false);
       jf.addKeyListener(new key());


    }
}
