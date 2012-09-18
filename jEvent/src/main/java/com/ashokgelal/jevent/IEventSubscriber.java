package com.ashokgelal.jevent;

/**
 * Interface for subscribers that will be called when an event is raised.
 * @author Ashok Gelal
 *
 * @param <E> An argument that extends {@link EventArgs}
 */
public interface IEventSubscriber<E extends EventArgs>{
	
	/**
	 * A callback that is called when an event is raised.
	 * @param sender The object that raised the event.
	 * @param args The value for the event.
	 */
	public void handleEventNotification(Object sender, E args);
}