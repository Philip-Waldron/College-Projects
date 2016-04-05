;BEGIN FRAGMENT CODE - Do not edit anything between this and the end comment
;NEXT FRAGMENT INDEX 1
Scriptname TIF__0200CF7C Extends TopicInfo Hidden

;BEGIN FRAGMENT Fragment_0
Function Fragment_0(ObjectReference akSpeakerRef)
Actor akSpeaker = akSpeakerRef as Actor
;BEGIN CODE
Utility.Wait(1.0)
GetOwningQuest().setstage(30)
Game.FadeOutGame(false, true, 3.0, 2.0)
Game.GetPlayer().MoveTo(ShipTeleport)
G3_LaS_Message.Show()
;END CODE
EndFunction
;END FRAGMENT

;END FRAGMENT CODE - Do not edit anything between this and the begin comment

ObjectReference Property ShipTeleport  Auto  

Message Property G3_LaS_Message  Auto  
