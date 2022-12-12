/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loydspuzzle;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;
import utilities.AnimatedCanvas;

/**
 * Třída reprezentuje jednoduchou hru Loydova patnáctka a implementuje metody
 * pro práci s ní
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class LoydsPuzzle extends Frame implements AWTEventListener {

    /**
     * Obsahuje true, pokud je aplikace právě spouštěna
     *
     * @since 0.0.1
     */
    private static boolean justStarted;

    /**
     * Metoda načte lokalizovaný text pro zadanou třídu
     *
     * @param objectClass reference na příslušnou třídu
     *
     * @return lokalizovaný text
     *
     * @since 0.0.1
     */
    private static String getLocalText(Class myClass) {
        // Inicializace návratové hodnoty
        StringBuilder retValue = new StringBuilder(":-((");
        // Podle aktuálního jazyka vytvoří jméno souboru zdroje
        StringBuilder fileName = new StringBuilder("" + myClass.getSimpleName() + "_");
        fileName.append(Locale.getDefault().toString().equals("cs_CZ") ? "CZ" : "EN");
        fileName.append(".txt");
        // Pokus o otevření textového souboru
        InputStream is = myClass.getResourceAsStream(fileName.toString());
        // Pokud sobor s textem existuje
        if (is != null) {
            // 
            Scanner scanner = new Scanner(is);
            //
            if (scanner.hasNext()) {
                // 
                retValue.delete(0, retValue.length());
                retValue.append(scanner.nextLine());
                retValue.append(" ");
            }
            //
            scanner.close();
        }
        //
        return retValue.toString();
    }

    /**
     * Metoda provede vlastní spuštění aplikace
     *
     * @param args argumenty příkazového řádku
     *
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//        try {
//            // 
//            SwingUtilities.invokeAndWait(new Runnable() {
//
//                @Override
//                public void run() {
        //
        LoydsPuzzle lp = new LoydsPuzzle();
        //lp.setBounds(200, 200, 600, 600);
        //lp.setPreferredSize(new Dimension(600, 600));
        //
        //lp.setVisible(true);
        //
        //lp.pack();
        //
        //LoydsPuzzle.fromMain = true;
        //
        lp.setLocationRelativeTo(null);
        lp.start(60f);
//                }
//            });
//        }
//        //
//        catch (InvocationTargetException ex) {
//            //Logger.getLogger(LoydsPuzzle.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Obsahuje referenci na animované plátno
     *
     * @since 0.0.1
     */
    private AnimatedCanvas animatedCanvas;

    /**
     * Obsahuje referenci na krabičku kostek
     *
     * @since 0.0.1
     */
    private TileBox tileBox;

    /**
     * Obsahuje referenci na panel zobrazení spotřebovaného času
     *
     * @since 0.0.1
     */
    private TimeBox timeBox;

    /**
     * Obsahuje referenci na panel zobrazení spotřebovaných tahů
     *
     * @since 0.0.1
     */
    private MovesBox movesBox;

    private HelpFrame helpFrame;

    /**
     * Soukromý bezparametrický konstruktor vytvoří a inicializuje novou
     * instanci
     *
     * @since 0.0.1
     */
    private LoydsPuzzle() {
        // Korektní inicializace bázové třídy
        super();
        // Aplikace je právě spouštěna
        justStarted = true;
        // Pokračování inicializací
        init();
    }

    /**
     * Metoda provede inicializace, které NETBeans nesnáší v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        //
        helpFrame = new HelpFrame(this);
        // Nastavení titulku okna
        this.setTitle(getLocalText(getClass()));
        // Příjemcem zráv okna bude tato instance
        this.getToolkit().addAWTEventListener(this, AWTEvent.WINDOW_EVENT_MASK);
        // Okno bude ignorovat systémové požadavky na překreslení
        setIgnoreRepaint(true);
        // Uživatel nebude mít možnost měnit velikost okna hry
        setResizable(false);
        //setUndecorated(true);
        // Inicializace reference na animované plátno
        animatedCanvas = createAnimatedCanvas();
        animatedCanvas.setBackground(new Color(6, 86, 118));
        add(animatedCanvas);
        // Musí být pack nebo visible, jinak nesedí insety
        //pack();
        setVisible(true);
        // Inicializace rozložení zobrazovaných prvků
        initLayout(400, 400);
    }

    /**
     * Metoda inicializuje rozměry zobrazovaných herních prvků na základě zadané
     * velikosti krabičky kostek
     *
     * @param width výchozí šířka krabičky kostek
     * @param height výchozí výška krabičky kostek
     *
     * @since 0.0.1
     */
    private synchronized void initLayout(int width, int height) {
        // Počáteční inicializace pracovních proměnných
        Insets i = getInsets();
        int spacer = 10;
        int left = spacer;
        int top = spacer;
        int height0 = 100;
        //----------------------------------------------------------------------
        // Pokud reference na panel zobrazení času nebyla dosud inicializována
        if (timeBox == null) {
            // Inicializace reference na panel zobrazení času
            timeBox = new TimeBox(left, top, width, height0);
            timeBox.setVisible(true);
        }
        // Reference již existuje, 
        else {
            // nastaví se pouze poloha a velikost
            timeBox.setBounds(left, top, width, height0);
        }
        // Font se však nastaví v obou případech
        timeBox.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, (2 * timeBox.getHeight()) / 5));
        //----------------------------------------------------------------------
        // Pokud reference na na krabičku kostek nebyla dosud inicializována
        if (tileBox == null) {
            // Inicializace reference na krabičku kostek
            tileBox = createTileBox(left, timeBox.getBottom() + spacer, width, height);
            tileBox.setVisible(true);
        }
        // Reference již existuje, 
        else {
            // nastaví se pouze poloha a velikost
            tileBox.setBounds(left, timeBox.getBottom() + spacer, width, height);
        }
        //----------------------------------------------------------------------
        // Pokud reference na panel spotřebovaných tahů nebyla dosud inicializována
        if (movesBox == null) {
            // Inicializace reference na panel spotřebovaných tahů
            movesBox = new MovesBox(left, tileBox.getBottom() + spacer, width, height0);
            movesBox.setVisible(true);
        }
        // Reference již existuje, 
        else {
            // nastaví se pouze poloha a velikost
            movesBox.setBounds(left, tileBox.getBottom() + spacer, width, height0);
        }
        // Font se však nastaví v obou případech
        movesBox.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, (2 * movesBox.getHeight()) / 5));
        //----------------------------------------------------------------------
        // Inicializace zaoblení rohů panelů
        timeBox.setArcs(tileBox.getArcWidth(), tileBox.getArcHeight());
        movesBox.setArcs(tileBox.getArcWidth(), tileBox.getArcHeight());
        // Inicializace velikosti okna aplikace
        width = i.left + movesBox.getRight() + spacer + i.right;
        height = i.top + movesBox.getBottom() + spacer + i.bottom;
        setPreferredSize(new Dimension(width, height));
        // Aktualizace velikosti okna aplikace
        pack();
    }

    @Override
    public final void eventDispatched(AWTEvent event) {
        // Pokud je požadavek na ukončení aplikace,
        if (event.getID() == WindowEvent.WINDOW_CLOSING) {
            // uvolníme její hlavní okno
            dispose();
        }
        // Pokud bylo okno hry aktivováno
        if (event.getID() == WindowEvent.WINDOW_ACTIVATED) {
            // a není povoleno,
            if (!isEnabled()) {
                // bude aktivní okno nápovědy
                helpFrame.requestFocus();
            }
        }
    }

    /**
     * Metoda vytvoří nové animované plátno
     *
     * @return reference na nové animované plátno
     *
     * @since 0.0.1
     */
    private AnimatedCanvas createAnimatedCanvas() {
        //
        return new AnimatedCanvas() {

            @Override
            protected void processInputs() {
            }

            @Override
            protected void updateStateAfter(long millis) {
                // Aktualizace stavu herních objektů
                tileBox.updateStateAfter(millis);
                timeBox.updateStateAfter(millis);
            }

            @Override
            protected void renderTo(Graphics g) {
                // Vykreslení herních objektů do grafického bufferu
                tileBox.renderTo(g);
                timeBox.renderTo(g);
                movesBox.renderTo(g);
            }

        };
    }

    /**
     * Metoda vytvoří novou krabičku kostek
     *
     * @param left výchozí levá souřadnice polohy objektu
     * @param top výchozí horní souřadnice polohy objektu
     * @param width výchozí šířka objektu
     * @param height výchozí výška objektu
     * @return reference na novou krabičku kostek
     *
     * @since 0.0.1
     */
    private TileBox createTileBox(float left, float top, float width, float height) {
        //
        return new TileBox(left, top, width, height) {

            @Override
            protected void tileBoxResized() {
                //
                animatedCanvas.stop();
                //
                initLayout(this.getWidth(), this.getHeight());
                //System.out.println("kuk ...");
                //
                animatedCanvas.resume();
            }

            @Override
            protected void moveMade() {
                //
                movesBox.increment();
                //
                if (!timeBox.isRunnig()) {
                    //
                    timeBox.start();
                }
            }

            @Override
            protected void shufflingFinished() {
                //
                movesBox.reset();
                //
                timeBox.reset();
            }

            @Override
            protected void puzzleResolved() {
                //
                timeBox.stop();
            }

            @Override
            protected void processKeyEvent(KeyEvent e) {
                // Na základě stisknuté klávesy
                switch (e.getKeyCode()) {
                    // Pokud je stisknuta klávesa Escape
                    case KeyEvent.VK_ESCAPE:
                        // a není zobrazena nápověda,
                        if (!helpFrame.isVisible()) {
                            // ukončíme aplikaci
                            LoydsPuzzle.this.dispose();
                        }
//                        // Nápověda je zobrazena
//                        else {
//                            // tak ji skryjeme
//                            helpFrame.setVisible(false);
//                            // a vstupy z klávesnice a myši nebudou ignorovány
//                            tileBox.setIgnoreInputs(false);                            
//                            // a pokud je aplikace právě spouštěna
//                            if (fromMain) {
//                                //
//                                fromMain = false;
//                                // Zamíchání krabičky kostek
//                                shuffle(getShuffleMoves());
//                            }
//                        }
                        //
                        break;
                    // Pokud byla stisknuta klávesa M
                    case KeyEvent.VK_M:
                        // a není zobrazena nápověda,
                        if (!helpFrame.isVisible()) {
                            // Vynulování ukazatelů
                            timeBox.reset();
                            movesBox.reset();
                            // Zamíchání krabičky kostek
                            shuffle(getShuffleMoves());
                        }
                        //
                        break;
                    //
                    case KeyEvent.VK_I:
                        //
                        if (!this.isShuffling() && !helpFrame.isVisible()) {
                            //
                            initTiles(3, 3);
                            //
                            movesBox.reset();
                            //
                            timeBox.reset();
                        }
                        //
                        break;
                    //
                    case KeyEvent.VK_T:
                        //
                        animatedCanvas.setDiagnostic(!animatedCanvas.isDiagnostic());
                        //
                        break;
                    // Pokud byla stisknuta klávesa pro nápovědu
                    case KeyEvent.VK_F1:
//                        // a nápověda je zobrazena,
//                        if (helpFrame.isVisible()) {
//                            // pak bude skryta
//                            helpFrame.setVisible(false);
//                            // a vstupy z klávesnice a myši nebudou ignorovány
//                            tileBox.setIgnoreInputs(false);
//                            // a pokud je aplikace právě spouštěna
//                            if (fromMain) {
//                                //
//                                fromMain = false;
//                                // Zamíchání krabičky kostek
//                                shuffle(getShuffleMoves());
//                            }
//                        }
                        // Nápověda zobrazena není -
//                        else {
                        // vstupy klávesnice a myši budou ignorovány
                        //tileBox.setIgnoreInputs(true);
                        // a nápověda bude viditelná
                        helpFrame.setVisible(true);
                        // Vystředění nápovědného okna na klientskou plochu aplikace
                        helpFrame.setLocationRelativeTo(LoydsPuzzle.this);
                        //
                        //helpFrame.requestFocus();
                        //
                        //LoydsPuzzle.this.setFocusable(false);// .setEnabled(false);
//                        }
                        //
                        break;
                }
            }

        };
    }

    /**
     * Metoda spustí animaci plátna
     *
     * @param fps požadovaný počet snímků za sekundu
     *
     * @since 0.0.1
     */
    private void start(float fps) {
        // Spuštění animační smyčky okna
        animatedCanvas.start(fps);
        // Pokud je aplikace právě spouštěna,
        if (justStarted) {
            // bude nejprve zobrazena nápověda
            helpFrame.setVisible(true);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        //
        super.setEnabled(enabled);
        // Pokud je okno hry aktivováno a hra je právě spouštěna,
        if (enabled && justStarted) {
            // bude třeba ji poprvé zamíchat
            tileBox.shuffle(getShuffleMoves());
            // Nadále už se po skrytí nápovědy hra míchat nebude
            justStarted = false;
        }
    }

    /**
     * Metoda spočte, kolik tahů je potřeba pro zamíchání krabičky
     *
     * @return počet tahů pro zamíchání krabičky
     *
     * @since 0.0.1
     */
    private int getShuffleMoves() {
        //
        return 7 * tileBox.getRows() * tileBox.getCols();
    }

    @Override
    public final synchronized void dispose() {
        // Instance nadále nebude přijímat události
        getDefaultToolkit().removeAWTEventListener(this);
        // Korektní uvolnění alokovaných prostředků 
        animatedCanvas.stop();
        remove(animatedCanvas);
        animatedCanvas.dispose();
        // Uvolnění herních objektů
        tileBox.dispose();
        tileBox = null;
        timeBox.dispose();
        timeBox = null;
        movesBox.dispose();
        movesBox = null;
        //
        helpFrame.dispose();
        // Korektní uvolnění prostředků bázové třídy
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
