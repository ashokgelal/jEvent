package com.ashokgelal.jevent;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A generic event handler based on GOF's observer pattern. A subscriber can
 * subscribe to this event to be notified when someone fires up this event. The
 * subscriber can then unsubscribe if it wants to be not notified again.
 * Subscribing to same event multiple times doesn't have any effect as only one
 * subscriber will be added to a list of subscribers, and, thus, will only be
 * notified once.
 * 
 * @author Ashok Gelal
 * 
 * @param <V>
 *            An instance of EventArgs
 */
public class GenericEventHandler<V extends EventArgs> {
	protected ConcurrentHashMap<SubscriptionToken, IEventSubscriber<V>> subscribers;
	private String eventName;
	private int raiseCount;

	public GenericEventHandler() {
		this("<no name>");
	}

	public GenericEventHandler(String name) {
		subscribers = new ConcurrentHashMap<SubscriptionToken, IEventSubscriber<V>>();
		eventName = name;
	}

	/**
	 * Subscribes the given event subscriber to this event. If the subscriber is
	 * already registered to be notified for this event, it won't be added to
	 * the subscribers' list and the old token is returned.
	 * 
	 * @param s
	 *            Subscriber to be added to the list of subscriber which will be
	 *            notified when this event is raised.
	 * @return A unique {@link SubscriptionToken} for a new subscriber to this
	 *         event. If the subscriber is already added to the list, the old
	 *         token is returned.
	 */
	public synchronized SubscriptionToken subscribe(IEventSubscriber<V> s) {
		SubscriptionToken token = getKeyByValue(subscribers, s);
		if (token == null) {
			token = new SubscriptionToken();
			subscribers.put(token, s);
			EventsRegistry.addEventSubscriber(eventName, s);
		}
		return token;
	}

	/**
	 * Unregisters the given event subscriber from the list of subscribers.
	 * 
	 * @param s
	 *            The subscriber to be unregistered from the list of
	 *            subscribers.
	 */
	public synchronized void unsubscribe(IEventSubscriber<V> s) {
		unsubscribe(getKeyByValue(subscribers, s));
	}

	/**
	 * Unregisters the given token from the list of subscribers.
	 * 
	 * @param token
	 *            The subscription token to be unregistered from the list of
	 *            subscribers.
	 */
	public synchronized void unsubscribe(SubscriptionToken token) {
		if (token == null)
			return;
		IEventSubscriber<V> subscriber = subscribers.remove(token);
		EventsRegistry.removeEventSubscriber(eventName, subscriber);
	}

	/**
	 * Notifies all the subscribers registered for this event.
	 * 
	 * @param sender
	 *            The sender of the event.
	 * @param args
	 *            The arguments for the event.
	 */
	public synchronized void raise(Object sender, V args) {
		for (IEventSubscriber<V> subscriber : subscribers.values()) {
			subscriber.handleEventNotification(sender, args);
		}
		raiseCount++;
	}

	/**
	 * A static helper that returns a key for the given value from the given
	 * map. This only returns the first key and thus it implicitly assumes that
	 * there is a 1:1 relationship between a key and a value.
	 * 
	 * @param map
	 *            Map to be looked into.
	 * @param value
	 *            The value to be searched for.
	 * @return A key for a given value in a map, if exists. Otherwise, a null.
	 */
	private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Returns the name of this event. Useful for debugging.
	 * @return A String that represents the name of this event.
	 */
	public String getEventName() {
		return eventName;
	}
	
	/**
	 * Returns the number of times this event has been raised.
	 * @return An integer indicating the total number of times this event is raised.
	 */
	public int getRaiseCount(){
		return raiseCount;
	}
	
	/**
	 * Returns true if the list of subscribers contain the given subscriber.
	 * @param subscriber The subscriber to be checked.
	 * @return True if the list contains the subscriber, otherwise false.
	 */
	public boolean containsSubscriber(IEventSubscriber<V> subscriber) {
		return subscribers.containsValue(subscriber);
	}
	
	/**
	 * Returns true if the list of subscribers contain the given token key.
	 * @param token The token to be checked.
	 * @return True if the list contains the token key, otherwise false.
	 */
	public boolean containsToken(SubscriptionToken token) {
		return subscribers.containsKey(token);
	}
	
	/**
	 * Returns the total no. of subscribers for this event.
	 * @return An integer indicating the total no. of subscribers for this event.
	 */
	public int getSubscribersCount(){
		return subscribers.size();
	}
}