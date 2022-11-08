/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 * Třída reprezentuje velikost objektu a implementuje metody pro práci s ní
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Size extends MyObject {

    /**
     * Obsahuje šířku objektu
     *
     * @since 0.0.1
     */
    private float width;

    /**
     * Obsahuje výšku objektu
     *
     * @since 0.0.1
     */
    private float height;

    /**
     * Veřejný parametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @param width výchozí šířka objektu
     * @param height výchozí výška objektu
     *
     * @since 0.0.1
     */
    public Size(float width, float height) {
        // Korektní inicializace bázové třídy
        super();
        //
        this.width = width;
        this.height = height;
    }

    /**
     * Metoda vrátí kopii aktuální pozice objektu
     *
     * @return kopie aktuální pozice objektu
     *
     * @since 0.0.1
     */
    public Size getCopy() {
        //
        return new Size(width, height);
    }

    /**
     * Metoda nastaví požadovanou šířku objektu
     *
     * @param width požadovaná šířka objektu
     *
     * @since 0.0.1
     */
    public void setWidth(float width) {
        //
        this.width = width;
    }

    /**
     * Metoda zjistí aktuální šířku objektu jako číslo typu Integer
     *
     * @return aktuální šířka objektu
     *
     * @since 0.0.1
     */
    public int getWidth() {
        //
        return (int) width;
    }

    /**
     * Metoda zjistí aktuální šířku objektu jako číslo typu Float
     *
     * @return aktuální šířka objektu
     *
     * @since 0.0.1
     */
    public float getWidthF() {
        //
        return width;
    }

    /**
     * Metoda nastaví požadovanou výšku objektu
     *
     * @param height požadovaná výška objektu
     *
     * @since 0.0.1
     */
    public void setHeight(float height) {
        //
        this.height = height;
    }

    /**
     * Metoda zjistí aktuální výšku objektu jako číslo typu Integer
     *
     * @return aktuální výška objektu
     *
     * @since 0.0.1
     */
    public int getHeight() {
        //
        return (int) height;
    }

    /**
     * Metoda zjistí aktuální výšku objektu jako číslo typu Float
     *
     * @return aktuální výška objektu
     *
     * @since 0.0.1
     */
    public float getHeightF() {
        //
        return height;
    }

    @Override
    public void dispose() {
        // Korektní uvolnění prostředků bázové třídy
        //super.dispose();
    }

}
