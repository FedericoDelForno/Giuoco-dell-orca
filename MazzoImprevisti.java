
public class MazzoImprevisti {
	private Imprevisto[] imprevisti;
    private int imprevistiContenuti;
    private int prossimoImprevisto;
    
    public MazzoImprevisti(int numImp) {
    	imprevisti = new Imprevisto[numImp];
    	imprevistiContenuti = 0;
    	prossimoImprevisto = 0;
    }
    
    public MazzoImprevisti(Imprevisto[] imprevisti) {
    	this.imprevisti = imprevisti;
    	imprevistiContenuti = imprevisti.length;
    	prossimoImprevisto = 0;
    }
    
    public String toString() {
    	String s = "CIMA DEL MAZZO\n";
    	for(int i = 0; i < imprevistiContenuti; i++) {
    		s += i + ") ";
    		s += imprevisti[i].toString();
    		s += "\n";
    	}
    	s += "FONDO DEL MAZZO";
    	return s;
    }
    
    public boolean addImprevisto(Imprevisto imprevisto) {
        boolean res = false;
        if(imprevistiContenuti < imprevisti.length) {
        	imprevisti[imprevistiContenuti] = imprevisto;
        	imprevistiContenuti++;
            res = true;
        }
        return res;
    }
    
    public void mischia() {
    	for(int i = 0; i < imprevistiContenuti; i++) {
    		swap(i, (int)(Math.random() * imprevistiContenuti));
    	}
    }
    
    private void swap(int x, int y) {
    	Imprevisto temp = imprevisti[x];
    	imprevisti[x] = imprevisti[y];
    	imprevisti[y] = temp;
    }
    
    public Imprevisto pesca(Giocatore g) {
    	Imprevisto q = imprevisti[prossimoImprevisto];
        prossimoImprevisto++;
        if(prossimoImprevisto >= imprevistiContenuti) {
        	prossimoImprevisto = 0;
        }
        q.applicaImprevisto(g);
        return q;
    }    
}
