package expert;

import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * @author Daniel Rosillo; Esta clase representa una regla.
 */
public class Rule
{
    protected LinkedList<Context> premises;// Premisas que conforman la regla.
    protected Context conclucion;// Hecho resultado de las premisas.
    protected String name;// Nombre de la regla.

    public Rule(String name, LinkedList<Context> premises, Context conclucion)
    {
	this.name = name;
	this.premises = premises;
	this.conclucion = conclucion;
    }

    /*
     * Retorna todas las premisas asociadas a la regla.
     */
    public LinkedList<Context> getPremises()
    {
	return premises;
    }

    /*
     * Asigna una lista de eventos como premisas de una regla.
     * 
     * @premises -> Lista de hechos.
     */
    public void setPremises(LinkedList<Context> premises)
    {
	this.premises = premises;
    }

    /*
     * Retorna la conclusión de la regla.
     */
    public Context getConclucion()
    {
	return conclucion;
    }

    /*
     * Asigna el evento que se le pase por parametro como conclución.
     */
    public void setConclucion(Context conclucion)
    {
	this.conclucion = conclucion;
    }

    /*
     * Retrona el nombre de la regla.
     */
    public String getName()
    {
	return name;
    }

    /*
     * Asigna como nombre de la regla el string que reciba por parametro.
     */
    public void setName(String name)
    {
	this.name = name;
    }

    @Override
    public String toString()
    {
	StringJoiner joiner = new StringJoiner(" AND ");
	StringBuilder builder = new StringBuilder(name + " : IF (");

	premises.stream().forEach(n -> joiner.add(n.toString()));
	builder.append(joiner.toString() + ") THEN " + conclucion.toString());
	return builder.toString();
    }
}
