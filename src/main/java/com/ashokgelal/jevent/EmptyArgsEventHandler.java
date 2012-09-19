package com.ashokgelal.jevent;

/**
 * An event handler that has no arguments. This class is useful in the case when you just want to notify the occurrence of
 * an event without passing any value.
 * @author Ashok Gelal
 *
 */
public class EmptyArgsEventHandler extends GenericEventHandler<EventArgs> {
	public EmptyArgsEventHandler(String name) {
		super(name);
	}

	public EmptyArgsEventHandler() {
		super();
	}

	public void raise(Object sender){
		super.raise(sender, EventArgs.Empty);
	}

}
