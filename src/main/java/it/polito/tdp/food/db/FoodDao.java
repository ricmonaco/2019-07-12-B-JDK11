package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.lf5.util.AdapterLogRecord;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> listaFoodsConNPorzioni(int porzioni){
		String sql = "SELECT tnp.food_code, tnp.display_name, tnp.nPorzioni "
				+ "FROM ( "
				+ "SELECT f.food_code, f.display_name, COUNT(p.portion_id) AS nPorzioni "
				+ "FROM portion p, food f "
				+ "WHERE f.food_code = p.food_code "
				+ "GROUP BY f.food_code, f.display_name "
				+ ") AS tnp "
				+ "WHERE tnp.nPorzioni >= ?;";
		List<Food> result = new ArrayList<Food>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, porzioni);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				result.add(new Food(res.getInt("food_code"), res.getString("display_name")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> listaAdiacenze(int porzioni){
		String sql = "SELECT DISTINCT v1.food_code, v1.display_name, v2.food_code, v2.display_name, v1.Media -v2.Media AS Peso "
				+ "FROM ( "
				+ "SELECT v.food_code, v.display_name, AVG(p.saturated_fats) as Media "
				+ "FROM portion p, (SELECT tnp.food_code, tnp.display_name, tnp.nPorzioni "
				+ "FROM ( "
				+ "SELECT f.food_code, f.display_name, COUNT(p.portion_id) AS nPorzioni "
				+ "FROM portion p, food f "
				+ "WHERE f.food_code = p.food_code "
				+ "GROUP BY f.food_code, f.display_name "
				+ ") AS tnp "
				+ "WHERE tnp.nPorzioni >= ?) as v "
				+ "WHERE p.food_code = v.food_code "
				+ "GROUP BY v.food_code, v.display_name "
				+ ") AS v1, ( "
				+ "SELECT v.food_code, v.display_name, AVG(p.saturated_fats) as Media "
				+ "FROM portion p, (SELECT tnp.food_code, tnp.display_name, tnp.nPorzioni "
				+ "FROM ( "
				+ "SELECT f.food_code, f.display_name, COUNT(p.portion_id) AS nPorzioni "
				+ "FROM portion p, food f "
				+ "WHERE f.food_code = p.food_code "
				+ "GROUP BY f.food_code, f.display_name "
				+ ") AS tnp "
				+ "WHERE tnp.nPorzioni >= ?) as v "
				+ "WHERE p.food_code = v.food_code "
				+ "GROUP BY v.food_code, v.display_name "
				+ ") AS v2 "
				+ "WHERE v1.food_code > v2.food_code "
				+ "GROUP BY v1.food_code, v1.display_name, v2.food_code, v2.display_name;";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, porzioni);
			st.setInt(2, porzioni);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(new Adiacenza(new Food(res.getInt("v1.food_code"), res.getString("v1.display_name")), new Food(res.getInt("v2.food_code"), res.getString("v2.display_name")), res.getDouble("Peso")));
			}
			conn.close();
			return result;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	}


