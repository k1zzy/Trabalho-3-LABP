package types;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack; //remover no caso de optar por uma classe de AED.

public class Bottle implements Iterable<Filling>{
    public static final int DEFAULT_CAPACITY = 5;
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();

    private Stack<Filling> contents;
    private int size; // TODO ver se eh final
    private int state; // numero de contents atuais na bottle

    /**
     * 
     */
    public Bottle() {
    	contents = new Stack<>();
        this.size = DEFAULT_CAPACITY;
    }

    /**
     * 
     * @param capacity
     */
    public Bottle(int capacity) {
    	contents = new Stack<>();
    	this.size = capacity;
    }

    /**
     * 
     * @param content
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
    }

    /**
     * 
     * @return
     */
    public boolean isFull() {
        return state == size;
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
    	return state == 0;
    }

    /**
     * 
     * @return
     */
    public Filling top() {
    	if (!isEmpty()) { // se nao estiver vazia
    	    return contents.peek();
    	} else {
    	    throw new EmptyStackException();
    	}
    }

    /**
     * 
     * @return
     */
    public int spaceAvailable() {
        return size - state;
    }

    /**
     * 
     */
    public void pourOut() {
    	if (!isEmpty()) {
    		contents.pop();
        	state--;
    	} else {
    		throw new IllegalArgumentException("A garrafa nao tem conteudo para verter");
    	}
    }

    /**
     * 
     * @param s
     */
    public boolean receive(Filling s) {
    	// so e valido caso reste espaco no topo, o filling nao for nulo e o filling for igual ao do topo
    	// ou a garrafa estiver vazia
        if (state < size && !(s.equals(null)) && (state == 0 || s.equals(top()))) {
        	contents.push(s); // coloca la o filling
        	state++;
        	return true; // e da operacao bem sucedida
        } else { // caso contrario
        	return false; // operacao mal sucedida
        }
    }

    /**
     * 
     * @return
     */
    public int capacity() {
        return size;
    }	

    /**
     * 
     * @return
     */
    public boolean isSingleFilling() {
    	for (Filling cont : contents) {
    		if (!contents.peek().equals(cont)) { // se o primeiro nao for igual aos proximos
    			return false; // quer dizer que nao sao todos iguais
    		}
    	}
    	return true;
    }
    
    // TODO deve haver melhor maneira com o iterador
    /**
     * 
     * @return
     */
    public Filling[] getContent() {
        Filling[] array = new Filling[size];
        Stack<Filling> copia = (Stack<Filling>) contents.clone();
        for (int i = 0; i < state; i++) {
        	array[i] = copia.pop();
        }
        return Arrays.copyOf(array, array.length);
    }
    
    /**
     * 
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
     * 
     */
    public Iterator<Filling> iterator() {
        return new BottleIterator();
    }
    
    /**
     * 
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
