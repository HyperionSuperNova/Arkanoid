import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Model {

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

    }

    class Ball{

    }

    class Block{

    }
}
