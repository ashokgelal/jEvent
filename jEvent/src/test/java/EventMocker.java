import com.ashokgelal.jevent.EmptyArgsEventHandler;
import com.ashokgelal.jevent.EventArgs;
import com.ashokgelal.jevent.GenericEventHandler;
import com.ashokgelal.jevent.SingleArgsEventHandler;


public class EventMocker {
	public GenericEventHandler<EventArgs> MyFakeEvent = new GenericEventHandler<EventArgs>("Fake Event");
	public GenericEventHandler<PersonInfoEventArgs> MyPersonInfoEvent = new GenericEventHandler<PersonInfoEventArgs>("PersonInfoEvent");
	public EmptyArgsEventHandler MyEmptyEvent = new EmptyArgsEventHandler("Empty Event");
	public SingleArgsEventHandler<Address> MySingleEvent = new SingleArgsEventHandler<Address>("Single Event Handler");
	
	public void FireEventWithEmptyArgs(){
		MyFakeEvent.raise(this, EventArgs.Empty);
	}
	
	public void FireEventWithPersonInfo(){
		MyPersonInfoEvent.raise(this, new PersonInfoEventArgs("Foo", 12345));
	}
	
	public void FireEmptyEvent(){
		MyEmptyEvent.raise(this);
	}
}
