package expert;

/**
 * @author Daniel Rosillo; Esta clase representa un evento de tipo booleano.
 */
public class Event implements Context
{
    protected String name;// Nombre
    protected int value;// Valor
    protected int level;// Nivel del inferencia.
    protected String question;// Pregunta asociada.

    public Event(String name, int value, String question, int level)
    {
	this.name = name;
	this.value = value;
	this.level = level;
	this.question = question;
    }

    @Override
    public String Name()
    {
	return name;
    }

    @Override
    public Object value()
    {
	return value;
    }

    @Override
    public int Level()
    {
	return level;
    }

    @Override
    public String question()
    {
	return question;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public void setValue(int value)
    {
	this.value = value;
    }

    public void setQuestion(String question)
    {
	this.question = question;
    }

    @Override
    public String toString()
    {
	return name + " = " + value + " (" + level + ")";
    }

    @Override
    public void setLevel(int l)
    {
	level = l;
    }

}
