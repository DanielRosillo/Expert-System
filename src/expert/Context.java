package expert;
/**
 * @author Daniel Rosillo;
 * Esta clase representa el contexto de trabajo de los eventos.
 * Un evento pasa a ser un hecho cuando el agente adquiere informacion de el, las reglas se conforman de eventos cuyo valor se toma como cierto. 
 */
public interface Context
{
    public String Name();//Nombre del evento.

    public Object value();//Valor del evento.

    public int Level();//Nivel de inferencia del evento.

    public String question();//Pregunta del evento.

    void setLevel(int l);//Asignar nivel de inferencia.

}
