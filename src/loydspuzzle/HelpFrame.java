/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loydspuzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import javax.swing.JTextArea;

/**
 * Třída reprezentuje nápovědné okno a implementuje metody pro práci s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class HelpFrame extends Frame implements MouseListener, KeyListener {

    /**
     * Reference na rodičovské okno
     *
     * @since 0.0.1
     */
    private final Frame parent;

    /**
     * Reference na panel nápovědy
     *
     * @since 0.0.1
     */
    private JTextArea textArea;

    /**
     * Veřejný parametrický konstruktor vytvoří a inicializuje novou instanci
     *
     * @param parent reference na rodičovské okno
     *
     * @since 0.0.1
     */
    public HelpFrame(Frame parent) {
        // Korektní inicializace bázové třídy
        super();
        // Záznam reference na rodičovské okno
        this.parent = parent;
        // Další inicializace, které NetBeans nesnesou v konstruktoru
        init();
    }

    /**
     * Pokračování inicializací, které NetBeans nesnesou v konstruktoru
     *
     * @since 0.0.1
     */
    private void init() {
        //
        addMouseListener(this);
        addKeyListener(this);
        // Nebudeme používat správce rozložení
        setLayout(null);
        // Nastavení výchozích barev
        setForeground(Color.BLACK);
        setBackground(new Color(6, 86, 118));
        // Okno nebude mít okraje
        setUndecorated(true);
        // a nebude možno měnit jeho velikost
        setResizable(false);
        // Preferovaná velikost okna
        setPreferredSize(new Dimension(700, 500));
        // Přidělení systémových prostředků
        pack();
        // Vystředění okna vzhledem k rodičovskému oknu
        setLocationRelativeTo(parent);
        // Velikost mezery od okraje okna
        int spacer = 10;
        // Zjištění vnitřních mezer v okně
        Insets i = getInsets();//new Insets(20, 20, 20, 20);
        // Aktualizace vnitřních mezer
        i.left += spacer;
        i.top += spacer;
        i.right += spacer;
        i.bottom += spacer;
        // Inicializace reference na text nápovědy
        textArea = new JTextArea();
        textArea.setBounds(i.left, i.top, getWidth() - i.left - i.right, getHeight() - i.top - i.bottom);
        // Inicializace barev panelu nápovědy
        textArea.setBackground(getBackground());
        textArea.setForeground(Color.WHITE);
        // Panel nápovědy nebude povolen
        textArea.setEnabled(false);
        // Inicializace reference na font výpisu
        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD + Font.ITALIC, 17));
        textArea.setLocale(Locale.getDefault());
        // Zalamování na koncích slov a řádků
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        // Konstrukce názvu souboru nápovědy
        String fileName = getClass().getSimpleName() + (Locale.getDefault().toString().equals("cs_CZ") ? "_CZ" : "_EN") + ".txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)));
        // V následujícím bloku kódu může nastat výjimka
        try {
            // Pokus o přečtení obsahu nápovědného boxu
            textArea.read(br, null);
        }
        // Něco se zvrtlo
        catch (IOException ex) {
            // Prdíme na to
        } //
        finally {
            //
            try {
                //
                br.close();
            }
            catch (IOException ex) {
                //
            }
        }
        // Zařazení panelu nápovědy na plochu okna
        add(textArea);
        textArea.setVisible(true);
    }

    @Override
    public void setVisible(boolean visible) {
        //
        super.setVisible(visible);
        // Rodičovské okno bude zakázáno
        parent.setEnabled(!visible);
        // ale okno nápovědy bude povoleno 
        setEnabled(visible);
        // a bude přijímat události klávesnice a myši
        requestFocus();
    }

    @Override
    public void dispose() {
        // Okno už nebude přijímat události myši a klávesnice
        removeMouseListener(this);
        removeKeyListener(this);
        //
        remove(textArea);
        textArea = null;
        // Korektní uvolnění bázové třídy
        super.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Na základě stisknuté klávesy
        switch (e.getKeyCode()) {
            // Pokud byla stiknuta klávesa F1
            case KeyEvent.VK_F1:
                // Okno nápovědy nebude povoleno a nebude vidět
                setEnabled(false);
                setVisible(false);
                // ale rodičovské okno povolewno bude
                parent.setEnabled(true);
                // a rovněž bude přijímat události myši a klávesnice
                parent.requestFocus();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }

}
