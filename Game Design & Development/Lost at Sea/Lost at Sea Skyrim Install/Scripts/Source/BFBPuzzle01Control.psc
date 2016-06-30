scriptName BFBPuzzle01Control extends ObjectReference
{
- This script lives on the lever that controls each of the pillars
- Each pillar should have its linkedRef point to the lever that this script is on
}


import debug
import utility

bool property puzzleSolved auto hidden
bool property doorOpened auto hidden
int property numPillarsSolved auto hidden

objectReference property puzzleDoorActivator auto
{The Parent Activator Dummy Ref for Door} 

int property pillarCount auto
{Number of Puzzle Pillars}

objectReference property refActOnSuccess01 auto
{This ref is activated when puzzle is solved and lever is pulled}

objectReference property refActOnFailure01 auto
{This ref is activated when puzzle is not solved and lever is pulled}

objectReference property refActOnFailure02 auto
{This ref is activated when puzzle is not solved and lever is pulled}

objectReference property refActOnFailure03 auto
{This ref is activated when puzzle is not solved and lever is pulled}

objectReference property refActOnFailure04 auto
{This ref is activated when puzzle is not solved and lever is pulled}

Action property animAction auto
{This is the action the idle manager is listening for}

;*****************************************

Function doorOrDarts()
	;if the puzzle is solved, activate the refActonSuccess01
	;else if the puzzle is not solved, activate the refActOnFailures (the dart traps
	;in the case of BleakFallsBarrow01)
	if (numPillarsSolved == PillarCount)
		puzzleSolved = true
		refActOnSuccess01.activate(puzzleDoorActivator)
		int openState = refActOnSuccess01.getOpenState()
		if (openState == 1 || openState == 2)
			doorOpened = true
		else
			doorOpened = false
		endif
	else
		wait(1)
		puzzleSolved = false
		refActOnFailure01.activate (self as objectReference)
		refActOnFailure02.activate (self as objectReference)
		refActOnFailure03.activate (self as objectReference)
		refActOnFailure04.activate (self as objectReference)
	endif
endFunction

;*****************************************

Auto STATE pulledPosition
	EVENT onActivate (objectReference triggerRef)
		Actor actorRef = triggerRef as Actor
		gotoState("busy")
		if (actorRef == game.GetPlayer())
			doorOrDarts()
			playAnimationandWait("FullPush","FullPushedUp")
		else
			doorOrDarts()
		endif
		gotoState("pushedPosition")
	endEVENT
endState

;*****************************************

STATE pushedPosition
	EVENT onActivate (objectReference triggerRef)
		gotoState("busy")
		Actor actorRef = triggerRef as Actor
		if (actorRef == game.GetPlayer())
			doorOrDarts()
			playAnimationandWait("FullPull","FullPulledDown")
		else
			doorOrDarts()
		endif
		gotoState("pulledPosition")
	endEVENT
endState

;*****************************************

STATE busy
	; This is the state when I'm busy animating
		EVENT onActivate (objectReference triggerRef)
			;do nothing
		endEVENT
endState

;*****************************************