package it.polito.tdp.gestorecorsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;


public class CorsoDAO {
	 
	public List<Corso> getCorsiByPeriodo(Integer periodo){

		String sql = "select * " +
				"from corso "// RICORDA LO SPAZIO PROMA DI CAMBIARE RIGA
			      +"where pd= ?";
		
		List<Corso> result= new ArrayList<>();
		
		try {
		Connection conn= DBConnect.getConnection();
		PreparedStatement st=conn.prepareStatement(sql);
		ResultSet res=st.executeQuery();
		
		
		while(res.next()) {
			Corso c=new Corso(res.getString("codins"), res.getInt("crediti"),res.getString("nome"),res.getInt("pd"));
			result.add(c);
		}
		
		res.close();
		st.close();
		conn.close();
		
		} catch (SQLException e) {
		 throw new RuntimeException("Database error in Corso", e);
		  
		}
		
		return result;
	}
	
	public Map<Corso,Integer>getIscrittiByPeriodo(Integer periodo){
		String sql = "SELECT c.codins, c.nome, c.crediti,  c.pd, COUNT(*) AS tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins= i.codins AND c.pd=? "
				+ "GROUP BY c.codins, c.nome, c.crediti,  c.pd ";
		
		Map<Corso,Integer> result= new HashMap<Corso,Integer>();
		
		try {
		Connection conn= DBConnect.getConnection();
		PreparedStatement st=conn.prepareStatement(sql);
		st.setInt(1, periodo);
		ResultSet res=st.executeQuery();
		
		
		while(res.next()) {
			Corso c=new Corso(res.getString("codins"), res.getInt("crediti"),res.getString("nome"),res.getInt("pd"));
			Integer n = res.getInt("tot");
			result.put(c,n);
		}
		
		res.close();
		st.close();
		conn.close();
		
		} catch (SQLException e) {
		 throw new RuntimeException("Database error in Corso", e);
		  
		}
		
		return result;
	}
	
	public List<Studente>getStudentiByCorso(Corso corso){
		String sql= "SELECT s.matricola, s.cognome, s.nome,s.CDS\r "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola "
				+ "AND i.codins= ? ";
		List <Studente> result = new LinkedList<Studente>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Studente s= new Studente(rs.getInt("matricola"), rs.getString("nome"),
						rs.getString("cognome"), rs.getString("CDS"));
				result.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
			}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public boolean esisteCorso(Corso corso) {
		String sql ="SELECT * FROM corso WHERE codins = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;
			}else {
				rs.close();
				st.close();
				conn.close();
				return false;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
