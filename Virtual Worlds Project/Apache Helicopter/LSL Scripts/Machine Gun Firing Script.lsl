vector relativePosOffset = <0.0, -0.2, 0.0>;
rotation relativeRot = <0.0, 0.0, 0.00, 0.250>; // Rotated to be in line with prim
integer startParam = 1;
integer count = 0;
integer listen_handle;
 
make_particles()
{
    llParticleSystem([
        PSYS_SRC_PATTERN, PSYS_SRC_PATTERN_EXPLODE,

        PSYS_SRC_MAX_AGE, 0.,
        PSYS_PART_MAX_AGE, 1.,

        PSYS_SRC_BURST_RATE, 0.01,
        PSYS_SRC_BURST_PART_COUNT, 500,

        PSYS_SRC_BURST_RADIUS, 0.01,
        PSYS_SRC_BURST_SPEED_MIN, 1.,
        PSYS_SRC_BURST_SPEED_MAX, 2.,
        PSYS_SRC_ACCEL, <0.0,0.0,-1.0>,

        PSYS_PART_START_COLOR, <1.000, 0.863, 0.000>,
        PSYS_PART_END_COLOR, <1.000, 0.863, 0.000>,

        PSYS_PART_START_ALPHA, 0.9,
        PSYS_PART_END_ALPHA, 0.0,

        PSYS_PART_START_SCALE, <.05,.1,0>,
        PSYS_PART_END_SCALE, <.01,.1,0>,

        PSYS_PART_FLAGS,
            PSYS_PART_EMISSIVE_MASK |
            PSYS_PART_INTERP_COLOR_MASK |
            PSYS_PART_INTERP_SCALE_MASK |
            PSYS_PART_FOLLOW_VELOCITY_MASK |
            PSYS_PART_WIND_MASK
        ]);
}
 
default
{
    state_entry()
    {
        listen_handle = llListen(3027, "", NULL_KEY, "");
    }

    listen(integer channel, string name, key id, string message)
    {
        list words = llParseString2List(message, [" ", ","], [""]);
        if (llList2String(words, 0) == "Fire")
        {
            do
            {
                vector myPos = llGetPos();
                vector rezPos = myPos+relativePosOffset;
                rotation myRot = llGetRot();
                rotation rezRot = relativeRot*myRot;
                llRezAtRoot("Bullet", rezPos, ZERO_VECTOR, rezRot, startParam);
                llSleep(2.0);
                count = count + 1;
            }
            while (count < 50);
            llParticleSystem([]);
            count = 0;
            llResetScript();
        }
    }
}