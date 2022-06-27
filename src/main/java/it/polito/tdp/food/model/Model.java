package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private Graph<Food, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<Food> listaVertici;
	private List<Adiacenza> listaAdiacenza;
	private Map<Integer, Food> idMapFood; 
	
	public Model() {
		dao = new FoodDao();
		
	}
	
	public void creaGrafo(int porzioni ) {
		grafo = new SimpleDirectedWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		listaVertici = new ArrayList<Food>(dao.listaFoodsConNPorzioni(porzioni));
		
		idMapFood = new HashMap<Integer, Food>();
		for(Food f : listaVertici) {
			idMapFood.put(f.getFood_code(), f);
		}
		//Aggiungo i vertici
		
		Graphs.addAllVertices(this.grafo, listaVertici);
		
		//System.out.println(this.grafo);
		//Aggiungo gli archi 
		listaAdiacenza = new ArrayList<Adiacenza>(this.dao.listaAdiacenze(porzioni));
		for(Adiacenza a : listaAdiacenza) {
			if(a.getPeso()<0) {
				Graphs.addEdgeWithVertices(this.grafo, a.getVertice2(), a.getVertice1(), a.getPeso());
			}
			else if(a.getPeso()>0) {
				Graphs.addEdgeWithVertices(this.grafo, a.getVertice1(), a.getVertice2(), a.getPeso());
			}
		}
		
	}
	
	public Integer nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Food> listaDeiVertici(){
		return this.listaVertici;
	}
	
	public List<FoodGrassiDiff> listaFoodDiff(Food food){
		List<FoodGrassiDiff> successivi = new ArrayList<FoodGrassiDiff>();
		for(Adiacenza a : listaAdiacenza) {
			if(a.getVertice1().equals(food) && a.getPeso() > 0) {
				successivi.add(new FoodGrassiDiff(a.getVertice2(), a.getPeso()));
			}
			if(a.getVertice2().equals(food) && a.getPeso()<0) {
				successivi.add(new FoodGrassiDiff(a.getVertice1(), -a.getPeso()));
			}
		}
		
		Collections.sort(successivi, new Comparator<FoodGrassiDiff>() {

			@Override
			public int compare(FoodGrassiDiff arg0, FoodGrassiDiff arg1) {
				if(arg0.getDifferenzaGrassi()<arg1.getDifferenzaGrassi())
					return -1;
				if(arg0.getDifferenzaGrassi()>arg1.getDifferenzaGrassi())
					return 1;
				return 0;
			}
			
		});
		
		return successivi;
		
		
	}
	
	
	
}
