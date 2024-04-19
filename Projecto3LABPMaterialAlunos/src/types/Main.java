package types;

import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // scanner para leitura de input	
		Random rd = new Random(System.currentTimeMillis() % 1000); // seed aleatoria com base no tempo atual em milissegundos
		
		// opcao escolhida pelo utilizador, 1 para valores por defeito, 2 para valores personalizados
		int option = 0;
		int nrBottles = 3; // numero de garrafas (sem contar com as adicionas da dificuldade)
		int bottleCapacity = Table.DEFAULT_BOTTLE_CAPACITY; // capacidade de cada garrafa
		Filling[] symbols = Filling.values(); // array com todos os simbolos possiveis
		int seed = rd.nextInt(999999); // seed para gerar numeros aleatorios
		
		System.out.println("----------------------------------- Welcome to ------------------------------------" + Table.EOL);
		System.out.println("------------------------------- Water Sort Puzzles --------------------------------" + Table.EOL);
		System.out.println("In this game, you will have to sort the bottles by colors." + Table.EOL);
		System.out.println("The colors are represented by emojis. A square is an empty space." + Table.EOL);
		System.out.println("Everytime you win a game, you will earn a different quantity of points depending on how well you did." + Table.EOL);
		System.out.println("If you need a new empty bottle to help you, you can trade 100 points for one." + Table.EOL);
		
		System.out.println("Do you want to play with default values or customize your game?" + Table.EOL);
		System.out.println("1. Default values (Default: 3 filled + 3 empty bottles, 5 capacity)" + Table.EOL);
		System.out.println("2. Custom values" + Table.EOL);
		
		option = oneOrTwo(sc); // leitura da opcao escolhida
		
		if (option == 1) { // valores por defeito
			System.out.println("Starting game with default values.");
		} else { // valores personalizados
			nrBottles = chooseNrBottles(sc); // leitura do numero de garrafas
			bottleCapacity = chooseCapacity(sc); // leitura da capacidade das garrafas
			System.out.println("Starting game with chosen values.");
		}
		
		Game game = new Game(symbols, nrBottles, seed, bottleCapacity); // instancia do jogo
		int playAgain = 0; // opcao para jogar novamente ou nao
		int settings = 0; // opcao para manter as mesmas definicoes ou alterar
		while (true) { // ciclo para jogar novamente, se quiser
    		while (!game.isRoundFinished()) {
    		    if(game.score() >= 100) {
    		        System.out.println("Enough points to add an empty bottle. '0' (zero) to add (-100 points)" + Table.EOL);
    		    }
    			playRound(game, nrBottles, sc);
    		}
    		System.out.println(game.toString()); // mostra o estado final do jogo
    		System.out.println("You finished the round with a score of " + game.score() + "." + Table.EOL);
    		System.out.println("Do you want to play again?" + Table.EOL);
    		System.out.println("1. Yes" + Table.EOL);
    		System.out.println("2. No" + Table.EOL);
    		
    		playAgain = oneOrTwo(sc); // leitura da opcao escolhida
            if (playAgain == 2) {
                break; // sai do ciclo se o utilizador nao quiser jogar novamente
            }
            
            System.out.println("Do you wish to maintain the same settings or change them?" + Table.EOL);
            System.out.println("1. Same settings" + Table.EOL);
            System.out.println("2. Change settings" + Table.EOL);
            
            settings = oneOrTwo(sc); // leitura da opcao escolhida
            if (settings == 2) { // se quiser alterar as definicoes
                nrBottles = chooseNrBottles(sc); // leitura do numero de garrafas
                bottleCapacity = chooseCapacity(sc); // leitura da capacidade das garrafas
                game = new Game(symbols, nrBottles, seed, bottleCapacity, game.score()); // instancia do jogo
            } else { // se quiser manter as definicoes
                game.startNewRound(); // comeca um novo round com as mesmas definicoes
            }
		}
		
		System.out.println("-------------------- Thank you for playing Water Sort Puzzles! --------------------" + Table.EOL);
		try {
		    Thread.sleep(3000); // espera 3 segundos antes de terminar para dar para ler a mensagem de despedida
		  } catch (InterruptedException e) {
		    Thread.currentThread().interrupt();
		  }
		
		sc.close(); // fecha o scanner
	}

	public static int oneOrTwo(Scanner sc) {
		int option = 0;
		while (true) { // ciclo para leitura da opcao
			try {
				option = sc.nextInt();
				if (option < 1 || option > 2) {
					System.out.println("Invalid option. Please choose 1 or 2." + Table.EOL);
				} else {
					break; // sai do ciclo se for uma opcao valida
				}
			} catch (Exception e) {
				System.out.println("Choose a valid option." + Table.EOL);
				sc.nextLine(); // limpar o buffer
				continue;
			}
		}
		return option;
	}
	
	public static void playRound(Game game, int nrBottles, Scanner sc) {
		int fromBottle = 0;
		int toBottle = 0;
		System.out.println(game.toString()); // mostra o estado atual do jogo
			
		System.out.println("Choose a bottle to pour from: " + Table.EOL);
		while (true) {
			try { // leitura da garrafa DE onde se vai verter
	            fromBottle = sc.nextInt() -1; // subtrai 1 para obter o indice correto
	            if (fromBottle < 0 || fromBottle >= nrBottles + Table.DIFFICULTY ) {
	                if (fromBottle == -1) { // se for -1 (se o utilizador enviar um 0)
                        try {
                            game.provideHelp(); // adiciona uma garrafa vazia
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + Table.EOL); // mostra mensagem de erro se o score for < 100
                            sc.nextLine(); // limpar o buffer
                        continue;
                        }
                    } else {
                        System.out.println("Invalid input. Please choose a valid bottle to pour from." + Table.EOL);
                        sc.nextLine(); // limpar o buffer
                    }
				} else {
					break; // sai do ciclo se for um valor valido
				}
	        } catch (Exception e) {
	        	System.out.println("Invalid input. Please choose a valid bottle to pour from." + Table.EOL);
	        	sc.nextLine(); // limpar o buffer
	            continue;
	        }
		}
        System.out.println("Choose a bottle to pour to: " + Table.EOL);
        while (true) {
        	try { // leitura da garrafa PARA onde se vai verter
        	    toBottle = sc.nextInt() -1; // subtrai 1 para obter o indice correto
	            if (toBottle <= 0 || toBottle >= nrBottles + Table.DIFFICULTY ) {
	                System.out.println("Invalid input. Please choose a valid bottle to pour to." + Table.EOL);
	                sc.nextLine(); // limpar o buffer
				} else {
					break; // sai do ciclo se for um valor valido
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please choose a valid bottle to pour to." + Table.EOL);
				sc.nextLine(); // limpar o buffer
				continue;
			}
        }
        try {
        	game.play(fromBottle, toBottle); // executa a jogada
        } catch (Exception e) {
            System.out.println(e.getMessage() + Table.EOL);
            playRound(game, nrBottles, sc); // volta a pedir as garrafas
        }
	}
	
	public static int chooseNrBottles(Scanner sc) {
		int nrBottles = 0;
		System.out.println("How many full bottles do you want to play with? Between 2 and 8:" + Table.EOL);
		System.out.println("Difficulty: " + Table.DIFFICULTY +
						   " (adds " + Table.DIFFICULTY + " empty bottles)"
						   + Table.EOL);
		while (true) { // ciclo para leitura do numero de garrafas
			try {
				nrBottles = sc.nextInt();
				if (nrBottles < 2 || nrBottles > 8) {
					System.out.println("The number of bottles must be between 2 and 8:" + Table.EOL);
					System.out.println("Difficulty: " + Table.DIFFICULTY +
							   " (adds " + Table.DIFFICULTY + " empty bottles)" + Table.EOL);
				} else {
					break; // sai do ciclo se for um valor valido
				}
			} catch (Exception e) {
					System.out.println("Choose a valid option." + Table.EOL);
					sc.nextLine(); // limpar o buffer
					continue;
			}
		}
		return nrBottles;
    }
	
	public static int chooseCapacity(Scanner sc) {
		int bottleCapacity = 0;
		System.out.println("What capacity do you want the bottles to have? Between 3 and 8:" + Table.EOL);
		while (true) { // ciclo para leitura da capacidade das garrafas
			try {
				bottleCapacity = sc.nextInt();
				if (bottleCapacity < 3 || bottleCapacity > 8) {
					System.out.println("The capacity of bottles must be between 3 and 8:" + Table.EOL);
				} else {
					break; // sai do ciclo se for um valor valido
				}
			} catch (Exception e) {
					System.out.println("Choose a valid option." + Table.EOL);
					sc.nextLine(); // limpar o buffer
					continue;
			}
		}
		return bottleCapacity;
	}
}
