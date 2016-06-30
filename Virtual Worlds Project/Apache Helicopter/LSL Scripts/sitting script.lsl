//GLOBAL VARIABLES
string animation = "ANIMATIONNAME";              //change to animation name
vector sittarget = < 0.0, 0.3, -0.2>;              //adjust sittarget offset
vector sitangle = < 0.0, 0.0, 90.0>;               //adjust sittarget rotation in degrees

//WHO CAN USE THIS?  ONLY OWNER?
integer only_owner = 0;                         //toggle access: 1 = only owner, 0 = anyone

integer OBJHIDE_ON = 0;                         //toggle object hide (poseball action): 1 = on, 0 = off


//OTHER GLOBAL VARIABLE DECLARATIONS
rotation sitrotation;
key owner;
key sitter = NULL_KEY;
integer SITTING;
integer LN;
integer LS;

//USER-DEFINED FUNCTION
integer test_sit() {
    if(LS == 1) {
        if(llAvatarOnLinkSitTarget(LN) != NULL_KEY) return 1;
        else return 0;
    }
    else if(LS == 0) {
        if(llAvatarOnSitTarget() != NULL_KEY) return 1;
        else return 0;
    }
    else return 0;
}

//START HERE
default {
    state_entry() {
        SITTING = 0;
        if(only_owner == 1) owner = llGetOwner();
        if(llGetNumberOfPrims() > 1) {
            LN = llGetLinkNumber();
            LS = 1;
        }
        else {
            LN = LINK_SET;
            LS = 0;
        }
        sitrotation = llEuler2Rot(sitangle * DEG_TO_RAD);          //deg to rad & --> rot
        llSitTarget(sittarget,sitrotation);                        //set sittarget
        llSetClickAction(CLICK_ACTION_SIT);                        //click action to sit
        if(SITTXT_ON) llSetSitText(SITTEXT);                       //set sit menu text
        if(OBJHIDE_ON) llSetLinkAlpha(LN,1.0,ALL_SIDES);           //show object
    }
    
    on_rez(integer num) {
        llResetScript();
    }

    changed(integer change) {
        if(change & CHANGED_LINK) {                                //links changed!
            if(SITTING == 0 && LS == 1) sitter = llAvatarOnLinkSitTarget(LN);
            if(SITTING == 0 && LS == 0) sitter = llAvatarOnSitTarget();
            if(only_owner == 1 && sitter != owner && sitter != NULL_KEY) {
                llUnSit(sitter);
                llInstantMessage(sitter,"You are not permitted to sit here.");
                sitter = NULL_KEY;
            }
            else if(SITTING == 0 && sitter != NULL_KEY) {          //someone sitting?
                SITTING = 1;
                llRequestPermissions(sitter,PERMISSION_TRIGGER_ANIMATION);
            }
            else if(SITTING == 1 && test_sit() == 0) {             //I think someone stood up!
                if(llGetPermissions() & PERMISSION_TRIGGER_ANIMATION) llStopAnimation(animation);
                if(OBJHIDE_ON) llSetLinkAlpha(LN,1.0,ALL_SIDES);   //show object
                SITTING = 0;
                sitter = NULL_KEY;
            }
        }
    }

    run_time_permissions(integer perm) {
        if(perm & PERMISSION_TRIGGER_ANIMATION) {
            if(OBJHIDE_ON) llSetLinkAlpha(LN,0.0,ALL_SIDES);       //hide object
            llStopAnimation("sit");                                //stop normal sit
            llStartAnimation(animation);                           //and use this animation
        }
    }
}