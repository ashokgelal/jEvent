package com.ashokgelal.jevent;

import java.util.UUID;

/**
 * A unique token for subscriptions. Subscribers can hold to this token and then unsubscribe this token when
 * they no longer need to be notified about an event.
 * @author Ashok Gelal
 *
 */
public class SubscriptionToken {
	private UUID uuid;
	
	public SubscriptionToken(){
		uuid = UUID.randomUUID();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof SubscriptionToken))
			return false;
		SubscriptionToken otherToken = (SubscriptionToken) other;
		return uuid.equals(otherToken.uuid);
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
}
