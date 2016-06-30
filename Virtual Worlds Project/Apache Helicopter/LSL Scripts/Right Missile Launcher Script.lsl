vector relativePosOffset = <0.0, 0.0, -1.0>;
rotation relativeRot = <0.707107, 0.707107, 0.707107, 0.707107>; // Rotated to be in line with prim
integer startParam = 10;
integer listen_handle;
 
default
{
    state_entry()
    {
        listen_handle = llListen(3026, "", NULL_KEY, "");
    }

    listen(integer channel, string name, key id, string message)
    {
        list words = llParseString2List(message, [" ", ","], [""]);
        if (llList2String(words, 0) == "Fire")
        {
            vector myPos = llGetPos();
            vector rezPos = myPos+relativePosOffset;
            rotation myRot = llGetRot();
            rotation rezRot = relativeRot*myRot;
            llRezAtRoot("Missile", rezPos, ZERO_VECTOR, rezRot, startParam);
            llSleep(2.0);
        }
    }
}