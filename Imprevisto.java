
public class Imprevisto {
	private String desc;
	private TipoImprevisto tipoImprevisto;
	private int parametro;
	private TipoImprevisto tipoImprevisto2;
	private int parametro2;
	
	/**
	 * Costruttore per gli imprevisti a doppio effetto
	 * @param desc
	 * @param tipoImprevisto
	 * @param parametro
	 * @param tipoImprevisto2
	 * @param parametro2
	 */
	public Imprevisto(String desc, TipoImprevisto tipoImprevisto, int parametro, TipoImprevisto tipoImprevisto2, int parametro2) {
		this.desc = desc;
		this.tipoImprevisto = tipoImprevisto;
		this.parametro = parametro;
		this.tipoImprevisto2 = tipoImprevisto2;
		this.parametro2 = parametro2;
	}
	
	/**
	 * Costruttore per gli imprevisti ad effetto singolo 
	 * @param desc
	 * @param tipoImprevisto
	 * @param parametro
	 */
	public Imprevisto(String desc, TipoImprevisto tipoImprevisto, int parametro) {
		this.desc = desc;
		this.tipoImprevisto = tipoImprevisto;
		this.parametro = parametro;
	}
	
	/**
	 * Applica effetti dell'imprevisto
	 * @param g
	 */
	public void applicaImprevisto(Giocatore g){
		switch(tipoImprevisto) {
		case AGGIUNGI_PUNTI: {
			g.addScore(parametro);
			break;
		}
		case SALTA_CASELLE: {
			g.movePos(parametro);
			break;
		}
		case VAI_A_CASELLA: {
			g.setPos(parametro);
			break;
		}
		}
	}

	/**
	 * @return la descrizione
	 */
	public String getDesc() {
		return desc;
	}
	
	
}
