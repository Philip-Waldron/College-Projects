;BEGIN FRAGMENT CODE - Do not edit anything between this and the end comment
;NEXT FRAGMENT INDEX 1
Scriptname TIF__0001F815 Extends TopicInfo Hidden

;BEGIN FRAGMENT Fragment_0
Function Fragment_0(ObjectReference akSpeakerRef)
Actor akSpeaker = akSpeakerRef as Actor
;BEGIN CODE
Game.GetPlayer().RemoveItem(Gold, 30)
Game.GetPlayer().AddSpell(SummonFASpell)
GetOwningQuest().SetStage(21)
(GetOwningQuest() as MG01QuestScript).NiryaSpellSold=1
;END CODE
EndFunction
;END FRAGMENT

;END FRAGMENT CODE - Do not edit anything between this and the begin comment

Spell Property SummonFASpell  Auto  

MiscObject Property Gold  Auto  
