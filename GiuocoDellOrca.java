import java.util.concurrent.TimeUnit;

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
		
		partita(nPers);
	}
	
	/**
	 * Metodo per la partita
	 * @param nGioc 	Numero di giocatori
	 */
	public static void partita(int nGioc) {
		final int L_TABELLONE = 15;
		Tabellone tabellone = initTabellone(L_TABELLONE);
		MazzoImprevisti mazzoImprevisti = initMazzoImprevisti(tabellone);
		
		// Loop
		int turno = 0;
		while(true) {
			stampaTabellone(tabellone);
			do {	
				System.out.println("Tocca a " + giocatori[turno].getTitoloG() + ", Scrivi 1 e poi premi invio per lanciare il dado\n");
			}while(Leggi.unChar() != '1');
			int risultDado = lanciaDado();
			System.out.println("Sto lanciando il dado...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("E' uscito il numero " + risultDado);		
			giocatori[turno].movePos(risultDado);
			// Torna indietro se hai un numero troppo alto alla fine
			if(giocatori[turno].getPos() >= L_TABELLONE) {
				giocatori[turno].movePos(2 * ((L_TABELLONE-1) - giocatori[turno].getPos())); 
			}
			
			
			// Attiva la lotta se due giocatori si incontrano su una casella che non e' la 0
			if(giocatoreIn(giocatori[turno].getPos(), turno) != null && giocatori[turno].getPos() != 0) {
				lotta(giocatori[turno], giocatoreIn(giocatori[turno].getPos(), turno));
			}
			
			// Cambia turno
			turno++;  
			if(turno >= nGioc) {
				turno = 0;
			}
		}
	}
	
	/**
	 * Metodo per inizializzare il tabellone
	 */
	public static Tabellone initTabellone(int lunghezza) {		
		int a[] = new int[lunghezza];
		for(int i = 0; i < lunghezza; i++) {
			a[i] = i;
		}
		return new Tabellone(a);
	}
	
	public static MazzoImprevisti initMazzoImprevisti(Tabellone t){
		final int L_MAZZO_IMPREVISTI = 50;
		MazzoImprevisti m = new MazzoImprevisti(L_MAZZO_IMPREVISTI);
		m.addImprevisto(new Imprevisto("Torna al VIA", TipoImprevisto.VAI_A_CASELLA, 0));
		m.addImprevisto(new Imprevisto("Per sbaglio inciampi su un sasso e tutti i punti che avevi ti escono dalla tasca", TipoImprevisto.SET_PUNTI, 0));
		m.addImprevisto(new Imprevisto("Trovi una scorciatoia, Vai avanti di 6 caselle", TipoImprevisto.SALTA_CASELLE, 6));
		m.addImprevisto(new Imprevisto("Trovi una strada che sembra una scorciatoia, ci entri, poi ti accorgi che quella strada ti ha portato indietro di 10 caselle", TipoImprevisto.SALTA_CASELLE, -10));
		//m.addImprevisto(new Imprevisto("Accidentalmente, calpesti un pulsante che fa partire un attacco nucleare contro la Calabria e fai esplodere Catanzaro. Il presidente della Nord Korea ti viene a trovare di persona, si congratula con te per l'incredibile attacco nucleare che nemmeno lui sarebbe riuscito a fare e ti regala 999 Punti", TipoImprevisto.AGGIUNGI_PUNTI, 999));
		m.addImprevisto(new Imprevisto("Trovi 10 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 10));
		m.addImprevisto(new Imprevisto("Trovi 30 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 30));
		m.addImprevisto(new Imprevisto("Trovi 50 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 50));
		m.addImprevisto(new Imprevisto("Trovi 69 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 69));
		m.addImprevisto(new Imprevisto("Trovi 120 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 120));
		m.addImprevisto(new Imprevisto("Trovi 1000 Punti per terra, e siccome sei una brava persona civile cerchi di rintracciare il proprietario per restituirli. Scherzo, te li tieni per te", TipoImprevisto.AGGIUNGI_PUNTI, 1000));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 20 punti", TipoImprevisto.AGGIUNGI_PUNTI, -20));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 40 punti", TipoImprevisto.AGGIUNGI_PUNTI, -40));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 60 punti", TipoImprevisto.AGGIUNGI_PUNTI, -60));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 180 punti", TipoImprevisto.AGGIUNGI_PUNTI, -180));
		m.addImprevisto(new Imprevisto("Un tassista ti offre un passaggio alla penultima casella, accetti, pero' poi ti chiede di pagarlo con tutti i punti che hai", TipoImprevisto.VAI_A_CASELLA, t.getLunghezza()-2, TipoImprevisto.SET_PUNTI, 0));
		return m;
	}
	
	/**
	 * Metodo che stampa il tabellone sotto forma di serpentina
	 * @param t	Tabellone
	 */
	public static void stampaTabellone(Tabellone t) {
		final int LARGHEZZA_SERPENTINA = 6;
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
			
			if(i % 2 == 1 || ultimaRigaDiCaselle) {
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
					if(casellaAttuale >= t.getLunghezza() && ultimaRigaDiCaselle) {
						System.out.print("     ");
					}
					else if(i % 2 == 0) {
						System.out.print("+---+");
					}
					else {
						if(giocatoreIn(casellaAttuale) != null) {
							stampaCas(pedinaIn(casellaAttuale));  // Stampa la pedina che c'e' in una casella, se ce n'e' una
						}
						else {
							stampaCas(t.nodoIn(casellaAttuale).getElemento());  // Stampa il numero della casella, se non c'e' nessuna pedina
						}
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
	
	/**
	 * Stampa i bordi laterali di una casella. prende un carattere in input e lo stampa in mezzo
	 * @param c 	carattere da stampare
	 */
	public static void stampaCas(char c) {
		System.out.print("| ");
		System.out.print(c);
		System.out.print(" |");
	}
	/**
	 * Stampa i bordi laterali di una casella. prende un int in input e lo stampa in mezzo, adattandolo in base al numero di cifre
	 * @param n 	intero da stampare
	 */
	public static void stampaCas(int n) {
		System.out.print("|");
		if(n < 10)
			System.out.print(" ");
		System.out.print(n);
		if(n < 100)
			System.out.print(" ");
		System.out.print("|");
	}
	/**
	 * Stampa i bordi laterali di una casella.
	 */
	public static void stampaCas() {
		System.out.print("|   |");
	}

	/**
	 * Ritorna il numero del giocatore in una certa posizione
	 * @param pos
	 * @return
	 */
	public static int numGiocatoreIn(int pos) {
		for(int i = 0; i < MAX_PLAYERS; i++) {
			if(giocatori[i].getPos() == pos) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Ritorna il carattere della pedina del giocatore in una certa posizione
	 * @param pos
	 * @return
	 */
	public static char pedinaIn(int pos) {
		return giocatoreIn(pos).getPedina();
	}
	/**
	 * Ritorna il giocatore in una certa posizione
	 * @param pos
	 * @return
	 */
	public static Giocatore giocatoreIn(int pos) {
		for(int i = 0; i < MAX_PLAYERS; i++) {
			if(giocatori[i] == null) {
				return null;
			}
			if(giocatori[i].getPos() == pos) {
				return giocatori[i];
			}
		}
		return null;
	}
	/**
	 * Ritorna il giocatore in una certa posizione
	 * @param pos
	 * @return
	 */
	public static Giocatore giocatoreIn(int pos, int giocDaIgnorare) {
		for(int i = 0; i < MAX_PLAYERS; i++) {
			if(i == giocDaIgnorare) {
				i++;
			}
			if(giocatori[i] == null) {
				return null;
			}
			if(giocatori[i].getPos() == pos) {
				return giocatori[i];
			}
		}
		return null;
	}
	
	
	public static int lanciaDado() {
		final int DADO_MIN = 1;
		final int DADO_MAX = 6;
		return (int)(Math.random() * (DADO_MAX) + DADO_MIN);
	}
	
	public static void lotta(Giocatore g1, Giocatore g2) {
		final int ROUND_DA_VINCERE = 2;
		int w1 = 0;
		int w2 = 0;
		System.out.println("Inizia la lotta tra " + g1.getTitoloG() + " e " + g2.getTitoloG());
		Giocatore winner = new Giocatore("", ' ');
		Giocatore loser = new Giocatore("", ' ');;
		for(int i = 0; i < (ROUND_DA_VINCERE*2) - 1; i++) {
			System.out.println("LANCIO NUMERO " + i);
			System.out.println(g1.getTitoloG() + " Sta lanciando il dado...");
			int d1 = lanciaDado();
			System.out.println("E' uscito " + d1);
			System.out.println(g2.getTitoloG() + " Sta lanciando il dado...");
			int d2 = lanciaDado();
			System.out.println("E' uscito " + d2);
			if(d1 > d2) {
				w1++;
			}
			else if(d1 < d2){
				w2++;
			}
			else {
				System.out.println("Pareggio, rifare!");
				i--;
			}
			
			if(w1 == ROUND_DA_VINCERE) {
				winner = g1;
				loser = g2;
				break;
			}
			else if(w2 == ROUND_DA_VINCERE){
				winner = g2;
				loser = g1;
				break;
			}
		}
		
		winner.addScore(loser.getScore());
		loser.setScore(0);
		
		while(giocatoreIn(loser.getPos()) != null) {
			loser.movePos(-1);  // Indietreggia finche non raggiungi una casella senza giocatori
		}
		if(loser.getPos() < 0) {
			loser.setPos(0);  // La casella 0 e' l'unico posto dove piu' giocatori possono stare insieme
		}  
	}
}
