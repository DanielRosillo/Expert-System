package expert;

/**
 * @author Daniel Rosillo; Esta clase es la fábrica de eventos y hechos del
 *         sistema, se encarga de generarlos mediante la información
 *         suministrada por el motor de inferencia.
 */
public class Factory
{
    /*
     * Al invocar este metodo se crea un hecho a partir de un evento disponible,
     * estos estados son suministrados por la base de reglas y su disponibilidad.
     * 
     * @h -> Evento que al ejecutarce (obtener de información), se convierte en un
     * Hecho.
     * 
     * @m -> Motor de inferencia en turno.
     */
    static Context event(Context h, InferenceEngine m)
    {
	try
	{
	    Context event = null;
	    Class<? extends Context> clase = h.getClass();

	    // Identificamos el tipo de evento, booleano o entero.
	    if (clase.equals(Class.forName("expert.Event")))// Si el evento es de tipo entero.
	    {
		int value = m.getValue(h.question());// Solicita el valor mediante interfaz al usuario.
		event = new Event(h.Name(), value, null, 0);// Crea un nuevo hecho.
	    }
	    else// Si el evento es de tipo booleano.
	    {
		boolean valorb = m.getValueBool(h.question());// Solicita el valor mediante interfaz al usuario.
		event = new BoolEvent(h.Name(), valorb, null, 0);// Crea un nuevo hecho.
	    }
	    return event;
	}
	catch (ClassNotFoundException ex)
	{
	    return null;
	}
    }

    /*
     * Con este metodo es posible crear un evento mediante una sentencia, esta
     * sentencia puede ser de tipo:
     * 
     * 1- Entero. 2.- Booleano.
     * 
     * @eventStr -> Sentencia que se desea convertir en evento.
     * 
     */
    static Context event(String eventStr)
    {
	eventStr = eventStr.trim();

	if (eventStr.contains("="))// Verificamos si la sentencia es de tipo entero.
	{
	    eventStr = eventStr.replaceFirst("^" + "\\(", "");// Mapeamos la cadena.
	    String[] aux = eventStr.split("[=()]");// Seccionamos los datos para interpretarlos.

	    if (aux.length >= 2)
	    {
		String pregunta = null;
		/*
		 * Es un evento correcto.
		 */
		if (aux.length == 3)// El evento contiene una pregunta.
		{
		    pregunta = aux[2].trim();
		}
		return new Event(aux[0].trim(), Integer.parseInt(aux[1].trim()), pregunta, 0);
	    }
	}
	else// Evento booleano.
	{
	    boolean value = true;

	    if (eventStr.startsWith("!"))
	    {
		value = false;// Es negativo.
		eventStr = eventStr.substring(1).trim();
	    }
	    eventStr = eventStr.replaceFirst("^" + "\\(", "");// Mapeamos la cadena.
	    String[] aux = eventStr.split("[=()]");// Seccionamos los datos para interpretarlos.
	    String pregunta = null;
	    /*
	     * Es un evento correcto.
	     */
	    if (aux.length == 2)// El evento contiene una pregunta.
	    {
		pregunta = aux[1].trim();
	    }
	    return new BoolEvent(aux[0].trim(), value, pregunta, 0);
	}
	return null;// Si llega aqui: Sintaxis incorrecta.

    }

}
