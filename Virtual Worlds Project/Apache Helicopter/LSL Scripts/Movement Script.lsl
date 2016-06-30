// * Portions based on a public-domain flight script from Jack Digeridoo.
// * Portions based on hoverboard script by Linden Lab.
 
integer sit = FALSE;
integer listenTrack;
integer brake = TRUE;
 
float X_THRUST = 25;
float Z_THRUST = 25;
 
float xMotor;
float zMotor;
 
key agent;
key pilot;
 
vector SIT_POS = <0.0, 0.1, 0.0>;
vector SIT_ANGLE = < 0.0, 0.0, 90.0>;
rotation SIT_ROTATION;
vector CAM_OFFSET = <0.0, -30.0, 10.0>;
vector CAM_ANG = <0.0, -20.0, 90.0>;
 
listenState(integer on) // start/stop listen
{
    if (listenTrack)
    {
        llListenRemove(listenTrack);
        listenTrack = 0;
    }
    if (on) listenTrack = llListen(0, "", pilot, "");
}
 
help()
{
            llWhisper(0,"Welcome user, you may use these commands to fly");
            llWhisper(0,"Say START to begin moving.");
            llWhisper(0,"Say STOP to stop moving.");
            llWhisper(0,"PgUp/PgDn or E/C = hover up/down");
            llWhisper(0,"Arrow keys or WASD = forward, back, left, right");
            llWhisper(0,"Say HELP to display help.");
}

