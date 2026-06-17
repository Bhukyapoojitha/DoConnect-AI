package com.doconnect.design_patterns.observer; 

 

/** 

 * EventObserver Interface 

 * DESIGN PATTERN: Observer Pattern 

 * 

 * PURPOSE: All observers implement this. 

 * Called when events happen in the system. 

 */ 

public interface EventObserver { 

    void onEvent(String eventType, Object data); 

}