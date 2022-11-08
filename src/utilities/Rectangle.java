/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Třída reprezentuje pravoúhlý zobrazitelný grafický prvek a implementuje
 * metody pro práci s ním. Udržuje informaci o poloze a velikosti, jakož i o
 * viditelnosti a barvě popředí a pozadí objektu
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Rectangle extends Bounds {

    /**
     * Obsahuje true, pokud má být objekt viditelný
     *
     * @since 0.0.1
     */
    private boolean visible;

    /**
     * Obsahuje aktuální barvu popředí objektu
     *
     * @since 0.0.1
     */
    private Color foreground;

    /**
     * Obsahuje aktuální barvu pozadí objektu
     *
     * @since 0.0.1
     */
    private Color background;

    /**
     * Obsahuje poloměr zkřivení rohu objektu v horizontálním směru
     *
     * @since 0.0.1
     */
    protected int arcWidth;

    /**
     * Obsahuje poloměr zkřivení rohu objektu ve vertikálním směru
     *
     * @since 0.0.1
     */
    protected int arcHeight;

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
    public Rectangle(float left, float top, float width, float height) {
        // Korektní inicializace bázové třídy
        super(left, top, width, height);
        // Další inicializace, které NetBeans nesnesou v konstruktoru
        init();
    }

    /**
     * Pokračování inicializací, které NetBeans nesnesou v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        //
        setForeground(Color.BLACK);
        setBackground(Color.LIGHT_GRAY);
        //
        visible = true;
    }

    /**
     * Metoda nastaví viditelnost objektu
     *
     * @param visible true, pokud má být objekt viditelný
     *
     * @since 0.0.1
     */
    public void setVisible(boolean visible) {
        //
        this.visible = visible;
    }

    /**
     * Metoda zjistí, zda je objekt viditelný
     *
     * @return true, pokud je objekt viditelný
     *
     * @since 0.0.1
     */
    public boolean isVisible() {
        //
        return visible;
    }

    /**
     * Metoda nastaví barvu popředí objektu
     *
     * @param foreground barva popředí objektu
     *
     * @since 0.0.1
     */
    public void setForeground(Color foreground) {
        //
        this.foreground = foreground;
    }

    /**
     * Metoda zjistí barvu popředí objektu
     *
     * @return barva popředí objektu
     *
     * @since 0.0.1
     */
    public Color getForeground() {
        //
        return foreground;
    }

    /**
     * Metoda nastaví barvu pozadí objektu
     *
     * @param background barva pozadí objektu
     *
     * @since 0.0.1
     */
    public void setBackground(Color background) {
        //
        this.background = background;
    }

    /**
     * Metoda zjistí barvu pozadí objektu
     *
     * @return barva pozadí objektu
     *
     * @since 0.0.1
     */
    public Color getBackground() {
        //
        return background;
    }

    /**
     * Metoda nastaví poloměry zakřivení rohů objektu
     *
     * @param arcWidth horizontální poloměr zakřivení rohu objektu
     * @param arcHeight vertikální poloměr zakřivení rohu objektu
     *
     * @since 0.0.1
     */
    public void setArcs(int arcWidth, int arcHeight) {
        //
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    /**
     * Metoda zjistí aktuální poloměr zakřivení rohů v horizontálním směru
     *
     * @return aktuální poloměr zakřivení rohů v horizontálním směru
     *
     * @since 0.0.1
     */
    public int getArcWidth() {
        //
        return arcWidth;
    }
    /**
     * Metoda zjistí aktuální poloměr zakřivení rohů ve vertikálním směru
     *
     * @return aktuální poloměr zakřivení rohů ve vertikálním směru
     *
     * @since 0.0.1
     */
    public int getArcHeight() {
        //
        return arcHeight;
    }

    /**
     * Metoda vykreslí objekt do požadovaného grafického kontextu.
     *
     * @param g reference na cílový grafický kontext
     *
     * @since 0.0.1
     */
    public void renderTo(Graphics g) {
        // Pokud je objekt viditelný
        if (visible) {
            // Vykreslení pozadí objektu
            renderBackground(g);
            // Vykreslení popředí objektu
            renderForeground(g);
        }
    }

    /**
     * Metoda vykreslí pozadí objektu příslušnou barvou
     *
     * @param g cílový grafický kontext
     *
     * @since 0.0.1
     */
    protected void renderForeground(Graphics g) {
        //
        g.setColor(getForeground());
        g.drawRoundRect(getLeft(), getTop(), getWidth(), getHeight(), arcWidth, arcHeight);
    }

    /**
     * Metoda vykreslí popředí objektu příslušnou barvou
     *
     * @param g cílový grafický kontext
     *
     * @since 0.0.1
     */
    protected void renderBackground(Graphics g) {
        //
        g.setColor(getBackground());
        g.fillRoundRect(getLeft(), getTop(), getWidth(), getHeight(), arcWidth, arcHeight);
    }

    @Override
    public void dispose() {
        // Uvolnění referencí na alokované objekty
        foreground = background = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
