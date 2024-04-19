package types;

/**
 * Classe que representa um jogo de Water Puzzle Sort.
 * 
 * @author Rodrigo Afonso (61839)
 * @version 1.0
 */

import java.util.EmptyStackException;

public class Game {
	private int score;
	private Table table;
	private int jogadas;

    /**
     * Contrutor de um jogo com um array de simbolos, um numero de simbolos usados, uma seed e uma capacidade
     * 
     * @param symbols array de simbolos possiveis
     * @param numberOfUsedSymbols numero de simbolos usados
     * @param seed seed para gerar numeros aleatorios
     * @param capacity capacidade das garrafas
     */
    public Game(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
    	this.table = new Table(symbols, numberOfUsedSymbols, seed, capacity);
    }

    /**
     * Contrutor de um jogo com um array de simbolos, um numero de simbolos usados, uma seed, uma capacidade e um score
     * 
     * @param symbols array de simbolos possiveis
     * @param numberOfUsedSymbols numero de simbolos usados
     * @param seed seed para gerar numeros aleatorios
     * @param capacity capacidade das garrafas
     * @param score pontuacao do jogo
     */
    public Game(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity, int score) {
    	this.table = new Table(symbols, numberOfUsedSymbols, seed, capacity);
    	this.score = score;
    }

    /**
     * Em troca de 100 pontos, adiciona uma nova garrafa vazia à mesa
     */
    public void provideHelp() {
    	if(score >= 100) {
    		table.addBottle(getNewBottle());
    		score -= 100;
		} else {
			throw new IllegalStateException("You don't have enough points to add a new bottle.");
		}
    }

    /**
     * Metodo que obtem o numero de jogadas efetuadas na ronda
     * 
     * @return numero de jogadas efetuadas na ronda
     */
    public int jogadas() {
        return jogadas;
    }

    /**
     * De acordo com o numero de jogadas, atualiza a pontuacao
     *  1 < Jogadas <= 10: 1000 pontos
     * 10 < Jogadas <= 15: 500 pontos
     * 15 < Jogadas <= 25: 100 pontos
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
     * Metodo que verifica se a ronda esta terminada, ou seja, se todas as garrafas
     * estao cheias e com o mesmo conteudo ou vazias
     * 
     * @return true se a ronda estiver terminada, false caso contrario
     */
    public boolean isRoundFinished() {
        return table.areAllFilled();
    }

    /**
     * Metodo que obtem a pontuacao do jogo
     * 
     * @return pontuacao do jogo
     */
    public int score() {
        return score;
    }

    /**
     * Inicia uma nova ronda
     */
    public void startNewRound() {
        jogadas = 0;
		table.regenerateTable();
    }

    /**
     * Metodo que obtem uma nova garrafa vazia
     * 
     * @return uma nova garrafa vazia
     */
    public Bottle getNewBottle() {
        return new Bottle(table.getSizeBottles());

    }

    /**
     * Metodo que obtem o estado da mesa
     * 
     * @return uma string com a pontuacao, o estado da mesa e o estado da ronda
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
     * Metodo que executa uma jogada verificando sempre se e possivel verter o conteudo
     * 
     * @param i garrafa de onde se vai verter
     * @param j garrafa para onde se vai verter
     * @throws IllegalArgumentException caso a jogada seja invalida
     */
    public void play(int i, int j) {
    	try {
    		Filling filling = table.top(i); // guarda o conteudo do topo da garrafa i
    		// enquanto o conteudo do topo da garrafa i for igual ao filling e a garrafa j nao estiver cheia
    		// e o conteudo do topo da garrafa j for igual ao filling ou nulo
    		while(table.top(i) == filling && !table.isFull(j) && (table.isEmpty(j) || table.top(j) == filling)) {
        		table.pourFromTo(i, j); // verte o conteudo da garrafa i para a garrafa j
        	}
    	} catch (EmptyStackException e) { // caso a garrafa i esteja vazia
    	    // nao faz nada
        } catch (IllegalArgumentException e) { // caso a jogada seja invalida
            throw new IllegalArgumentException("Invalid move. Please choose a valid move." + Table.EOL);
        }
    	jogadas++; // incrementa o numero de jogadas
    	updateScore(); // atualiza a pontuacao
    }
}
