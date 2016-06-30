float counter = 4.0;
float gap = 1.0;
integer listen_handle;
default
{

    state_entry()
    {
        counter = 4.0;
        listen_handle = llListen(0,"",NULL_KEY,"");
        llTargetOmega(llRot2Up(llGetLocalRot()), 0, 0);
    }

    listen(integer channel, string name, key id, string message)
    {
        message = llToLower(message);
        if (message == "start")
        {
            state initiateTimer;
        }

    }
}

state initiateTimer
{
    state_entry()
    {
        llSetTimerEvent(1.0);
    }
    
        timer()
    {
        counter = counter - gap;
        if(counter > 0)
        {
            llSay(0, (string)counter+"..."); 
        }
        if(counter == 0)
        {
            llSay(0, "Starting!");
            state spin;
        }
    }
}
state spin
{
       state_entry()
       {
           listen_handle = llListen(0,"",NULL_KEY,"");
           llTargetOmega(llRot2Up(llGetLocalRot()), 10, 1.0);
       }
       
       listen(integer channel, string name, key id, string message)
        {
            message = llToLower(message);
            if(message == "stop")
            {
                state default;
            }
        }
       
}