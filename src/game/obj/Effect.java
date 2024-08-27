package game.obj;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Effect {
    private final double x;
    private final double y;
    private final double max_distance;
    private final int max_size;
    private final Color color;
    private final int totalEffect;
    private final float speed;
    private double current_distance;
    private ModelBoom booms[];
    private float alpha = 1f;


    public Effect(double x,double y, double max_distance, int max_size, int totalEffect, float speed, Color color){
        this.x = x;
        this.y = y;
        this.max_distance =max_distance;
        this.max_size =max_size;
        this.color = color;
        this.totalEffect = totalEffect;
        this.speed = speed;
        createRandom();
    }

    private void createRandom() {
        booms = new ModelBoom[totalEffect];
        float per = 360f / totalEffect;
        Random ran = new Random();
        for (int i = 0; i < totalEffect; i++) {
            int r = ran.nextInt((int) per) + 1;
            int boomSize = ran.nextInt(max_size) + 1;
            float angle = i * per + r;
            booms[i] = new ModelBoom(boomSize, angle);
        }
    }


    public void draw(Graphics2D g2){

        AffineTransform oldTransform = g2.getTransform();
        Composite oldComposite = g2.getComposite();
        g2.setColor(color);
        g2.translate(x, y);
        for (ModelBoom b:booms){
            double bx = Math.cos(Math.toRadians(b.getAngle())) * current_distance;
            double by = Math.sin(Math.toRadians(b.getAngle())) * current_distance;
            double boomSize = b.getSize();
            double space= boomSize/2;
            if(current_distance>=max_distance-(max_distance*0.7f)){
                alpha=(float) ((max_distance-current_distance)/(max_distance*0.7f));
            }
            if (alpha>1){
                alpha =1;
            } else if (alpha<0) {
                alpha =0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g2.fill(new Rectangle2D.Double(bx - space,by -space,boomSize,boomSize));
        }
        g2.setComposite(oldComposite);
        g2.setTransform(oldTransform);
    }

    public void update() {
        current_distance += speed;
        System.out.println("Current Distance: " + current_distance + ", Alpha: " + alpha);
    }


    public boolean check(){
        return current_distance < max_distance;
    }
}
