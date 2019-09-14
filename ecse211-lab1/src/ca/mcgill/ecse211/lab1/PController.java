package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 150;
  
  public static final int WALLDIST = 30; //Standoff distance to wall/ bandcenter
  //public static final int DEADBAND = 2;  //Error threshold or bandwidth
  public static final int CORRVAR = 1; //Bang-bang constant
  public static final int SLEEPINT = 50;  //Sleep interval 50ms = 20Hz

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
 int errorDist = WALLDIST - distance; //compute error
   int deltaSpd = Math.abs(errorDist* CORRVAR);
     if (deltaSpd > 150) {
       deltaSpd = 150;
     }
   /* if(Math.abs(errorDist) <= DEADBAND) {
      LEFT_MOTOR.setSpeed(MOTOR_SPEED);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();
    } */
     if(errorDist > 0) {
      LEFT_MOTOR.setSpeed(MOTOR_SPEED + 2*deltaSpd);
     // RIGHT_MOTOR.setSpeed(MOTOR_SPEED - 2*deltaSpd);
      RIGHT_MOTOR.setSpeed(3);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();  
    }
    else if(errorDist < 0) {
      LEFT_MOTOR.setSpeed(MOTOR_SPEED - deltaSpd);
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED + deltaSpd);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();  
    }
  /*  try {
      Thread.sleep(SLEEPINT);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } */
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
