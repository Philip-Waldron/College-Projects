Scriptname RationScript extends ObjectReference  

Quest Property G3_LaS_WarehouseRations  Auto  
Event OnContainerChanged(ObjectReference newContainer, ObjectReference oldContainer)
	if (newContainer == Game.GetPlayer())
		G3_LaS_WarehouseRations.SetStage(10)
	endif
EndEvent