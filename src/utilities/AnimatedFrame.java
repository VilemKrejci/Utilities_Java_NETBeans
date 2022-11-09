/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package utilities;

import java.awt.Frame;
import java.awt.Graphics;

/**
 * Třída reprezentuje animované okno a implementuje metody pro práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class AnimatedFrame extends Frame {

    /**
     * Reference na animované plátno
     *
     * @since 0.0.1
     */
    private AnimatedCanvas animatedCanvas;

    /**
     * Veřejný parametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @param title reference na titulek okna
     *
     * @since 0.0.1
     *
     */
    public AnimatedFrame(String title) {
        // Korektní inicializace bázové třídy
        super(title);
        // Pokračování inicializací
        init();
    }

    /**
     * Provede inicializace, které NETBeans nesnesou v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        // Okno nebude brát zřetel na požadavky systému na překreslení
        setIgnoreRepaint(true);
        // Inicializace a zařazení animovaného plátna na plochu okna
        animatedCanvas = createAnimatedCanvas();
        add(animatedCanvas);
    }

    /**
     * Metoda vytvoří nové animované plátno
     *
     * @return refrence na nové animované plátno
     *
     * @since 0.0.1
     */
    private AnimatedCanvas createAnimatedCanvas() {
        //
        return new AnimatedCanvas() {

            @Override
            protected void processInputs() {
                //
                AnimatedFrame.this.processInputs();
            }

            @Override
            protected void updateStateAfter(long millis) {
                //
                AnimatedFrame.this.updateStateAfter(millis);
            }

            @Override
            protected void renderTo(Graphics g) {
                //   
                AnimatedFrame.this.renderTo(g);
            }

        };
    }

    /**
     * Metoda spustí animaci plátna na požadované frekvenci
     *
     * @param fps požadovaný počet snímků za sekundu
     *
     * @since 0.0.1
     */
    public void start(float fps) {
        //
        animatedCanvas.start(fps);
    }

    /**
     * Metoda zpracuje případné vstupy
     *
     * @since 0.0.1
     */
    protected void processInputs() {
        //
    }

    /**
     * Metoda aktualizuje stav alikace
     *
     * @param millis uplynulý časový interval
     *
     * @since 0.0.1
     */
    protected void updateStateAfter(long millis) {
        //
    }

    /**
     * Metoda vykreslí aplikaci do požadovaného grafického kontextu
     *
     * @param g cílový grafický kontext
     *
     * @since 0.0.1
     */
    protected void renderTo(Graphics g) {
        //
    }

    @Override
    public void dispose() {
        // Uvolnění animovaného plátna
        animatedCanvas.dispose();
        remove(animatedCanvas);
        animatedCanvas = null;
        // Korektní uvolnění bázové třídy
        super.dispose();
    }

    //**************************************************************************
    // Následující metody byly zablokovány, aby nám systém nekafral do 
    //  překreslování
    //**************************************************************************
    @Override
    public final void paint(Graphics g) {
        //super.paintComponents(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public final void paintComponents(Graphics g) {
        //super.paintComponents(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public final void repaint(long tm, int x, int y, int width, int height) {
        //super.repaint(tm, x, y, width, height); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public final void repaint(int x, int y, int width, int height) {
        //super.repaint(x, y, width, height); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public final void repaint(long tm) {
        //super.repaint(tm); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public final void repaint() {
        //super.repaint(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public final void paintAll(Graphics g) {
        //super.paintAll(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}