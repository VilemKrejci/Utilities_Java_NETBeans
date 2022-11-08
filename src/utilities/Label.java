/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

/**
 * Třída retrezentuje zobrazitelný jednořádkový text a implementuje metody pro
 * práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Label extends Rectangle {

    /**
     * Obsahuje referenci na skrytý obrázek pro výpočet parametrů zmenšení
     * pozadí textu - je pro celou třídu společný a tudíž jej instance nesmí
     * uvolnit !!!
     *
     * @since 0.0.1
     */
    private static BufferedImage image;

    /**
     * Obsahuje referenci na metriku fontu
     *
     * @since 0.0.1
     */
    private FontMetrics fontMetrics;

    /**
     * Obsahuje levou souřadnici vypisovaného textu
     *
     * @since 0.0.1
     */
    private int left;

    /**
     * Obsahuje horní souřadnici vypisovaného textu
     *
     * @since 0.0.1
     */
    private int top;

    /**
     * Reference na zobrazovaný řetězec
     *
     * @since 0.0.1
     */
    private String text;

    /**
     * Obsahuje referenci na font výpisu
     */
    private Font font;

    /**
     * True, pokud má být pozadí objektu viditelné
     * 
     * @since 0.0.1
     */
    private boolean backgroundVisible;
    
    /**
     * Veřejný parametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @param text reference na zobrazovaný text
     * @param left výchozí levá souřadnice polohy objektu
     * @param top výchozí horní souřadnice polohy objektu
     * @param width výchozí šířka objektu
     * @param height výchozí výška objektu
     *
     * @since 0.0.1
     */
    public Label(String text, float left, float top, float width, float height) {
        // Korektní inicializace bázové třídy
        super(left, top, width, height);
        // Prvotní inicializace fontu
        font = new Font(Font.SERIF, Font.BOLD, (int) height);
        // Prvotní inicializace textu
        this.text = text;
        // Další inicializace, které NetBeans nesnesou v konstruktoru
        init();
    }

    /**
     * Pokračování inicializací, které NetBeans nesnesou v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        // Vytvoření fiktivního obrázku
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        image = gc.createCompatibleImage(1, 1);
        // Inicializace výchozích barev objektu
        setForeground(Color.BLACK);
        setBackground(Color.ORANGE);
        // Zmenšení pozadí objektu
        pack();
    }

    /**
     * Metoda upraví velikost pozadí textu tak, aby nikde nepřesahovalo více,
     * než je nutné
     *
     * @since 0.0.1
     */
    private void pack() {
        // Reference na grafický kontext fiktivního obrázku
        Graphics g = image.getGraphics();
        // Úschova původního fontu
        Font f = g.getFont();
        // Zjištění plochy, zabrané řetězcem v příslušném fontu
        g.setFont(font);
        fontMetrics = g.getFontMetrics();
        Dimension d = fontMetrics.getStringBounds(text, g).getBounds().getSize();
        // Obnova původního fontu
        g.setFont(f);
        // Uvolnění reference na fiktivní obrázek
        g.dispose();
        // Nastavení velikosti objektu
        super.setSize(d.width, d.height);
    }

    /**
     * Metoda nastaví zobrazovaný text
     *
     * @param text zobrazovaný text
     *
     * @since 0.0.1
     */
    public void setText(String text) {
        //
        this.text = text;
        // Aktualizace velikosti objektu
        pack();
    }

    @Override
    public void setSize(float width, float height) {
        //
        font = new Font(Font.SERIF, Font.BOLD, (int) height);
        // Aktualizace velikosti objektu
        pack();
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
        font = new Font(Font.SERIF, Font.BOLD, (int) height);
        // Aktualizace velikosti objektu
        pack();
    }

    /**
     * Metoda zjistí zobrazovaný text
     *
     * @return text zobrazovaný text
     *
     * @since 0.0.1
     */
    public String getText() {
        //
        return text;
    }

    /**
     * Metoda nastaví aktuální font výpisu
     *
     * @param font požadovaný font výpisu
     *
     * @since 0.0.1
     */
    public void setFont(Font font) {
        //
        this.font = font;
        // Aktualizace velikosti objektu
        pack();
    }

    /**
     * Metoda zjistí aktuální font výpisu
     *
     * @return font aktuální font výpisu
     *
     * @since 0.0.1
     */
    public Font getFont() {
        //
        return font;
    }

    /**
     * Metoda nastaví viditelnot pozadí objektu
     *
     * @param backgroundVisible true, pokud má být pozadí objektu viditelné
     *
     * @since 0.0.1
     */
    public void setBackgroundVisible(boolean backgroundVisible) {
        //
        this.backgroundVisible = backgroundVisible;
    }

    /**
     * Metoda zjistí viditelnot pozadí objektu
     *
     * @return true, pokud je pozadí objektu viditelné
     *
     * @since 0.0.1
     */
    public boolean isBackgroundVisible() {
        //
        return backgroundVisible;
    }

    @Override
    protected void renderBackground(Graphics g) {
        // Pokud má být pozadí textu viditelné,
        if (isBackgroundVisible()) {
            // bude vykresleno
            super.renderBackground(g);
        }
    }

    @Override
    protected void renderForeground(Graphics g) {
        // Korektní vykreslení bázové třídy
        //super.renderForeground(g);
        // Nastavení parametrů výpisu
        g.setColor(getForeground());
        g.setFont(font);
        // Výpočet polohy textu v indikátoru
        fontMetrics = g.getFontMetrics();
        left = getLeft() + (getWidth() - fontMetrics.stringWidth(text)) / 2;
        top = getTop() + (getHeight() + fontMetrics.getAscent() - fontMetrics.getDescent() - fontMetrics.getLeading()) / 2;
        // Vlastní výpis textu
        g.drawString(text, left, top);
    }

    @Override
    public void dispose() {
        // Korektní uvolnění alokovaných prostředků 
        font = null;
        fontMetrics = null;
        text = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
