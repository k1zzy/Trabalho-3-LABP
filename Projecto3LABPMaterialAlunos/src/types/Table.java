package types;

import java.util.Arrays;
import java.util.Random;

public class Table {
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();
    public static final int DIFFICULTY = 3;     //grau de dificuldade
    public static final int DEFAULT_BOTTLE_CAPACITY = 5;    //tamanho por defeito das garrafas
    
    private final int nrSymbols;
    private final Filling[] symbols;
    private final int bottleCapacity;
    private Bottle[] bottles;
    private int nrBottles;
    private final Random rd;
    private final int nrBottlesInicial; // o numero de garrafas que existem quando a table eh criada

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
    	this.rd = new Random(seed);
    	this.bottleCapacity = capacity;
    	
        // numero de garrafas = ao numero de simbolos + a dificuldade
        nrBottles = nrSymbols + DIFFICULTY;
        nrBottlesInicial = nrBottles;
        
        // fill cada bottle da mesa
        bottles = fillBottles();
    }
    
    /**
     * Escolhe aleatoriamente os simbolos a usar nas garrafas
     * 
     */
    private Filling[] chooseSymbols(int[] contadorSimbolos) {
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
    
    private Bottle[] fillBottles() {
    	int[] contadorSimbolos = new int[nrSymbols];
    	Bottle[] bottles = new Bottle[nrBottles];
    	int i = 0;
        for (; i < nrSymbols; i++) {
        	bottles[i] = new Bottle(chooseSymbols(contadorSimbolos));
        }
        for (; i < nrBottles; i++) {
        	bottles[i] = new Bottle(bottleCapacity);
        }
        return Arrays.copyOf(bottles, bottles.length);
    }
    
    /**
     * 
     */
    public void regenerateTable() {
        nrBottles = nrBottlesInicial;
        bottles = fillBottles();
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
     * @throws IllegalArgumentException
     */
    public void pourFromTo(int i, int j) {
		if (bottles[j].receive(bottles[i].top())) {
			bottles[i].pourOut();
		} else {
			throw new IllegalArgumentException("The bottle " + j+1 + " cannot receive the filling from bottle " +i);
		}
    }
    
    /**
     * 
     * @param bottle
     */
    public void addBottle(Bottle bottle) {
        nrBottles++; // incrementa o numero de garrafas
        bottles = Arrays.copyOf(bottles, bottles.length+1); // aumenta o tamanho do array de garrafas
        bottles[bottles.length-1] = bottle; // adiciona a garrafa
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
     * @throws EmptyStackException
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
