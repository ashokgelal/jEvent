package com.ashokgelal.diagnostics.events;

public class RegisteredEmptyArgsEventHandler extends RegisteredEventHandler<IEventSubscriber<EventArgs>, EventArgs> {
	public void raise(Object sender){
		for (IEventSubscriber<EventArgs> subscriber : subscribers) {
			subscriber.handleEventNotification(sender, EventArgs.Empty);
		}
	}
}