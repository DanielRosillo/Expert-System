package expert;

import java.util.LinkedList;
/**
 * @author Daniel Rosillo;
 * Esta clase representa un motor de inferencia.
 */
public class InferenceEngine
{
    private HMI ihm;//Interfaz disponible para poder trabajar con el motor.
    private EventBase eb;//Base de hechos.
    private RulesBase rb;//Base de reglas.
    private int maxLevel;//Maximo nivel de inferencia disponible.

    /*
     * Agrega una regla al sistema.
     * Condiciones: La regla debe de ser del tipo lógico "AND".
     * @str -> Regla del universo la cual se sabes es cierta.
     */
    public void addRule(String str)
    {
	//R2 : IF (Triángulo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Triángulo Rectángulo"
	String[] aux = str.split(":");//Separamos la regla en partes.
	
	//Si la regla contiene 2 partes, es una regla valida.
	if (aux.length == 2)
	{
	    String name = aux[0].trim();//La primera parte representa el nombre de la regla.
	    String rule = aux[1].trim();//Esta es la parte lógica de la regla que hay que interpretar.

	    rule = rule.replaceFirst("^" + "IF", "");
	    String[] premisse = rule.split("THEN");//Separa las sentencias lógicas de la regla(condiciones,resultado).

	    //Verifica nuevamente si las partes son validas.
	    if (premisse.length == 2)
	    {
		LinkedList<Context> premises = new LinkedList<>();//Lista con las premisas interpretadas.
		String[] premisasStr = premisse[0].split(" AND ");//Separas las premisas de la regla.

		for (String cadena : premisasStr)
		{
		    Context event = Factory.event(cadena.trim());//Crea un evento por cada premisa, recordar que estos eventos se dan por ciertos por que componen la regla.
		    premises.add(event);
		}
		String conclusionStr = premisse[1].trim();//La 2a parte de las sentencias extraidas en un principio, es la conclusión de la regla cuando pasan los eventos intrepretados.

		Context conclusion = Factory.event(conclusionStr);//Se crea el evento de la conclusión.
		rb.add(new Rule(name, premises, conclusion));//Se crea una nueva regla a partir de los eventos y se agrega a la base de reglas del sistema.
	    }

	}

    }

    public InferenceEngine(HMI ihm)
    {
	this.ihm = ihm;
	eb = new EventBase();
	rb = new RulesBase();
    }

    /*
     * Metodo de redirección a la interfaz de control que obtiene los valores enteros de la consola ingresados por el usuario.
     * @ question -> Pregunta de la  cual se desea saber el valor.
     */
    int getValue(String question)
    {
	return ihm.getValue(question);
    }

    /*
     * Metodo de redirección a la interfaz de control que obtiene los valores booleanos de la consola ingresados por el usuario.
     * @ question -> Pregunta de la  cual se desea saber el valor.
     */
    boolean getValueBool(String question)
    {
	return ihm.getValueBool(question);
    }

    /*
     * Verifica si es posible aplicar una regla al estado actual.
     * @r -> Regla que se desea saber si es aplicable.
     */
    int isApplicable(Rule r)
    {
	int nivelmax = -1;

	//Recorremos las premisas de la regla para comprobarlas.
	for (Context h : r.getPremises())
	{
	    
	    Context event = eb.search(h.Name());

	    if (event == null)//Si el hecho no existe.
		if (h.question() != null)
		{
		    //Solicita la información al usuario.
		    event = Factory.event(h, this);
		    eb.add(event);//Agrega el nuevo evento a la base de eventos.
		}
	
		else return -1;//La regla no es aplicable.
	 

	    /*
	     * El hecho existe en la base, verifica si ambos valores se corresponden.
	     */
	    if (!event.value().equals(h.value())) return -1;
	    else nivelmax = Math.max(nivelmax, event.Level());//Si es asi actualiza el nivel actual de inferencia.

	}
	return nivelmax;
    }

    /*
     * Busca entre todas las reglas de la base cual puede aplicarse primero, por tanto se basa en si una regla es aplicable o no para el estado actual.
     * @bdrLocal -> Reglas a verificar.
     */
    Rule findRule(RulesBase bdrLocal)
    {
	int level;
	for (Rule r : bdrLocal.getRules())
	{
	    level = isApplicable(r);//Si la regla se puede aplicar, se retorna y el valor de inferencia se actualiza para mantener la regla.
	    if (level != -1)//Si el valor es -1, sicnifica que la regla no es una opcion por el momento
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
	RulesBase rbLocal = new RulesBase();//Crea una nueva base de reglas vacia.
	rbLocal.setRules(rb.getRules());//Copia las reglas principales a la base local.

	eb.clear();//Limpia la base de eventos para evitar errores.

	Rule r = findRule(rbLocal);//Busca la primera regla que aplicar.

	while (r != null)//Mientras exista alguna regla para aplicar.
	{
	    //Genera el evento apartir de la regla.
	    Context newEvent = r.conclucion;
	    newEvent.setLevel(maxLevel + 1);
	    eb.add(newEvent);
	    rbLocal.delete(r);//Elimina la regla de las psobiles reglas aplicables.
	    r = findRule(rbLocal);//Busca la siguiente regla que se pueda aplicar.

	}
	
	ihm.showEvent(eb.getEvents());//Mostrar resultados.
    }
}
