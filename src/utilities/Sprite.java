/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 * Třída reprezentuje pohyblivý pravoúhlý objekt a implementuje metody pro práci
 * s ním
 *
 * @author Vilem KREJCI <https://github.com/VilemKrejci>
 *
 * @version 0.0.1
 */
public class Sprite extends Rectangle {

    /**
     * Obsahuje rychlost pohybu v ose X
     *
     * @since 0.0.1
     */
    private float velocityX = 0.54325f;

    /**
     * Obsahuje rychlost pohybu v ose Y
     *
     * @since 0.0.1
     */
    private float velocityY = 0.54325f;

    /**
     * Obsahuje true, pokud se objekt pohybuje horizontálně
     *
     * @since 0.0.1
     */
    private boolean motionInX;

    /**
     * Obsahuje true, pokud se objekt pohybuje vertikálně
     *
     * @since 0.0.1
     */
    private boolean motionInY;

    /**
     * Obsahuje referenci na cílovou pozici objektu
     *
     * @since 0.0.1
     */
    private Location target;

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
    public Sprite(float left, float top, float width, float height) {
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
        //
    }

    /**
     * Metoda nastaví rychlost pohybu v ose X
     *
     * @param velocityX rychlost pohybu v ose X
     *
     * @since 0.0.1
     */
    public void setVelocityX(float velocityX) {
        //
        this.velocityX = velocityX;
    }

    /**
     * Metoda zjistí aktuální rychlost pohybu v ose X
     *
     * @return aktuální rychlost pohybu v ose X
     *
     * @since 0.0.1
     */
    public float getVelocityX() {
        //
        return velocityX;
    }

    /**
     * Metoda nastaví rychlost pohybu v ose Y
     *
     * @param velocityY rychlost pohybu v ose Y
     *
     * @since 0.0.1
     */
    public void setVelocityY(float velocityY) {
        //
        this.velocityY = velocityY;
    }

    /**
     * Metoda zjistí aktuální rychlost pohybu v ose Y
     *
     * @return aktuální rychlost pohybu v ose Y
     *
     * @since 0.0.1
     */
    public float getVelocityY() {
        //
        return velocityY;
    }

    /**
     * Metoda zahájí přesun objektu do cílové pozice
     *
     * @param location souřadnice cílového levého horního rohu objektu
     * @param velocity požadovaná rychlost přesunu objektu
     *
     * @since 0.0.1
     */
    public void moveTo(Location location, float velocity) {
        // Aktualizace rychlosti přesunu
        velocityX = velocityY = velocity;
        // Inicializace reference na cílovou pozici objektu
        this.target = location;
        // Pokud je cílová pozice vpravo od současné,
        if (target.getLeft() > getLeft()) {
            // objekt se bude pohybovat kladnou rychlostí
            velocityX = Math.abs(velocityX);
        }
        // Pokud je cílová pozice vlevo od současné,
        else if (target.getLeft() < getLeft()) {
            // objekt se bude pohybovat zápornou rychlostí
            velocityX = -Math.abs(velocityX);
        }
        // Pokud je cílová pozice pod současnou,
        if (target.getTop() > getTop()) {
            // objekt se bude pohybovat kladnou rychlostí
            velocityY = Math.abs(velocityY);
        }
        // Pokud je cílová pozice nad současnou,
        else if (target.getTop() < getTop()) {
            // objekt se bude pohybovat zápornou rychlostí
            velocityY = -Math.abs(velocityY);
        }
        // Nahození příznaků pohybu
        motionInX = true;
        motionInY = true;
    }

    /**
     * Metoda aktualizuje stav objektu po uplynulém časovém intervalu.
     *
     * @param millis počet milisekund od poslední aktualizace
     *
     * @since 0.0.1
     */
    public void updateStateAfter(long millis) {
        // Pokud se objekt pohybuje v ose X
        if (motionInX) {
            // Aktualzace levé souřadnice objektu
            float left = getLeftF();
            left += millis * velocityX;
            // Pokud objekt překročil cílovou pozici vpravo nebo vlevo
            if ((left >= target.getLeftF() && velocityX > 0) || (left <= target.getLeftF() && velocityX < 0)) {
                // Aktualizace polohy objektu
                left = target.getLeftF();
                // Objekt už se nepohybuje
                motionInX = false;
            }
            // Nová poloha objektu
            setLeft(left);
            // Pokud se pohyb objektu zastavil, 
            if (!motionInX && !motionInY) {
                // bylo dosaženo cílové pozice
                positionReached();
            }
        }
        // Pokud se objekt pohybuje v ose Y
        if (motionInY) {
            // Aktualzace horní souřadnice objektu
            float top = getTopF();
            top += millis * velocityY;
            // Pokud objekt překročil cílovou pozici dole nebo nahoře
            if ((top >= target.getTopF() && velocityY > 0) || (top <= target.getTopF() && velocityY < 0)) {
                // Aktualizace polohy objektu
                top = target.getTopF();
                // Objekt už se nepohybuje
                motionInY = false;
            }
            // Nová poloha objektu
            setTop(top);
            // Pokud se pohyb objektu zastavil, 
            if (!motionInX && !motionInY) {
                // bylo dosaženo cílové pozice
                positionReached();
            }
        }
    }

    /**
     * Metoda je volána v případě dosažení cílové pozice objektu. Potomek ji
     * může předefinovat a tak reagovat na tuto událost
     *
     * @since 0.0.1
     */
    protected void positionReached() {
    }

    @Override
    public void dispose() {
        // Pokud byla objektu zadána cílová pozice,
        if (target != null) {
            // pak je třeba ji uvolnit
            target.dispose();
        }
        // Korektní uvolnění bázové třídy
        super.dispose();
    }

}