default
{
    state_entry()
    {
        llSetClickAction(CLICK_ACTION_SIT);                        //click action to sit
        llSetSitText("PILOT");
        SIT_ROTATION = llEuler2Rot(SIT_ANGLE * DEG_TO_RAD);
        llSitTarget(SIT_POS, SIT_ROTATION);
        llSetCameraEyeOffset(CAM_OFFSET);
        llSetCameraAtOffset(CAM_ANG);

        //SET VEHICLE PARAMETERS
        llSetVehicleType(VEHICLE_TYPE_AIRPLANE);

        llSetVehicleVectorParam( VEHICLE_LINEAR_FRICTION_TIMESCALE, <200, 20, 20> );

        //uniform angular friction
        llSetVehicleFloatParam( VEHICLE_ANGULAR_FRICTION_TIMESCALE, 2 );

        //linear motor
        llSetVehicleVectorParam( VEHICLE_LINEAR_MOTOR_DIRECTION, <0, 0, 0> );
        llSetVehicleFloatParam( VEHICLE_LINEAR_MOTOR_TIMESCALE, 2 );
        llSetVehicleFloatParam( VEHICLE_LINEAR_MOTOR_DECAY_TIMESCALE, 120 );

        //angular motor
        llSetVehicleVectorParam( VEHICLE_ANGULAR_MOTOR_DIRECTION, <0, 0, 0> );
        llSetVehicleFloatParam( VEHICLE_ANGULAR_MOTOR_TIMESCALE, 0 );
        llSetVehicleFloatParam( VEHICLE_ANGULAR_MOTOR_DECAY_TIMESCALE, .4);

        //hover
        llSetVehicleFloatParam( VEHICLE_HOVER_HEIGHT, 2 );
        llSetVehicleFloatParam( VEHICLE_HOVER_EFFICIENCY, 0 );
        llSetVehicleFloatParam( VEHICLE_HOVER_TIMESCALE, 10000 );
        llSetVehicleFloatParam( VEHICLE_BUOYANCY, 1.0 );

        //no linear deflection
        llSetVehicleFloatParam( VEHICLE_LINEAR_DEFLECTION_EFFICIENCY, 0 );
        llSetVehicleFloatParam( VEHICLE_LINEAR_DEFLECTION_TIMESCALE, 5 );

        //no angular deflection
        llSetVehicleFloatParam( VEHICLE_ANGULAR_DEFLECTION_EFFICIENCY, 0);
        llSetVehicleFloatParam( VEHICLE_ANGULAR_DEFLECTION_TIMESCALE, 5);

        //no vertical attractor
        llSetVehicleFloatParam( VEHICLE_VERTICAL_ATTRACTION_EFFICIENCY, 1 );
        llSetVehicleFloatParam( VEHICLE_VERTICAL_ATTRACTION_TIMESCALE, 1 );

        //banking
        llSetVehicleFloatParam( VEHICLE_BANKING_EFFICIENCY, 1);
        llSetVehicleFloatParam( VEHICLE_BANKING_MIX, .5);
        llSetVehicleFloatParam( VEHICLE_BANKING_TIMESCALE, 0.01);

        //default rotation of local frame
        llSetVehicleRotationParam( VEHICLE_REFERENCE_FRAME, <0,0,0,1>);

        //remove these flags
        llRemoveVehicleFlags(VEHICLE_FLAG_NO_DEFLECTION_UP
                              | VEHICLE_FLAG_HOVER_WATER_ONLY
                              | VEHICLE_FLAG_LIMIT_ROLL_ONLY
                              | VEHICLE_FLAG_HOVER_TERRAIN_ONLY
                              | VEHICLE_FLAG_HOVER_GLOBAL_HEIGHT
                              | VEHICLE_FLAG_HOVER_UP_ONLY
                              | VEHICLE_FLAG_LIMIT_MOTOR_UP);
 
    }
 
    //LISTEN FOR USER COMMANDS IN CHAT
    listen(integer channel, string name, key id, string message)
    {
        message = llToLower(message);
        if (message == "help")
        {
            help();
        }
     
        if (message == "stop")
        {
            brake = TRUE;
            llWhisper(0,"System shut down. You must say START to resume flight.");
            llSetStatus(STATUS_PHYSICS, FALSE);
        }
        else if (message == "start")
        {
                brake = FALSE;
                llWhisper(0,"System power up. You may begin flight. Say STOP to end flight.");
                llSetStatus(STATUS_PHYSICS, TRUE);  
        }
    }
 
    //DETECT SITTING/UNSITTING AND GIVE PERMISSIONS
    changed(integer change)
    {
       agent = llAvatarOnSitTarget();
       if(change & CHANGED_LINK)
       {
            if((agent == NULL_KEY) && (sit))
            {
               //
               //  Avatar gets off vehicle
               //
               llSetStatus(STATUS_PHYSICS, FALSE);
               //llStopAnimation("");
               llMessageLinked(LINK_SET, 0, "unseated", "");
               llStopSound();
               llReleaseControls();
               sit = FALSE;
               listenState(FALSE);
               llSay(120, "unseat");
            }
           else if ((agent != NULL_KEY) && (!sit))
           {
               // 
               // Avatar gets on vehicle
               //
               pilot = llAvatarOnSitTarget();
               sit = TRUE;
               llRequestPermissions(pilot, PERMISSION_TAKE_CONTROLS | PERMISSION_TRIGGER_ANIMATION);
               listenState(TRUE);
               llWhisper(0,"Welcome pilot " + llKey2Name(pilot) + ", say START to power up helicopter and STOP to power down helicopter. Say HELP to get controls.");          
               llMessageLinked(LINK_SET, 0, "seated", "");
            } 
        }
    }

   //CHECK PERMISSIONS AND TAKE CONTROLS
    run_time_permissions(integer perm)
    {
        if (perm & (PERMISSION_TAKE_CONTROLS))
        {           
            llTakeControls(CONTROL_UP | CONTROL_DOWN | CONTROL_FWD | CONTROL_BACK | CONTROL_RIGHT | CONTROL_LEFT | CONTROL_ROT_RIGHT | CONTROL_ROT_LEFT, TRUE, FALSE);
        }
        if (perm & PERMISSION_TRIGGER_ANIMATION)
        {
            llStopAnimation("sit");
        }
    }

    //FLIGHT CONTROLS    
    control(key id, integer level, integer edge)
    {
        vector angular_motor;
        integer motor_changed;
 
        if ((level & CONTROL_FWD) || (level & CONTROL_BACK))
        {
            if (edge & CONTROL_FWD) xMotor = X_THRUST;
            if (edge & CONTROL_BACK) xMotor = -X_THRUST;
        }
        else
        {                
            xMotor = 0;
        }

        if ((level & CONTROL_UP) || (level & CONTROL_DOWN))
        {
            if (level & CONTROL_UP)
            {                   
                zMotor = Z_THRUST;
            }  
            if (level & CONTROL_DOWN)
            {
                zMotor = -Z_THRUST;
            }
        }
        else
        {
            zMotor = 0;
        }           

        llSetVehicleVectorParam(VEHICLE_LINEAR_MOTOR_DIRECTION, <xMotor,0,zMotor>);

        if (level & CONTROL_RIGHT)
        {
            angular_motor.x = 1.5;
            angular_motor.y /= 8;
        }  
        if (level & CONTROL_LEFT)
        {
            angular_motor.x = -1.5;
            angular_motor.y /= 8;
        }

        if (level & CONTROL_ROT_RIGHT)
        {           
            angular_motor.x = 1.5;
            angular_motor.y /= 8;
        }

        if (level & CONTROL_ROT_LEFT)
        {
            angular_motor.x = -1.5;
            angular_motor.y /= 8;
        }

        llSetVehicleVectorParam(VEHICLE_ANGULAR_MOTOR_DIRECTION, angular_motor);
    }   
}