package com.ashokgelal.diagnostics.events;

import java.util.ArrayList;

public class RegisteredEventHandler<S extends IEventSubscriber<V>, V extends EventArgs>{
	private ArrayList<S> subscribers;
	public RegisteredEventHandler(){
		subscribers = new ArrayList<S>();
	}
	
	public void subscribe(S s){
		subscribers.add(s);
	}
	
	public void unsubscribe(S s){
		subscribers.remove(s);
	}
	
	public void raise(Object sender, V args){
		for (S subscriber : subscribers) {
			subscriber.handleEventNotification(sender, args);
		}
	}
}