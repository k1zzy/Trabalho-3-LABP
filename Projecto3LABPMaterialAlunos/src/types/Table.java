package types;

import java.util.Arrays;
import java.util.Random;

public class Table {
    public static final String empty = "⬜";
    public static final String EOL = System.lineSeparator();
    public static final int DIFFICULTY = 3;     //grau de dificuldade
    public static final int DEFAULT_BOTTLE_CAPACITY = 5;    //tamanho por defeito das garrafas
    
    private Filling[] usedSymbols;
    private int nrSymbols;
    private int seed;
    private int bottleCapacity;
    private Bottle[] bottles;
    private int nrBottles;

    /**
     * 
     * @param symbols
     * @param numberOfUsedSymbols
     * @param seed
     * @param capacity
     */
    public Table(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
    	// o minimo entre o tamanho de simbolos possiveis e o numero de simbolos pretendidos
    	this.nrSymbols = Math.min(symbols.length, numberOfUsedSymbols);
    	// escolher aleatoriamente os simbolos que vao ser usados
    	this.usedSymbols = chooseSymbols(symbols, nrSymbols, seed);
        this.seed = seed;
        bottleCapacity = capacity;
        bottles = new Bottle[nrBottles];
        
        // fill cada bottle da mesa
        for (int i = 0; i < nrBottles; i++) {
        	bottles[i] = fillBottle(usedSymbols, bottleCapacity, seed);
        }
    }
    
    /**
     * Escolhe aleatoriamente os simbolos a usar nas garrafas dada uma seed e um vetor de simbolos a escolher
     * 
     */
    private Filling[] chooseSymbols(Filling[] symbols, int nrSymbols, int seed) {
    	Random rd = new Random(seed); // criar o random com a seed
    	Filling[] choseSymbols = new Filling[nrSymbols];
    	for (int i = 0; i < nrSymbols; i++) {
    		choseSymbols[i] = symbols[rd.nextInt(symbols.length-1)]; // indice aleatorio entre [0, symbols length-1]
    	}
		return Arrays.copyOf(choseSymbols, choseSymbols.length);
    }
    
    /**
     * Enche as garrafas aleatoriamente dada uma seed com os simbolos escolhidos
     * 
     */
    private Bottle fillBottle(Filling[] symbols, int capacity, int seed) {
    	Filling[] chosen = new Filling[capacity];
    	Random rd = new Random(seed);
    	for (int i = capacity; i > 0; i--) {
    		chosen[i] = symbols[rd.nextInt(symbols.length-1)];
    	}
    	return new Bottle(chosen);
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
        return bottles[i].isSingleFilling() ? true : false;
    }

    /**
     * 
     * @param i
     * @return
     */
    public boolean isEmpty(int i) {
        return bottles[i].isEmpty() ? true : false;
    }

    /**
     * 
     * @param i
     * @return
     */
    public boolean isFull(int i) {
        return bottles[i].isFull() ? true : false;
    }

    /**
     * 
     * @return
     */
    public boolean areAllFilled() {
        for (Bottle bottle : bottles) {
        	// se alguma garrafa que nao esteja vazia nao estiver cheia ou nao seja apenas um conteudo
        	if (!bottle.isEmpty() && (!bottle.isSingleFilling() || !bottle.isFull())) {
        		return false; // retorna false
        	}
        }
        return true; // caso contrario retorna true
    }

    /**
     * 
     * @param i
     * @param j
     */
    public void pourFromTo(int i, int j) {
        if(bottles[i].isEmpty() || bottles[j].isFull()) {
        	throw new IllegalArgumentException("Accao Invalida");
        } else {
        	bottles[j].receive(bottles[i].top());
        	bottles[i].pourOut();
        }
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
        return bottleCapacity;
    }

    /**
     * 
     * @param i
     * @return
     */
    public Filling top(int i) {
        return bottles[i].top();
    }

    /**
     * 
     */
    public String toString() {
        return null;
    } 
}
