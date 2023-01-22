
public class ListaDomande {
    private Quiz[] domande;
    private int domandeContenute;
    private int prossimaDomanda;
    
    public ListaDomande(int numDomande) {
        domande = new Quiz[numDomande];
        domandeContenute = 0;
        prossimaDomanda = 0;
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
