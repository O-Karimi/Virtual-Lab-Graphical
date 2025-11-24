package Logic;

public class logicMain {

    private Initializer initializer;
    public logicMain(){
        this.initializer = new Initializer();
    }

    public void setInitializer(Initializer initializer){
        this.initializer = new Initializer();
    }
    public Initializer getInitializer(){
        return this.initializer;
    }

}
