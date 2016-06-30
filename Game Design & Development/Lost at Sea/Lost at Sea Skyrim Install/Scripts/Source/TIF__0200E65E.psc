;BEGIN FRAGMENT CODE - Do not edit anything between this and the end comment
;NEXT FRAGMENT INDEX 1
Scriptname TIF__0200E65E Extends TopicInfo Hidden

;BEGIN FRAGMENT Fragment_0
Function Fragment_0(ObjectReference akSpeakerRef)
Actor akSpeaker = akSpeakerRef as Actor
;BEGIN CODE
GetOwningQuest().setstage(60)
G3_LaS_JoshameeGibbs.StartCombat(Game.GetPlayer())
LASDeeks.StartCombat(Game.GetPlayer())
LASBarbaros.StartCombat(Game.GetPlayer())
LASCraig.StartCombat(Game.GetPlayer())
LASDavy.StartCombat(Game.GetPlayer())
LASHook.StartCombat(Game.GetPlayer())
LASJones.StartCombat(Game.GetPlayer())
LASTret.StartCombat(Game.GetPlayer())
JoshBase.SetEssential(false)
;END CODE
EndFunction
;END FRAGMENT

;END FRAGMENT CODE - Do not edit anything between this and the begin comment

Actor Property LASDeeks  Auto  

Actor Property LASBarbaros  Auto  

Actor Property G3_LaS_JoshameeGibbs  Auto  

Actor Property LASCraig  Auto  

Actor Property LASDavy  Auto  

Actor Property LASHook  Auto  

Actor Property LASJones  Auto  

Actor Property LASTret  Auto  

ActorBase Property JoshBase  Auto  
