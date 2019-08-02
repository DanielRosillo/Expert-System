package expert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

/*
 * Sistema Experto
 * Problema de juguete: Adivinar en que triangulo piensa el usuario.
 * L�gica de programaci�n: Inteligencia Artificial para desarrolladores: Conceptos e implementaci�n en java
 * Autor: Virginie MATHIVET
 * Implementaci�n y optimizaci�n: DanielRosillo;
 * GitHub:@DanielRosillo
 * Esta clase representa un sistema experto.
 */
public class App implements HMI
{
    private StringBuilder builder;// Variable multiusos para mostrar datos por consola.

    @Override
    public void showEvent(LinkedList<Context> events)
    {
	builder = new StringBuilder("Soluciones Encontradas : \n");
	Collections.sort(events, (Context f1, Context f2) ->
	{
	    if (f1.Level() == f2.Level()) return 0;

	    else if (f1.Level() > f2.Level()) return -1;

	    else return 1;
	});

	events.stream().filter(n -> (n.Level() != 0)).forEach(n -> builder.append(n.toString() + "\n"));
	System.out.println(builder.toString());
    }

    /*
     * Muestra por consola una lista de reglas que se le pasen por parametro.
     * 
     * @rules -> Lista enlazada de reglas.
     */
    public void showRules(LinkedList<Rule> rules)
    {
	builder = new StringBuilder();
	rules.forEach(n -> builder.append(n.toString() + "\n"));
	System.out.print(builder.toString());
    }

    @Override
    public int getValue(String question)
    {
	System.out.println(question);
	try
	{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    return Integer.decode(br.readLine());
	}
	catch (Exception e)
	{
	    return 0;
	}
    }

    @Override
    public boolean getValueBool(String question)
    {
	System.out.println(question + " (Si, No )");
	try
	{
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String res = br.readLine();

	    if (res.equalsIgnoreCase("si")) return true;
	    else return false;
	}
	catch (Exception e)
	{
	    return false;
	}

    }

    // Inicio de la aplicaci�n.
    public static void main(String[] args)
    {
	App app = new App();
	app.run();
    }

    /*
     * Algoritmo de ejecuci�n:
     * 
     * 1.- Crear un motor de inferencia. 2.- Cargar las reglas al motor. 3.- Invocar
     * el metodo principal del motor.
     */
    public void run()
    {
	System.out.println(" *** Creaci�n del Motor ***");
	System.out.println(" *** Agregar Reglas ***");

	InferenceEngine Ie = new InferenceEngine(this);

	// Reglas
	Ie.addRule("R1 : IF (Orden=3(�Cu�l es el orden?)) THEN Tri�ngulo");
	Ie.addRule(
		"R2 : IF (Tri�ngulo AND �ngulo Recto(�La figura tiene al menos un �ngulo recto?)) THEN Tri�ngulo Rect�ngulo");
	Ie.addRule(
		"R3 : IF (Tri�ngulo AND Lados Iguales=2(�Cu�ntos lados iguales tiene la figura?)) THEN Tri�ngulo Is�sceles");
	Ie.addRule("R4 : IF (Tri�ngulo Rect�ngulo AND Tri�ngulo Is�sceles) THEN Tri�ngulo Rect�ngulo Is�sceles");
	Ie.addRule(
		"R5 : IF (Tri�ngulo AND Lados Iguales=3(�Cu�ntos lados iguales tiene la figura?)) THEN Tri�ngulo Equil�tero");

	Ie.addRule("R6 : IF (Orden=4(�Cu�l es el orden?)) THEN Cuadril�tero");
	Ie.addRule(
		"R7 : IF (Cuadril�tero AND Lados Paralelos=2(�Cu�ntos lados paralelos entre si - O, 2 o 4?)) THEN Trapecio");
	Ie.addRule(
		"R8 : IF (Cuadril�tero AND Lados Paralelos=4(�Cu�ntos lados paralelos entre si - O, 2 o 4?)) THEN Paralelogramo");
	Ie.addRule(
		"R9 : IF (Paralelogramo AND �ngulo Recto(�La figura tiene al menos un �ngulo recto?)) THEN Rect�ngulo");
	Ie.addRule("R10 : IF (Paralelogramo AND Lados Iguales=4(�Cuantos lados iguales tiene la figura?)) THEN Rombo");
	Ie.addRule("R11 : IF (Rect�ngulo AND Rombo THEN Cuadrado");
	System.out.println(" *** Soluci�n ***");
	Ie.Think();
	System.exit(0);
    }

}
