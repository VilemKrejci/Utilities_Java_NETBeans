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
public class HelpFrame extends Frame {

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
        // Zalamování na koncích slov
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
    public void dispose() {
        //
        remove(textArea);
        //
        textArea = null;
        //
        super.dispose();
    }

}
