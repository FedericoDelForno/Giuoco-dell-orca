class Casella {
    private int elemento;
    private Casella successivo;

    public Casella(){
        this(0,null);
    }

    public Casella(int elemento, Casella successivo){
        this.elemento = elemento;
        this.successivo = successivo;
    }
    
    public Casella(int elemento){
        this.elemento = elemento;
    }

    public int getElemento(){
        return this.elemento;
    }

    public Casella getSuccessivo(){
        return this.successivo;
    }

    public void setElemento(int elemento){
        this.elemento = elemento;
    }

    public void setSuccessivo(Casella successivo){
        this.successivo = successivo;
    }
}
