package com.ashokgelal.diagnostics.events;

public interface IEventSubscriber<E extends EventArgs>{
	public void handleEventNotification(Object sender, E args);
}