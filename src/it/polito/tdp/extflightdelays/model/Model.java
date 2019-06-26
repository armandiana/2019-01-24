package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	
	private List<String>stati;
	
	private Graph<String, DefaultWeightedEdge>grafo;
	
	public Model() {
		this.dao= new ExtFlightDelaysDAO();
		this.stati= dao.loadAllStates();
	}
	
	public void creaGrafo() {
		this.grafo= new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//agigungo i vertici-> tutti gli stati
		Graphs.addAllVertices(this.grafo, this.stati);
		
		//aggiungo gli archi-> gli stati sono collegati se esiste almeno un volo e i pesi degli archi sono il numero di voli effettuati da compagnie diverse
		
		for(String s1: this.stati) {
			for(String s2: this.stati) {
				int nVoli= dao.getNumerFlights(s1, s2);
				
				DefaultWeightedEdge e= this.grafo.getEdge(s1, s2);
				
				if(e==null) {
					Graphs.addEdge(this.grafo, s1, s2, nVoli);
				}
				
			}
		}
		System.out.println("GRAFO CREATO!");
		System.out.println("#VERTICI: "+this.grafo.vertexSet().size());
		System.out.println("#ARCHI: "+this.grafo.edgeSet().size());
		
	}
	
	public List<String> getListStati(){
		Collections.sort(stati);
		return this.stati;
	}
	
	public Graph<String, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	public List<StatoPeso> getListStatoPeso(String stato){
		List<StatoPeso>st= new ArrayList<StatoPeso>();
		
		if(this.grafo==null) {
			return null;
		}
		for(String s: Graphs.successorListOf(this.grafo, stato)) {
			Integer peso= (int) this.grafo.getEdgeWeight(this.grafo.getEdge(stato, s));
			if(peso!=0) {
			StatoPeso sp= new StatoPeso(s, peso);
			
			st.add(sp);
			}
		}
		Collections.sort(st);
		return st;
		
		/*if(this.grafo==null) {
			System.out.println("Devi prima creare il grafo!");
			return null;
		}
		List<DefaultWeightedEdge>archiUscenti= new ArrayList<DefaultWeightedEdge>();
	    archiUscenti.addAll(this.grafo.outgoingEdgesOf(stato));
	   
	    for(DefaultWeightedEdge e: archiUscenti) {
	    	String stato2= grafo.getEdgeTarget(e);
	    	
	    	if(!stato2.equals(stato)) {
	    		int peso= (int)grafo.getEdgeWeight(e);
	    		if(peso!=0) {
	    		StatoPeso sp= new StatoPeso(stato2, peso);
	    		st.add(sp);
	    		}
	    	}
	    }
	    
	    Collections.sort(st);
	    
	    return st;*/
	
	}
	
	
	

}
