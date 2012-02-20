package com.ashokgelal.diagnostics.events;

import java.util.ArrayList;

public class RegisteredEvent<V extends EventArgs>{
	protected ArrayList<IEventSubscriber<V>> subscribers;
	public RegisteredEvent(){
		subscribers = new ArrayList<IEventSubscriber<V>>();
	}
	
	public void subscribe(IEventSubscriber<V> s){
		subscribers.add(s);
	}
	
	public void unsubscribe(IEventSubscriber<V> s){
		subscribers.remove(s);
	}
	
	public void raise(Object sender, V args){
		for (IEventSubscriber<V> subscriber : subscribers) {
			subscriber.handleEventNotification(sender, args);
		}
	}
}