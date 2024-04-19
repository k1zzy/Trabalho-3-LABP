package testsTable;

import types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestesColegas {

	private Table tableTeste;
	
	public String EOL = Table.EOL;
	
	// que teste estupido caralho
//	@Test
//	void testMelody() {
//		Filling[] symbols = Filling.values();
//		int numberOfUsedSymbols = 3;
//		int seed =1;
//		int bootleSize = 4;
//
//
//		tableTeste = new Table(symbols, numberOfUsedSymbols, seed, bootleSize);
//
//		tableTeste.addBottle(new Bottle());
//		
//		tableTeste.pourFromTo(0, 6);
//		String actual = tableTeste.toString();
//		String expected = 
//				"⬜    😒    😡    ⬜    ⬜    ⬜    ⬜    "+ Table.EOL
//				+ "😒    😡    😡    ⬜    ⬜    ⬜    ⬜    "+ Table.EOL
//				+ "😒    😒    😃    ⬜    ⬜    ⬜    ⬜    "+ Table.EOL
//				+ "😃    😡    😃    ⬜    ⬜    ⬜    😃    "+ Table.EOL
//				;
//
//		assertEquals(expected, actual);
//	}
	
	@Test
	void testMelody2() {
		Filling[] symbols = Filling.values();
		int numberOfUsedSymbols = 3;
		int seed =1;
		int boottleSize = 4;


		tableTeste = new Table(symbols, numberOfUsedSymbols, seed, boottleSize);

		tableTeste.addBottle(new Bottle(boottleSize));
		
		tableTeste.pourFromTo(2, 6);
		tableTeste.pourFromTo(2, 6);
		String actual = tableTeste.toString();
		
		
		String expected = 
				"😃    😒    ⬜    ⬜    ⬜    ⬜    ⬜    "+ Table.EOL
				+ "😒    😡    ⬜    ⬜    ⬜    ⬜    ⬜    "+ Table.EOL
				+ "😒    😒    😃    ⬜    ⬜    ⬜    😡    "+ Table.EOL
				+ "😃    😡    😃    ⬜    ⬜    ⬜    😡    "+ Table.EOL
				;

		assertEquals(expected, actual);
	}
	
	@Test
	void testMelody3() {
		Filling[] symbols = Filling.values();
		int numberOfUsedSymbols = 3;
		int seed = 10;
		int bootleSize = 4;
		

		tableTeste = new Table(symbols, numberOfUsedSymbols, seed, bootleSize);
		tableTeste.pourFromTo(0, 3);
		
		boolean actual = tableTeste.areAllFilled();
		boolean expected = false;
		

		assertEquals(expected, actual);
	}
}
