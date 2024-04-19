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

    private Stack<Filling> contents; // pilha de contents
    private Filling[] contentsArray; // array de contents
    private final int size; // capacidade maxima da bottle
    private int state; // numero de contents atuais na bottle

    /**
     * 
     */
    public Bottle() {
    	contents = new Stack<>();
        this.size = DEFAULT_CAPACITY;
        this.contentsArray = getContentArray();
    }

    /**
     * 
     * @param capacity
     */
    public Bottle(int capacity) {
    	contents = new Stack<>();
    	this.size = capacity;
    	this.contentsArray = getContentArray();
    }

    /**
     * 
     * @param content
     */
    public Bottle(Filling[] content) {
        this.size = content.length;
        contents = new Stack<>();
        for (int i = 0; i < content.length; i++) { // loop de trás para frente
            if (content[i] != null) { // se o content nao for null
            	contents.push(content[i]); // adicionar o elemento a pilha
                state++; // e aumentar o state atual da pilha
            }
        }
        this.contentsArray = getContentArray();
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
    	return contents.isEmpty();
    }

    /**
     * 
     * @return
     * @throws EmptyStackException caso a garrafa esteja vazia
     */
    public Filling top() {
    	    return contents.peek();
    }

    /**
     * 
     * @return
     */
    public int spaceAvailable() {
        return size - state;
    }
    
    //  TODO meter no javadoc
    /**
     * 
     */
    public void pourOut() {
    	if (!isEmpty()) {
    		contents.pop();
    		updateContentArray();
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
    
    /**
     * 
     * @return
     */
    public Filling[] getContent() {
        return Arrays.copyOf(contentsArray, contentsArray.length);
    }
    
    /**
     * Inicializa a array de contents
     * 
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
     * Atualiza a array de contents
     * 
     */
    private void updateContentArray() {
    	this.contentsArray[size - state] = null;
    }
    
    /**
     * Atualiza a array de contents
     * 
     */
    private void updateContentArray(Filling s) {
    	this.contentsArray[size - state - 1] = s;
    }
    
    /**
     * Dado um index pega no filling nessa posicao na bottle
     */
    public String getFilling(int index) {
    	return this.contentsArray[index] == null ? EMPTY : this.contentsArray[index].toString();
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
