package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    private DcMotor fLDrive;
    private DcMotor fRDrive;
    private DcMotor bLDrive;
    private DcMotor bRDrive;
    private DcMotor inTake;
    private DcMotor lift;
    private DcMotor flywheel;
    private Servo ballreleasing;
    private Servo balltofly;
    private Servo pollenbar;
    //this is a useful comment

    static final double COUNTS_PER_MOTOR_REV = 537.7;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = -1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 4.09449;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    public Robot(HardwareMap hm) {
        this.fLDrive = hm.get(DcMotor.class, "fL Drive");
        this.fRDrive = hm.get(DcMotor.class, "fR Drive");
        this.bLDrive = hm.get(DcMotor.class, "bL Drive");
        this.bRDrive = hm.get(DcMotor.class, "bR Drive");
        this.inTake = hm.get(DcMotor.class, "in Take");
        this.lift = hm.get(DcMotor.class, "Lift");
        this.flywheel = hm.get(DcMotor.class, "fly wheel");

        this.ballreleasing = hm.get(Servo.class, "ball release");
        this.balltofly = hm.get(Servo.class, "ball to fly");
        this.pollenbar = hm.get(Servo.class,"pollen bar");


        this.fLDrive.setDirection(DcMotor.Direction.REVERSE);
        this.fRDrive.setDirection(DcMotor.Direction.FORWARD);
        this.bLDrive.setDirection(DcMotor.Direction.REVERSE);
        this.bRDrive.setDirection(DcMotor.Direction.FORWARD);
        this.inTake.setDirection(DcMotor.Direction.FORWARD);
        this.lift.setDirection(DcMotor.Direction.REVERSE);
        this.flywheel.setDirection(DcMotor.Direction.FORWARD);

    }

    public DcMotor getfLDrive() {
        return fLDrive;
    }

    public DcMotor getfRDrive() {
        return fRDrive;
    }

    public DcMotor getbLDrive() {
        return bLDrive;
    }

    public DcMotor getbRDrive() {
        return bRDrive;
    }

    public DcMotor getFlywheel() {
        return flywheel;
    }

    public DcMotor getInTake() {
        return inTake;
    }

    public DcMotor getLift() {
        return lift;
    }

    public Servo getBallreleasing() {
        return ballreleasing;
    }

    public Servo getBalltofly() {
        return balltofly;
    }
    public Servo getPollenbar(){
        return pollenbar;
    }


    /**
     * 312 is 537.7 ppr
     *
     * @param mode
     */
    public void setDriveMode(DcMotor.RunMode mode) {
        fLDrive.setMode(mode);
        fRDrive.setMode(mode);
        bLDrive.setMode(mode);
        bRDrive.setMode(mode);
    }

    public void setMotorMode(DcMotor.RunMode mode) {
        setDriveMode(mode);
        flywheel.setMode(mode);
        lift.setMode(mode);
        inTake.setMode(mode);

    }

    public void setPowerDrive(double fl, double fr, double bl, double br) {
        fLDrive.setPower(fl);
        fRDrive.setPower(fr);
        bLDrive.setPower(bl);
        bRDrive.setPower(br);
    }

    public void setPowerDrive(double d) {
        setPowerDrive(d, d, d, d);

    }

    public void setDrivePosition(int fl, int fr, int bl, int br) {
        fLDrive.setTargetPosition(fl);
        fRDrive.setTargetPosition(fr);
        bLDrive.setTargetPosition(bl);
        bRDrive.setTargetPosition(br);
    }

    public void setDrivePosition(int d) {
        setDrivePosition(d, d, d, d);
    }

    public void setAutoStraightMode(int d1, double p) {
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDrivePosition((int) (d1 * COUNTS_PER_INCH));
        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPowerDrive(p);
        while (bLDrive.isBusy() && bRDrive.isBusy() && fLDrive.isBusy() && fRDrive.isBusy()) {
            int i = 1;
        }
        setPowerDrive(0);
    }

    public void setAutoTurn(int d1, double p) {
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDrivePosition((int) (d1 * COUNTS_PER_INCH));
        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPowerDrive(p);
        while (bLDrive.isBusy() && bRDrive.isBusy() && fLDrive.isBusy() && fRDrive.isBusy()) {
            int i = 1;
        }
        setPowerDrive(0);
    }

    public void setAutoStrafe(int d1, double p) {
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDrivePosition((int) (d1 * COUNTS_PER_INCH), (int)(-d1 * COUNTS_PER_INCH),(int)(d1 * COUNTS_PER_INCH),(int)(-d1 * COUNTS_PER_INCH));
        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPowerDrive(p);
        while (bLDrive.isBusy() && bRDrive.isBusy() && fLDrive.isBusy() && fRDrive.isBusy()) {
            int i = 1;
        }
        setPowerDrive(0);
    }

}
