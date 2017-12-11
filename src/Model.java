import javafx.scene.paint.Color;
import sun.plugin.dom.css.RGBColor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Model {
    public int width;
    public int height;
    Reader r;
    Paddle p;
    Ball b;
    BlockGrid bg;
    String m = "";

    public Model() throws IOException {
        r = new Reader();
        width = Integer.parseInt(levelLoader("Niveaux/niveau1").get(0)[0]);
        height = Integer.parseInt(levelLoader("Niveaux/niveau1").get(0)[1]);
        p = new Paddle();
        b = new Ball(width/2,height - p.PLAYER_HEIGHT - b.BALL_R,width/2,height - p.PLAYER_HEIGHT - b.BALL_R);
        bg = levelBlocks("src/Niveaux/niveau1");

    }

    public void setBg(BlockGrid bg) {
        this.bg = bg;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<String[]> levelLoader(String path) throws IOException{
        ArrayList<String> l = r.levelparameters(path);
        ArrayList<String[]> d = r.formatBeforeView(l);
        return d;
    }

    public BlockGrid levelBlocks(String path) throws IOException {
        width = Integer.parseInt(levelLoader(path).get(0)[0]);
        height = Integer.parseInt(levelLoader(path).get(0)[1]);
        ArrayList<String[]> level = levelLoader(path);
        BlockGrid bg = new BlockGrid(new Block[level.size() - 1]);
        for(int i = 1; i < level.size();i++){
            bg.b[i-1] = new Block(Integer.parseInt(level.get(i)[0]),
                        Integer.parseInt(level.get(i)[1]),Integer.parseInt(level.get(i)[2]),Integer.parseInt(level.get(i)[3]),Color.web(level.get(i)[4]));

        }
        return bg;
    }
    class Reader{
        public ArrayList<String> levelparameters(String name) throws IOException {
            ArrayList<String> str = new ArrayList<String>();
            try (Stream<String> stream = Files.lines(Paths.get(name))) {
                stream.forEach(str::add);
            }
            return str;
        }

        public String[] formatTailleBlocCouleur(String s) throws IOException{
            String res[] = new String[5];
            try{ 
                String deb[] = s.split(" & ");
                for(int i = 0; i < deb.length-1; i++){
                    deb[i] = deb[i].substring(1,deb[i].length()-1);
                }
                String prem[] = deb[0].split(", ");
                String deux[] = deb[1].split(", ");
                res[0] = prem[0];
                res[1] = prem[1];
                res[2] = deux[0];
                res[3] = deux[1];
                res[4] = deb[2];
                return res;
            }catch(Exception e){
                e = new IOException("FileFormatException");
                m = "Unable to load";
                return null;
            }
        }

        public ArrayList<String[]> formatBeforeView(ArrayList<String> l) throws IOException{
            ArrayList<String[]> res = new ArrayList<String[]>();
            String deb[] = l.get(0).split(" x ");
            int largeur = Integer.parseInt(deb[0]);
            int longueur = Integer.parseInt(deb[1]);
            res.add(deb);
            for(int i = 1; i < l.size(); i++){
                String []tmp = formatTailleBlocCouleur(l.get(i));
                if(tmp != null){
                    for(int j = 0; j < tmp.length-1; j++){
                        if(j%2 == 0){
                            if(Integer.parseInt(tmp[j]) < 0 || Integer.parseInt(tmp[j]) > largeur){
                                m = "unable to load";
                                throw new IOException("NumberFormatException");
                            }
                        }else{
                            if(Integer.parseInt(tmp[j]) < 0 || Integer.parseInt(tmp[j]) > longueur){
                                m = "Unable to load";
                                throw new IOException("NumberFormatException");
                            }
                        }
                    }
                    res.add(tmp);
                }else{
                    m = "Unable to load";
                    throw new IOException("FileFormatException");
                }
            }
            return res;
        }

        public void affiche(ArrayList<String[]> l){
            for(String s[]: l){
                for(int i = 0; i < s.length; i++){
                    System.out.print(s[i] + " ");
                }
                System.out.println();
            }
        }


    }

    class Paddle{
        private final double originpadposX = width/2 - width/10;
        private final double originpadposY = height - 15;
        private final int PLAYER_HEIGHT = 15;
        private final int PLAYER_WIDTH = 100;
        private double playerOneXPos = width/2 - width/10;
        private double playerOneYPos = height - 15;


        public double getOriginpadposX() {
            return originpadposX;
        }

        public double getOriginpadposY() {
            return originpadposY;
        }

        public int getPLAYER_HEIGHT() {
            return PLAYER_HEIGHT;
        }

        public int getPLAYER_WIDTH() {
            return PLAYER_WIDTH;
        }

        public double getPlayerOneXPos() {
            return playerOneXPos;
        }

        public double getPlayerOneYPos() {
            return playerOneYPos;
        }

        public void setPlayerOneXPos(double playerOneXPos) {
            this.playerOneXPos = playerOneXPos;
        }

        public void setPlayerOneYPos(double playerOneYPos) {
            this.playerOneYPos = playerOneYPos;
        }
    }

    class Ball{
        private final double BALL_R = 15;
        private int ballYSpeed = 1;
        private int ballXSpeed = 1;
        private final double originballX;
        private final double originballY;
        private double ballXPos;
        private double ballYPos;

        public Ball(double originballX,double originballY,double ballXPos,double ballYPos){
            this.originballX = originballX;
            this.originballY = originballY;
            this.ballXPos = ballXPos;
            this.ballYPos = ballYPos;
        }

        public double getBallR() {
            return BALL_R;
        }

        public int getBallYSpeed() {
            return ballYSpeed;
        }

        public int getBallXSpeed() {
            return ballXSpeed;
        }

        public double getOriginballX() {
            return originballX;
        }

        public double getOriginballY() {
            return originballY;
        }

        public double getBallXPos() {
            return ballXPos;
        }

        public double getBallYPos() {
            return ballYPos;
        }

        public void setBallYSpeed(int ballYSpeed) {
            this.ballYSpeed = ballYSpeed;
        }

        public void setBallXSpeed(int ballXSpeed) {
            this.ballXSpeed = ballXSpeed;
        }

        public void setBallXPos(double ballXPos) {
            this.ballXPos = ballXPos;
        }

        public void setBallYPos(double ballYPos) {
            this.ballYPos = ballYPos;
        }
    }

    class Block{
        private double brickposx1;
        private double brickposy1;
        private double brickposx2;
        private double brickposy2;
        private Color c;
        public boolean isvisible = true;

        public Block(double brickposx1, double brickposy1, double brickposx2, double brickposy2, Color c) {
            this.brickposx1 = brickposx1;
            this.brickposy1 = brickposy1;
            this.brickposx2 = brickposx2;
            this.brickposy2 = brickposy2;
            this.c = c;
        }

        public double getBrickposx1() {
            return brickposx1;
        }

        public double getBrickposy1() {
            return brickposy1;
        }

        public double getBrickposx2() {
            return brickposx2;
        }

        public double getBrickposy2() {
            return brickposy2;
        }

        public Color getC() {
            return c;
        }

        public void setBrickposx1(double brickposx1) {
            this.brickposx1 = brickposx1;
        }

        public void setBrickposy1(double brickposy1) {
            this.brickposy1 = brickposy1;
        }

        public void setBrickposx2(double brickposx2) {
            this.brickposx2 = brickposx2;
        }

        public void setBrickposy2(double brickposy2) {
            this.brickposy2 = brickposy2;
        }

        public void setC(Color c) {
            this.c = c;
        }
    }

    class BlockGrid{
        public Block [] b;

        public BlockGrid(Block [] bl){
            b = bl;
        }

    }
}
