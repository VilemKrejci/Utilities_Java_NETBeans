/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loydspuzzle;

import java.awt.Color;
import utilities.TextPanel;

/**
 * Třída reprezentuje panel pro zobrazení počtu spotřebovaných tahů a
 * implementuje metody pro práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class MovesBox extends TextPanel {

    /**
     * Obsahuje výchozí text objektu
     *
     * @since
     */
    private String text;

    /**
     * Obsahuje aktuální počet tahů
     *
     * @since 0.0.1
     */
    private int value;

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
    public MovesBox(float left, float top, float width, float height) {
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
        // Inicializace požadovaných barev
        setForeground(Color.BLACK);
        setBackground(Color.LIGHT_GRAY);
        // Pozadí textu nebude viditelné
        super.setTextBackgroundVisible(false);
        //
        text = getText();
        //
        reset();
    }

    /**
     * Metoda aktualizuje stav indikátoru
     *
     * @since 0.0.1
     */
    public void increment() {
        // Zvýšení aktuální hodnoty
        value++;
        // Aktualizace zobrazovaného textu
        setText(text + value);
    }

    /**
     * Metoda resetuje indikátor
     *
     * @since 0.0.1
     */
    public void reset() {
        // Inicializace hodnoty indikátoru
        value = 0;
        // Aktualizace zobrazovaného textu
        setText(text + value);
    }

    @Override
    public void dispose() {
        // Uvolnění textového objektu
        text = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
