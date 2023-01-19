
public class Imprevisto {
	private String desc;
	private TipoImprevisto tipoImprevisto;
	private int parametro;
	
	/**
	 * Costruttore completo che chiede in input ogni parametro 
	 * @param desc
	 * @param tipoImprevisto
	 * @param parametro
	 */
	public Imprevisto(String desc, TipoImprevisto tipoImprevisto, int parametro) {
		super();
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
