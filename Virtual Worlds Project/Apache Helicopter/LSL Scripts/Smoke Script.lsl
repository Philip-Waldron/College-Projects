default
{
    state_entry()
    {
        llParticleSystem([
        PSYS_PART_FLAGS ,
        PSYS_PART_WIND_MASK |          //Particles are moved by wind
        PSYS_PART_INTERP_COLOR_MASK |  //Colors fade from start to end
        PSYS_PART_INTERP_SCALE_MASK |  //Scale fades from beginning to end
        PSYS_PART_EMISSIVE_MASK |  //Particles follow the emitter
        PSYS_PART_FOLLOW_VELOCITY_MASK |//Particles are created at the velocity of the emitter
        PSYS_PART_EMISSIVE_MASK,       //Particles are self-lit (glow)

        PSYS_SRC_PATTERN,           PSYS_SRC_PATTERN_ANGLE_CONE,
        PSYS_SRC_TEXTURE,           "sprite-particle-cloud",        //UUID of the desired particle texture, or inventory name
        PSYS_SRC_MAX_AGE,           0.0,            //Time, in seconds, for particles to be emitted. 0 = forever
        PSYS_PART_MAX_AGE,          5.0,            //Lifetime, in seconds, that a particle lasts
        PSYS_SRC_BURST_RATE,        0.1,            //How long, in seconds, between each emission
        PSYS_SRC_BURST_PART_COUNT,  20,              //Number of particles per emission
        PSYS_SRC_BURST_RADIUS,      0.5,           //Radius of emission
        PSYS_SRC_BURST_SPEED_MIN,   .4,             //Minimum speed of an emitted particle
        PSYS_SRC_BURST_SPEED_MAX,   .5,             //Maximum speed of an emitted particle
        PSYS_SRC_ACCEL,             <0.0,0,.05>,    //Acceleration of particles each second
        PSYS_PART_START_COLOR,      <1.0,1.0,1.0>,  //Starting RGB color
        PSYS_PART_END_COLOR,        <1.0,1.0,1.0>,  //Ending RGB color, if INTERP_COLOR_MASK is on
        PSYS_PART_START_ALPHA,      0.9,            //Starting transparency, 1 is opaque, 0 is transparent.
        PSYS_PART_END_ALPHA,        0.0,            //Ending transparency
        PSYS_PART_START_SCALE,      <.25,.25,.25>,  //Starting particle size
        PSYS_PART_END_SCALE,        <.75,.75,.75>,  //Ending particle size, if INTERP_SCALE_MASK is on
        PSYS_SRC_ANGLE_BEGIN,       0 * DEG_TO_RAD, //Inner angle for ANGLE patterns
        PSYS_SRC_ANGLE_END,         45 * DEG_TO_RAD, //Outer angle for ANGLE patterns
        PSYS_SRC_OMEGA,             <0.0,0.0,0.0> //Rotation of ANGLE patterns, similar to llTargetOmega()
    ]);
    }
}