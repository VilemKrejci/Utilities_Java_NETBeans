/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loydspuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import utilities.Rectangle;

/**
 * Třída reprezentuje indikátor průběhu míchání a implementuje metody pro práci
 * s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
class ShufflingBar extends Rectangle {

    /**
     * Obsahuje referenci na font výpisu textu v objektu
     *
     * @since 0.0.1
     */
    private Font font;

    /**
     * Obsahuje aktuální hodnotu indikátoru
     *
     * @since
     */
    private int value;

    /**
     * Obsahuje maximální hodnotu indikátoru
     *
     * @since 0.0.1
     */
    private int maximum;

    /**
     * Obsahuje aktuální přírůstek indikátoru
     *
     * @since 0.0.1
     */
    private float increment;

    /**
     * Obsahuje aktuální šířku sloupce indikátoru
     *
     * @since 0.0.1
     */
    private float barWidth;

    /**
     * Obsahuje referenci na text, zobrazovaný v indikátoru
     *
     * @since 0.0.1
     */
    private final String text = "Mícháme ...";

    /**
     * Obsahuje aktuální barvu textu
     *
     * @since 0.0.1
     */
    private Color textColor;

    /**
     * Obsahují jednotlivé barevné složky pro konstrukci barvy textu
     *
     * @since 0.0.1
     */
    private int red;
    private int green;
    private int blue;

    /**
     * Obsahuje aktuální hodnotu přírůstku barevných složek
     *
     * @since 0.0.1
     */
    private int colorIncrement;

    /**
     * Obsahuje referenci na metriku fontu
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
    public ShufflingBar(float left, float top, float width, float height) {
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
        // Inicializace maximální hodnoty
        maximum = 100;
        // Inicializace použitých barev
        setBackground(new Color(90, 90, 90, 220));
        setForeground(Color.BLACK);
        // Inicializace přírůstku barevných složek
        colorIncrement = -1;
        // Inicializace barvy textu
        textColor = new Color(155, 9, 14);
        // a jednotlivých jejích složek
        red = textColor.getRed();
        green = textColor.getGreen();
        blue = textColor.getBlue();
        // Inicializace reference na font pro výpis
        font = new Font(Font.SERIF, Font.ITALIC + Font.BOLD, (2 * getHeight()) / 3);
        // Reset indikátoru
        reset();
    }

    /**
     * Metoda nastaví maximální hodnotu indikátoru
     *
     * @param maximum maximální hodnota indikátoru
     *
     * @since 0.0.1
     */
    public void setMaximum(int maximum) {
        // Aktualizace maximální hodnoty
        this.maximum = maximum;
        // Reset indikátoru
        reset();
    }

    /**
     * Metoda resetuje indikátor do výchozího stavu
     *
     * @since 0.0.1
     */
    public void reset() {
        //
        barWidth = 0;
        //
        value = 0;
        //
        increment = getWidthF() / maximum;
    }

    /**
     * Metoda aktualizuje stav indikátoru
     *
     * @since 0.0.1
     */
    public void increment() {
        // Zvýšení aktuální hodnoty
        value++;
        // Zvětšení šířky sloupce indikátoru
        barWidth += increment;
        // Pokud šířka překročila šířku objektu
        if (barWidth >= getWidth()) {
            //
            barWidth = getWidth();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        // Inicializace jednotlivých barevných složek
        red = textColor.getRed();
        green = textColor.getGreen();
        blue = textColor.getBlue();
        // 
        super.setVisible(visible);
    }

    /**
     * Metoda aktualizuje stav objektu po uplynulém časovém intervalu.
     *
     * @param millis počet milisekund od poslední aktualizace
     *
     * @since 0.0.1
     */
    public void updateStateAfter(long millis) {
        // Pokud je objekt viditelný
        if (isVisible()) {
            // Aktualizace jednotlivých barevných složek
            red += colorIncrement;
            green += colorIncrement;
            blue += colorIncrement;
            // Pokud barvy přetekly
            if (red > 255 || green > 255 || blue > 255) {
                // Změna znaménka přírůstku
                colorIncrement = -colorIncrement;
                // Inicializace jednotlivých barevných složek
                red = textColor.getRed();
                green = textColor.getGreen();
                blue = textColor.getBlue();
            }
            // Pokud barvy podtekly
            else if (red < 0 || green < 0 || blue < 0) {
                // Změna znaménka přírůstku
                colorIncrement = -colorIncrement;
                // Korekce jednotlivých barevných složek
                red += colorIncrement;
                green += colorIncrement;
                blue += colorIncrement;
            }
            // Konstrukce barvy vypisovaného textu
            textColor = new Color(red, green, blue);
        }
    }

    @Override
    protected void renderBackground(Graphics g) {
        // Vykreslení aktuálního sloupce indikátoru
        g.setColor(getBackground());
        g.fillRoundRect(getLeft(), getTop(), (int) barWidth, getHeight(), arcWidth, arcHeight);
    }

    @Override
    protected void renderForeground(Graphics g) {
        // Korektní vykreslení bázové třídy
        super.renderForeground(g);
        // Nastavení parametrů výpisu
        g.setColor(textColor);
        g.setFont(font);
        // Výpočet polohy textu v indikátoru
        fontMetrics = g.getFontMetrics();
        int left = getLeft() + (getWidth() - fontMetrics.stringWidth(text)) / 2;
        int top = getTop() + (getHeight() + fontMetrics.getAscent() - fontMetrics.getDescent() - fontMetrics.getLeading()) / 2;
        // Vlastní výpis textu
        g.drawString(text, left, top);
    }

    @Override
    public void dispose() {
        //
        font = null;
        //
        fontMetrics = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
