;BEGIN FRAGMENT CODE - Do not edit anything between this and the end comment
;NEXT FRAGMENT INDEX 2
Scriptname TIF__0200E0D4 Extends TopicInfo Hidden

;BEGIN FRAGMENT Fragment_1
Function Fragment_1(ObjectReference akSpeakerRef)
Actor akSpeaker = akSpeakerRef as Actor
;BEGIN CODE
getowningquest().setstage(40)
LASCrewmateQuest1.SetStage(0)
LASCrewmateQuest2.SetStage(0)
LASCrewmateQuest3.SetStage(10)
;END CODE
EndFunction
;END FRAGMENT

;END FRAGMENT CODE - Do not edit anything between this and the begin comment

Quest Property LASCrewmateQuest1  Auto  

Quest Property LASCrewmateQuest2  Auto  

Quest Property LASCrewmateQuest3  Auto  
