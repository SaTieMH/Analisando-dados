import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CSVReader {

    /*
    ---------------------------------------------------------------------
    NÃO ESQUECER DE ALTERAR OS NOMES DOS ARQUIVOS DE ENTRADA E SAÍDA
    ---------------------------------------------------------------------
    */
    static String fileName = nomeArquivo();
    static String path = "D:/MEGA/OUTROS/Investimentos/Meta Trader/Análise de dados/entrada/";
    static String entrada = String.format("%s%s.csv", path,fileName);
    static String line = "";
    static int nLinhas = getNLinhas();
    static int nColunas = getNColunas();
    static String[][] table = new String[nLinhas][nColunas];
    static double[][] valores = new double[nLinhas][nColunas];
    static double[][][] abasTabelas = new double[nColunas][nLinhas][2];
    static int qtddLinhas = 10;
    static double[][][] registrosUnicos = new double[nColunas][qtddLinhas][4];
    static double[][][] indicadorPorLucroMedio = new double[nColunas][qtddLinhas][4];
    
    public static void main (String[] args) throws IOException {
        geraTableInteira();
        parseTableDouble();

        geraAbasTabelas();
        //callCreateFile();

        createTableLucroPorIndicador(10);
        String stg = "tabela por indicador";
        createTable(stg, 10);
    }

    public static int whereIsInTheTable (int aba, double number) {
        for(int i=1; i<qtddLinhas; i++) {
            if (registrosUnicos[aba][i][0] == number) {
                return i;
            } else if (registrosUnicos[aba][i][0] == -1.0) {
                registrosUnicos[aba][i][0] = number;
                return i;
            }
        }
        return -1;
    }
    public static void createTableLucroPorIndicador(int aba) {
        for (int j=1; j<qtddLinhas; j++){
            registrosUnicos[aba][j][0] = -1.0;
        }
        for (int j=1; j<qtddLinhas; j++){
            for (int i=1; i<4; i++){
                //Zera contadores
                registrosUnicos[aba][j][i] = 0;
            }
        }
        for (int i=1; i<nLinhas; i++) {
            int linhaNaTabela = whereIsInTheTable(aba, abasTabelas[aba][i][1]);
            if (registrosUnicos[aba][linhaNaTabela][0] == abasTabelas[aba][i][1]) {
                registrosUnicos[aba][linhaNaTabela][1]++;                               //incrementa frequência
                registrosUnicos[aba][linhaNaTabela][2] += abasTabelas[aba][i][0];       //soma o profit da linha
            }
        }
        for (int j=1; j<qtddLinhas; j++){
            if(registrosUnicos[aba][j][0] != -1.0){
                
            }
            indicadorPorLucroMedio[aba][j][0] = -1.0;
        }
    }

    public static void createTable(String nameOfFile, int column) throws IOException {
        File file1 = new File(String.format("saida/%s-%s.txt", fileName, nameOfFile));
        FileWriter fw = new FileWriter(file1);
        PrintWriter pw = new PrintWriter(fw);

        for (int i=1; i<qtddLinhas; i++){
            for (int j=0; j<4; j++){
                pw.print(registrosUnicos[column][i][j] + "\t");
            }
            pw.println();
        }
        pw.close();
    }

    public static void geraAbasTabelas() {
        //Preencher coluna profit
        for (int i=0; i<nColunas; i++){
            for (int j=1; j<nLinhas; j++){
                abasTabelas[i][j][0] = valores[j][2];
            }
        }
        //Preencher coluna de cada tabela
        for (int i=0; i<nColunas; i++){
            for (int j=1; j<nLinhas; j++){
                abasTabelas[i][j][1] = valores[j][i];
            }
        }
    }

    public static String nomeArquivo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo .csv SEM a extensão.\nLembre-se de que o arquivo deve estar na pasta 'entrada'!\n");
        String fname = sc.nextLine();
        sc.close();
        return fname;
    }

    public static int getNColunas() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(entrada));
            line = br.readLine();
            br.close();
            String[]values = line.split(";");
            //System.out.println(values.length);
            return values.length;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNLinhas() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(entrada));
            int j = 0;

            while((line = br.readLine()) != null) {
                j++;
            }
            br.close();
            //System.out.println(j);
            return j;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void geraTableInteira() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(entrada));
            int j=0;
            while((line = br.readLine()) != null) {
                String[]values = line.split(";");
                for (int i=0; i<values.length; i++){
                    String line1 = values[i].replace("\"", "");
                    String line2 = line1.replace(",", ".");
                    table[j][i]=line2;
                    //System.out.print(table[i][j] + "\t");
                }
                j++;
                //System.out.println();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void parseTableDouble() {
        for (int i=1; i<nLinhas; i++){
            for (int j=0; j<nColunas; j++){
                if(table[i][j] != null){
                    valores[i][j] = Double.parseDouble(table[i][j]);
                    //System.out.print(valores[i][j] + "\t");
                }
            }
            //System.out.println();
        }
    }

    public static void createFile(String nameOfFile, int column) throws IOException {
        File file1 = new File(String.format("saida/%s-%s.txt", fileName, nameOfFile));
        FileWriter fw = new FileWriter(file1);
        PrintWriter pw = new PrintWriter(fw);

        for (int i=1; i<nLinhas; i++){
            for (int j=0; j<2; j++){
                pw.print(abasTabelas[column][i][j] + "\t");
            }
            pw.println();
        }
        pw.close();
    }

    public static void callCreateFile() throws IOException {
        for (int i=0; i<nColunas; i++){
           String columnName = table[0][i];
           createFile(columnName, i);
        }
    }
}
