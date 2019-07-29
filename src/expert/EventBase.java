package expert;

import java.util.LinkedList;

/**
 * @author Daniel Rosillo; Esta clase respresenta una base de eventos,
 *         basicamente una envoltura de una lista de eventos.
 */
public class EventBase
{
    protected LinkedList<Context> events;

    public EventBase()
    {
	events = new LinkedList<>();
    }

    public void add(Context event)
    {
	events.add(event);
    }

    public void clear()
    {
	events.clear();
    }

    public Context search(String name)
    {
	for (Context f : events)
	{
	    if (f.Name().equals(name)) return f;
	}
	return new Event("x", 0, null, 0);

    }

    /*
     * Recupera el valor de algun evento.
     * 
     * @name -> nombre del evento.
     */
    public Object retriveValue(String name)
    {
	return events.stream().filter(x -> x.Name().equals(name)).limit(1).map(x -> x.value());
    }

    public LinkedList<Context> getEvents()
    {
	return events;
    }

    public void setEvents(LinkedList<Context> events)
    {
	this.events = events;
    }
}
