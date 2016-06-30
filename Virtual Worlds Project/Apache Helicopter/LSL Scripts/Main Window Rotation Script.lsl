vector center_of_rotation = <0.0, 1.10, 0.0>;
vector rot = <-90.0, 0.0, 0.0>;

rotate(vector rot)
{
    //creates a rotation constant
    rotation vRotArc = llEuler2Rot(rot * DEG_TO_RAD);

    //updates center_of_rotation to the current local rotation
    vector local_center_of_rotation = center_of_rotation * llGetLocalRot();

    //rotates center_of_rotation to get the motion caused by the desired rotation
    vector vPosRotOffset = local_center_of_rotation * vRotArc;

    //difference between the two - equal to the difference between the current
    //and the new position of the prim
    vector vPosOffsetDiff = local_center_of_rotation - vPosRotOffset;

    //the new position of the prim relative to the root prim
    vector vPosNew = llGetLocalPos() + vPosOffsetDiff;

    //the new rotation of the prim relative to the root prim
    rotation vRotNew = llGetLocalRot() * vRotArc;

    llSetPrimitiveParams( [PRIM_POSITION, vPosNew, PRIM_ROTATION, vRotNew/llGetRootRotation()] );
}

default //initially closed
{
    touch_start(integer num_detected)
    {
        if (llDetectedKey(0) == llGetOwner()) state open;
    }
}

state open
{
    state_entry()
    {
        rotate(rot);
    }

    touch_start(integer num_detected)
    {
        state closed;
    }
}

state closed
{
    state_entry()
    {
        rotate(-rot);
    }

    touch_start(integer num_detected)
    {
        state open;
    }
}