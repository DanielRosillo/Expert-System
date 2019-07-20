package expert;
/**
 * @author Daniel Rosillo;
 * Esta clase representa el contexto de de los eventos.
 */
public interface Context
{
    public String Name();//Nombre del evento.

    public Object value();//Valor del evento.

    public int Level();//Nivel de inferencia del evento.

    public String question();//Pregunta del evento.

    void setLevel(int l);

}
