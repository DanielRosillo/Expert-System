package expert;

import java.util.LinkedList;
/**
 * @author Daniel Rosillo;
 * La Interfaz Humano-Maquina es la parte del sistema que se comunica con el usuario.
 * Entiéndase como interfaz, todas aquellas funciones interactivas del sistema, que proporcionan información de utilidad.
 */
public interface HMI
{
    int getValue(String question);//Solicita un valor entero al usuario.

    boolean getValueBool(String question);//Solicita un valor booleano al usuario.

    void showRules(LinkedList<Rule> rules);//Muestra las reglas asociadas al sistema.

    void showEvent(LinkedList<Context> events);//Muestra hechos(resultados) del sistema.
}
