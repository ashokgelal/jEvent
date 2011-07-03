package com.ashokgelal.diagnostics.events.tests;
import com.ashokgelal.diagnostics.events.EventArgs;
import com.ashokgelal.diagnostics.events.IEventSubscriber;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventHandlerTests implements IEventSubscriber<EventArgs> {

	private static CreateEventMocker mocker;
	@Before
	public void setUp() throws Exception {
		mocker = new CreateEventMocker();
	}
	
	@After
	public void tearDown() throws Exception {
		mocker.MyFakeEvent.unsubscribe(this);
	}
	
	@Override
	public void handleEventNotification(Object sender, EventArgs args) {
		Assert.fail("I wasn't registered to receive this event");
	}
	
	@Test
	public void SubscriberShouldReceiveEmptyArgs(){
		// there is no way to unsubscribe this
		mocker.MyFakeEvent.subscribe(new IEventSubscriber<EventArgs>() {
			@Override
			public void handleEventNotification(Object sender, EventArgs args) {
				Assert.assertTrue(sender instanceof CreateEventMocker);
			}
		});
		mocker.FireEventWithEmptyEventArgs();
	}
	
	@Test
	public void SubscriberShouldReceivePersonInfoArgs(){
		IEventSubscriber<PersonInfoEventArgs> eventHandler = new PersonInfoArgsEventHandler();
		mocker.MyPersonInfoEvent.subscribe(eventHandler);
		mocker.FireEventWithPersonInfoEventArgs();
		mocker.MyPersonInfoEvent.unsubscribe(eventHandler);
	}
	
	@Test
	public void SubscriberShouldGetProperArgs(){
		mocker.MyPersonInfoEvent.subscribe(new IEventSubscriber<PersonInfoEventArgs>() {
			@Override
			public void handleEventNotification(Object sender, PersonInfoEventArgs args) {
				Assert.assertEquals("Foo", args.getName());
				Assert.assertEquals(12345, args.getId());
			}
		});
		mocker.FireEventWithPersonInfoEventArgs();
	}
	
	private class PersonInfoArgsEventHandler implements IEventSubscriber<PersonInfoEventArgs>{
		@Override
		public void handleEventNotification(Object sender, PersonInfoEventArgs args) {
			Assert.assertTrue(args instanceof PersonInfoEventArgs);
		}
	}
}
