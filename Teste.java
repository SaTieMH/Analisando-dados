import java.util.Scanner;

public class Teste {

    static int globalVariable = 10;
    static String[] months = {"Jan","Feb","Mar"};
    static String fileName = scanMethod();
    static String path = "D:/MEGA/OUTROS/Investimentos/Meta Trader/Análise de dados/entrada/";
    static String entrada = entradaPath();

    public static void main(String[] args){
        System.out.println("Hello World");
        method(11, 13);
        for (int i = 0; i<3; i++){
            System.out.println(months[i]);
        }
        System.out.println(getGlobalVariables());
        System.out.println(entrada);
        
        
    }

    public static String entradaPath() {
        //System.out.println(path.concat(fileName));
        return path.concat(fileName);
    }

    public static String scanMethod() {
        Scanner scan = new Scanner (System.in);
        System.out.print("Enter name file: ");
        String fname = scan.nextLine();
        System.out.println(fname);
        scan.close();
        return String.format("%s.csv", fname);
    }

    public static void method (int a, int b){
        System.out.println(a+b);
    }

    public static int getGlobalVariables(){
        return globalVariable;
    }
}
