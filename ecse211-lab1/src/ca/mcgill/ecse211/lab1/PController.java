package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 150;
  
//  public static final int motorSpeedLeft = 175; 
//  public static final int motorSpeedRight = 150; 
  public static final int WALLDIST = BAND_CENTER; 
  public static final int DEADBAND = BAND_WIDTH;
  

  public PController() {
    LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
    // TODO: process a movement based on the us distance passed in (P style)
    int errorDist = WALLDIST - distance; // errorDist is the difference between bandCenter and robot's distance from wall.
    
    if(Math.abs(errorDist) <= DEADBAND) {   //Move robot forward if offset distance from wall distance path is within bandwidth threshold
      LEFT_MOTOR.setSpeed(MOTOR_SPEED);      
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();
    }
    else if(errorDist > 0) { // robot is close to wall
      int diff = calcGain(errorDist);
      LEFT_MOTOR.setSpeed(MOTOR_SPEED + diff);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED + diff);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.backward();  //Roll wheels backwards to ensure robot does not hit wall
    }
    else if(errorDist < 0) { // robot is far away from wall
      int diff = calcGain(errorDist);
      LEFT_MOTOR.setSpeed(MOTOR_SPEED - diff);       //decrease left motor speed.
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED + diff);     //Increase right motor speed to allow robot to move closer to wall
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();  
    }
  
  }
  
  public int calcGain(int errorDist) {
//    int correction = (200*(errorDist))/8 + MOTOR_SPEED;
    int correction;
    int bound = 50; // correction boundary to avoid over correction.
    int CORRVAR = 10;
    
//   correction = (int)(CORRVAR * Math.abs(errorDist) );
   correction = (CORRVAR * Math.abs(errorDist) );
    
   if (correction >= MOTOR_SPEED ) {
     correction = bound;
   }
    
    return correction;
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
