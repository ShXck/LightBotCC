
//clase de los token devueltos
class Token {
    Token (int token_num, String token, String type, int code_line, int column){
        //Contador para el número de tokens reconocidos
        this.token_num = token_num;
        //String del token reconocido
        this.token = new String(token);
        //Tipo de componente léxico encontrado
        this.type = type;
        //Número de linea
        this.code_line = code_line;
        //Columna donde empieza el primer carácter del token
        this.column = column;
    }
    //Métodos de los atributos de la clase
    public int token_num;
    public String token;
    public String type;
    public int code_line;
    public int column;

    //Metodo que devuelve los datos necesarios que escribiremos en un archive de salida
    public String toString() {
        return "(" + type + "," + token + ")";
    }
}

/* Declaraciones de JFlex */
%%
//Función para obtener el siguiente token.
%function next_token

%public

%class Lexical_Analyzer

%unicode

%{

    private int counter = 0;
    private ArrayList<Token> tokens = new ArrayList<Token>();

	private void writeOutputFile() throws IOException{
			String file_name = "file.out";
			BufferedWriter out = new BufferedWriter(
				new FileWriter(file_name));
            System.out.println("\n*** TOKENS SAVED ***\n");
			for(Yytoken t: this.tokens){
				System.out.println(t);
				out.write(t + "\n");
			}
			out.close();
	}
%}

%eof{
	try{
		this.writeOutputFile();
        System.exit(0);
	} catch(IOException ioe){
		ioe.printStackTrace();
	}
%eof}

//NO ESTA TERMINADO