package types;

/**
 * Classe que representa uma table de garrafas.
 * 
 * @author Rodrigo Afonso (61839)
 * @version 1.0
 */

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
     * Contrutor de uma table comm um certo numero de simbolos, um numero de simbolos usados, uma seed e uma capacidade
     * 
     * @param symbols array de simbolos possiveis
     * @param numberOfUsedSymbols numero de simbolos usados
     * @param seed seed para gerar numeros aleatorios
     * @param capacity capacidade das garrafas
     */
    public Table(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
    	// o minimo entre o tamanho de simbolos possiveis e o numero de simbolos pretendidos
    	this.nrSymbols = Math.min(symbols.length, numberOfUsedSymbols);
    	this.symbols = Arrays.copyOf(symbols, nrSymbols);
    	this.rd = new Random(seed);
    	this.bottleCapacity = capacity;
    	
        // numero de garrafas = ao numero de simbolos + a dificuldade
        nrBottles = nrSymbols + DIFFICULTY;
        nrBottlesInicial = nrBottles; // guarda o numero de garrafas inicial
        
        // fill cada bottle da mesa
        bottles = fillBottles();
    }

    /**
     * Enche as garrafas da mesa com fillings aleatorios
     * 
     * @return array de garrafas
     */
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
     * Escolhe aleatoriamente os simbolos a usar nas garrafas
     * 
     * @param contadorSimbolos array com o numero de vezes que cada simbolo foi escolhido
     * @return array com os simbolos escolhidos
     */
    private Filling[] chooseSymbols(int[] contadorSimbolos) {
    	int rdSymbolIndex = 0;
    	Filling[] chosenSymbols = new Filling[bottleCapacity];
    	for (int i = bottleCapacity-1; i >= 0; i--) {
    		do {
    			rdSymbolIndex = rd.nextInt(nrSymbols);
    		} while(contadorSimbolos[rdSymbolIndex] == bottleCapacity);	
    		contadorSimbolos[rdSymbolIndex]++;
    		chosenSymbols[i] = symbols[rdSymbolIndex];
    	}
		return Arrays.copyOf(chosenSymbols, chosenSymbols.length);
    }
    
    /**
     * Re-enche a mesa com as definiçoes iniciais mas com conteudos diferentes
     */
    public void regenerateTable() {
        nrBottles = nrBottlesInicial;
        bottles = fillBottles();
    }

    /**
     * Veririca se uma certa garrafa da mesa eh considerada como uma garrafa de um so conteudo
     * 
     * @param i indice da garrafa a verificar
     * @return true se a garrafa eh de um so conteudo, false caso contrario
     */
    public boolean singleFilling(int i) {
        return bottles[i].isSingleFilling() ? true : false;
    }

    /**
     * Verifica se uma certa garrafa da mesa esta vazia
     * 
     * @param i indice da garrafa a verificar
     * @return true se a garrafa esta vazia, false caso contrario
     */
    public boolean isEmpty(int i) {
        return bottles[i].isEmpty() ? true : false;
    }

    /**
     * Verifica se uma certa garrafa da mesa esta cheia
     * 
     * @param i indice da garrafa a verificar
     * @return true se a garrafa esta cheia, false caso contrario
     */
    public boolean isFull(int i) {
        return bottles[i].isFull() ? true : false;
    }

    /**
     * Verifica se todas as garrafas da mesa estao cheias
     * 
     * @return true se todas as garrafas estao cheias, false caso contrario
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
     * Verte o conteudo de uma garrafa para outra
     * 
     * @param i garrafa de onde se vai verter
     * @param j garrafa para onde se vai verter
     * @throws IllegalArgumentException caso a garrafa i esteja vazia
     */
    public void pourFromTo(int i, int j) {
		if (bottles[j].receive(top(i))) {
			bottles[i].pourOut();
		}
    }
    
    /**
     * Adiciona uma certa garrafa a mesa
     * 
     * @param bottle garrafa a adicionar
     */
    public void addBottle(Bottle bottle) {
        nrBottles++; // incrementa o numero de garrafas
        bottles = Arrays.copyOf(bottles, bottles.length+1); // aumenta o tamanho do array de garrafas
        bottles[bottles.length-1] = bottle; // adiciona a garrafa
    }

    /**
     * Metodo que obtem a capacidade das garrafas na table
     * 
     * @return a capacidade das garrafas
     */
    public int getSizeBottles() {
        return bottleCapacity;
    }

    /**
     * Metodo que obtem o filling no topo de uma garrafa
     * 
     * @param i indice da garrafa
     * @throws EmptyStackException caso a garrafa esteja vazia
     * @return o filling no topo da garrafa
     */
    public Filling top(int i) {
        return bottles[i].top();
    }
    
    /**
     * Metodo que representa a mesa em forma de string
     * 
     * @return uma representaçao textual da mesa
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
