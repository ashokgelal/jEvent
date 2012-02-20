package com.ashokgelal.diagnostics.events;

public class RegisteredEmptyArgsEvent extends RegisteredEvent<EventArgs> {
	public void raise(Object sender){
		for (IEventSubscriber<EventArgs> subscriber : subscribers) {
			subscriber.handleEventNotification(sender, EventArgs.Empty);
		}
	}
}
