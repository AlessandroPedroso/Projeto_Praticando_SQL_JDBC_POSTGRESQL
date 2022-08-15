package pos_java_jdc.pos_java_jdc;

import java.util.List;

import org.junit.Test;
import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc {
	
	@Test
	public void initBanco()  {
		
		
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();
		
		userposjava.setNome("Taís Mariano froner");
		userposjava.setEmail("tais.marianof@gmail.com");
		
		userPosDAO.salvar(userposjava);
		
	}
	
	@Test
	public void initListar() {
		
		UserPosDAO dao = new UserPosDAO();
		try {
			List<Userposjava> list = dao.listar();
			
			for (Userposjava userposjava : list) {
				
				System.out.println(userposjava);
				System.out.println("---------------------------------------");
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void initBuscar() {
		
		UserPosDAO dao = new UserPosDAO();
		
		try {
			//Userposjava userposjava = new Userposjava();
			Userposjava userposjava = dao.Buscar(2L);	
			System.out.println(userposjava);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void initAtualizar() {
			
			
			try {
				
				UserPosDAO dao = new UserPosDAO();
				
				Userposjava objetoBanco = dao.Buscar(3L);
				
				objetoBanco.setNome("Nome mudado com o metodo atualizar");
				dao.atualizar(objetoBanco);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	}
	
	@Test
	public void initDeletar() {
		try {
			
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(2L);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeInsertTelefone() {
		
	
			Telefone telefone = new Telefone();
			telefone.setNumero("(51) 998153-32");
			telefone.setTipo("Celular");
			telefone.setUsuario(20L);
			
			UserPosDAO dao = new UserPosDAO();
			dao.salvarTelefone(telefone);
		
	}
	
	
	@Test
	public void testeCarregaFoneUser() {
		
		UserPosDAO dao = new UserPosDAO();
		
		List<BeanUserFone> beanUserFones = dao.listaUserFone(5L);
		
		for (BeanUserFone beanUserFone : beanUserFones) {
			
			System.out.println("Nome: " + beanUserFone.getNome());
			System.out.println("E-mail: " + beanUserFone.getEmail());
			System.out.println("Telefone: " + beanUserFone.getNumero() + "\n");
		}
		
		System.out.println(beanUserFones);
	}
	
	@Test
	public void testeDeleteUserFone() {
		
		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(6L);
	}
	
	
	
	
	
}
