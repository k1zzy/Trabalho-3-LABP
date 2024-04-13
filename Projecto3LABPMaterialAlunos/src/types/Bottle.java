package types;

import java.util.Iterator;
import java.util.Stack; //remover no caso de optar por uma classe de AED.

public class Bottle implements Iterable<Filling>{
    public static final int DEFAULT_CAPACITY = 5;
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();

    private Stack<Filling> contents;
    //pode acrescentar outros atributos

    /**
     * 
     */
    public Bottle() {
        
    }

    /**
     * 
     * @param capacity
     */
    public Bottle(int capacity) {
        
    }

    /**
     * 
     * @param content
     */
    public Bottle(Filling[] content) {
        
    }

    /**
     * 
     * @return
     */
    public boolean isFull() {
        return false;
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * 
     * @return
     */
    public Filling top() {
        return null;
    }

    /**
     * 
     * @return
     */
    public int spaceAvailable() {
        return -1;
    }

    /**
     * 
     */
    public void pourOut() {

    }

    /**
     * 
     * @param s
     */
    public boolean receive(Filling s) {
        return false;
    }

    /**
     * 
     * @return
     */
    public int capacity() {
        return -1;
    }	

    /**
     * 
     * @return
     */
    public boolean isSingleFilling() {
        return false;
    }

    /**
     * 
     * @return
     */
    public Filling[] getContent() {
        return null;
    }

    /**
     * 
     */
    public String toString() {
        return null;
    }

    /**
     * 
     */
    public Iterator<Filling> iterator() {
        return null;
    }
}
