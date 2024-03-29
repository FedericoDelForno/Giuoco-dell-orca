public class Quiz {
	private int id;  // Numero identificativo univoco della domanda
    private String domanda;
    private String[] risposte;
    private int rispostaCorretta;  // da 0 a 3
    private Diffic diffic;
    private int punti;
    
    /**
     * Costruttore che prende in input ogni parametro e prende un array per le risposte
     * @param domanda
     * @param r
     * @param rispostaCorretta		Indice della risposta corretta, da 0 a 3
     * @param id
     * @param diffic
     */
    public Quiz(int id, String domanda, String[] r, int rispostaCorretta, Diffic diffic) {
        this(id, domanda, r[0], r[1], r[2], r[3], rispostaCorretta, diffic);
    }
    
    /**
     * Costruttore completo che prende in input ogni parametro e prende le risposte come stringhe separate
     * @param domanda
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @param rispostaCorretta		Indice della risposta corretta, da 0 a 3
     * @param id
     * @param diffic
     */
    public Quiz(int id, String domanda, String r1, String r2, String r3, String r4, int rispostaCorretta, Diffic diffic) {
    	this.domanda = domanda;
        this.rispostaCorretta = rispostaCorretta;
        this.id = id;
        this.diffic = diffic;
        risposte = new String[4];
    	risposte[0] = r1;
    	risposte[1] = r2;
    	risposte[2] = r3;
    	risposte[3] = r4;
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
    
    public Quiz() {
    	this.risposte = new String[4];
    }
    
    public boolean corretta(int inputUtente) {
        return inputUtente == rispostaCorretta;
    }
    
    public String toString() {
        String s = domanda;
        for(int i=0; i<risposte.length; i++) {
            s += "\n" + (char)(i+97) + ") " + risposte[i];
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

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param domanda the domanda to set
	 */
	public void setDomanda(String domanda) {
		this.domanda = domanda;
	}

	/**
	 * @param risposte the risposte to set
	 */
	public void setRisposte(String[] risposte) {
		this.risposte = risposte;
	}
	
	public void setRisposta(String risposta, int pos) {
		risposte[pos] = risposta;
	}

	/**
	 * @param rispostaCorretta the rispostaCorretta to set
	 */
	public void setRispostaCorretta(int rispostaCorretta) {
		this.rispostaCorretta = rispostaCorretta;
	}

	/**
	 * @param diffic the diffic to set
	 */
	public void setDiffic(Diffic diffic) {
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
	public void setDiffic(String s) {
		switch(s) {
		case "FACILE": {
			this.setDiffic(Diffic.FACILE);
			break;
		}
		case "MEDIO": {
			this.setDiffic(Diffic.MEDIO);
			break;
		}
		case "DIFFICILE": {
			this.setDiffic(Diffic.DIFFICILE);
			break;
		}
		default: {
			this.setDiffic(Diffic.FACILE);
			break;
		}
		}
		
	}
	
	/**
	 * @param punti the punti to set
	 */
	public void setPunti(int punti) {
		this.punti = punti;
	}

	
	
	
}
