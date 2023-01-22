/**
 * Classe Casella (edit della classe Nodo)
 * @author Del Forno Sinicco Federico
 * @version 1.0
 */

class Casella {
    private int elemento;  // L'elemento e' il numero identificativo univoco
    private Casella successivo;
    private Casella precedente;
    private TipoCasella tipoCasella;
    private int param;  // Parametro 
    private String titolo; 
    
    public Casella(){
        this(0,null,null,false);
    }

    /**
     * Versione completa del costruttore di casella. Possiede ogni parametro
     * @param elemento			Numero univoco
     * @param successivo		Casella succesiva
     * @param precedente		Casella precedente
     * @param tipoCasella 		Il tipo di casella (vedi l'omonimo ENUM)
     * @param param				Se questa casella ti fa andare avanti, il parametro indica di quante caselle lo fa
     * @param titolo			
     */
    public Casella(int elemento, Casella successivo, Casella precedente, TipoCasella tipoCasella, int param, String titolo){
        this.elemento = elemento;
        this.successivo = successivo;
        this.precedente = precedente;
        this.tipoCasella = tipoCasella;
        this.param = param;
        this.titolo = titolo; 
    }
    
    public Casella(int elemento, Casella successivo, Casella precedente, boolean imprevisto, String titolo){
        this(elemento, successivo, precedente, TipoCasella.NORMALE, 0, titolo);
        if(imprevisto)
        	tipoCasella = TipoCasella.IMPREVISTO; 
    }
    
    public Casella(int elemento, Casella successivo, Casella precedente, boolean imprevisto){
    	this(elemento, successivo, precedente, imprevisto, "");
    }
    
    public Casella(int elemento){
        this(elemento, false);
    }
    
    public Casella(int elemento, boolean imprevisto){
    	this(elemento, null, null, imprevisto, "");
    }
    
    public Casella(int elemento, Casella successivo, Casella precedente){
        this(elemento, successivo, precedente, false);
    }

    @Override
	public Casella clone() {
		Casella b = new Casella(elemento, successivo, precedente, tipoCasella, param, titolo);
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
		if(tipoCasella == TipoCasella.IMPREVISTO)
			return true;
		else
			return false;
	}

	/**
	 * assegna valore ad imprevisto
	 * @param imprevisto 		il valore da assegnare ad imprevisto
	 */
	public void setImprevisto(boolean imprevisto) {
		if(imprevisto)
			tipoCasella = TipoCasella.IMPREVISTO;
		else if(tipoCasella == TipoCasella.IMPREVISTO)
			tipoCasella = TipoCasella.NORMALE;
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

	
	/**
	 * @return il tipo della casella
	 */
	public TipoCasella getTipoCasella() {
		return tipoCasella;
	}

	/**
	 * @param tipoCasella the tipoCasella to set
	 */
	public void setTipoCasella(TipoCasella tipoCasella, int param) {
		this.tipoCasella = tipoCasella;
		this.param = param;
	}
}