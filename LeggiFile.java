import java.io.File; 
import java.io.FileNotFoundException;  
import java.util.Scanner; 

public class LeggiFile {
	public static void initDomandiere(Domandiere domandiere) {
		int i = 0;
		try {
		    File myObj = new File("domande.txt");
		    Scanner myReader = new Scanner(myObj);
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	analizzaStringa(data, domandiere, i);
		    	i++;
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	/**
	 * Analizza una stringa per inserirla nel domandiere sottoforma di domanda
	 * @param data
	 * @param domandiere
	 * @param idDomanda
	 */
	private static void analizzaStringa(String data, Domandiere domandiere, int idDomanda) {
		Quiz q = new Quiz();
		int e = 0;
		String s = "";
		for(int i = 0; i < data.length(); i++) {			
			if(data.charAt(i) == ';') {		
				if(e == 0) {
					q.setDomanda(s);
				}
				else if(e >= 1 && e <= 4) {
					q.setRisposta(s, e-1); 
				}
				else if(e == 5) {
					q.setRispostaCorretta(Integer.valueOf(s));
				}
				e++;
				s = "";
				while(data.charAt(i+1) == ' ') {
					i++;  // Incrementa contatore fino a quando non punta all'ultimo spazio prima della prossima stringa
				}
			}
			else {
				s += data.charAt(i);
			}
		}
		if(e == 6)
			q.setDiffic(s);
			q.setId(idDomanda);
			domandiere.addDomanda(q);
	}
}
