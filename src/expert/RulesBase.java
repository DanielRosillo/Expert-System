package expert;

import java.util.LinkedList;

/**
 * @author Daniel Rosillo; Esta clase representa la libreria de todas las reglas
 *         del motor de inferencia. Aqui se guardan los Eventos que el agente
 *         sabe con certeza que son ciertos, no confundir con la base de
 *         eventos.
 */
public class RulesBase
{
    protected LinkedList<Rule> rules;// Reglas del universo.

    public RulesBase()
    {
	rules = new LinkedList<>();
    }

    /*
     * Retorna las reglas asociadas al sistema.
     */
    public LinkedList<Rule> getRules()
    {
	return rules;
    }

    /*
     * Asigna un conjunto de reglas como nuevas reglas del sistema.
     */
    public void setRules(LinkedList<Rule> rules)
    {
	Rule n;
	for (Rule r : rules)
	{
	    n = new Rule(r.name, r.premises, r.conclucion);
	    this.rules.add(n);
	}
    }

    /*
     * Limpia las reglas de la memoria.
     */
    public void clear()
    {
	rules.clear();
    }

    /*
     * Agrega una regla por parametro. r -> Regla a agregar.
     */
    public void add(Rule r)
    {
	rules.add(r);
    }

    /*
     * Elimina por comparación la regla que se le pase por parametro. r -> Regla a
     * borrar.
     */
    public void delete(Rule r)
    {
	rules.remove(r);
    }

}
