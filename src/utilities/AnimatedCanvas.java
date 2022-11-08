/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Třída reprezentuje plátno, animované požadovaným počtem snímků za sekundu a
 * implementuje metody pro práci s ním. Pokud to konfigurace umožňuje, grafika
 * je akcelerovaná a bufferovaná
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class AnimatedCanvas extends Canvas {

    /**
     * Obsahuje referenci na animátor plátna
     *
     * @since 0.0.1
     */
    private Animator animator;

    /**
     * Obsahuje referenci na buffer videokarty
     *
     * @since 0.0.1
     */
    private BufferStrategy bufferStrategy;

    /**
     * Obsahuje referenci na výstupní grafický kontext
     *
     * @since 0.0.1
     */
    private Graphics graphics;

    /**
     * Obsahuje výchozí barvu grafického kontextu
     *
     * @since 0.0.1
     */
    private Color color;

    /**
     * Obsahuje výchozí font grafického kontextu
     *
     * @since 0.0.1
     */
    private Font font;

    /**
     * Obsahuje aktuální počet snímků za sekundu
     *
     * @since 0.0.1
     */
    private float fps = 30f;

    private boolean diagnostic;

    /**
     * Veřejný bezparametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @since 0.0.1
     */
    public AnimatedCanvas() {
        // Korektní inicializace bázové třídy
        super();
        // Inicializace, které NetBeans nesnese v konstruktoru
        init();
    }

    /**
     * Inicializace, které NetBeans nesnese v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        // Inicializace reference na animátor
        animator = createAnimator();
    }

    public void setDiagnostic(boolean diagnostic) {
        //
        this.diagnostic = diagnostic;
    }

    public boolean isDiagnostic() {
        //
        return diagnostic;
    }

    @Override
    public void setBackground(Color c) {
        //
        super.setBackground(c);
    }

    /**
     * Vrátí referenci na animátor plátna
     *
     * @return reference na animátor plátna
     *
     * @since 0.0.1
     */
    private Animator createAnimator() {
        //
        return new Animator() {

            private long methodStartsAt;
            private long iterations;
            private long interval;
            private long consumption;

            @Override
            protected void tickAfter(long millis) {
                //**************************************************************
                methodStartsAt = System.currentTimeMillis();
                interval += millis;
                if (interval >= 1000) {
                    //
                    if (diagnostic) {
                        //
                        System.out.println("Průměr za poslední sekundu = " + (consumption / iterations));
                        System.out.println("Consumption = " + consumption);
                        System.out.println("Iterations = " + iterations);
                    }
                    //
                    interval = 0;
                    iterations = 0;
                    consumption = 0;
                }
                // Zpracování případných vstupů
                processInputs();
                // Aktualizace stavu po uplynulém časovém intervalu
                updateStateAfter(millis);
                // Aktualizace reference na grafický buffer
                bufferStrategy = getBufferStrategy();
                // Aktualizace reference na grafický kontext bufferu
                graphics = bufferStrategy.getDrawGraphics();
                // Úschova výchozích objektů
                color = graphics.getColor();
                font = graphics.getFont();
                // Vykreslení aktuálního stavu do bufferu
                renderTo(graphics);
                // Obnova výchozích objektů
                graphics.setFont(font);
                graphics.setColor(color);
                // Uvolnění grafického kontextu bufferu
                graphics.dispose();
                // Synchronizace zobrazení
                bufferStrategy.show();
                //**************************************************************
                consumption += (System.currentTimeMillis() - methodStartsAt);
                iterations++;
            }

        };
    }

    /**
     * Metoda zpracuje položku z fronty vstupů. Potomek ji může předefinovat a
     * reagovat tak na příslušnou událost
     *
     * @since 0.0.1
     */
    protected void processInputs() {
    }

    /**
     * Metoda aktualizuje stav aplikace po uplynulém časovém intervalu.Potomek
     * ji může předefinovat a reagovat tak na příslušnou událost
     *
     * @param millis počet milisekund od poslední aktualizace
     *
     * @since 0.0.1
     */
    protected void updateStateAfter(long millis) {
    }

    /**
     * Metoda vykreslí aktuální stav aplikace do požadovaného grafického
     * kontextu.Potomek ji může předefinovat a reagovat tak na příslušnou
     * událost
     *
     * @param g reference na cílový grafický kontext
     *
     * @since 0.0.1
     */
    protected void renderTo(Graphics g) {
    }

    /**
     * Metoda spustí animátor plátna
     *
     * @param fps požadovaný počet snímků za sekundu
     *
     * @since 0.0.1
     */
    public void start(float fps) {
        // Pokud animační vlákno neběží
        if (!animator.isRunning()) {
            //
            this.fps = fps;
            // Inicializace reference na grafický buffer
            bufferStrategy = getBufferStrategy();
            // Pokud existuje grafický buffer
            if (bufferStrategy != null) {
                // bude uvolněn
                bufferStrategy.dispose();
            }
            // Alokace nového grafického bufferu
            createBufferStrategy(2);
            // Vlastní spuštění animátoru
            animator.start(fps);
        }
    }

    /**
     * Metoda pozastaví běh animátoru plátna
     *
     * @since 0.0.1
     */
    public void pause() {
    }

    /**
     * Metoda obnoví běh animátoru plátna
     *
     * @since 0.0.1
     */
    public void resume() {
        //
        start(fps);
    }

    /**
     * Metoda ukončí animátor plátna
     *
     * @since 0.0.1
     */
    public void stop() {
        //
        animator.stop();
    }

    public void dispose() {
        // Korektní uvolnění alokovaných prostředků
        animator.dispose();
        // Inicializace reference na grafický buffer
        bufferStrategy = getBufferStrategy();
        // Pokud existuje grafický buffer
        if (bufferStrategy != null) {
            // bude uvolněn
            bufferStrategy.dispose();
        }
        //
        color = null;
        font = null;
        graphics = null;
    }

    //**************************************************************************
    // Následující metody byly zablokovány, aby nám systém nekafral do 
    //  překreslování
    //**************************************************************************
    @Override
    public final void paint(Graphics g) {
    }

    @Override
    public final void repaint(long tm, int x, int y, int width, int height) {
    }

    @Override
    public final void repaint(int x, int y, int width, int height) {
    }

    @Override
    public final void repaint(long tm) {
    }

    @Override
    public final void repaint() {
    }

    @Override
    public final void paintAll(Graphics g) {
    }

}
