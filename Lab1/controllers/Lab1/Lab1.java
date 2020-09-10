import static simlejos.ExecutionController.performPhysicsStep;
import static simlejos.ExecutionController.setNumberOfParties;
import static simlejos.ExecutionController.sleepFor;

import simlejos.hardware.motor.Motor;
import simlejos.hardware.port.SensorPort;
import simlejos.hardware.sensor.EV3UltrasonicSensor;
import simlejos.robotics.RegulatedMotor;

/**
 * Main class of the program.
 */
public class Lab1 {
  
  /** The number of threads used in the program (main and controller). */
  public static final int NUMBER_OF_THREADS = 2;
  /** The maximum distance detected by the ultrasonic sensor, in cm. */
  public static final int MAX_SENSOR_DIST = 255;
  
  //Parameters: adjust these for desired performance. You can also add new ones.
  
  /** Ideal distance between the sensor and the wall (cm). */
  public static final int WALL_DIST = 20;
  /** Maximum tolerated deviation from the ideal wall distance (deadband), in cm. */
  public static final int WALL_DIST_ERR_THRESH = 3;
  /** Speed of slower rotating wheel (deg/sec). */
  public static final int MOTOR_LOW = 25;
  /** Speed of the faster rotating wheel (deg/sec). */
  public static final int MOTOR_HIGH = 200;
  /** The limit of invalid samples that we read from the US sensor before assuming no obstacle. */
  public static final int INVALID_SAMPLE_LIMIT = 20;
  /** The poll sleep time, in milliseconds. */
  public static final int POLL_SLEEP_TIME = 50;

  // Hardware resources

  /** The ultrasonic sensor. */
  public static final EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S1);
  /** The left motor. */
  public static final RegulatedMotor leftMotor = Motor.A;
  /** The right motor. */
  public static final RegulatedMotor rightMotor = Motor.D;
  
  // Instance and class variables
  
  /** The distance remembered by the filter() method. */
  private static int prevDistance;
  /** The number of invalid samples seen by filter() so far. */
  private static int invalidSampleCount;

  // These arrays are used to avoid creating new ones at each iteration.
  /** Buffer (array) to store US samples. */
  private static float[] usData = new float[usSensor.sampleSize()];
  /** The left and right motor speeds, respectively. */
  private static int[] motorSpeeds = new int[2];
  
  /** The initial value of the error distance. */
  // public static int distError = 0;
  
  private static final int LEFT = 0;
  private static final int RIGHT = 1;

  /**
   * Main entry point.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    System.out.println("Starting Lab 1 demo");
    
    // Wait 1 second before moving to make sure everything has settled
    for (int i = 0; i < 10; i++) {
      performPhysicsStep();
    }
    
    // Need to define how many threads are synchronized to simulation steps
    setNumberOfParties(NUMBER_OF_THREADS);
    
    leftMotor.setSpeed(MOTOR_HIGH);
    rightMotor.setSpeed(MOTOR_HIGH);
    leftMotor.forward();
    rightMotor.forward();
    
    // Start the controller thread
    new Thread(() -> {
      while (true) {
        controller(readUsDistance(), motorSpeeds);
        leftMotor.setSpeed(motorSpeeds[LEFT]);
        rightMotor.setSpeed(motorSpeeds[RIGHT]);
        sleepFor(POLL_SLEEP_TIME);
      }
    }).start();
    
    // Main simulation loop, run steps until Webots indicates that we're done
    while (performPhysicsStep()) {
      // do nothing
    }

    leftMotor.stop();
    rightMotor.stop();
    
    System.exit(0);
  }
  
  /**
   * Process a movement based on the US distance passed in.
   * 
   * @param distance the distance in cm
   * @param motorSpeeds output parameter you need to set
   */
  public static void controller(int distance, int[] motorSpeeds) {
    int leftSpeed = MOTOR_HIGH;
    int rightSpeed = MOTOR_HIGH;
    
    // TODO Calculate the correct motor speeds and assign them to motorSpeeds like this
    
    motorSpeeds[LEFT] = leftSpeed;
    motorSpeeds[RIGHT] = rightSpeed;
  }
  
  /** Returns the filtered distance between the US sensor and an obstacle in cm. */
  public static int readUsDistance() {
    usSensor.fetchSample(usData, 0);
    // extract from buffer, cast to int, and filter
    return filter((int) (usData[0] * 100.0));
  }
  
  /**
   * Rudimentary filter - toss out invalid samples corresponding to null signal.
   * 
   * @param distance raw distance measured by the sensor in cm
   * @return the filtered distance in cm
   */
  static int filter(int distance) {
    if (distance >= MAX_SENSOR_DIST && invalidSampleCount < INVALID_SAMPLE_LIMIT) {
      // bad value, increment the filter value and return the distance remembered from before
      invalidSampleCount++;
      return prevDistance;
    } else {
      if (distance < MAX_SENSOR_DIST) {
        invalidSampleCount = 0; // reset filter and remember the input distance.
      }
      prevDistance = distance;
      return distance;
    }
  }

}
