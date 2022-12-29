class Casella {
    private int elemento;
    private Casella successivo;
    private Casella precedente;
    private boolean imprevisto;
    
    public Casella(){
        this(0,null,null,false);
    }

    public Casella(int elemento, Casella successivo, Casella precedente, boolean imprevisto){
        this.elemento = elemento;
        this.successivo = successivo;
        this.precedente = precedente;
        this.imprevisto = imprevisto;
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
}
