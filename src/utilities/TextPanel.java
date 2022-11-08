/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Třída reprezentuje jednořádkový textový panel a implementuje metody pro práci
 * s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class TextPanel extends Rectangle {

    /**
     * Obsahuje referenci na objekt textu
     *
     * @since 0.0.1
     */
    private Label label;

    /**
     * Pracovní proměnné, aby nebylo nutno při každém renderování je znovu
     * definovat
     *
     * @since 0.0.1
     */
    private int left;
    private int top;

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
    public TextPanel(float left, float top, float width, float height) {
        // Korektní inicializace bázové třídy
        super(left, top, width, height);
        //
        label = new Label("", left, top, width, height);
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
    }

    /**
     * Metoda nastaví požadovaný font zobrazení
     *
     * @param font požadovaný font zobrazení
     *
     * @since 0.0.1
     */
    public void setFont(Font font) {
        //
        label.setFont(font);
    }

    @Override
    public void setVisible(boolean visible) {
        //
        super.setVisible(visible);
        label.setVisible(visible);
    }

    /**
     * Metoda nastaví viditelnot pozadí textu
     *
     * @param backgroundVisible true, pokud má být pozadí textu viditelné
     *
     * @since 0.0.1
     */
    public void setTextBackgroundVisible(boolean backgroundVisible) {
        //
        label.setBackgroundVisible(backgroundVisible);
    }

    /**
     * Metoda zjistí viditelnot pozadí textu
     *
     * @return true, pokud je pozadí textu viditelné
     *
     * @since 0.0.1
     */
    public boolean isTextBackgroundVisible() {
        //
        return label.isBackgroundVisible();
    }

    @Override
    public void setBounds(float left, float top, float width, float height) {
        //
        super.setBounds(left, top, width, height);
        //
        label.setSize(width, height);
    }

    /**
     * Metoda nastaví zobrazovaný text
     *
     * @param text reference na zobrazovaný text
     *
     * @since 0.0.1
     */
    public void setText(String text) {
        //
        label.setText(text);
    }

    /**
     * Metoda zjistí zobrazovaný text
     *
     * @return text reference na zobrazovaný text
     *
     * @since 0.0.1
     */
    public String getText() {
        //
        return label.getText();
    }

    @Override
    protected void renderForeground(Graphics g) {
        // Korektní vykreslení bázové třídy
        super.renderForeground(g);
        // Výpočet polohy textu v objektu
        left = getLeft() + (getWidth() - label.getWidth()) / 2;
        top = getTop() + (getHeight() - label.getHeight()) / 2;
        label.setLocation(left, top);
        // Vykreslení textu na požadovanou pozici
        label.renderTo(g);
    }

    @Override
    public void dispose() {
        // Uvolnění textového objektu
        label.dispose();
        label = null;
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
