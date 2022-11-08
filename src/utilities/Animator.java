/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 * Třída reprezentuje animátor, běžící na požadovaném počtu snímků za sekundu a
 * implementuje metody pro práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Animator extends MyObject implements Runnable {

    /**
     * True, pokud animační vlákno běží
     *
     * @since 0.0.1
     */
    private boolean running;

    /**
     * True, pokud je animátor spouštěn z metody start() - ochrana před
     * spuštěním z vnějšku přes metodu run()
     *
     * @since 0.0.1
     */
    private boolean fromStart;

    /**
     * Obsahuje referenci na vlastní animační vlákno
     *
     * @since 0.0.1
     */
    private Thread animationThread;

    /**
     * Obsahuje počet milisekund, na který by mělo být animační vlákno uspáno po
     * každém snímku
     *
     * @since 0.0.1
     */
    private long sleepTime;

    /**
     * Veřejný bezparametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @since 0.0.1
     */
    public Animator() {
        // Korektní inicializace bázové třídy
        super();
        //
    }

    /**
     * Metoda vytvoří a spustí animační vlákno
     *
     * @param fps požadovaný počet snímků ("tiků") za sekundu
     *
     * @since 0.0.1
     */
    public void start(float fps) {
        // Pokud animační vlákno dosud nebylo inicializováno nebo neběží
        if (animationThread == null || !animationThread.isAlive()) {
            // Počet milisekund pro uspání vlákna
            sleepTime = (long) (1000f / fps);
            // Inicializace reference na animační vlákno
            animationThread = new Thread(this);
            // Vlákno bylo spuštěno z této metody
            fromStart = true;
            // Vlastní spuštění animačního vlákna
            animationThread.start();
        }
    }

    /**
     * Metoda zjistí, zda animační vlákno "žije"
     *
     * @return True, pokud animační vlákno běží
     *
     * @since 0.0.1
     */
    public boolean isRunning() {
        //
        return running;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public final void run() {
        // Pokud vlákno dosud neběží 
        if (!running) {
            // a pokud je spouštěno z metody start()
            if (fromStart) {
                // shození příznaku spouštění z metody start()
                fromStart = false;
                // Vlákno bylo spuštěno
                running = true;
                // Inicializace pracovních proměnných
                long frameStartsAt;
                long deltaT = 0;
                // Animátor "tiknul" po požadovaném intervalu
                tickAfter(deltaT);
                // Dokud vlákno běží
                while (running) {
                    // Záznam okamžiku startu aktuálního snímku
                    frameStartsAt = System.currentTimeMillis();
                    // Animátor "tiknul" po požadovaném intervalu
                    tickAfter(deltaT);
                    // Zohlednění doby zpracování "tiku" animátoru
                    deltaT = sleepTime + frameStartsAt - System.currentTimeMillis();
                    // Pokud po zpracování "tiku" zbyl nějaký čas
                    if (deltaT > 0) {
                        // V následujícím bloku kódu může nastat výjimka
                        try {
                            // Pokus o uspání vlákna na zbývající dobu
                            Thread.sleep(deltaT);
                        }
                        // Vlákno bylo přerušeno - patrně končíme
                        catch (InterruptedException ex) {
                            // a tudíž je třeba vlákno zastavit
                            running = false;
                        }
                    }
                    // Výpočet doby trvání aktuálního snímku
                    deltaT = System.currentTimeMillis() - frameStartsAt;
                }
            }
        }
    }

    /**
     * Chráněná metoda je volána při každém "tiku" animačního vlákna. Potomek ji
     * může předefinovat a tak na tuto událost reagovat
     *
     * @param millis počet milisekund od posledního "tiku"
     *
     * @since 0.0.1
     */
    protected synchronized void tickAfter(long millis) {
    }

    /**
     * Metoda zastaví běh animačního vlákna
     *
     * @since 0.0.1
     */
    public synchronized void stop() {
        // Animační vlákno neběží
        //running = false;
        // Pokud animační vlákno žije
        if (animationThread.isAlive()) {
            // přerušíme jeho běh
            animationThread.interrupt();
            //
            try {
                // a počkáme na jeho doběhnutí
                animationThread.join();
            }
            catch (InterruptedException ex) {
                //
            }
        }
    }

    @Override
    public void dispose() {
        // Korektní uvolnění alokovaných prostředků 
        stop();
    }

}
