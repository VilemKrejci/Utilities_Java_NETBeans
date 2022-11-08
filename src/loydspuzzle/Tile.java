/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loydspuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import utilities.Sprite;

/**
 * Třída reprezentuje jednotlivou kostku hry Loydova patnáctka a implementuje
 * metody pro práci s ní
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Tile extends Sprite {

    /**
     * Obsahuje referenci na společný font všech kostek, tudíž jej žádná z
     * instancí nesmí uvolnit !!!
     *
     * @since 0.0.1
     */
    private static Font font;

    /**
     * Metoda nastaví font, společný všem kostkám třídy
     *
     * @param font reference na font, společný všem kostkám třídy
     *
     * @since 0.0.1
     */
    public static void setFont(Font font) {
        //
        Tile.font = font;
    }

    /**
     * Obsahuje text, zobrazovaný na kostce
     *
     * @since 0.0.1
     */
    private String text;

    /**
     * Obsahuje levou souřadnici vykreslovaného textu
     *
     * @since 0.0.1
     */
    private int textLeft;

    /**
     * Obsahuje horní souřadnici vykreslovaného textu
     *
     * @since 0.0.1
     */
    private int textTop;

    /**
     * Obsahuje referenci na metriku aktuálního fontu
     *
     * @since 0.0.1
     */
    private FontMetrics fontMetrics;

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
    public Tile(float left, float top, float width, float height) {
        // Korektní inicializace bázové třídy
        super(left, top, width, height);
        // Další inicializace, které NetBeans nesnesou v konstruktoru
        init();
    }

    /**
     * Inicializace, které NetBeans nesnese v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        // Nastavení výchozích barev
        setForeground(Color.BLACK);
        setBackground(Color.YELLOW);
    }

    /**
     * Metoda nastaví text, aktuálně zobrazovaný na ploše kostky
     *
     * @param text text, aktuálně zobrazovaný na ploše kostky
     *
     * @since 0.0.1
     */
    public void setText(String text) {
        //
        this.text = text;
    }

    /**
     * Metoda zjistí text, aktuálně zobrazovaný na ploše kostky
     *
     * @return text, aktuálně zobrazovaný na ploše kostky
     *
     * @since 0.0.1
     */
    public String getText() {
        //
        return text;
    }

    @Override
    public void renderTo(Graphics g) {
        // Pokud je aktuální kostka viditelná
        if (isVisible()) {
            // Korektní vykreslení bázové třídy
            super.renderTo(g);
            //
            g.setFont(Tile.font);
            // Inicializace reference na metriku aktuálního fontu
            fontMetrics = g.getFontMetrics();
            // Incializace souřadnic textu
            textLeft = getLeft() + (getWidth() - fontMetrics.stringWidth(text)) / 2;
            textTop = getTop() + (getHeight() + fontMetrics.getAscent() - fontMetrics.getLeading() - fontMetrics.getDescent()) / 2;
            // Vykreslení textu barvou popředí na střed plochy aktuální kostky
            g.setColor(getForeground());
            g.drawString(text, textLeft, textTop);
        }
    }

    @Override
    public void dispose() {
        // Korektní uvolnění alokvaných prostředků
        text = null;
        fontMetrics = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
