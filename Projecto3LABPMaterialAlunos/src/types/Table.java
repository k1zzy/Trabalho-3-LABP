package types;

import java.util.Arrays;
import java.util.Random;

public class Table {
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();
    public static final int DIFFICULTY = 3;     //grau de dificuldade
    public static final int DEFAULT_BOTTLE_CAPACITY = 5;    //tamanho por defeito das garrafas
    
    private int nrSymbols;
    private int seed;
    private final Filling[] symbols;
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
    	this.symbols = Arrays.copyOf(symbols, nrSymbols);
    	int[] contadorSimbolos = new int[nrSymbols];
    	Random rd = new Random(seed); // criar o random com a seed
    	
    	this.seed = seed; //  definir a seed
        bottleCapacity = capacity;
        
        // numero de garrafas = ao numero de simbolos + a dificuldade
        nrBottles = nrSymbols + DIFFICULTY;
        bottles = new Bottle[nrBottles];
        
        // fill cada bottle da mesa
        int i = 0;
        for (; i < nrSymbols; i++) {
        	bottles[i] = new Bottle(chooseSymbols(contadorSimbolos, rd));
        }
        for (; i < nrBottles; i++) {
        	bottles[i] = new Bottle(capacity);
        }
    }
    
    /**
     * Escolhe aleatoriamente os simbolos a usar nas garrafas
     * 
     */
    private Filling[] chooseSymbols(int[] contadorSimbolos, Random rd) {
    	int rdSymbolIndex = 0;
    	Filling[] chosenSymbols = new Filling[bottleCapacity];
    	for (int i = 0; i < bottleCapacity; i++) {
    		do {
    			rdSymbolIndex = rd.nextInt(nrSymbols);
    		} while(contadorSimbolos[rdSymbolIndex] == bottleCapacity);	
    		contadorSimbolos[rdSymbolIndex]++;
    		chosenSymbols[i] = symbols[rdSymbolIndex];
    	}
		return Arrays.copyOf(chosenSymbols, chosenSymbols.length);
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
    
    // TODO
    /**
     * 
     * @param bottle
     */
    public void addBottle(Bottle bottle) {
        nrBottles++;
        bottles = Arrays.copyOf(bottles, bottles.length+1);
        bottles[bottles.length-1] = bottle;
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
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < bottleCapacity; i++) {
    		for (int j = 0; j < nrBottles; j++) {
    				sb.append(bottles[j].getFilling(i));
    				sb.append("    ");
    			}
    		sb.append(EOL);
    	}
    	return sb.toString();
    }
}
