package it.polito.tdp.gestorecorsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;


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
	
	

}
