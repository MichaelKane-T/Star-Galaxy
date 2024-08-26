package game.obj;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class Rocket {
    public static final double ROCKET_SIZE = 50;
    private double x;
    private double y;
    private final float speed = 0.3f;
    private float angle = 0;
    private final Image image;
    private final Area rocketShape;

    public Rocket(){
        this.image = new ImageIcon(getClass().getResource("/game/image/rocket.png")).getImage();
        Path2D path =  new Path2D.Double();
        path.moveTo(0, ROCKET_SIZE/2);
        path.lineTo(15,10);
        path.lineTo(ROCKET_SIZE-5,13);
        path.lineTo(ROCKET_SIZE+10,ROCKET_SIZE/2);
        path.lineTo(ROCKET_SIZE-5,ROCKET_SIZE-13);
        path.lineTo(15,ROCKET_SIZE-10);
        rocketShape= new Area();

    }

    public void changeLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void update() {
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    public void changeAngel(float angle){
        if(angle < 0){
            angle = 359;
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x,y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 45), ROCKET_SIZE/2, ROCKET_SIZE/2);
        g2.drawImage(image,tran,null);
        Shape shape = getRocketShape();
        g2.setTransform(oldTransform);

        g2.setColor(Color.green);
        g2.draw(shape);

    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public Area getRocketShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x,y);
        afx.rotate(Math.toRadians(angle), ROCKET_SIZE/2,ROCKET_SIZE/2);
        return new Area(afx.createTransformedShape(rocketShape));
    }
}
