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
		while(true) {
			for(int i = 0; i < MAX_PLAYERS; i++) {
				giocatori[i] = null;
			}
			String s = "IL GIUOCO DELL' ORCA\n\n";
			s += "1 - Gioca\n";
			s += "2 - Esci\n";
			s += "\n\n(Inserisci 0 per la debug mode)\n";
			System.out.println(s);
			switch(Leggi.unInt()) {
			case 1: {
				playerMenu();
				break;
			}
			case 2: {
				return;
			}
			case 0:{
				// Debug mode
				giocatori[0] = new Giocatore("@", '@');
				giocatori[1] = new Giocatore("#", '#');
				partita(2, true);
				break;
			}
			default: {
				System.out.println("Input non valido");
			}
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
					if(pedineDisponibili[j] == pedina && pedina != '.') {
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
	
	public static void partita(int nGioc) {
		partita(nGioc, false);
	}
	/**
	 * Metodo per la partita
	 * @param nGioc 		Numero di giocatori
	 * @param debugMode
	 */
	public static void partita(int nGioc, boolean debugMode) {
		final int L_TABELLONE = 32;
		final int PUNTI_PER_VINCERE = 1;  // Punti che il giocatore deve possedere per vincere quando raggiunge la casella finale
		final int L_LISTA_DOMANDE = 50;
		
		Tabellone tabellone = initTabellone(L_TABELLONE);
		MazzoImprevisti mazzoImprevisti = initMazzoImprevisti(tabellone);
		mazzoImprevisti.mischia();
		Domandiere domandiere = new Domandiere(L_LISTA_DOMANDE);
		LeggiFile.initDomandiere(domandiere);
		domandiere.mischiaTutto();
		if(debugMode) {
			System.out.println(mazzoImprevisti.toString());
			System.out.println("\n\n");
			System.out.println(domandiere.toString());
		}
		
		// Loop
		int turno = 0;
		while(true) {
			cls();
			stampaScoreboard();
			stampaTabellone(tabellone);
			int risultDado;
			if(!debugMode) {
				do {	
					System.out.println("Tocca a " + giocatori[turno].getTitoloG() + ", Scrivi 1 e poi premi invio per lanciare il dado\n");
				}while(Leggi.unChar() != '1');
				risultDado = lanciaDado();
				System.out.println("Sto lanciando il dado...");
				delay(1000);
			}
			else {
				System.out.println("Tocca a " + giocatori[turno].getTitoloG() + ", Che risultato avra' il lancio del dado?");
				risultDado = Leggi.unInt();
			}
			cls();
			System.out.println("E' uscito il numero " + risultDado);		
			giocatori[turno].movePos(risultDado);
			// Torna indietro se hai un numero troppo alto alla fine
			if(giocatori[turno].getPos() >= L_TABELLONE) {
				giocatori[turno].movePos(2 * ((L_TABELLONE-1) - giocatori[turno].getPos())); 
			}
			else if(giocatori[turno].getPos() == L_TABELLONE-1) {
				// Vittoria
				if(giocatori[turno].getScore() >= PUNTI_PER_VINCERE) {
					System.out.println(giocatori[turno].getTitoloG() +" HA VINTO!");
					return;
				}
				else {
					System.out.println("Non hai abbastanza punti, Torna al VIA!");
					giocatori[turno].setPos(0);
				}
			}
			stampaScoreboard();
			stampaTabellone(tabellone);
			delay(1200);
			
			// Attiva la lotta se due giocatori si incontrano su una casella che non e' la 0
			boolean eventoFineTurno = false;  // Questa varabile e' falsa fino a quando non parte una lotta o qualcuno finisce su una casella speciale
			if(giocatoreIn(giocatori[turno].getPos(), turno) != null && giocatori[turno].getPos() != 0) {
				lotta(giocatori[turno], giocatoreIn(giocatori[turno].getPos(), turno));
				eventoFineTurno = true;
			}
			while(giocatori[turno].getPos() != 0 && giocatoreIn(giocatori[turno].getPos(), turno) != null) {
				giocatori[turno].movePos(-1);  // Indietreggia finche non raggiungi una casella senza giocatori
			}
			if(giocatori[turno].getPos() < 0) {
				giocatori[turno].setPos(0);  // La casella 0 e' l'unico posto dove piu' giocatori possono stare insieme
			}
			
			// Pesca Imprevisto (Non succede se hai appena lottato)
			if(tabellone.nodoIn(giocatori[turno].getPos()).getTipoCasella() == TipoCasella.IMPREVISTO && !eventoFineTurno) {
				System.out.println("Sei finito su una casella imprevisto...\n" + mazzoImprevisti.pesca(giocatori[turno]).getDesc()+ "\n\nScrivi qualcosa per continuare");
				Leggi.unChar();
				
				eventoFineTurno = true;
			}
			// 
		
			Quiz q = domandiere.pescaDomanda();
			System.out.println(q);
			char risp;
			int rispInt = 0;
			do {
				risp = Leggi.unChar();
				if(((int)risp) >= 65 && ((int)risp) <= 68) {
					rispInt = ((int)risp) - 65;
				}
				else if (((int)risp) >= 97 && ((int)risp) <= 100) {
					rispInt = ((int)risp) - 97;
				}
				else {
					System.out.println("Input non valido");
					continue;
				}
				break;
			}while(true);
			if(q.corretta(rispInt)) {
				giocatori[turno].addScore(q.getPunti());  // Aumenta punti
				System.out.println("\n\nRisposta giusta, ottieni " +q.getPunti()+ " punti");
				delay(1000);
				cls();
				stampaScoreboard();
				stampaTabellone(tabellone, true);
				System.out.println("\nScegli una casella da spostare, scrivi in input il suo numero (Non puo' essere la casella iniziale o finale)");
				int casellaDaSpostare;		// ID della casella da scegliere
				int posCasellaDaSpostare = 0;	// Posizione della casella da scegliere
				do {
					casellaDaSpostare = Leggi.unInt();
					if(casellaDaSpostare > 0 && casellaDaSpostare < (L_TABELLONE-1)) {
						break;
					}
					System.out.println("Input non valido");
				} while(true);
				System.out.println("Sto lanciando il dado...");
				delay(1000);
				cls();
				int d = lanciaDado();
				System.out.println("E' uscito " +d+ " La casella e' stata scambiata con un'altra distante" +d+ " caselle");
				for(int i = 1; i < L_TABELLONE; i++) {
					if(tabellone.nodoIn(i).getElemento() == casellaDaSpostare) {
						posCasellaDaSpostare = i;
						break;
					}
				}
				if(posCasellaDaSpostare + d < (L_TABELLONE-1)) {
					tabellone.swap(posCasellaDaSpostare, posCasellaDaSpostare+d);
				}
				else {
					tabellone.swap(posCasellaDaSpostare, L_TABELLONE-2);
				}
				stampaTabellone(tabellone, true);
				delay(3800);
			}
			else {
				giocatori[turno].halfScore();
				System.out.println("Risposta sbagliata, punteggio dimezzato");
				delay(1200);
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
		Tabellone t = new Tabellone(a);
		for(int i = 5; i < lunghezza-1; i+=3) {
			t.nodoIn(i).setTipoCasella(TipoCasella.IMPREVISTO, 0);
		}
		return t;
	}
	
	public static MazzoImprevisti initMazzoImprevisti(Tabellone t){
		final int L_MAZZO_IMPREVISTI = 50;
		MazzoImprevisti m = new MazzoImprevisti(L_MAZZO_IMPREVISTI);
		m.addImprevisto(new Imprevisto("Torna al VIA", TipoImprevisto.VAI_A_CASELLA, 0));
		m.addImprevisto(new Imprevisto("Per sbaglio inciampi su un sasso e tutti i punti che avevi ti escono dalle tasche", TipoImprevisto.SET_PUNTI, 0));
		m.addImprevisto(new Imprevisto("Trovi una scorciatoia, Vai avanti di 3 caselle", TipoImprevisto.SALTA_CASELLE, 3));
		m.addImprevisto(new Imprevisto("Trovi una scorciatoia, Vai avanti di 6 caselle", TipoImprevisto.SALTA_CASELLE, 6));
		m.addImprevisto(new Imprevisto("Trovi una scorciatoia, Vai avanti di 9 caselle", TipoImprevisto.SALTA_CASELLE, 9));
		m.addImprevisto(new Imprevisto("Trovi una strada che sembra una scorciatoia, la percorri fino in fondo, ma poi ti accorgi che quella strada ti ha portato indietro di 15 caselle", TipoImprevisto.SALTA_CASELLE, -15));
		m.addImprevisto(new Imprevisto("Trovi 10 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 10));
		m.addImprevisto(new Imprevisto("Trovi 15 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 15));
		m.addImprevisto(new Imprevisto("Trovi 20 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 20));
		m.addImprevisto(new Imprevisto("Trovi 30 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 30));
		m.addImprevisto(new Imprevisto("Trovi 50 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 50));
		m.addImprevisto(new Imprevisto("Trovi 69 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 69));
		m.addImprevisto(new Imprevisto("Trovi 120 Punti per terra, li prendi", TipoImprevisto.AGGIUNGI_PUNTI, 120));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 10 punti", TipoImprevisto.AGGIUNGI_PUNTI, -10));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 15 punti", TipoImprevisto.AGGIUNGI_PUNTI, -15));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 20 punti", TipoImprevisto.AGGIUNGI_PUNTI, -20));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 30 punti", TipoImprevisto.AGGIUNGI_PUNTI, -30));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 40 punti", TipoImprevisto.AGGIUNGI_PUNTI, -40));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 60 punti", TipoImprevisto.AGGIUNGI_PUNTI, -60));
		m.addImprevisto(new Imprevisto("Ti inciampi e fai cadere 180 punti", TipoImprevisto.AGGIUNGI_PUNTI, -100));
		m.addImprevisto(new Imprevisto("Un tassista ti offre un passaggio alla penultima casella, accetti, pero' poi ti chiede di pagarlo con tutti i punti che hai", TipoImprevisto.VAI_A_CASELLA, t.getLunghezza()-2, TipoImprevisto.SET_PUNTI, 0));
		return m;
	}
	
	/**
	 * """Pulisci""" schermo
	 */
	public static void cls() {
		for(int i = 0; i < 50; i++)
			System.out.println("\n\n");
	}
	
	/**
	 * Metodo che stampa il tabellone sotto forma di serpentina
	 * @param t	Tabellone
	 */
	public static void stampaTabellone(Tabellone t) {
		stampaTabellone(t, false);
	}
	public static void stampaTabellone(Tabellone t, boolean MostraNumeriCaselle) {
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
						else if(t.nodoIn(casellaAttuale).getTipoCasella() == TipoCasella.IMPREVISTO && !MostraNumeriCaselle) {
							stampaCas('!');
						}
						else {
							if(MostraNumeriCaselle)
								stampaCas(t.nodoIn(casellaAttuale).getElemento(), t.nodoIn(casellaAttuale).isImprevisto());  // Stampa il numero della casella, se non c'e' nessuna pedina
							else
								stampaCas();  // Stampa casella vuota
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
	 * Stampa la scoreboard
	 */
	public static void stampaScoreboard() {
		for(int i = 0; i < 20; i++)
			System.out.print("--");
		System.out.print("\n");
		System.out.println("SCORE");
		for(int i = 0; i < MAX_PLAYERS; i++) {
			if(giocatori[i] == null) {
				break;
			}
			System.out.println(giocatori[i].getTitoloG()+ " " +giocatori[i].getScore() );
		}
		for(int i = 0; i < 20; i++)
			System.out.print("--");
		System.out.print("\n");
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
	public static void stampaCas(int n, boolean imp) {
		if(imp) {
			System.out.print("!");
			if(n < 10)
				System.out.print(" ");
			System.out.print(n);
			if(n < 100)
				System.out.print(" ");
			System.out.print("!");
		}
		else {
			stampaCas(n);
		}
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
	 * Ritorna il giocatore in una certa posizione. Ignora il giocatore specificato
	 * @param pos
	 * @param giocDaIgnorare 	Giocatore da ignorare
	 * @return
	 */
	public static Giocatore giocatoreIn(int pos, int giocDaIgnorare) {
		for(int i = 0; i < MAX_PLAYERS; i++) {
			if(i == giocDaIgnorare) {
				continue;
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
	/**
	 * Ritorna il giocatore in una certa posizione. Ignora il giocatore specificato
	 * @param pos
	 * @param giocDaIgnorare	Giocatore da ignorare
	 * @return
	 */
	public static Giocatore giocatoreIn(int pos, Giocatore giocDaIgnorare) {
		for(int i = 0; i < MAX_PLAYERS; i++) {
			if(giocatori[i] == giocDaIgnorare) {
				continue;
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
		System.out.println("\n\n\nINIZIA LA LOTTA TRA " + g1.getTitoloG() + " e " + g2.getTitoloG() + ".\nVince il primo che ottiene il numero maggiore per due volte");
		delay(2000);
		Giocatore winner = new Giocatore("", ' ');
		Giocatore loser = new Giocatore("", ' ');;
		for(int i = 0; i < (ROUND_DA_VINCERE*2) - 1; i++) {
			delay(2000);
			System.out.println("\nLANCIO NUMERO " + (i+1));
			delay(2000);
			System.out.println(g1.getTitoloG() + " Sta lanciando il dado...");
			delay(1000);
			int d1 = lanciaDado();
			System.out.println("E' uscito " + d1);
			delay(1500);
			System.out.println(g2.getTitoloG() + " Sta lanciando il dado...");
			delay(1000);
			int d2 = lanciaDado();
			System.out.println("E' uscito " + d2);
			delay(800);
			if(d1 > d2) {
				w1++;
			}
			else if(d1 < d2){
				w2++;
			}
			else {
				System.out.println("Pareggio, rifare!");
				i--;
				continue;
			}
			System.out.println(g1.getTitoloG()+"  "+w1+" - "+w2+"  "+g2.getTitoloG());
			
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
		
		System.out.println(winner.getTitoloG() + " ha vinto la lotta");
		winner.addScore(loser.getScore());
		loser.setScore(0);
		
		while(loser.getPos() != 0 && giocatoreIn(loser.getPos(), loser) != null) {
			loser.movePos(-1);  // Indietreggia finche non raggiungi una casella senza giocatori
		}
		if(loser.getPos() < 0) {
			loser.setPos(0);  // La casella 0 e' l'unico posto dove piu' giocatori possono stare insieme
		}
		return;  
	}
	
	/**
	 * Metodo per fermare l'esecuzione del codice per x millisecondi
	 * @param x		Numero di millisecondi
	 */
	public static void delay(int x) {
		try {
			TimeUnit.MILLISECONDS.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
