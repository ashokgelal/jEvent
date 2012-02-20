package com.ashokgelal.diagnostics.events.tests;
import com.ashokgelal.diagnostics.events.*;

public class CreateEventMocker {
	public RegisteredEvent<EventArgs> MyFakeEvent = new RegisteredEvent<EventArgs>();
	public RegisteredEvent<PersonInfoEventArgs> MyPersonInfoEvent = new RegisteredEvent<PersonInfoEventArgs>();
	public RegisteredEmptyArgsEvent MyEmptyEvent = new RegisteredEmptyArgsEvent();
	
	public void FireEventWithEmptyEventArgs(){
		MyFakeEvent.raise(this, EventArgs.Empty);
	}
	
	public void FireEventWithPersonInfoEventArgs(){
		MyPersonInfoEvent.raise(this, new PersonInfoEventArgs("Foo", 12345));
	}
	
	public void FireRegisteredEmptyArgs(){
		MyEmptyEvent.raise(this);
	}
}