
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Board extends JPanel implements ActionListener {
        int b_hei=400;
        int b_wid=400;
        int max_dot=1600;
        int dot_size=10;
        int dot;
        int x[]=new int[max_dot];
        int y[]=new int[max_dot];
        int apple_x;
        int apple_y;
        Image body,head,apple;
        Timer timer;
    int DELAY=200;//0.3 sec time taken to change
        boolean leftDirection=true;
        boolean rightDirection=false;
        boolean upDirection=false;
    boolean downDirection=false;
  boolean inGame=true;
    Board(){
       TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
     setFocusable(true);
        setPreferredSize(new Dimension(b_wid,b_hei));
        setBackground(Color.black);
        initgame();
        loadImages();

        //setBackground(Color.black);
    }
    public void initgame(){
       dot=3;
        x[0]=250;
        y[0]=250;
        for(int i=1;i<dot;i++){
            x[i]=x[0]+dot_size*i;
            y[i]=y[0];
        }
       //apple_x=50;
        //apple_y=50;
         locateApple();
        timer =new Timer(DELAY,this);
        timer.start();
    }
    public void loadImages(){
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body=bodyIcon.getImage();
        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head=headIcon.getImage();
       ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);

    }
    //draw image
    public void doDrawing(Graphics g) {
        if(inGame){
        g.drawImage(apple, apple_x, apple_y, this);


        for (int i = 0; i < dot; i++) {
            if (i == 0) {
                g.drawImage(head, x[0], y[0], this);
            } else{
                g.drawImage(body, x[i], y[i], this);
        }
    }}
        else{
            gameOver(g);
            timer.stop();
        }}
   //randomize apples position
    public void locateApple(){
        apple_x=((int)(Math.random()*39))*dot_size;
        apple_y=((int)(Math.random()*39))*dot_size;
    }

public void checkCollision(){
        //collision with body
        for(int i=1;i<dot;i++){
            if(i>4&&x[0]==x[i]&&y[0]==y[i]){
                inGame=false;
            }
        }
        //collision with boarder
    if(x[0]<0){
        inGame=false;
    }
    if(x[0]>=b_wid){
        inGame=false;
    }
    if(y[0]<0){
        inGame=false;
    }
    if(y[0]>=b_hei) {
        inGame=false;
    }
}
//display game over msg
    public void gameOver(Graphics g){
        String msg="Game Over";
        int score=(dot-3)*100;
        String scoremsg="Score:"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(b_wid-fontMetrics.stringWidth(msg))/2,b_hei/4);
        g.drawString(scoremsg,(b_wid-fontMetrics.stringWidth(scoremsg))/2,3*(b_hei/4));

    }
   @Override
    public void actionPerformed(ActionEvent e){
      if(inGame) {
            checkApple();
            checkCollision();
        move();}
//move();
repaint();

    }
    //to move a snake
   public void move(){
       for(int i=dot-1;i>0;i--){
           x[i]=x[i-1];
           y[i]=y[i-1];}
       if(leftDirection){
           x[0]-=dot_size;
       }
       if(rightDirection){
           x[0]+=dot_size;
       }
       if(upDirection){
           y[0]-=dot_size;
       }
       if(downDirection){
           y[0]+=dot_size;
       }
    }
    //make snake eat food
   public void checkApple(){
        if(apple_x==x[0]&&apple_y==y[0]){
            dot++;
            locateApple();
        }
    }
    //implement controls
    public class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&(!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT&&(!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP&&(!downDirection)){
                leftDirection=false;
                upDirection=true;
                downDirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&(!upDirection)){
                leftDirection=false;
                downDirection=true;
                rightDirection=false;
            }
        }
    }
}
