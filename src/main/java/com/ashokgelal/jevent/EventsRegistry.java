package com.ashokgelal.jevent;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A registry class that keeps track of all the subscribers for a particular
 * event name.
 * 
 * @author Ashok Gelal
 * 
 */
public class EventsRegistry {

	private static ConcurrentHashMap<String, ArrayList<IEventSubscriber<?>>> subscribers = new ConcurrentHashMap<String, ArrayList<IEventSubscriber<?>>>();

	/**
	 * Add an event subscriber for the given event name.
	 * 
	 * @param name
	 *            Name of the event.
	 * @param subscriber
	 *            The subscriber of the given event.
	 */
	synchronized static void addEventSubscriber(String name,
			IEventSubscriber<?> subscriber) {
		if (subscriber == null)
			return;

		ArrayList<IEventSubscriber<?>> list = subscribers.get(name);
		if (list == null) {
			list = new ArrayList<IEventSubscriber<?>>();
			subscribers.put(name, list);
		}
		list.add(subscriber);
	}

	/**
	 * Removes an event subscriber for the given event name.
	 * 
	 * @param name
	 *            Name of the event.
	 * @param subscriber
	 *            The subscriber of the given event.
	 */
	synchronized static void removeEventSubscriber(String name,
			IEventSubscriber<?> subscriber) {
		if (subscriber == null)
			return;
		ArrayList<IEventSubscriber<?>> list = subscribers.get(name);
		if (list == null)
			return;
		list.remove(subscriber);

		if (list.isEmpty())
			subscribers.remove(name);
	}

	/**
	 * Prints a list of all remaining subscriptions to the given stream.
	 * 
	 * @param out
	 *            Stream to print the output
	 */
	public synchronized static void printAllRemainingSubscriptions(
			PrintStream out) {
		for (Entry<String, ArrayList<IEventSubscriber<?>>> entries : subscribers
				.entrySet()) {
			printList(entries.getKey(), entries.getValue(), out);
		}
	}

	/**
	 * A helper method to print the given list, with the given name to the given
	 * stream.
	 * 
	 * @param name
	 *            Event name
	 * @param list
	 *            List of subscribers
	 * @param out
	 *            Stream to print the output
	 */
	private synchronized static void printList(String name,
			ArrayList<IEventSubscriber<?>> list, PrintStream out) {
		out.println();
		out.println(String.format("Event Name: %s, Count: %d", name,
				list.size()));
		out.println("--------------------------------------------------------");
		for (IEventSubscriber<?> subscriber : list) {
			out.println(String.format("\tSubscriber Name: %s", subscriber
					.getClass().getName()));
		}
		out.println("--------------------------------------------------------");
	}

	/**
	 * Prints a list of all remaining subscriptions for the given event name to
	 * the given stream.
	 * 
	 * @param name
	 *            Name of the event.
	 * @param out
	 *            Stream to print the output
	 */
	public synchronized static void printRemainingSubscriptions(String name,
			PrintStream out) {
		ArrayList<IEventSubscriber<?>> list = subscribers.get(name);
		if (list != null)
			printList(name, list, out);
	}
	
	/**
	 * Returns the count of all remaining subscriptions for the given event name
	 * @param name Name of the event
	 * @return A count of all remaining subscriptions. 
	 */
	public synchronized static int getRemainingSubscriptionsCount(String name) {
		ArrayList<IEventSubscriber<?>> list = subscribers.get(name);
		return list == null ? 0 : list.size();
	}
	
	/**
	 * Returns a list of names all the events that a subscriber is subscribed to.
	 * @param subscriber The registered subscriber.
	 * @return A list of all the names of all the events.
	 */
	public synchronized static ArrayList<String> getSubscribedEvents(IEventSubscriber<?> subscriber){
		ArrayList<String> ret = new ArrayList<String>();
		for (Entry<String, ArrayList<IEventSubscriber<?>>> entries : subscribers.entrySet()) {
			for (IEventSubscriber<?> sub : entries.getValue()) {
				if(sub == subscriber)
					ret.add(entries.getKey());
			}
		}
		return ret;
	}
}
