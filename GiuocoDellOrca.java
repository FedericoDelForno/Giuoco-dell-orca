
public class GiuocoDellOrca {
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 6;
	public static Giocatore[] giocatori = new Giocatore[MAX_PLAYERS];
	
	public static void main(String[] args) {
		mainMenu();		
	}
	
	/**
	 * Menu principale
	 */
	public static void mainMenu(){		
		String s = "IL GIUOCO DELL' ORCA\n\n";
		s += "1 - Gioca\n";
		s += "2 - Esci\n";
		System.out.println(s);
		switch(Leggi.unInt()) {
		case 1: {
			playerMenu();
			break;
		}
		case 2: {
			return;
		}
		default: {
			System.out.println("Input non valido");
		}
		}
	}	
	
	/**
	 * Menu dove scegli la quantita' dei giocatori
	 */
	public static void playerMenu() {		
		String s = "Scegli un numero di giocatori da 2 a 6, oppure scrivi 0 per tornare indietro\n";
		System.out.println(s);
		int input = Leggi.unInt();
		if(input == 0) {
			return;
		}
		else if(input >= MIN_PLAYERS && input <= MAX_PLAYERS) {
			creazionePersonaggi(input);
		}
	}
	
	/**
	 * Metodo per scegliere nome e pedina di ogni giocatore
	 * @param nPers
	 */
	public static void creazionePersonaggi(int nPers) {
		char[] pedineDisponibili = {'#', '@', '%', '&', '£', '€', '$', 'W'};  // Devono sempre esserci piu' pedine disponibili rispetto a MAX_PLAYERS
		
		for(int i = 0; i < nPers; i++) {
			System.out.println("Giocatore " + i + ", Come ti chiami?\n");
			String nome = Leggi.unoString();
			System.out.println("Giocatore " + i + ", Seleziona una pedina inserendo il carattere che vuoi fra i seguenti: \n");
			for(int j = 0; j < pedineDisponibili.length; j++) {
				if(pedineDisponibili[j] != '.') {
					System.out.print(pedineDisponibili[j] + ", ");
				}	
			}
			
			boolean inputValido;
			char pedina;
			do {
				inputValido = true;
				pedina = Leggi.unChar();				
				for(int j = 0; j < pedineDisponibili.length; j++) {
					if(pedineDisponibili[j] == pedina) {
						pedineDisponibili[j] = '.';  // Sostituisce la pedina rimossa con un punto, che non verra' stampato la prossima volta che l'array delle pedine disponibili verra' mostrato
						break;
					}
					if(j == pedineDisponibili.length-1) {
						System.out.println("input non valido");
						inputValido = false;
					}
				}
			}while(!inputValido);
			
			giocatori[i] = new Giocatore(nome, pedina);
		}
		
		partita();
	}
	
	/**
	 * Metodo per la partita
	 */
	public static void partita() {
		
	}
}
