package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {

		connection = SingleConnection.getConnection();

	}

	public void salvar(Userposjava userposjava) {

		try {

			String sql = "Insert into userposjava (nome,email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());
			insert.execute();
			connection.commit(); /* Salva no banco */

			System.out.println("Inser realizado!");

		} catch (Exception e) {

			try {
				connection.rollback(); // reverte operação
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}

	public void salvarTelefone(Telefone telefone) {

		try {

			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
			PreparedStatement salvar = connection.prepareStatement(sql);
			salvar.setString(1, telefone.getNumero());
			salvar.setString(2, telefone.getTipo());
			salvar.setLong(3, telefone.getUsuario());
			salvar.execute();
			connection.commit();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				connection.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		}
	}

	public List<Userposjava> listar() {

		List<Userposjava> listaUserposjavas = new ArrayList<Userposjava>();

		try {

			String sql = "select * from userposjava";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();

			
			while (resultado.next()) { /* enquanto tiver dados */

				Userposjava userposjava = new Userposjava();
				userposjava.setId(resultado.getLong("id"));
				userposjava.setNome(resultado.getString("nome"));
				userposjava.setEmail(resultado.getString("email"));

				listaUserposjavas.add(userposjava);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return listaUserposjavas;

	}

	public Userposjava Buscar(Long id) {

		Userposjava retorno = new Userposjava();

		try {

			String sql = "select * from userposjava where id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) { /* retorna apenas um ou nenhum */

				retorno.setId(resultado.getLong("id"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setEmail(resultado.getString("email"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;

	}

	public List<BeanUserFone> listaUserFone(Long idUser) {

		List<BeanUserFone> ListBeanUserFone = new ArrayList<BeanUserFone>();

		String sql = " select nome,numero,email from telefoneuser as fone ";
		sql += "inner join userposjava as userp";
		sql += " on fone.usuariopessoa = userp.id";
		sql += " where userp.id = " + idUser;

		try {
			PreparedStatement buscar = connection.prepareStatement(sql);
			ResultSet resultSet = buscar.executeQuery();
			
			while(resultSet.next()) {
				
				BeanUserFone beanUserFone = new BeanUserFone();
				beanUserFone.setNome(resultSet.getString("nome"));
				beanUserFone.setNumero(resultSet.getString("numero"));
				beanUserFone.setEmail(resultSet.getString("email"));
				
				ListBeanUserFone.add(beanUserFone);
				
			} /*enquanto tiver dados*/
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return ListBeanUserFone;
	}

	public void atualizar(Userposjava userposjava) {

		try {

			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();

			PreparedStatement alterar;
			alterar = connection.prepareStatement(sql);
			alterar.setString(1, userposjava.getNome());

			alterar.execute();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public void deletar(Long id) {

		try {

			String sql = "delete from userposjava where id = " + id;
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();

			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	public void deleteFonesPorUser(Long idUser) {
		
		String sqlFone = "delete from telefoneuser where usuariopessoa = " + idUser;
		String sqlUser = "Delete from userposjava where id = " + idUser;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
	

}
