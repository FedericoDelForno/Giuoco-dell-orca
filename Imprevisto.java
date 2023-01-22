
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
	
	
	
	@Override
	public String toString() {
		return "desc= " + desc + ",    tipoImprevisto= " + tipoImprevisto + ", parametro= " + parametro 
				+ ", tipoImprevisto2= " + tipoImprevisto2 + ", parametro2= " + parametro2;
	}

	/**
	 * Applica effetti dell'imprevisto
	 * @param g		Giocatore sul quale ha effetto l'imprevisto
	 */
	public void applicaImprevisto(Giocatore g){
		TipoImprevisto ti = tipoImprevisto;
		int p = parametro;
		for(int i = 0; i < 2; i++) {
			switch(ti) {
			case AGGIUNGI_PUNTI: {
				g.addScore(p);
				break;
			}
			case SET_PUNTI: {
				g.setScore(p);
				break;
			}
			case SALTA_CASELLE: {
				g.movePos(p);
				break;
			}
			case VAI_A_CASELLA: {
				g.setPos(p);
				break;
			}
			default: {
				break;
			}
			}
			ti = tipoImprevisto2;
			p = parametro2;
		}
	}

	/**
	 * @return la descrizione
	 */
	public String getDesc() {
		return desc;
	}
	
	
}
