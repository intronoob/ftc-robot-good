package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Handles all robot movement logic (mecanum drive + encoders).
 * This class does NOT extend OpMode.
 */
public class RobotMovementController {

    // Drive motors
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // --- Robot constants ---
    private static final double STEPS_PER_ROT = 530.0;
    private static final double WHEEL_DIAMETER_IN = 4.0;
    private static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_IN;
    private static final double DIST_PER_STEP = WHEEL_CIRCUMFERENCE / STEPS_PER_ROT;

    private static final double DEFAULT_MOVE_POWER = 0.5;

    /**
     * Constructor
     * Maps motors and sets directions
     */
    public RobotMovementController(HardwareMap hardwareMap) {

        frontLeft  = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft   = hardwareMap.get(DcMotor.class, "back_left");
        backRight  = hardwareMap.get(DcMotor.class, "back_right");

        // Motor directions (adjust if wiring changes)
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);

        // Brake when power = 0
        setBrakeMode(true);

        // Reset encoders
        resetEncoders();
    }

    /**
     * Sets BRAKE or FLOAT mode for all drive motors
     */
    public void setBrakeMode(boolean brake) {
        DcMotor.ZeroPowerBehavior behavior =
                brake ? DcMotor.ZeroPowerBehavior.BRAKE
                        : DcMotor.ZeroPowerBehavior.FLOAT;

        frontLeft.setZeroPowerBehavior(behavior);
        frontRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
    }

    /**
     * Stops all drive motors
     */
    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    /**
     * Resets encoders and puts motors in encoder mode
     */
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Standard mecanum drive (teleop or timed auto)
     * @param drive  forward/backward
     * @param strafe left/right
     * @param rotate rotation
     */
    public void drive(double drive, double strafe, double rotate) {

        double fl = (drive + strafe - rotate);
        double fr = (drive - strafe + rotate);
        double bl = (drive - strafe - rotate);
        double br = (drive + strafe + rotate);

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);
    }

    /**
     * Moves the robot using encoders (X/Y inches).
     * X = forward/back
     * Y = strafe
     */
    public void moveInches(double yInches, double xInches, double power) {

        int flTarget = (int) ((yInches - xInches) / DIST_PER_STEP);
        int frTarget = (int) ((yInches + xInches) / DIST_PER_STEP);
        int blTarget = (int) ((yInches + xInches) / DIST_PER_STEP);
        int brTarget = (int) ((yInches - xInches) / DIST_PER_STEP);

        setTargetPositions(flTarget, frTarget, blTarget, brTarget);

        setRunToPositionMode();
        setAllPower(Math.abs(power));
        while(isBusy());
        setAllPower(0);
        resetEncoders();
    }

    /**
     * Returns true while any motor is still moving
     */
    public boolean isBusy() {
        return frontLeft.isBusy()
                || frontRight.isBusy()
                || backLeft.isBusy()
                || backRight.isBusy();
    }

    /**
     * Sets target positions for all motors
     */
    private void setTargetPositions(int fl, int fr, int bl, int br) {
        frontLeft.setTargetPosition(fl);
        frontRight.setTargetPosition(fr);
        backLeft.setTargetPosition(bl);
        backRight.setTargetPosition(br);
    }

    /**
     * Enables RUN_TO_POSITION mode
     */
    private void setRunToPositionMode() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets power to all drive motors
     */
    private void setAllPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
}
