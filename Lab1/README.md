# Lab 1 - Wall Follower

**Code due: Immediately before your demo(s), Wednesday, September 16 at the latest**

**Report due: Thursday, September 17, 23:59 EDT (Montréal time)**

This is the repository that contains the required files for the wall following lab.
For lab objectives, demo and report requirements, and submission instructions, see
detailed instructions on MyCourses.

## Implementation details

Although you only need to implement one method, `controller()` in [`Lab1.java`](controllers/Lab1/Lab1.java),
you should still go over the other code to understand the program structure, which we
briefly outline below:

[`Lab1.java`](controllers/Lab1/Lab1.java) is the main entry point for your program.
The main method sets up the program and starts the
ultrasonic controller in a second thread. The main simulation runs steps until Webots indicates that we're done
(eg, when we stop the simulation using the interface), then stops.

The ultrasonic controller controls the robot's movements based on data from the ultrasonic sensor.
It is run in a second thread, where an infinite loop continuously does the following:
  * Reads the distance from the ultrasonic sensor and calls your `controller()` method, which
    calculates and updates the motor speeds based on that distance. **This is what you need to implement.**
  * Sets the motor speeds to what you just calculated. This already done in `setMotorSpeeds()`,
    so you should **not** set them again in the `controller()` method.
  * Sleeps for a predefined time period `POLL_SLEEP_TIME`, (which you can experiment with).
  


