package com.ashokgelal.jevent;

/**
 * This event handler wraps up the given value in a SingleEventArgs and raises the event with the wrapped value.
 * This class is useful when you want to pass a value as an argument to an event without extending it from {@link EventArgs}.
 * @author Ashok Gelal
 */
public class SingleArgsEventHandler<V> extends GenericEventHandler<SingleEventArgs<V>> {
	public SingleArgsEventHandler(String name) {
		super(name);
	}

	public void raise(Object sender, V args){
		super.raise(sender, new SingleEventArgs<V>(args));
	}
}
