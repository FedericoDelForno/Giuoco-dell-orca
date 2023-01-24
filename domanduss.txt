
public class Domandiere {
    private ListaDomande domandeFacile;
    private ListaDomande domandeMedio;
    private ListaDomande domandeDifficile;
    
    
    public Domandiere(int numDomande) {
        domandeFacile = new ListaDomande(numDomande);
        domandeMedio = new ListaDomande(numDomande);
        domandeDifficile = new ListaDomande(numDomande);
    }
    
    public String toString() {
    	String s = "";
    	s += "MAZZO DOMANDE FACILI: \n";
    	s += domandeFacile.toString() +"\n\n";
    	s += "MAZZO DOMANDE MEDIO: \n";
    	s += domandeMedio.toString() +"\n\n";
    	s += "MAZZO DOMANDE DIFFICILE: \n";
    	s += domandeDifficile.toString() +"\n\n";
    	return s;
    }
    
    public void addDomanda(Quiz domanda) {
    	switch(domanda.getDiffic()) {
    	case FACILE: {
    		domandeFacile.addDomanda(domanda);
    		break;
    	}
    	case MEDIO: {
    		domandeMedio.addDomanda(domanda);
    		break;
    	}
    	case DIFFICILE: {
    		domandeDifficile.addDomanda(domanda);
    		break;
    	}
    	default:{
    		break;
    	}
    	}
    }
    
    /**
     * Estrai la prossima domanda di una certa difficolta'
     * @param diffic
     * @return
     */
    public Quiz pescaDomanda(Diffic x) {
    	switch(x) {
    	case FACILE: {
    		return domandeFacile.prossimaDomanda();
    	}
    	case MEDIO: {
    		return domandeMedio.prossimaDomanda();   		
    	}
    	case DIFFICILE: {
    		return domandeDifficile.prossimaDomanda();
    	}
    	default:{
    		break;
    	}
    	}
    	return null;
    }    
    /**
     * Estrai una domanda di una difficolta' a caso
     * @return
     */
    public Quiz pescaDomanda() {
    	switch((int)Math.floor(Math.random()*3)) {
    	case 0: {
    		return pescaDomanda(Diffic.FACILE);
    	}
    	case 1: {
    		return pescaDomanda(Diffic.MEDIO);
    	}
    	case 2: {
    		return pescaDomanda(Diffic.DIFFICILE);
    	}
    	}
    	return null;
    }
    
    public void mischiaTutto() {
    	domandeFacile.mischia();
    	domandeMedio.mischia();
    	domandeDifficile.mischia();
    }
}