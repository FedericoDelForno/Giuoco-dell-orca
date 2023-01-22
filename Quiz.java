public class Quiz {
    private String domanda;
    private String[] risposte;
    private int rispostaCorretta;
    private int id;  // Numero identificativo univoco della domanda
    private Diffic diffic;
    private int punti;
    
    /**
     * Costruttore completo che prende in input ogni parametro
     * @param domanda
     * @param risposte
     * @param rispostaCorretta
     * @param id
     * @param diffic
     */
    public Quiz(String domanda, String[] risposte, int rispostaCorretta, int id, Diffic diffic) {
        this.domanda = domanda;
        this.risposte = risposte;
        this.rispostaCorretta = rispostaCorretta;
        this.id = id;
        this.diffic = diffic;
        switch(diffic) {
	        case FACILE: {
	        	punti = 10;
	        	break;
	        }
	        case MEDIO: {
	        	punti = 30;
	        	break;
	        }
	        case DIFFICILE: {
	        	punti = 50;
	        	break;
	        }
        }
    }
    
    public boolean corretta(int inputUtente) {
        return inputUtente == rispostaCorretta;
    }
    
    public String toString() {
        String s = domanda;
        for(int i=0; i<risposte.length; i++) {
            s += "\n" + i + ") " + risposte[i];
        }
        s += "\nID = " + id;
        s += "\nDifficolta': " + diffic + " Punti: " + punti;
        return s;
    }

	/**
	 * @return la difficolta'
	 */
	public Diffic getDiffic() {
		return diffic;
	}
	public int getPunti() {
		return punti;
	}
    
    
}
