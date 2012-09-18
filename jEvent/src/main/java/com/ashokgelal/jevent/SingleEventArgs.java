package com.ashokgelal.jevent;

/**
 * Wraps up an argument so that the argument can be passed without extending from {@link EventArgs}
 * @author Ashok Gelal
 */
public class SingleEventArgs<V> extends EventArgs {
	private V value;

	public V getValue() {
		return value;
	}

	public SingleEventArgs(V val) {
		value = val;
	}
}
