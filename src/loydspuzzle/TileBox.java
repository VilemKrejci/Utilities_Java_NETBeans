/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loydspuzzle;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import utilities.Location;
import utilities.Randomizer;
import utilities.Rectangle;

/**
 * Třída reprezentuje krabičku kostek hry Loydova patnáctka a implementuje
 * metody pro práci s ní
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class TileBox extends Rectangle implements AWTEventListener {

    /**
     * Třída reprezentuje možné směry pohybu kostky
     *
     * @version 0.0.1
     */
    private static enum Direction {
        //
        NONE,
        //
        LEFT,
        //
        UP,
        //
        RIGHT,
        //
        DOWN;

        /**
         * Metoda vygeneruje
         *
         * @return
         */
        public static Direction getRandomDirection() {
            //
            return NONE;
        }

        /**
         * Metoda vrátí směr opačný k aktuálnímu
         *
         * @return směr opačný k aktuálnímu
         *
         * @since 0.0.1
         */
        public Direction getOppositeDirection() {
            // Inicializace návratové proměnné
            Direction retValue = NONE;
            // Na základě zadaného směru
            switch (this) {
                //
                case LEFT:
                    //
                    retValue = RIGHT;
                    //
                    break;
                //
                case RIGHT:
                    //
                    retValue = LEFT;
                    //
                    break;
                //
                case UP:
                    //
                    retValue = DOWN;
                    //
                    break;
                //
                case DOWN:
                    //
                    retValue = UP;
                    //
                    break;
            }
            //
            return retValue;
        }
    }

    /**
     * Obsahuje referenci na generátor náhodných čísel
     *
     * @since 0.0.1
     */
    private static Randomizer randomizer;

    /**
     * Obsahuje rychlost přesunu kostek při míchání
     *
     * @since 0.0.1
     */
    private static final float SHUFFLING_VELOCITY = 2.1f;//1.95f;

    /**
     * Obsahuje rychlost přesunu kostek při běžném pohybu
     *
     * @since 0.0.1
     */
    private static final float MOVING_VELOCITY = 0.5f;

    /**
     * Obsahuje výchozí počet řádků krabičky kostek
     *
     * @since 0.0.1
     */
    private static final int INIT_ROWS = 4;

    /**
     * Obsahuje výchozí počet sloupců krabičky kostek
     *
     * @since 0.0.1
     */
    private static final int INIT_COLS = 4;

    /**
     * Obsahuje aktuální šířku kostky
     *
     * @since 0.0.1
     */
    private int tileWidth;

    /**
     * Obsahuje aktuální výšku kostky
     *
     * @since 0.0.1
     */
    private int tileHeight;

    /**
     * Reference na pole kostek
     *
     * @since 0.0.1
     */
    private Tile[][] tiles;

    /**
     * Obsahuje počet aktuálně se pohybujících kostek
     *
     * @since 0.0.1
     */
    private int tilesInMotion;

    /**
     * Obsahuje true, pokud je krabička kostek ve složeném stavu
     *
     * @since 0.0.1
     */
    private boolean resolved;

    /**
     * Obsahuje referenci na míchací vlákno
     *
     * @since 0.0.1
     */
    private Thread shufflingThread;

    /**
     * Obsahuje aktuální rychlost pohybu kostek
     *
     * @since 0.0.1
     */
    private float currentVelocity;

    /**
     * Obsahuje referenci na indikátor průběhu míchání
     *
     * @since 0.0.1
     */
    private ShufflingBar shufflingBar;

    /**
     * Obsahuje referenci na vstupní proud dat zvuku kliknutí
     *
     * @since 0.0.1
     */
    private InputStream clickInputStream;

    /**
     * Pole referencí na vlastní zvukové klipy kliknutí
     *
     * @since 0.0.1
     */
    private Clip[] clickClips;

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
    public TileBox(float left, float top, float width, float height) {
        // Korektní inicializace bázové třídy
        super(left, top, width, height);
        // Pokračování inicializacemi, které NetBeans nesnese v konstruktoru
        init();
    }

    /**
     * Inicializace, které NetBeans nesnese v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        // Pokud dosud nebyl inicializován generátor náhodných čísel,
        if (randomizer == null) {
            // pak to provedeme
            randomizer = new Randomizer();
        }
        // Inicializace velikosti pole zvukových klipů
        clickClips = new Clip[15];
        // Pro všechny pozice v poli
        for (int i = 0; i < clickClips.length; i++) {
            // Inicializace aktuálního zvukového klipu
            clickClips[i] = createAudioClip("/loydspuzzle/Click.wav");
        }
        // Nastavení výchozích barev
        setForeground(Color.BLACK);
        setBackground(new Color(0x21, 0x88, 0x67));
        // Nastavení aktuálních rozměrů kostky
        tileWidth = getWidth() / TileBox.INIT_COLS;
        tileHeight = getHeight() / TileBox.INIT_ROWS;
        // Výpočet poloměrů zakřivení rohů kostek
        arcWidth = tileWidth / 4;
        arcHeight = tileHeight / 4;
        // Inicializace velikosti fontu
        int fontSize = tileWidth > tileHeight ? tileWidth : tileHeight;
        fontSize = (fontSize * 2) / 5;
        // Nastavení fontu, společného pro všechny kostky
        Tile.setFont(new Font(Font.SERIF, Font.BOLD, fontSize));
        // Vlastní inicializace pole kostek
        initTiles(TileBox.INIT_ROWS, TileBox.INIT_COLS);
        // Inicializace indikátoru průběhu míchání
        int left = getLeft() + 10;
        int width = getWidth() - 20;
        int height = getHeight() / 6;
        int top = getTop() + (getHeight() - height) / 2;
        shufflingBar = new ShufflingBar(left, top, width, height);
        shufflingBar.setArcs(arcWidth, arcHeight);
        shufflingBar.setVisible(false);
        // Příjemcem událostí myši a klávesnice bude tato instance
        getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * Metoda vytvoří audio clip ze zadaného zdroje
     *
     * @param resourceName řetězec názvu zdroje zvuku
     * @return reference na vytvořený audio clip nebo null
     *
     * @since 0.0.1
     */
    private Clip createAudioClip(String resourceName) {
        // Inicializace návratové proměnné
        Clip retValue = null;
        //
        try {
            // Pokus o otevření datového proudu zvuku kliknutí
            clickInputStream = TileBox.class.getResourceAsStream(resourceName);
            // Pokus o přidělení zvukového klipu
            retValue = AudioSystem.getClip();
            // Pokus o otevření zvukového klipu;
            retValue.open(AudioSystem.getAudioInputStream(new BufferedInputStream(clickInputStream)));
        }
        catch (LineUnavailableException ex) {
            //
            getDefaultToolkit().beep();
        }
        catch (UnsupportedAudioFileException ex) {
            //
            getDefaultToolkit().beep();
        }
        catch (IOException ex) {
            //
            getDefaultToolkit().beep();
        }
        //
        return retValue;
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        // Na základě vzniklé události
        switch (event.getID()) {
            // Pokud došlo ke kliknutí myší
            case MouseEvent.MOUSE_CLICKED:
                //
                MouseEvent me = (MouseEvent) event;
                // uvnitř krabičky kostek,
                if (contains(me.getX(), me.getY())) {
                    // je třeba událost obsloužit
                    mouseClickedInside(me);
                }
                //
                break;
            // Pokud doško ke stisku klávesy
            case KeyEvent.KEY_PRESSED:
                //
                KeyEvent ke = (KeyEvent) event;
                // je třeba událost obsloužit
                keyPressed(ke);
                //
                break;
        }
    }

    /**
     * Metoda je volána, pokud došlo ke kliknutí myší uvnitř krabičky kostek
     *
     * @param e reference na událost myši
     *
     * @since 0.0.1
     */
    private void mouseClickedInside(MouseEvent e) {
        // Pokud není krabička složena
        if (!resolved) {
            // a pokud se žádná kostka nepohybuje
            if (tilesInMotion == 0) {
                // Inicializace pro ev. chybovou hlášku
                int row = -1;
                int col = -1;
                // Pro všechny řádky pole kostek
                for (int r = 0; r < tiles.length; r++) {
                    // Pro všechny sloupce aktuálního řádku pole kostek
                    for (int c = 0; c < tiles[r].length; c++) {
                        // Pokud bylo kliknuto na aktuální kostce
                        if (tiles[r][c].contains(e.getX(), e.getY())) {
                            //
                            row = r;
                            col = c;
                        }
                    }
                }
                // Pokud nebylo kliknuto na prázdné pozici
                if (tiles[row][col].isVisible()) {
                    // pak přesun zvolené kostky na volnou pozici, pokud je to možné
                    moveTileAtRowColToEmptyPosition(row, col);
                    // Událost byla obsloužena
                    e.consume();
                }
            }
        }
        // Krabička už je složena !
        else {
            //
            getDefaultToolkit().beep();
        }
    }

    /**
     * Metoda je volána, pokud došlo ke stisku klávesy uvnitř krabičky kostek
     *
     * @param e reference na událost klávesnice
     *
     * @since 0.0.1
     */
    private void keyPressed(KeyEvent e) {
        // Na základě stiskunté klávesy
        switch (e.getKeyCode()) {
            // Byla stisknuta klávesa vlevo
            case KeyEvent.VK_LEFT:
                // Pokus o posun prázdné pozice vpravo
                if (processDirection(Direction.RIGHT)) {
                    // Pokud došlo k přesunu, událost byla obsloužena
                    e.consume();
                }
                //
                break;
            // Byla stisknuta klávesa nahoru
            case KeyEvent.VK_UP:
                // Pokus o posun prázdné pozice dolů
                if (processDirection(Direction.DOWN)) {
                    // Pokud došlo k přesunu, událost byla obsloužena
                    e.consume();
                }
                //
                break;
            // Byla stisknuta klávesa vpravo
            case KeyEvent.VK_RIGHT:
                // Pokus o posun prázdné pozice vlevo
                if (processDirection(Direction.LEFT)) {
                    // Pokud došlo k přesunu, událost byla obsloužena
                    e.consume();
                }
                //
                break;
            // Byla stisknuta klávesa dolů
            case KeyEvent.VK_DOWN:
                // Pokus o posun prázdné pozice vzhůru
                if (processDirection(Direction.UP)) {
                    // Pokud došlo k přesunu, událost byla obsloužena
                    e.consume();
                }
                //
                break;
            // Pokud nebyla stisknutá klávesa obsloužena, 
            default:
                // možná ji obslouží potomek
                processKeyEvent(e);
                //
                break;
        }
    }

    /**
     * Metoda provede přesun volné pozice požadovaným směrem
     *
     * @param direction požadovaný směr
     *
     * @return true, pokud skutečně došlo k přesunu
     *
     * @since 0.0.1
     */
    private boolean processDirection(Direction direction) {
        //
        boolean retValue = false;
        // Pokud krabička není složena
        if (!resolved) {
            // a žádná z kostek nepohybuje
            if (tilesInMotion == 0) {
                // Pokus o posun prázdné pozice vzhůru
                if (moveEmptyPosition(direction)) {
                    // Pokud došlo k přesunu, událost byla obsloužena
                    retValue = true;
                }
            }
        }
        // Krabička už je složena !
        else {
            //
            getDefaultToolkit().beep();
        }
        //
        return retValue;
    }

    /**
     * Metoda je volána pro všechny neobsloužené stisknuté klávesy. Potomek ji
     * může překrýt a tak na událost reagovat
     *
     * @param e reference na událost klávesnice
     *
     * @since 0.0.1
     */
    protected void processKeyEvent(KeyEvent e) {
    }

    /**
     * Metoda přesune volnou pozici požadovaným směrem
     *
     * @param direction požadovaný směr přesunu
     *
     * @since 0.0.1
     */
    private boolean moveEmptyPosition(Direction direction) {
        // Inicializace návratové proměnné
        boolean retValue = false;
        // Pokud krabička dosud není složena
        if (!resolved) {
            // Inicializace pro ev. chybovou hlášku
            int row = -1;
            int col = -1;
            // Pro všechny řádky pole kostek
            for (int r = 0; r < tiles.length; r++) {
                // Pro všechny sloupce aktuálního řádku pole kostek
                for (int c = 0; c < tiles[r].length; c++) {
                    // Pokud byla nalezena prázdná pozice
                    if (!tiles[r][c].isVisible()) {
                        //
                        row = r;
                        col = c;
                    }
                }
            }
            // Na základě požadovaného směru přesunu
            switch (direction) {
                // Volná pozice se má přesunout vlevo
                case LEFT:
                    // a není na levém okraji
                    if (col > 0) {
                        // vlastní prohození kostek
                        switchTiles(row, col, row, col - 1);
                        // Skutečně došlo k přesunu
                        retValue = true;
                    }
                    //
                    break;
                // Volná pozice se má přesunout vzhůru
                case UP:
                    // a není na horním okraji
                    if (row > 0) {
                        // vlastní prohození kostek
                        switchTiles(row, col, row - 1, col);
                        // Skutečně došlo k přesunu
                        retValue = true;
                    }
                    //
                    break;
                // Volná pozice se má přesunout vpravo
                case RIGHT:
                    // a není na pravém okraji
                    if (col < tiles[row].length - 1) {
                        // vlastní prohození kostek
                        switchTiles(row, col, row, col + 1);
                        // Skutečně došlo k přesunu
                        retValue = true;
                    }
                    //
                    break;
                // Volná pozice se má přesunout dolů
                case DOWN:
                    // a není na dolním okraji
                    if (row < tiles.length - 1) {
                        // vlastní prohození kostek
                        switchTiles(row, col, row + 1, col);
                        // Skutečně došlo k přesunu
                        retValue = true;
                    }
                    //
                    break;
            }
            // Pokud skutečně došlo k přesunu kostek,
            if (retValue) {
                // je třeba prověřit, zda nemáme složeno
                resolved = isResolved();
                // Pokud máme složeno
                if (resolved) {
                    //
                    getDefaultToolkit().beep();
                }
            }
        }
        // Krabička kostek už je složena !
        else {
            //
            getDefaultToolkit().beep();
        }
        // True, pokud skutečně došlo k přesunu kostek
        return retValue;
    }

    /**
     * Metoda přesune kostku na zadané pozici na volné místo, pokud je to možné
     *
     * @param row index řádku přesouvané kostky
     * @param col index sloupce přesouvané kostky
     *
     * @since 0.0.1
     */
    private boolean moveTileAtRowColToEmptyPosition(int row, int col) {
        //
        boolean retValue = false;
        // Nebylo klinuto na poslední kostce v řádku a vpravo je volné místo
        if (col < tiles[row].length - 1 && !tiles[row][col + 1].isVisible()) {
            // Vzájemný přesun kostek
            switchTiles(row, col, row, col + 1);
            // Skutečně došlo k přesunu kostek
            retValue = true;
        }
        // Nebylo kliknuto na první kostce v řádku a vlevo je volné místo
        if (col > 0 && !tiles[row][col - 1].isVisible()) {
            // Vzájemný přesun kostek
            switchTiles(row, col, row, col - 1);
            // Skutečně došlo k přesunu kostek
            retValue = true;
        }
        // Nebylo kliknuto na poslední kostce ve sloupci a dole je volné místo
        if (row < tiles.length - 1 && !tiles[row + 1][col].isVisible()) {
            // Vzájemný přesun kostek
            switchTiles(row, col, row + 1, col);
            // Skutečně došlo k přesunu kostek
            retValue = true;
        }
        // Nebylo kliknuto na první kostce ve sloupci a nahoře je volné místo
        if (row > 0 && !tiles[row - 1][col].isVisible()) {
            // Vzájemný přesun kostek
            switchTiles(row, col, row - 1, col);
            // Skutečně došlo k přesunu kostek
            retValue = true;
        }
        // Pokud skutečně došlo k přesunu kostek,
        if (retValue) {
            // je třeba prověřit, zda nemáme složeno
            resolved = isResolved();
            // Pokud je krabička složena
            if (resolved) {
                //
                getDefaultToolkit().beep();
            }
        }
        //
        return retValue;
    }

    /**
     * Metoda vzájemně prohodí kostky na zadaných pozicích
     *
     * @param srcRow řádek zdrojové kostky
     * @param srcCol sloupec zdrojové kostky
     * @param dstRow řádek cílové kostky
     * @param dstCol sloupec cílové kostky
     *
     * @since 0.0.1
     */
    private void switchTiles(int srcRow, int srcCol, int dstRow, int dstCol) {
        // V pohybu je o dvě kostky více
        tilesInMotion += 2;
        // Fyzické prohození kostek v poli
        Tile t = tiles[srcRow][srcCol];
        tiles[srcRow][srcCol] = tiles[dstRow][dstCol];
        tiles[dstRow][dstCol] = t;
        // Animované dojetí do požadovaných cílových pozic
        Location l = tiles[srcRow][srcCol].getLocation();
        tiles[srcRow][srcCol].moveTo(tiles[dstRow][dstCol].getLocation(), currentVelocity);
        tiles[dstRow][dstCol].moveTo(l, currentVelocity);
        //
        moveMade();
    }

    /**
     * Metoda je volána v případě provedení tahu. Potomek ji může předefinovat a
     * tak reagovat na událost
     *
     * @since 0.0.1
     */
    protected void moveMade() {
    }

    /**
     * Metoda uvolní předchozí pole kostek (pokud existuje) a inicializuje nové
     *
     * @param rows požadovaný počet řádků pole kostek
     * @param cols požadovaný počet sloupců pole kostek
     *
     * @since 0.0.1
     */
    public synchronized void initTiles(int rows, int cols) {
        // Pokud existuje nějaké pole kostek, 
        if (tiles != null) {
            // pak bude uvolněno
            disposeTiles();
        }
        // Inicializace nového pole kostek
        tiles = new Tile[rows][cols];
        // Inicializace pracovních proměnných
        int indexMax = rows * cols;
        int index = 1;
        int left;
        int top = getTop();
        // Pro všechny řádky pole kostek
        for (int r = 0; r < tiles.length; r++) {
            // Inicializace levé souřadnice
            left = getLeft();
            // Pro všechny sloupce aktuálního řádku pole kostek
            for (int c = 0; c < tiles[r].length; c++) {
                // Inicializace reference na aktuální kostku
                tiles[r][c] = createTile(left, top, tileWidth, tileHeight);
                // Inicializace zobrazovaného textu
                tiles[r][c].setText("" + index);
                // Nastavení poloměru zakřivení rohu kostky
                tiles[r][c].setArcs(arcWidth, arcHeight);
                // Pokud kostka není poslední, bude viditelná
                tiles[r][c].setVisible((index < indexMax));//== 1));//< indexMax));
                // Posun na další kostku v řádku
                index++;
                left += tileWidth;
            }
            // Posun na další řádek kostek
            top += tileHeight;
        }
        // Krabička je složena
        resolved = true;
        // Žádná z kostek se nepohybuje
        tilesInMotion = 0;
        //
        int width = tileWidth * tiles[0].length;
        int height = tileHeight * tiles.length;
        //
        if (width != getWidth() || height != getHeight()) {
            //
            setSize(width, height);
            //
            tileBoxResized();
        }
    }

    /**
     * Metoda je volána v případě změny velikosti krabičky kostek
     *
     * @since 0.0.1
     */
    protected void tileBoxResized() {
    }

    /**
     * Metoda vytvoří novou kostku hry
     *
     * @param left výchozí levá souřadnice polohy objektu
     * @param top výchozí horní souřadnice polohy objektu
     * @param width výchozí šířka objektu
     * @param height výchozí výška objektu
     *
     * @since 0.0.1
     */
    private Tile createTile(float left, float top, float width, float height) {
        // 
        return new Tile(left, top, width, height) {

            @Override
            protected void positionReached() {
                // Kostka dosáhla pozice - pohybuje se tedy o kostku méně
                tilesInMotion--;
                // Pokud se žádná kostka nepohybuje nebo se míchá
                if (tilesInMotion == 0 || tilesInMotion == -3) {
                    //
                    boolean jo = false;
                    // Pro všechny klipy v poli
                    for (int i = 0; i < clickClips.length; i++) {
                        // Pokud aktuální klip neběží,
                        if (!clickClips[i].isRunning()) {
                            // získáme referenci na kontrolu hlasitosti
                            FloatControl fc = (FloatControl) clickClips[i].getControl(FloatControl.Type.MASTER_GAIN);
                            // a pokud se míchá, ztlumíme zvuk
                            fc.setValue(tilesInMotion == -3 ? -13 : 0);
                            // Nastavení výchozí polohy a spuštění klipu
                            clickClips[i].setFramePosition(0);
                            clickClips[i].start();
                            //
                            jo = true;
                            break;
                        }
                    }
                    //
                    if (!jo) {
                        //
                        System.out.println("Málo klipů ...");
                        getDefaultToolkit().beep();
                    }
                }
            }

        };
    }

    /**
     * Metoda uvolní pole kostek, pokud existuje
     *
     * @since 0.0.1
     */
    private void disposeTiles() {
        // Pokud existuje nějaké pole kostek 
        if (tiles != null) {
            // Pro všechny řádky pole kostek
            for (int r = 0; r < tiles.length; r++) {
                // Pro všechny sloupce aktuálního řádku pole kostek
                for (int c = 0; c < tiles[r].length; c++) {
                    // Uvolnění reference na aktuální kostku
                    tiles[r][c].dispose();
                    //
                    tiles[r][c] = null;
                }
            }
            // Uvolnění reference na pole kostek
            tiles = null;
        }
    }

    /**
     * Metoda zjistí aktuální počet řádků krabičky kostek
     *
     * @return aktuální počet řádků krabičky kostek
     *
     * @since 0.0.1
     */
    public int getRows() {
        //
        return tiles.length;
    }

    /**
     * Metoda zjistí aktuální počet sloupců krabičky kostek
     *
     * @return aktuální počet sloupců krabičky kostek
     *
     * @since 0.0.1
     */
    public int getCols() {
        //
        return tiles[0].length;
    }

    /**
     * Metoda provede promíchání krabičky kostek z aktuálního stavu
     *
     * @param moves požadovaný počet tahů pro míchání
     *
     * @since 0.0.1
     */
    public void shuffle(int moves) {
        // Pokud se žádné kostky nepřesouvají 
        if (tilesInMotion == 0) {
            // Aktuální rychlost přesunu
            currentVelocity = TileBox.SHUFFLING_VELOCITY;
            // Při míchání nebudou přijímány jakékoliv vstupy
            tilesInMotion = -3;
            // Aktualizace polohy a velikosti indikátoru míchání
            int left = getLeft() + 10;
            int width = getWidth() - 20;
            int height = getHeight() / 6;
            int top = getTop() + (getHeight() - height) / 2;
            shufflingBar.setBounds(left, top, width, height);
            // Inicializace reference na míchací vlákno
            shufflingThread = new Thread() {

                // Inicializace pracovních proměnných
                private Direction currentMove = Direction.NONE;
                private Direction lastMove;
                private final long sleepTime = 20;

                @Override
                public void run() {
                    // Inicializace indikátoru průběhu míchání
                    shufflingBar.setMaximum(moves);
                    shufflingBar.setVisible(true);
                    //
                    //System.out.println("Moves = " + moves);
                    //
                    do {
                        // Krabička zatím není složena
                        resolved = false;
                        // Pro požadovaný počet tahů míchání
                        for (int i = 0; i < moves; i++) {
                            // Aktuální tah bude tahem minulým
                            lastMove = currentMove;
                            // Dokud se pohybují kostky při míchání
                            while (tilesInMotion != -3) {
                                //
                                try {
                                    // musíme chvíli počkat
                                    Thread.sleep(sleepTime);
                                }
                                //
                                catch (InterruptedException ex) {
                                    //
                                    return;
                                }
                            }
                            //
                            do {
                                //
                                do {
                                    // Vygenerování náhodného směru přesunu
                                    currentMove = Direction.values()[randomizer.getInt(1, 4)];
                                } // opakuj, pokud byl vygenerován zpětný tah (to nechceme)
                                while (currentMove == lastMove.getOppositeDirection());
                            } // opakuj, pokud nebylo možno kostku přesunout
                            while (!moveEmptyPosition(currentMove));
                            // Posun indikátoru průběhu míchání
                            shufflingBar.increment();
                        }
                        // Po zamíchání je třeba otestovat, zda nemáme složeno
                        resolved = isResolved();
                    }// opakuj, pokud došlo k zamíchání do složeného stavu (to nechceme)
                    while (resolved);
                    // Dokud  se poslední kostky ještě přesouvají
                    while (tilesInMotion != -3) {
                        //
                        try {
                            // je třeba chvilku počkat
                            Thread.sleep(sleepTime);
                        }
                        //
                        catch (InterruptedException ex) {
                            //
                            return;
                        }
                    }
                    // Je domícháno
                    tilesInMotion = 0;
                    // Aktální rychlost přesunu
                    currentVelocity = TileBox.MOVING_VELOCITY;
                    //
                    shufflingBar.setVisible(false);
                    //
                    shufflingFinished();
                }
            };
            // Vlastní zahájení míchání
            shufflingThread.start();
        }
    }

    /**
     * Metoda je volána v případě dokončení míchání. Potomek ji může
     * předefinovat a tak na událost zareagovat
     *
     * @since 0.0.1
     */
    protected void shufflingFinished() {
    }

    /**
     * Metoda zjistí, zda se krabička právě míchá
     *
     * @return true, pokud se krabička právě míchá
     *
     * @since 0.0.1
     */
    public boolean isShuffling() {
        //
        return shufflingThread != null && shufflingThread.isAlive();
    }

    /**
     * Metoda zjistí, zda je krabička kostek ve složeném stavu
     *
     * @return true, pokud je krabička složena
     *
     * @since 0.0.1
     */
    private boolean isResolved() {
        // Krabička zatím složena není
        boolean retValue = false;
        // Pokud pole kostek skutečně existuje
        if (tiles != null) {
            // Inicializace návratové hodnoty
            retValue = true;
            // Inicializace indexu první kostky
            int index = 1;
            //
            found:
            // Pro všechny řádky pole kostek
            for (int r = 0; r < tiles.length; r++) {
                // Pro všechny sloupce aktuálního řádku pole kostek
                for (int c = 0; c < tiles[r].length; c++) {
                    // Pokud se kostka nenachází na správné pozici,
                    if (!tiles[r][c].getText().equals("" + index)) {
                        // pak krabička není složena
                        retValue = false;
                        //
                        break found;
                    }
                    // Posun na index další kostky
                    index++;
                }
            }
        }
        //
        if (retValue) {
            //
            puzzleResolved();
        }
        // True, pokud je krabička složena
        return retValue;
    }

    /**
     * Metoda je volána v případě složení krabičky kostek. Potomek ji může
     * předefinovat a tak na událost reagovat
     *
     * @since 0.0.1
     */
    protected void puzzleResolved() {
    }

    /**
     * Metoda aktualizuje stav objektu po uplynulém časovém intervalu.
     *
     * @param millis počet milisekund od poslední aktualizace
     *
     * @since 0.0.1
     */
    public void updateStateAfter(long millis) {
        // Pokud pole kostek existuje
        if (tiles != null) {
            // Pro všechny řádky pole kostek
            for (int r = 0; r < tiles.length; r++) {
                // Pro všechny sloupce aktuálního řádku pole kostek
                for (int c = 0; c < tiles[r].length; c++) {
                    // Aktualizace stavu kostky
                    tiles[r][c].updateStateAfter(millis);
                }
            }
        }
        // Aktualizace stavu indikátoru průběhu míchání
        shufflingBar.updateStateAfter(millis);
    }

    @Override
    public void renderTo(Graphics g) {
        // Korektní vykreslení bázové třídy
        super.renderTo(g);
        // Pro všechny řádky pole kostek
        for (int r = 0; r < tiles.length; r++) {
            // Pro všechny sloupce aktuálního řádku pole kostek
            for (int c = 0; c < tiles[r].length; c++) {
                // Vykreslení aktuální kostky do požadovaného grafického kontextu
                tiles[r][c].renderTo(g);
            }
        }
        // Vykreslení indikátoru průběhu míchání
        shufflingBar.renderTo(g);
    }

    @Override
    protected void renderBackground(Graphics g) {
        //
        g.setColor(getBackground());
        g.fillRoundRect(getLeft(), getTop(), getWidth(), getHeight(), arcWidth, arcHeight);
    }

    @Override
    protected void renderForeground(Graphics g) {
        //
        g.setColor(getForeground());
        g.drawRoundRect(getLeft(), getTop(), getWidth(), getHeight(), arcWidth, arcHeight);
    }

    @Override
    public void dispose() {
        // Pokud míchací vlákno existuje a právě běží
        if (shufflingThread != null && shufflingThread.isAlive()) {
            // je třeba jej ukončit
            shufflingThread.interrupt();
        }
        // Instance nadále nebude přijímat události
        getDefaultToolkit().removeAWTEventListener(this);
        // Pokud byla inicializována reference na generátor čísel
        if (randomizer != null) {
            // je třeba ji uvolnit
            randomizer.dispose();
        }
        //
        try {
            // Pro všechny klipy v poli
            for (int i = 0; i < clickClips.length; i++) {
                // Zastavení klipu
                clickClips[i].stop();
                //clickClips[i].close();
                clickClips[i] = null;
            }
            // 
            clickClips = null;
            clickInputStream.close();
        }
        catch (IOException ex) {
            //
        }
        // Uvolnění pole kostek
        disposeTiles();
        // Korektní uvolnění prostředků bázové třídy
        super.dispose();
    }

}
