/**
 * Classe Tabellone (edit della classe Lista)
 * @author Del Forno Sinicco Federico
 * @version 1.0
 */
class Tabellone {
    private Casella primo;      // riferimento al primo nodo della lista
    private Casella ultimo;     // riferimento all'ultimo nodo della lista
    private int lunghezza;   // numero di elementi inseriti nella lista

    public Tabellone() {
        primo = null;
        ultimo = null;
        lunghezza = 0;
    }
    
    public Tabellone(int[] array) {
        this(); 
        for(int i=0; i<array.length; i++) {
            inserisciUltimo(array[i]);
        }
    }
    
    public Tabellone(Tabellone nuovaLista) {
        this();
        Casella nodo = nuovaLista.primo;
        for(int i=0; i<nuovaLista.lunghezza; i++) {
            this.inserisciUltimo(nodo.getElemento());
            nodo = nodo.getSuccessivo();
        }
    }

    // verifica se la lista e' vuota
    public boolean vuota(){
        return lunghezza == 0;
    }

    public int getLunghezza(){
        return lunghezza;
    }

    public int getPrimoElemento(){
        return primo.getElemento();
    }

    public int getUltimoElemento(){
        return ultimo.getElemento();
    }

    // Inserisce un nuovo elemento nella lista al primo posto
    public void inserisciPrimo(int elemento){
    	primo = new Casella(elemento,primo,null);
    	
        if (vuota())
            ultimo = primo;
        lunghezza++;
        
        if(lunghezza > 1)
        	nodoIn(1).setPrecedente(primo);
    }

    // Inserisce un nuovo elemento nella lista in ultima posizione
    public void inserisciUltimo(int elemento){
        if (vuota())
            inserisciPrimo(elemento);
        else {
            ultimo.setSuccessivo(new Casella(elemento,null,ultimo));
            ultimo = ultimo.getSuccessivo();
            lunghezza++;
        }
    }
    
    public void inserisci(int elemento, int posizione, boolean imprevisto) {
    	Casella nuovoNodo = new Casella(elemento, imprevisto);
    	Casella precedente = nodoIn(posizione-1);
    	Casella seguente = precedente.getSuccessivo();
        precedente.setSuccessivo(nuovoNodo);
        nuovoNodo.setSuccessivo(seguente);   
        
        nuovoNodo.setPrecedente(precedente);	// il precedente del nodo seguente e' il nuovo nodo
        seguente.setPrecedente(nuovoNodo);
        lunghezza++;
        
    }
    
    public void inserisci(int elemento, int posizione) {
    	this.inserisci(elemento, posizione, false);
    }
    
    public Casella nodoIn(int posizione) {
    	Casella nodo = primo;
        for(int salto=0; salto<posizione; salto++) {
            nodo = nodo.getSuccessivo();
        }
        return nodo;
    }
    
    public int elementoIn(int posizione) {
        return nodoIn(posizione).getElemento();
    }
    
    public String toString() {
        String s = "";
        Casella nodo = primo;
        for(int salto=1; salto<=lunghezza; salto++) {
            s += nodo.getElemento() + " ";
            nodo = nodo.getSuccessivo();
        }
        return s;
    }
    
    public void cancella(int posizione) {
        // Cancella primo nodo
        if(posizione == 0) {
            primo = primo.getSuccessivo();
        }
        // Cancella ultimo nodo
        else if(posizione == lunghezza - 1) {
        	Casella penultimo = nodoIn(lunghezza-2);
            penultimo = null;
            ultimo = penultimo;
        }
        // Cancella nodo in mezzo
        else {
        	Casella precedente = nodoIn(posizione-1);
        	Casella daEliminare = precedente.getSuccessivo();
            precedente.setSuccessivo(daEliminare.getSuccessivo());
            
            daEliminare.getSuccessivo().setPrecedente(precedente); // Il precedente del nodo successivo a quello cancellato diventa il nodo precedente a quello cancellato
        }
        lunghezza--;
    }
    
    
    /**
     * Scambia gli elementi di due posizioni
     * @param pos1
     * @param pos2
     */
    public void swap(int pos1, int pos2) {
    	Casella temp1 = nodoIn(pos1).clone();
    	Casella temp2 = nodoIn(pos2).clone();
    	cancella(pos1);
    	this.inserisci(temp2.getElemento(), pos1, temp2.isImprevisto());
    	    	  
    	cancella(pos2);
    	this.inserisci(temp1.getElemento(), pos2, temp1.isImprevisto());
    }
    
    

} 
