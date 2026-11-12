/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Iterative OpMode")

public class TeleOp extends OpMode
{

    private Robot robot;
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private boolean toggle = false;
    private boolean toggle2 = false;
    private boolean toggle3 = false;
    private boolean toggle4 = false;
    private boolean toggle5 = false;
    private boolean toggle6 = false;
    private boolean toggle7 = false;
    private boolean toggle8 = false;
    private boolean gate = false;
    private int liftPos = 0;
    private int liftDown = -10;
    private int liftup = 10;

    private double driveslow = .7;

    enum Liftstate {
        IDLE, UP, DOWN, INCREMENTUP, INCREMENTDOWN, RESET
    }

    Liftstate status = Liftstate.IDLE;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        robot = new Robot(hardwareMap);

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        robot.setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.setDriveMode((DcMotor.RunMode.RUN_USING_ENCODER));
        robot.getLift().setTargetPosition(liftPos);
        robot.getFlywheel().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.getInTake().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.getBalltofly().setPosition(.3);
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry

        double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1) ;
        double frontLeftPower = driveslow * (y + x + rx) / denominator;
        double backLeftPower = driveslow * (y + x - rx) / denominator;
        double frontRightPower = driveslow * (y - x - rx) / denominator;
        double backRightPower = driveslow * (y - x + rx) / denominator;
        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.
        robot.setPowerDrive(frontLeftPower,frontRightPower,backLeftPower,backRightPower);
        // POV Mode uses left stick to go forward, and right stick to turn.

        double flyWheelstick = gamepad2.right_trigger;
        robot.getFlywheel().setPower(0.8 * Range.clip(-flyWheelstick,-1.0,1.0)); // * Range.clip(flyWheelstick,-1.0,1.0)
        double intakestick = -gamepad2.left_trigger;


        if (gamepad2.right_bumper) {
            robot.getInTake().setPower(1);
        }
        else {
            robot.getInTake().setPower(Range.clip(intakestick,-1.0,1.0));
        }


        robot.getLift().setPower(.5);



        if (gamepad2.a) {
            toggle = true;
        }
        else {
            if (toggle) {
                status = Liftstate.UP;
                toggle = false;
            }
        }

        if (gamepad2.b) {
            toggle2 = true;
        }
        else {
            if (toggle2) {
                status = Liftstate.DOWN;
                toggle2 = false;
            }
        }

        if (gamepad2.y) {
            toggle3 = true;
        }
        else {
            if (toggle3) {
                status = Liftstate.RESET;
                toggle3 = false;
            }
        }

        if (gamepad2.left_bumper) {
            robot.getBalltofly().setPosition(0.8);
        }
        else {
            robot.getBalltofly().setPosition(0.3);
        }

        if (gamepad2.dpad_up) {
            toggle6 = true;
        }
        else {
            if (toggle6) {
                gate = !gate;
                toggle6 = false;
            }
        }

        if (gate) {
            robot.getPollenbar().setPosition(-1);
        }
        else {
            robot.getPollenbar().setPosition(0.1);
        }
        if (gamepad2.x) {
            robot.getBallreleasing().setPosition(-1);
        }
        else {
            robot.getBallreleasing().setPosition(0);
        }


        switch (status) {
            case UP: //a
                if (liftPos < 500)
                    liftPos = liftPos + 1350;//188195;
                status = Liftstate.IDLE;
                break;
            case DOWN: //b
                liftPos = 0;
                status = Liftstate.IDLE;
            case INCREMENTUP:
                liftPos = liftPos + 10;
                status = Liftstate.IDLE;
                break;
            case INCREMENTDOWN:
                liftPos = liftPos - 10;
                status = Liftstate.IDLE;
                break;
            case RESET: //y
                robot.getLift().setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
                robot.getLift().setMode((DcMotor.RunMode.RUN_TO_POSITION));
                break;
            case IDLE:
                break;
            default:
                /*
                try {
                    Liftstate test = status;
                }
                catch (NullPointerException e) {
                    System.out.println("Defult can't equal 0:" + e.getMessage());
                }
                }

                 */
                break;

        }
        /*

        robot.getLift().setPower(.5);

        ***********************************************/



        robot.getLift().setTargetPosition(liftPos);
       // if (Math.abs(1350 - robot.getLift().getCurrentPosition())  )

        if (gamepad2.x) {
            toggle4 = true;
        }
        else {
            if (toggle4 == true) {
                toggle5 = !toggle5;
                toggle4 = false;
            }
        }

        if (toggle5) {
            robot.getBallreleasing().setPosition(1);
        }
        else {
            robot.getBallreleasing().setPosition(0);
        }

        telemetry.addData("lift position: ", liftPos);
        telemetry.addData("shoot speed: ", 0.65 * Range.clip(flyWheelstick,-1.0,1.0));


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
