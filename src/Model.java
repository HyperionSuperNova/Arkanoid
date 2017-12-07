import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Model {
    private static int width = 400;
    private static int height = 600;
    Reader r = new Reader();
    Paddle p = new Paddle();
    Ball b = new Ball();

    public Model() {
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    class Reader{
        public ArrayList<String> levelparameters(String name) throws IOException {
            ArrayList<String> str = new ArrayList<String>();
            try (Stream<String> stream = Files.lines(Paths.get(name))) {
                stream.forEach(str::add);
            }
            return str;
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
        private final double originballX = width/2;
        private final double originballY = height - new Paddle().PLAYER_HEIGHT - BALL_R;
        private double ballXPos = width/2;
        private double ballYPos = height - new Paddle().PLAYER_HEIGHT - BALL_R;

        public Ball(){

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

    }
}
