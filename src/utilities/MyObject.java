/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 * Třída reprezentuje základní objekt, od kterého jsou odvozovány všechny
 * ostatní a implementuje metody pro práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public abstract class MyObject extends Object {

    /**
     * Metodu nechceme používat, jelikož zatěžuje systém šířením výjímek, proto
     * je deklarována jako "final"
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        //
        return super.clone();
    }

    /**
     * Veřejná metoda instance uvolní veškeré objektem alokované systémové
     * prostředky
     *
     * @since 0.0.1
     */
    abstract public void dispose();

}
