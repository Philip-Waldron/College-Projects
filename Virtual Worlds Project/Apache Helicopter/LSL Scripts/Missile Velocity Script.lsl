default
{
    on_rez(integer start_param)
    {
        state operating;
    }
}

state operating
{
    state_entry()
    {
        llSetStatus(STATUS_PHYSICS, TRUE);
        llApplyImpulse(10*llRot2Left(llGetRot())*-1.0, 1);
    }

    land_collision(vector pos)
    {
        llSleep(0.2);
        llDie();
    }
}