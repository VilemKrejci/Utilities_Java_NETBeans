/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.util.Random;

/**
 * Třída reprezentuje generátor pseudonáhodných čísel a implementuje metody pro
 * práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Randomizer extends MyObject {

    /**
     * Obsahuje referenci na generátor náhodných čísel
     *
     * @since 0.0.1
     */
    private static Random random;

    /**
     * Veřejný bezparametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @since 0.0.1
     */
    public Randomizer() {
        // Korektní inicializace bázové třídy
        super();
        // Inicializace reference na zabudovaný generátor
        random = new Random();
    }

    /**
     * Metoda vrátí vygenerované číslo v zadaném intervalu hodnot (včetně jeho
     * mezí)
     *
     * @param from dolní hranice intervalu
     * @param to horní hranice intervalu
     * @return vygenerované číslo typu integer v zadaném intervalu
     *
     * @since 0.0.1
     */
    public int getInt(float from, float to) {
        //
        float retValue = ((1f + to - from) * random.nextFloat());// - 0.0f);
        //
        //System.out.println("int = " + (from + (int) retValue) + " z " + retValue);
        //
        return (int) from + (int) retValue;
    }

    @Override
    public void dispose() {
        //
        random = null;
    }

}
