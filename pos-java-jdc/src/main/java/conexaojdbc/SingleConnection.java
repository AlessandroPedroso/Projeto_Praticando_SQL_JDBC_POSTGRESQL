package conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;


public class SingleConnection {
	
	/*dados de conexão do banco de dados*/
	private static String url = "jdbc:postgresql://localhost:5433/posjava";
	private static String password = "";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		
		conectar();
	}
	
	
	/*classe construtora, chama o método conectar*/
	public SingleConnection() {
		
		conectar();
	}
	
	private static void conectar(){
		
		try {
			
			if(connection == null) { /*verifica se a conexão está permitida somente uma vez*/
				
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conectou com sucesso");
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {
		
		return connection;
	}
	
	
}
