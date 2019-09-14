package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class BangBangController extends UltrasonicController {

  public static final int WALLDIST = 30; //Standoff distance to wall/ bandcenter
  public static final int DEADBAND = 5;  //Error threshold or bandwidth
  public static final int DELTASPDHIGH = 100; //Bang-bang constant
  public static final int DELTASPDLOW = 20; //Bang-bang constant
  public static final int SLEEPINT = 50;  //Sleep interval 50ms = 20Hz
  
  public BangBangController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start robot moving forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);

    // TODO: process a movement based on the us distance passed in (BANG-BANG style)
    int errorDist = WALLDIST - distance; //compute error
    
    if(Math.abs(errorDist) <= DEADBAND) {
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();
    }
    else if(errorDist > 0) { // close to wall
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(75);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();  
    }
    else if(errorDist < 0) { // far away
      LEFT_MOTOR.setSpeed(75);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();  
    }
    try {
      Thread.sleep(SLEEPINT);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
