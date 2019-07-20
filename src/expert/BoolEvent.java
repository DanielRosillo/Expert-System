package expert;
/**
 * @author Daniel Rosillo;
 * Esta clase representa un evento de tipo booleano.
 */
public class BoolEvent implements Context
{
    protected String name;//Nombre del evento.
    protected boolean value;//Valor real del evento.
    protected int level;//Nivel de inferencia.
    protected String question;//Pregunta del evento.

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

    @Override
    public void setLevel(int l)
    {
	level = l;
    }

    public BoolEvent(String name, boolean value, String question, int level)
    {
	this.name = name;
	this.value = value;
	this.level = level;
	this.question = question;
    }

    @Override
    public String toString()
    {
	return name + "=" + value + " (" + level + ")";
    }
}
