
public class MazzoImprevisti {
	private Imprevisto[] imprevisti;
    private int imprevistiContenuti;
    private int prossimoImprevisto;
    
    public MazzoImprevisti(int numImp) {
    	imprevisti = new Imprevisto[numImp];
    	imprevistiContenuti = 0;
    	prossimoImprevisto = 0;
    }
    
    public boolean aggiungiImprevisto(Imprevisto imprevisto) {
        boolean res = false;
        if(imprevistiContenuti < imprevisti.length) {
        	imprevisti[imprevistiContenuti] = imprevisto;
        	imprevistiContenuti++;
            res = true;
        }
        return res;
    }
    
    public Imprevisto prossimoImprevisto() {
    	Imprevisto q = imprevisti[prossimoImprevisto];
        prossimoImprevisto++;
        return q;
    }
    
    public boolean finito() {
        return prossimoImprevisto >= imprevisti.length;
    }
}
