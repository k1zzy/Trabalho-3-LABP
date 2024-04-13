package types;

public class Table {
    public static final String empty = "⬜";
    public static final String EOL = System.lineSeparator();
    public static final int DIFFICULTY = 3;     //grau de dificuldade
    public static final int DEFAULT_BOTTLE_CAPACITY = 5;    //tamanho por defeito das garrafas

    /**
     * 
     * @param symbols
     * @param numberOfUsedSymbols
     * @param seed
     * @param capacity
     */
    public Table(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
        
    }

    /**
     * 
     */
    public void regenerateTable() {
        
    }

    /**
     * 
     * @param i
     * @return
     */
    public boolean singleFilling(int i) {
        return false;
    }

    /**
     * 
     * @param i
     * @return
     */
    public boolean isEmpty(int i) {
        return false;
    }

    /**
     * 
     * @param i
     * @return
     */
    public boolean isFull(int i) {
        return false;
    }

    /**
     * 
     * @return
     */
    public boolean areAllFilled() {
        return false;
    }

    /**
     * 
     * @param i
     * @param j
     */
    public void pourFromTo(int i, int j) {
        
    }

    /**
     * 
     * @param bottle
     */
    public void addBottle(Bottle bottle) {
        
    }

    /**
     * 
     * @return
     */
    public int getSizeBottles() {
        return -1;
    }

    /**
     * 
     * @param i
     * @return
     */
    public Filling top(int i) {
        return null;
    }

    /**
     * 
     */
    public String toString() {
        return null;
    } 
}
