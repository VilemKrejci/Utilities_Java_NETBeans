/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 * Třída reperezentuje pozici objektu a implementuje metody pro práci s ní
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Location extends MyObject {

    /**
     * Obsahuje levou souřadnici pozice objektu
     *
     * @since 0.0.1
     */
    private float left;

    /**
     * Obsahuje horní souřadnici pozice objektu
     *
     * @since 0.0.1
     */
    private float top;

    /**
     * Veřejný parametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @param left výchozí levá souřadnice pozice objektu
     * @param top výchozí horní souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public Location(float left, float top) {
        // Korektní inicializace bázové třídy
        super();
        //
        this.left = left;
        this.top = top;
    }

    /**
     * Metoda vrátí kopii aktuální pozice objektu
     *
     * @return kopie aktuální pozice objektu
     *
     * @since 0.0.1
     */
    public Location getCopy() {
        //
        return new Location(left, top);
    }

    /**
     * Metoda nastaví levou souřadnici pozice objektu
     *
     * @param left levá souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public void setLeft(float left) {
        //
        this.left = left;
    }

    /**
     * Metoda zjistí levou souřadnici pozice objektu jako číslo typu Integer
     *
     * @return levá souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public int getLeft() {
        //
        return (int) left;
    }

    /**
     * Metoda zjistí levou souřadnici pozice objektu jako číslo typu Float
     *
     * @return levá souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public float getLeftF() {
        //
        return left;
    }

    /**
     * Metoda nastaví horní souřadnici pozice objektu
     *
     * @param top horní souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public void setTop(float top) {
        //
        this.top = top;
    }

    /**
     * Metoda zjistí horní souřadnici pozice objektu jako číslo typu Integer
     *
     * @return horní souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public int getTop() {
        //
        return (int) top;
    }

    /**
     * Metoda zjistí horní souřadnici pozice objektu jako číslo typu Float
     *
     * @return horní souřadnice pozice objektu
     *
     * @since 0.0.1
     */
    public float getTopF() {
        //
        return top;
    }

    @Override
    public void dispose() {
        // Korektní uvolnění prostředků bázové třídy
        //super.dispose();
    }

}
