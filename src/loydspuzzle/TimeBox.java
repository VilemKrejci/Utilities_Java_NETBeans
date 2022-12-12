/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loydspuzzle;

import java.awt.Color;
import utilities.TextPanel;

/**
 * Třída reprezentuje panel pro zobrazení uplynulého času a implementuje metody
 * pro práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class TimeBox extends TextPanel {

    /**
     * Obsahuje výchozí text objektu
     *
     * @since 0.0.1
     */
    private String text;

    /**
     * Obsahuje aktuální počet uplynulých setin sekundy
     *
     * @since 0.0.1
     */
    private long hundredths;

    /**
     * Obsahuje aktuální počet uplynulých sekund
     *
     * @since 0.0.1
     */
    private long seconds;

    /**
     * Obsahuje aktuální počet uplynulých minut
     *
     * @since 0.0.1
     */
    private long minutes;

    /**
     * Obsahuje okamžik spuštění čítače
     *
     * @since 0.0.1
     */
    private long startTime;

    /**
     * Obsahuje počet milisekund od spuštění
     *
     * @since 0.0.1
     */
    private long elapsedTime;

    /**
     * Obsahuje true, pokud byl panel zobrazení času spuštěn
     *
     * @since 0.0.1
     */
    private boolean running;

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
    public TimeBox(float left, float top, float width, float height) {
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
        // Inicializace výchozího textu
        text = getText();
        // Reset časovače
        reset();
    }

    /**
     * Metoda restuje časovač
     *
     * @since 0.0.1
     */
    public void reset() {
        // Zastavení časovače
        stop();
        // Inicializace uplynulého času
        elapsedTime = 0;
        hundredths = 0;
        seconds = 0;
        minutes = 0;
        // Aktualizace zobrazovaného textu na základě uplynulého času
        setText(text + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%02d", hundredths));
    }

    /**
     * Metoda spustí časovač
     *
     * @since 0.0.1
     */
    public void start() {
        //
        startTime = System.currentTimeMillis();
        //
        running = true;
    }

    /**
     * Metoda zastaví časovač
     *
     * @since 0.0.1
     */
    public void stop() {
        //
        running = false;
    }

    /**
     * Metoda zjistí, zda časovač běží
     *
     * @return true, pokud časovač běží
     *
     * @since 0.0.1
     */
    public boolean isRunnig() {
        //
        return running;
    }

    /**
     * Metoda aktualizuje stav objektu po uplynulém časovém intervalu.
     *
     * @param millis počet milisekund od poslední aktualizace
     *
     * @since 0.0.1
     */
    public void updateStateAfter(long millis) {
        // Pokud časovač běží,
        if (running) {
            // aktualizuje se uplynulý čas
            elapsedTime = System.currentTimeMillis() - startTime;
            // a zobrazované hodnoty
            millis = elapsedTime / 10;
            hundredths = millis % 100;
            millis /= 100;
            seconds = millis % 60;
            millis /= 60;
            minutes = millis % 60;
            // Aktualizace zobrazovaného textu na základě uplynulého času
            setText(text + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%02d", hundredths));
        }
    }

    @Override
    public void dispose() {
        // Korektní uvolnění alokovaných prostředků
        text = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
