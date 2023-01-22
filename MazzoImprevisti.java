
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
    
    
    public boolean addImprevisto(Imprevisto imprevisto) {
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
    
    public void comprimi() {
    	for(int i = imprevisti.length; i >= 0; i--) {
    		if(imprevisti[i] != null) {
    			int imprevistoPiuAlto;
    			imprevistoPiuAlto = i;
    			for(int j = imprevistoPiuAlto; j >= 0; j-- ) {
    				if(imprevisti[j] == null) {
    					imprevisti[j] = imprevisti[i];
    					imprevisti[i] = null;
    				}
    			}
    		}
    	}
    }
}
