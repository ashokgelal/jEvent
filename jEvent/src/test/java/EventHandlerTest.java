import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ashokgelal.jevent.EventArgs;
import com.ashokgelal.jevent.EventsRegistry;
import com.ashokgelal.jevent.IEventSubscriber;
import com.ashokgelal.jevent.SubscriptionToken;

public class EventHandlerTest implements IEventSubscriber<EventArgs> {
	private static EventMocker mocker;

	@Before
	public void setUp() throws Exception {
		mocker = new EventMocker();
	}

	@After
	public void tearDown() throws Exception {
		EventsRegistry.printAllRemainingSubscriptions(System.out);
	}

	@Test
	public void subscriberShouldReceiveEmptyArgs() {
		SubscriptionToken token = mocker.MyFakeEvent
				.subscribe(new IEventSubscriber<EventArgs>() {
					@Override
					public void handleEventNotification(Object sender,
							EventArgs args) {
						Assert.assertTrue(sender instanceof EventMocker);
					}
				});

		mocker.FireEventWithEmptyArgs();
		mocker.MyFakeEvent.unsubscribe(token);
	}

	@Test
	public void subscribingMultipleTimesToAnEventShouldHaveNoEffect() {
		SubscriptionToken token = mocker.MyEmptyEvent.subscribe(this);
		SubscriptionToken token1 = mocker.MyEmptyEvent.subscribe(this);
		Assert.assertEquals(token, token1);
		Assert.assertTrue(mocker.MyEmptyEvent.containsSubscriber(this));
		Assert.assertEquals(1, mocker.MyEmptyEvent.getSubscribersCount());
		mocker.MyEmptyEvent.unsubscribe(token);
		Assert.assertEquals(0, mocker.MyEmptyEvent.getSubscribersCount());
	}

	@Test
	public void subscriberShouldGetProperArgs() {
		SubscriptionToken token = mocker.MyPersonInfoEvent
				.subscribe(new IEventSubscriber<PersonInfoEventArgs>() {
					@Override
					public void handleEventNotification(Object sender,
							PersonInfoEventArgs args) {
						Assert.assertEquals("Foo", args.getName());
						Assert.assertEquals(12345, args.getId());
					}
				});
		mocker.MyPersonInfoEvent.unsubscribe(token);
		mocker.FireEventWithPersonInfo();
	}

	@Test
	public void testRaiseCounts() {
		SubscriptionToken token = mocker.MyEmptyEvent.subscribe(this);

		mocker.MyEmptyEvent.raise(this);
		Assert.assertEquals(1, mocker.MyEmptyEvent.getRaiseCount());
		mocker.MyEmptyEvent.raise(this);
		Assert.assertEquals(2, mocker.MyEmptyEvent.getRaiseCount());
		mocker.MyEmptyEvent.raise(this);
		Assert.assertEquals(3, mocker.MyEmptyEvent.getRaiseCount());

		mocker.MyEmptyEvent.unsubscribe(token);

		SubscriptionToken token1 = mocker.MyEmptyEvent
				.subscribe(new IEventSubscriber<EventArgs>() {
					public void handleEventNotification(Object sender,
							EventArgs args) {
					}
				});
		mocker.MyEmptyEvent.raise(this);
		mocker.MyEmptyEvent.raise(this);
		Assert.assertEquals(5, mocker.MyEmptyEvent.getRaiseCount());
		mocker.MyEmptyEvent.unsubscribe(token1);
	}

	@Test
	public void testEventName() {
		Assert.assertEquals("Fake Event", mocker.MyFakeEvent.getEventName());
	}

	@Test
	public void unsubscriptionTest() {
		SubscriptionToken token = mocker.MyEmptyEvent
				.subscribe(new IEventSubscriber<EventArgs>() {
					int count = 0;

					@Override
					public void handleEventNotification(Object sender,
							EventArgs args) {
						if (count > 0) {
							Assert.fail("Received notification after unsubscritpion");
						}
						mocker.MySingleEvent.raise(this, new Address("foo",
								"bar"));
						count++;
					}
				});
		mocker.MyEmptyEvent.raise(this);
		Assert.assertTrue(mocker.MyEmptyEvent.containsToken(token));
		mocker.MyEmptyEvent.unsubscribe(token);
		mocker.MyEmptyEvent.raise(this);
		Assert.assertFalse(mocker.MyEmptyEvent.containsToken(token));
	}

	@Test
	public void testNestedEventsRaise() {
		SubscriptionToken token = mocker.MyEmptyEvent
				.subscribe(new IEventSubscriber<EventArgs>() {

					@Override
					public void handleEventNotification(Object sender,
							EventArgs args) {
						mocker.MySingleEvent.raise(this, new Address("foo",
								"bar"));
					}
				});
		mocker.MyEmptyEvent.unsubscribe(token);
	}

	
	@Test
	public void testRemainingSubscriptionsCount(){
		Assert.assertEquals(0, EventsRegistry.getRemainingSubscriptionsCount("foo"));
		SubscriptionToken token = mocker.MyEmptyEvent.subscribe(this);
		mocker.MyEmptyEvent.subscribe(this);
		Assert.assertEquals(1, EventsRegistry.getRemainingSubscriptionsCount("Empty Event"));

		SubscriptionToken token1 = mocker.MyEmptyEvent
				.subscribe(new IEventSubscriber<EventArgs>() {
					public void handleEventNotification(Object sender,
							EventArgs args) {
					}
				});
		Assert.assertEquals(2, EventsRegistry.getRemainingSubscriptionsCount("Empty Event"));
		mocker.MyEmptyEvent.unsubscribe(token);
		Assert.assertEquals(1, EventsRegistry.getRemainingSubscriptionsCount("Empty Event"));
		mocker.MyEmptyEvent.unsubscribe(token1);
		Assert.assertEquals(0, EventsRegistry.getRemainingSubscriptionsCount("Empty Event"));
	}
	
	@Test
	public void testSubscribedEvents(){
		SubscriptionToken token = mocker.MyEmptyEvent.subscribe(this);
		SubscriptionToken token1 = mocker.MyEmptyEvent.subscribe(this);
		SubscriptionToken token2 = mocker.MyFakeEvent.subscribe(this);
		Assert.assertEquals(2, EventsRegistry.getSubscribedEvents(this).size());
		mocker.MyEmptyEvent.unsubscribe(token);
		mocker.MyEmptyEvent.unsubscribe(token1);
		mocker.MyFakeEvent.unsubscribe(token2);
	}
	
	@Override
	public void handleEventNotification(Object sender, EventArgs args) {
	}
}
