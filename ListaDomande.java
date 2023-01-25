
public class ListaDomande {
    private Quiz[] domande;
    private int domandeContenute;
    private int prossimaDomanda;
    
    public ListaDomande(int numDomande) {
        domande = new Quiz[numDomande];
        domandeContenute = 0;
        prossimaDomanda = 0;
    }
    
    
    
    @Override
	public String toString() {
		String s = "";
		s += "Domande contenute: " +domandeContenute+ "\n";
		s += "Prossima domanda: " +prossimaDomanda+ "\n";
		s += "CIMA DEL MAZZO\n";
		for(int i = 0; i < domandeContenute; i++) {
			s += i + ") ";
			s += domande[i].toString() + "\n";
		}
		s += "FONDO DEL MAZZO\n";
    	return s;
	}



	public boolean addDomanda(Quiz domanda) {
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
        if(prossimaDomanda >= domande.length)
        	prossimaDomanda = 0;
        return q;
    }
    
    public void mischia() {
    	for(int i = 0; i < domandeContenute; i++) {
    		swap(i, (int)(Math.random() * domandeContenute));
    	}
    }
    
    private void swap(int x, int y) {
    	Quiz temp = domande[x];
    	domande[x] = domande[y];
    	domande[y] = temp;
    }
}
