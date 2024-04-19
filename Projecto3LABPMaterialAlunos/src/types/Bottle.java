package types;

/**
 * Classe que representa uma garrafa com fillings. A garrafa tem uma capacidade maxima e um estado atual.
 * 
 * @author Rodrigo Afonso (61839)
 * @version 1.0
 */

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack; //remover no caso de optar por uma classe de AED.

public class Bottle implements Iterable<Filling>{
    public static final int DEFAULT_CAPACITY = 5;
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();

    private Stack<Filling> contents; // pilha de contents
    private Filling[] contentsArray; // array de contents
    private final int size; // capacidade maxima da bottle
    private int state; // numero de contents atuais na bottle

    /**
     * Contrutor de uma bottle com as definições por defeito
     */
    public Bottle() {
    	contents = new Stack<>();
        this.size = DEFAULT_CAPACITY;
        this.contentsArray = getContentArray();
    }

    /**
     * Contrutor de uma bottle com uma capacidade definida
     * 
     * @param capacity capacidade da bottle
     */
    public Bottle(int capacity) {
    	contents = new Stack<>();
    	this.size = capacity;
    	this.contentsArray = getContentArray();
    }

    /**
     * Contrutor de uma bottle com um array de fillings
     * 
     * @param content array de fillings a adicionar a bottle
     */
    public Bottle(Filling[] content) {
        this.size = content.length;
        contents = new Stack<>();
        for (int i = content.length-1; i >= 0; i--) { // loop de trás para frente
            if (content[i] != null) { // se o content nao for null
            	contents.push(content[i]); // adicionar o elemento a pilha
                state++; // e aumentar o state atual da pilha
            }
        }
        this.contentsArray = getContentArray();
    }

    /**
     * Metodo utilizado para verificar se a bottle esta cheia
     * 
     * @return true se a bottle estiver cheia, false caso contrario
     */
    public boolean isFull() {
        return state == size;
    }

    /**
     * Metodo utilizado para verificar se a bottle esta vazia
     * 
     * @return true se a bottle estiver vazia, false caso contrario
     */
    public boolean isEmpty() {
    	return contents.isEmpty();
    }

    /**
     * Metodo utilizado para verificar o filling no topo da bottle
     * 
     * @return o filling no topo da bottle
     * @throws EmptyStackException caso a garrafa esteja vazia
     */
    public Filling top() {
    	    return contents.peek();
    }

    /**
     * Metodo utilizado para obter o espaço disponivel na bottle
     * 
     * @return o espaço disponivel na bottle
     */
    public int spaceAvailable() {
        return size - state;
    }
    
    /**
     * Metodo utilizado para retirar o conteudo no topo da bottle
     * 
     * @throws  EmptyStackException caso a garrafa esteja vazia
     */
    public void pourOut() {
    	if (!isEmpty()) {
    		contents.pop();
    		updateContentArray();
        	state--;
    	}
    }

    /**
     * Recebe um filling e coloca-o no topo da bottle
     * 
     * @param s filling a ser colocado no topo da bottle
     * @return true se a operação for bem sucedida, false caso contrario
     */
    public boolean receive(Filling s) {
    	// so e valido caso reste espaco no topo, o filling nao for nulo e o filling for igual ao do topo
    	// ou a garrafa estiver vazia
        if (state < size && !(s.equals(null)) && (isEmpty() || s.equals(top()))) {
        	contents.push(s); // coloca la o filling
        	updateContentArray(s);
        	state++;
        	return true; // e da operacao bem sucedida
        } else { // caso contrario
        	return false; // operacao mal sucedida
        }
    }

    /**
     * Metodo utilizado para obter a capacidade da bottle
     * 
     * @return a capacidade da bottle
     */
    public int capacity() {
        return size;
    }

    /**
     * Metodo utilizado para verificar se a bottle eh constituida por um unico filling
     * 
     * @return true se a bottle for constituida por um unico filling, false caso contrario
     */
    public boolean isSingleFilling() {
    	for (Filling cont : contents) {
    		if (!contents.peek().equals(cont)) { // se o primeiro nao for igual aos proximos
    			return false; // quer dizer que nao sao todos iguais
    		}
    	}
    	return true;
    }
    
    /**
     * Metodo utilizado para obter o conteudo da bottle representado em array
     * 
     * @return o conteudo da bottle em array
     */
    public Filling[] getContent() {
        return Arrays.copyOf(contentsArray, contentsArray.length);
    }
    
    /**
     * Inicializa a array de contents com os fillings da bottle
     * 
     * @return a array de contents que representa o conteudo da bottle
     */
    private Filling[] getContentArray() {
    	Filling[] array = new Filling[size];
        Stack<Filling> copia = (Stack<Filling>) contents.clone();
        for (int i = 0; i < state; i++) {
        	array[i] = copia.pop();
        }
        this.contentsArray = Arrays.copyOf(array, array.length);
        return Arrays.copyOf(array, array.length);
    }
    
    /**
     * Atualiza a array de contents, eh chamada apos cada operacao de pourOut
     */
    private void updateContentArray() {
    	this.contentsArray[size - state] = null;
    }
    
    /**
     * Atualiza a array de contents, eh chamada apos cada operacao de receive
     * 
     * @param s filling a ser colocado na array de contents
     */
    private void updateContentArray(Filling s) {
    	this.contentsArray[size - state - 1] = s;
    }
    
    /**
     * Dado um index pega no filling nessa posicao na bottle
     * 
     * @param index posicao do filling na bottle
     */
    public String getFilling(int index) {
    	return this.contentsArray[index] == null ? EMPTY : this.contentsArray[index].toString();
    }
    
    /**
     *  Metodo utilizado para obter o estado atual da bottle em string
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	Stack<Filling> copia = (Stack<Filling>) contents.clone();
        for (int i = 0; i < size; i++) {
        	if (i < size - state) {
        		sb.append(EMPTY);
        		sb.append(EOL);
        	} else {
        		sb.append(copia.pop());
        		sb.append(EOL);
        	}
        }
        return sb.toString();
    }

    /**
     * Metodo utilizado para obter o iterador da bottle
     * 
     * @return o iterador da bottle
     */
    public Iterator<Filling> iterator() {
        return new BottleIterator();
    }
    
    /**
     * Classe interna que implementa o iterador da bottle
     */
    private class BottleIterator implements Iterator<Filling> {
    	Stack<Filling> copia = (Stack<Filling>) contents.clone();

        public boolean hasNext() {
            return !copia.isEmpty(); // tem proximo desde que nao esteja vazia
        }

        public Filling next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copia.pop();
        }
    }
}
