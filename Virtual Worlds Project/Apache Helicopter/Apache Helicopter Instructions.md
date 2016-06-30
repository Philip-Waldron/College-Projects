*INSTRUCTIONS FOR APACHE HELICOPTER*

*Firestorm OXP file types were used instead of XML files, as XML files did not work with this project build.
They must be imported through firestorm. The separation of some objects may seem unnecessary, but player permissions
are a recurring issue in Opensim preventing the natural join of some objects.*

To build the helicopter:
1. Import Apache Skeleton, Machine Gun, Chair, Missile and Bullet from the OXP Objects folder.
2. Import the Apache Body Collada file.
3. Position the Apache Skeleton so that it comes in line with the Apache Body.
4. Position the Machine Gun under the helicopter between the wheels.
5. Place two chairs in the cockpit, one on each pedestal.
6. Link all prims.
7. Leave the bullet and rocket aside.
8. All untextured objects should use the Apache Texture from the textures folder.
9. Place the sprite-particle-cloud texture in the Missile.

Script Placements:
1.  Place the Missile Velocity Script in the the Missile.
2.  Place the Smoke Script in the Missile.
  **Place the Missile in the vertical beam of each missile launcher under the wings of the helicopter**
3.  Place the Right Missile Launcher Script in the vertical beam of the right side missile launcher, and
    Place the Left Missile Launcher Script in the vertical beam of the left side missile launcher.
4.  Place the Bullet Script in the Bullet.
  **Place the Bullet in the tip of the barrel of the machine gun**
5.  Place the Machine Gun Firing Script in the tip of the barrel of the machine gun.
6.  In the cockpit, place the Left Launcher Button Script, Machine Gun Button Script and the
    Right Launcher Button Script respectively from left to right on the three buttons
    on the panel in front of the forward chair.
7.  Place the sitting script in the forward (co-pilot) chair in the cockpit.
8.  Place the Movement Script in the further back (pilot) chair in the cockpit.
9.  Place the Main Window Rotation Script in the top window and the large front window (Not the smaller one) of the cockpit.
10. Place the Right Window Rotation Script in the right window of the cockpit, and
	Place the Left Window Rotation Script in the left window of the cockpit.
11. Place the Main Rotation Script in one of the main rotary blades above the helicopter.
12. Place the Secondary Rotation Script in the other main rotary blades above the helicopter, as well as
    the two tail rotary blades.
	
	*EXCLAIMER*
We were unsure if the physics of the bullets or the missiles worked properly on the helicopter due to
the lag on the virtual worlds server, but these physics worked as intended before attaching to the helicopter.

The movement of the helicopter does not work due to problems with physics in OpenSim, but an example of working movement
may be viewed if you import the Donutcopter from the OXP Objects folder(or rez any object in the virtual world) and apply the
Movement Script to the object.