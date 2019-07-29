package expert;

import java.util.LinkedList;

/**
 * @author Daniel Rosillo; Esta clase representa un motor de inferencia.
 */
public class InferenceEngine
{
    private HMI ihm;// Interfaz HMI.
    private EventBase eb;// Base de hechos.
    private RulesBase rb;// Base de reglas.
    private int maxLevel;// Maximo nivel de inferencia alcanzado.

    /*
     * Este metodo agrega una regla al sistema. Condiciones: La regla debe de ser
     * del tipo l�gico "AND".
     * 
     * @str -> Regla en formato de texto.
     */
    public void addRule(String str)
    {
	// Ejemplo de una regla simple-> R2 : IF (Tri�ngulo AND �ngulo Recto(�La figura
	// tiene al menos un �ngulo recto?)) THEN Tri�ngulo Rect�ngulo"
	String[] aux = str.split(":");

	if (aux.length == 2)// Si la regla contiene 2 partes, es una regla valida.
	{
	    String name = aux[0].trim();// La primera parte representa el nombre.
	    String rule = aux[1].trim();// Esta es la parte l�gica que hay que interpretar.

	    rule = rule.replaceFirst("^" + "IF", "");
	    String[] premisse = rule.split("THEN");// Separa las sentencias l�gicas de la regla(condiciones,resultado).

	    // Verifica nuevamente si las partes son validas.
	    if (premisse.length == 2)
	    {
		LinkedList<Context> premises = new LinkedList<>();// Lista con las premisas interpretadas.
		String[] premisasStr = premisse[0].split(" AND ");// Separar las premisas de la regla.

		for (String cadena : premisasStr)
		{
		    Context event = Factory.event(cadena.trim());// Crea un evento por cada premisa, recordar que estos
								 // eventos se dan por ciertos y componen la regla.
		    premises.add(event);
		}
		String conclusionStr = premisse[1].trim();// La 2a parte es la conclusi�n de la regla cuando pasan los
							  // eventos.

		Context conclusion = Factory.event(conclusionStr);// Se crea el evento de la conclusi�n.
		rb.add(new Rule(name, premises, conclusion));// Crea una nueva regla y se agrega a la base de reglas del
							     // sistema.
	    }

	}

    }

    /*
     * Asigna una interfaz IHM al motor.
     */
    public InferenceEngine(HMI ihm)
    {
	this.ihm = ihm;
	eb = new EventBase();
	rb = new RulesBase();
    }

    /*
     * Metodo de redirecci�n a la interfaz de control que obtiene los valores
     * enteros por consola.
     * 
     * @ question -> Pregunta de la cual se desea saber el valor.
     */
    int getValue(String question)
    {
	return ihm.getValue(question);
    }

    /*
     * Metodo de redirecci�n a la interfaz de control que obtiene los valores
     * enteros por consola.
     * 
     * @ question -> Pregunta de la cual se desea saber el valor.
     */
    boolean getValueBool(String question)
    {
	return ihm.getValueBool(question);
    }

    /*
     * Verifica si es posible aplicar una regla en el estado actual.
     * 
     * @r -> Regla que se desea saber si es aplicable.
     */
    int isApplicable(Rule r)
    {
	int nivelmax = -1;

	// Recorremos las premisas de la regla para comprobarlas.
	for (Context h : r.getPremises())
	{
	    Context event = eb.search(h.Name());

	    if (event.Name().equalsIgnoreCase("x"))
	    {
		if (h.question() != null)
		{
		    // Solicita la informaci�n al usuario.
		    event = Factory.event(h, this);
		    eb.add(event);// Agrega el nuevo hecho a la base de eventos.
		}
		else return -1;// La regla no es aplicable.
	    }

	    /*
	     * El hecho existe en la base, verifica si ambos valores se corresponden.
	     */
	    if (!event.value().equals(h.value())) return -1;
	    else nivelmax = Math.max(nivelmax, event.Level());// Si es asi, actualiza el nivel actual de inferencia.
	}

	return nivelmax;
    }

    /*
     * Busca entre todas las reglas de la base cual puede aplicarse primero, por
     * tanto se basa en si una regla es aplicable o no para el estado actual.
     * 
     * @bdrLocal -> Reglas a verificar.
     */
    Rule findRule(RulesBase bdrLocal)
    {
	int level;
	for (Rule r : bdrLocal.getRules())
	{
	    level = isApplicable(r);// Si la regla se puede aplicar, se retorna y el valor de inferencia se
				    // actualiza para mantener la regla.
	    if (level != -1)// Si el valor es -1, sicnifica que la regla no es una opci�n por el momento
	    {
		maxLevel = level;
		return r;
	    }
	}
	return null;
    }

    /*
     * Metodo principal del motor, aqui se inician las inferencias.
     */
    public void Think()
    {
	RulesBase rbLocal = new RulesBase();// Crea una nueva base de reglas vacia.
	rbLocal.setRules(rb.getRules());// Copia las reglas principales a la base local.

	eb.clear();// Limpia la base de eventos para evitar errores.

	Rule r = findRule(rbLocal);// Busca la primera regla que aplicar.

	while (r != null)// Mientras exista alguna regla para aplicar.
	{
	    // Genera el evento apartir de la regla.
	    Context newEvent = r.conclucion;
	    newEvent.setLevel(maxLevel + 1);
	    eb.add(newEvent);
	    rbLocal.delete(r);// Elimina la regla de las psobiles reglas aplicables.
	    r = findRule(rbLocal);// Busca la siguiente regla que se pueda aplicar.

	}

	ihm.showEvent(eb.getEvents());// Mostrar resultados.
    }
}
