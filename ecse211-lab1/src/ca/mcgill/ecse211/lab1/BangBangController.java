package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class BangBangController extends UltrasonicController {

  
  public static final int WALLDIST = BAND_CENTER; //Stand off distance to wall/ band center
  public static final int DEADBAND = BAND_WIDTH;  //Error threshold or bandwidth


  
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
    int errorDist = WALLDIST - distance; // errorDist is the difference between bandCenter and robot's distance from wall.
    
    if(Math.abs(errorDist) <= DEADBAND) {   //Move robot forward if offset distance from wall distance path is within bandwidth threshold
      LEFT_MOTOR.setSpeed(MOTOR_HIGH + 50);      
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();
    }
    else if(errorDist > 0) { // robot is close to wall
      LEFT_MOTOR.setSpeed(MOTOR_HIGH );
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH );
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward();  //Roll wheels backwards to ensure robot does not hit wall
    }
    else if(errorDist < 0) { // robot is far away from wall
      LEFT_MOTOR.setSpeed(MOTOR_LOW + 50);       //decrease left motor speed.
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH+ 50);     //Increase right motor speed to allow robot to move closer to wall
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();  
    }
  
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
