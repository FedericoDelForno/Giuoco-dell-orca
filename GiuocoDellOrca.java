
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
		final int L_TABELLONE = 34;
		int a[] = new int[L_TABELLONE];
		for(int i = 0; i < L_TABELLONE; i++) {
			a[i] = i;
		}
		Tabellone tabellone = new Tabellone(a);
		stampaTabellone(tabellone);  // Riga di test
	}
	
	/**
	 * Metodo che stampa il tabellone sotto forma di serpentina
	 * @param t
	 */
	public static void stampaTabellone(Tabellone t) {
		final int LARGHEZZA_SERPENTINA = 4;
		int altezzaTot = 0;
		int c = t.getLunghezza();
		// Calcola quanto sarebbe alto il tabellone se stampato a forma di serpentina dove tra ogni due righe c'e' uno spazio occupato solamente da una casella ad un estremo della figura
		while(true) {
			altezzaTot++;
			c -= LARGHEZZA_SERPENTINA;
			if(c <= 0) {
				break;
			}
			altezzaTot++;
			c -= 1;
			if(c <= 0) {
				break;
			}
		}
		
		boolean ultimaRigaDiCaselle = false;
		boolean versoDestra = true;  // variabile che e' true quando il programma sta stampando le caselle in ordine crescente
		int casellaAttuale = 0;  // Variabile che contiene il numero della casella sta stampando in un determinato momento
		int primaCasellaRiga = 0;  // Numero della prima casella della riga
		for(int i = 0; i < (altezzaTot*2)+1; i++) {					
			if(i % 8 <= 3) {
				versoDestra = true;
			}
			else {
				versoDestra = false;
			}
			
			if(i % 2 == 1) {
				casellaAttuale = primaCasellaRiga;  
				/* Se hai appena finito di stampare i lati superiori delle caselle,
				 * fai tornare il contatore della casella attuale a quello della
				 * prima casella della riga
				*/
			}
			else if(i != 0 && versoDestra) {
				primaCasellaRiga = casellaAttuale;
			}
			else if(!versoDestra) {
				if((i/2) % 4 == 3) {
					primaCasellaRiga++;
				}
				else {
					primaCasellaRiga = casellaAttuale + LARGHEZZA_SERPENTINA-1;
				}
				casellaAttuale = primaCasellaRiga;
			}
					
			for(int j = 0; j < LARGHEZZA_SERPENTINA; j++) {			
				if(i % 2 == 0  || i % 4 == 1 || (i % 8 == 3 && j == LARGHEZZA_SERPENTINA-1) || (i % 8 == 7 && j == 0)) {					
					if(i % 2 == 0) {
						System.out.print("+---+");
					}
					else {
						stampaCas((char)(casellaAttuale + 48));
					}
					
					if(versoDestra || (i/2) % 4 != 2) {
						casellaAttuale++;
					}
					else{
						casellaAttuale--;
					}			
				}
				
				else {
					System.out.print("     ");
				}
				
				if(i >= (altezzaTot-1)*2) {
					ultimaRigaDiCaselle = true;
				}
				
			}
			System.out.println("");
			
		}	
	}
	
	public static void stampaCas(char c) {
		System.out.print("| ");
		System.out.print(c);
		System.out.print(" |");
	}
	
	public static void stampaCas() {
		System.out.print("|   |");
	}

}