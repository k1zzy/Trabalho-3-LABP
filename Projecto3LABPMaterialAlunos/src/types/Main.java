package types;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // scanner para leitura de input	
		Random rd = new Random(System.currentTimeMillis() % 1000); // seed aleatoria com base no tempo atual em milissegundos
		
		int option = 0; // opcao escolhida pelo utilizador, 1 para valores por defeito, 2 para valores personalizados
		Filling[] symbols = Filling.values(); // array com todos os simbolos possiveis
		int seed = rd.nextInt(999999); // seed para gerar numeros aleatorios
		int bottleCapacity = Table.DEFAULT_BOTTLE_CAPACITY; // capacidade de cada garrafa
		int nrBottles = 3; // numero de garrafas (sem contar com as adicionas da dificuldade)
		
		System.out.println("Welcome to the game Water Sort Puzzles" + Table.EOL);
		System.out.println("In this game, you will have to sort the bottles by colors." + Table.EOL);
		System.out.println("The colors are represented by emojis. A square is an empty space" + Table.EOL);
		
		System.out.println("Do you want to play with default values or costumize your game?" + Table.EOL);
		System.out.println("1. Default values" + Table.EOL);
		System.out.println("2. Custom values" + Table.EOL);
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
			}
		}
		
		if (option == 1) {
			System.out.println("Starting game with default values.");
		} else {
			System.out.println("How many full bottles do you want to play with? Between 2 and 12:" + Table.EOL);
			System.out.println("Difficulty: " + Table.DIFFICULTY +
							   " (adds " + Table.DIFFICULTY + " empty bottles)"
							   + Table.EOL);
			while (true) { // ciclo para leitura do numero de garrafas
				try {
					nrBottles = sc.nextInt();
					if (nrBottles < 2 || nrBottles > 12) {
						System.out.println("The number of bottles must be between 2 and 12:" + Table.EOL);
						System.out.println("Difficulty: " + Table.DIFFICULTY +
								   " (adds " + Table.DIFFICULTY + " empty bottles)" + Table.EOL);
					} else {
						break; // sai do ciclo se for um valor valido
					}
				} catch (Exception e) {
						System.out.println("Choose a valid option." + Table.EOL);
				}
			}
			
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
				}
			}
		}
		sc.close();
	}
}
