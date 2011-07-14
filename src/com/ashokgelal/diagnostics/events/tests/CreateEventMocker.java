package com.ashokgelal.diagnostics.events.tests;
import com.ashokgelal.diagnostics.events.*;

public class CreateEventMocker {
	public RegisteredEventHandler<EventArgs> MyFakeEvent = new RegisteredEventHandler<EventArgs>();
	public RegisteredEventHandler<PersonInfoEventArgs> MyPersonInfoEvent = new RegisteredEventHandler<PersonInfoEventArgs>();
	public RegisteredEmptyArgsEventHandler MyEmptyEvent = new RegisteredEmptyArgsEventHandler();
	
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