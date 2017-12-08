import java.util.*;
import java.io.IOException;

class Main{

    public static void main(String [] args) throws IOException{
        Model m = new Model();
        ArrayList<String> l = m.r.levelparameters("Niveaux/niveau4");
        ArrayList<String[]> d = m.r.formatBeforeView(l);
        m.r.affiche(d);
    }
}