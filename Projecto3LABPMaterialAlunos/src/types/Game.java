package types;

import java.util.EmptyStackException;

public class Game {
	private int score;
	private Table table;
	private int jogadas;

    /**
     * 
     * @param symbols
     * @param numberOfUsedSymbols
     * @param seed
     * @param capacity
     */
    public Game(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
    	this.table = new Table(symbols, numberOfUsedSymbols, seed, capacity);
    }

    /**
     * 
     * @param symbols
     * @param numberOfUsedSymbols
     * @param seed
     * @param capacity
     * @param score
     */
    public Game(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity, int score) {
    	this.table = new Table(symbols, numberOfUsedSymbols, seed, capacity);
    	this.score = score;
    }

    /**
     * 
     */
    public void provideHelp() {
    	if(score >= 100) {
    		table.addBottle(getNewBottle());
    		score -= 100;
		} else {
			throw new IllegalStateException("You don't have enough points to use this feature.");
		}
    }

    /**
     * 
     * @return
     */
    public int jogadas() {
        return jogadas;
    }

    /**
     * 
     */
    public void updateScore() {
    	if(isRoundFinished()) {
    		if(jogadas <= 10) {
    			score += 1000;
    		} else if(jogadas <= 15) {
    			score += 500;
    		} else if(jogadas <= 25){
    			score += 100;
    		}
    	}
    }

    /**
     * 
     * @return
     */
    public boolean isRoundFinished() {
        return table.areAllFilled();
    }

    /**
     * 
     * @return
     */
    public int score() {
        return score;
    }

    /**
     * 
     */
    public void startNewRound() {
		table.regenerateTable();
    }

    /**
     * 
     * @return
     */
    public Bottle getNewBottle() {
        return new Bottle(table.getSizeBottles());

    }

    /**
     * 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Score: " + score + Table.EOL);
        sb.append(table.toString());
        if(!isRoundFinished()) {
        	sb.append("Status: The round is not finished." + Table.EOL);
        	sb.append(jogadas + " moves have been used until now." + Table.EOL);
		} else {
			sb.append("Status: This round is finished." + Table.EOL);
			sb.append(jogadas + " moves were used." + Table.EOL);
		}
        return sb.toString();
    }
    
    /**
     * 
     * @param i
     * @param j
     * @throws EmptyStackException se a garrafa i estiver vazia
     */
    public void play(int i, int j) {
    	jogadas++; // incrementa o numero de jogadas
    	try {
    		Filling filling = table.top(i); // guarda o conteudo do topo da garrafa i
    		while(table.top(i) == filling) { // enquanto o conteudo do topo da garrafa i for igual
        		table.pourFromTo(i, j); // verte o conteudo da garrafa i para a garrafa j
        	}
    	} catch (EmptyStackException e) { // caso a garrafa i esteja vazia
    		// nada acontece
    	}
    	updateScore(); // atualiza a pontuacao
    }
}
