package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
public List<Artist> listArtist() {
		
		String sql = "SELECT * from artists";
		List<Artist> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artist art = new Artist(res.getInt("artist_id"),res.getString("name"));
				result.add(art);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
public List<String> listRoles() {
		
		String sql = "SELECT DISTINCT role from authorship";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String role = res.getString("role");
				
				result.add(role);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
public List<Artist> getArtistsByRole(String role) {
	
	String sql = "SELECT DISTINCT a.* "
			+ "FROM artists a,authorship au "
			+ "WHERE au.`role`=? AND au.`artist_id`=a.artist_id";
	List<Artist> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, role);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Artist artist = new Artist(res.getInt("artist_id"),res.getString("name"));
			
			result.add(artist);
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}
public List<Adiacenza> getAdiacenza(String role) {
	
	String sql = "SELECT DISTINCT a1.`artist_id` as a1,a2.`artist_id` as a2,count(DISTINCT e1.exhibition_id) as peso "
			+ "FROM authorship a1,authorship a2,exhibition_objects e1,exhibition_objects e2 "
			+ "WHERE e1.`object_id`=a1.`object_id`AND e2.`object_id`=a2.object_id AND e1.`exhibition_id`=e2.`exhibition_id` AND "
			+ "a1.`artist_id`>a2.`artist_id` AND a1.`role`=? AND a2.`role`=? "
			+ "GROUP BY a1.`artist_id`,a2.`artist_id` ";
	List<Adiacenza> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, role);
		st.setString(2, role);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Adiacenza a = new Adiacenza(res.getInt("a1"),res.getInt("a2"),res.getInt("peso"));
			
			result.add(a);
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}
}
