/**
 * Classe Casella (edit della classe Nodo)
 * @author Del Forno Sinicco Federico
 * @version 1.0
 */

class Casella {
    private int elemento;  // L'elemento e' il numero identificativo univoco
    private String titolo; 
    private Casella successivo;
    private Casella precedente;
    private boolean imprevisto;
    
    public Casella(){
        this(0,null,null,false);
    }

    /**
     * Versione completa del costruttore di casella. Possiede ogni parametro
     * @param elemento
     * @param successivo
     * @param precedente
     * @param imprevisto
     * @param titolo
     */
    public Casella(int elemento, Casella successivo, Casella precedente, boolean imprevisto, String titolo){
        this.elemento = elemento;
        this.successivo = successivo;
        this.precedente = precedente;
        this.imprevisto = imprevisto;
        this.titolo = titolo;
    }
    
    public Casella(int elemento, Casella successivo, Casella precedente, boolean imprevisto){
    	this(elemento, successivo, precedente, imprevisto, "");
    }
    
    public Casella(int elemento){
        this(elemento, false);
    }
    
    public Casella(int elemento, boolean imprevisto){
        this.elemento = elemento;
        this.imprevisto = imprevisto;
    }
    
    public Casella(int elemento, Casella successivo, Casella precedente){
        this(elemento, successivo, precedente, false);
    }

    @Override
	public Casella clone() {
		Casella b = new Casella(elemento, successivo, precedente, imprevisto);
    	return b;
	}

	public int getElemento(){
        return this.elemento;
    }

    public Casella getSuccessivo(){
        return this.successivo;
    }

    public void setElemento(int elemento){
        this.elemento = elemento;
    }

    public void setSuccessivo(Casella successivo){
        this.successivo = successivo;
    }

	public Casella getPrecedente() {
		return precedente;
	}

	public void setPrecedente(Casella precedente) {
		this.precedente = precedente;
	}

	/**
	 * ritorna il valore booleano imprevisto
	 * @return imprevisto
	 */
	public boolean isImprevisto() {
		return imprevisto;
	}

	/**
	 * assegna valore ad imprevisto
	 * @param imprevisto 		il valore da assegnare ad imprevisto
	 */
	public void setImprevisto(boolean imprevisto) {
		this.imprevisto = imprevisto;
	}

	/**
	 * @return il titolo
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * @param setta il titolo
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
}
