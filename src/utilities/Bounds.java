/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 * Třída reprezentuje hranice objektu (polohu a velikot) a implementuje metody
 * pro práci s nimi
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Bounds extends MyObject {

    /**
     * Obsahuje aktuální polohu objektu
     *
     * @since 0.0.1
     */
    private Location location;

    /**
     * Obsahuje aktuální velikost objektu
     *
     * @since 0.0.1
     */
    private final Size size;

    /**
     * Veřejný parametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @param left výchozí levá souřadnice polohy objektu
     * @param top výchozí horní souřadnice polohy objektu
     * @param width výchozí šířka objektu
     * @param height výchozí výška objektu
     *
     * @since 0.0.1
     */
    public Bounds(float left, float top, float width, float height) {
        // Korektní inicializace bázové třídy
        super();
        //
        location = new Location(left, top);
        size = new Size(width, height);
    }

    /**
     * Metoda zjistí, zda se bod o zadaných souřadnicich nachází uvnitř objektu
     *
     * @param x levá souřadnice testovaného bodu
     * @param y horní souřadnice testovaného bodu
     * @return true, pokud se bod o zadaných souřadnicich nachází uvnitř objektu
     *
     * @since 0.0.1
     */
    public boolean contains(float x, float y) {
        // Test vodorovné souřadnice
        boolean retValue = location.getLeftF() <= x && x <= getRightF();
        // plus test svislé souřadnice
        retValue &= location.getTopF() <= y && y <= getBottomF();
        // True, pokud je zadaný bod uvnitř objektu
        return retValue;
    }

    /**
     * Metoda nastaví polohu a velikost objektu
     *
     * @param left výchozí levá souřadnice polohy objektu
     * @param top výchozí horní souřadnice polohy objektu
     * @param width výchozí šířka objektu
     * @param height výchozí výška objektu
     *
     * @since 0.0.1
     */
    public void setBounds(float left, float top, float width, float height) {
        //
        location.setLeft(left);
        location.setTop(top);
        //
        size.setWidth(width);
        size.setHeight(height);
    }

    /**
     * Metoda zjistí pravou souřadnici objektu jako číslo typu float
     *
     * @return pravá souřadnice objektu
     *
     * @since 0.0.1
     */
    public float getRightF() {
        //
        return location.getLeftF() + size.getWidthF();
    }

    /**
     * Metoda zjistí pravou souřadnici objektu jako číslo typu integer
     *
     * @return pravá souřadnice objektu
     *
     * @since 0.0.1
     */
    public int getRight() {
        //
        return location.getLeft() + size.getWidth();
    }

    /**
     * Metoda zjistí dolní souřadnici objektu jako číslo typu Integer
     *
     * @return dolní souřadnice objektu
     *
     * @since 0.0.1
     */
    public int getBottom() {
        //
        return location.getTop() + size.getHeight();
    }

    /**
     * Metoda zjistí dolní souřadnici objektu jako číslo typu float
     *
     * @return dolní souřadnice objektu
     *
     * @since 0.0.1
     */
    public float getBottomF() {
        //
        return location.getTopF() + size.getHeightF();
    }

    /**
     * Metoda nastaví aktuální pozici objektu
     *
     * @param left levá souřadnice polohy objektu
     * @param top horní souřadnice polohy objektu
     *
     * @since 0.0.1
     */
    public void setLocation(float left, float top) {
        //
        location.setLeft(left);
        location.setTop(top);
    }

    /**
     * Metoda nastaví polohu objektu
     *
     * @param location požadovaná poloha objektu
     * 
     * @since 0.0.1
     */
    public void setLocation(Location location) {
        //
        this.location = location;
    }

    /**
     * Metoda zjistí polohu objektu
     *
     * @return poloha objektu
     *
     * @since 0.0.1
     */
    public Location getLocation() {
        //
        return location.getCopy();
    }

    /**
     * Metoda nastaví aktuální velikost objektu
     *
     * @param width požadovaná šířka objektu
     * @param height požadovaná výška objektu
     *
     * @since 0.0.1
     */
    public void setSize(float width, float height) {
        //
        size.setWidth(width);
        size.setHeight(height);
    }

    /**
     * Metoda zjistí velikost objektu
     *
     * @return velikost objektu
     *
     * @since 0.0.1
     */
    public Size getSize() {
        //
        return size.getCopy();
    }

    /**
     * Metoda nastaví levou souřadnici objektu
     *
     * @param left levá souřadnice objektu
     *
     * @since 0.0.1
     */
    public void setLeft(float left) {
        //
        location.setLeft(left);
    }

    /**
     * Metoda zjistí levou souřadnici objektu jako číslo typu Integer
     *
     * @return levá souřadnice objektu
     *
     * @since 0.0.1
     */
    public int getLeft() {
        //
        return location.getLeft();
    }

    /**
     * Metoda zjistí levou souřadnici objektu jako číslo typu Float
     *
     * @return levá souřadnice objektu
     *
     * @since 0.0.1
     */
    public float getLeftF() {
        //
        return location.getLeftF();
    }

    /**
     * Metoda nastaví horní souřadnici objektu
     *
     * @param top horní souřadnice objektu
     *
     * @since 0.0.1
     */
    public void setTop(float top) {
        //
        location.setTop(top);
    }

    /**
     * Metoda zjistí horní souřadnici objektu
     *
     * @return horní souřadnice objektu
     *
     * @since 0.0.1
     */
    public int getTop() {
        //
        return location.getTop();
    }

    /**
     * Metoda zjistí horní souřadnici objektu
     *
     * @return horní souřadnice objektu
     *
     * @since 0.0.1
     */
    public float getTopF() {
        //
        return location.getTop();
    }

    /**
     * Metoda nastaví požadovanou šířku objektu
     *
     * @param width požadovaná šířku objektu
     *
     * @since 0.0.1
     */
    public void setWidth(float width) {
        //
        size.setWidth(width);
    }

    /**
     * Metoda zjistí aktuální šířku objektu
     *
     * @return aktuální šířka objektu jako číslo typu Integer
     *
     * @since 0.0.1
     */
    public int getWidth() {
        //
        return size.getWidth();
    }

    /**
     * Metoda zjistí aktuální šířku objektu
     *
     * @return aktuální šířka objektu jako číslo typu Float
     *
     * @since 0.0.1
     */
    public float getWidthF() {
        //
        return size.getWidthF();
    }

    /**
     * Metoda zjistí aktuální výšku objektu
     *
     * @return aktuální výška objektu
     *
     * @since 0.0.1
     */
    public int getHeight() {
        //
        return size.getHeight();
    }

    @Override
    public void dispose() {
        // Korektní uvolnění alokovaných prostředků 
        location.dispose();
        size.dispose();
    }

}
