
public class Domandiere {
    private Quiz[] domande;
    private int domandeContenute;
    private int prossimaDomanda;
    
    
    public Domandiere(int numDomande) {
        domande = new Quiz[numDomande];
        domandeContenute = 0;
        prossimaDomanda = 0;
    }
    
    public boolean aggiungiDomanda(Quiz domanda) {
        boolean res = false;
        if(domandeContenute < domande.length) {
            domande[domandeContenute] = domanda;
            domandeContenute++;
            res = true;
        }
        return res;
    }
    
    public Quiz prossimaDomanda() {
        Quiz q = domande[prossimaDomanda];
        prossimaDomanda++;
        return q;
    }
    
    public boolean finito() {
        return prossimaDomanda >= domande.length;
    }
    
    /**
     * Estrai la prossima domanda che ha la difficolta' inserita in input come parametro
     * @param diffic
     * @return
     */
    public Quiz prossimaDomandaDiffic(Diffic x) {
    	while(domande[prossimaDomanda].getDiffic() != x) {
    		prossimaDomanda++;
    		if(finito()) {
    			prossimaDomanda = 0;  // Se la lista di domande finisce allora torna all'inizio
    		}
    	}
    	Quiz q = domande[prossimaDomanda];
    	return q;
    }
    
    
}
