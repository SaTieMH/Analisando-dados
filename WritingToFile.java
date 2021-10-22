import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WritingToFile {
    
    public static void main(String[] args) throws IOException {

        File file1 = new File("saida/out.txt");

        FileWriter fw = new FileWriter(file1);

        PrintWriter pw = new PrintWriter(fw);

        pw.write("line 1\n");
        pw.write("line 2\n");
        pw.write("line 3\n");
        pw.write("line 4\n");
        pw.println("teste");

        pw.close();
    }
}
