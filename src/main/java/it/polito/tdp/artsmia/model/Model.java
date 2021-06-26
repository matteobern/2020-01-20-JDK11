package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
ArtsmiaDAO dao;
Map<Integer,Artist> idArtist;
private Graph<Artist,DefaultWeightedEdge> grafo;

public Model() {
	this.dao=new ArtsmiaDAO();
	this.idArtist=new HashMap<>();
	for(Artist a :dao.listArtist())
		idArtist.put(a.getArtist_id(), a);
	
}
public void creaGrafo(String role) {
	this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	Graphs.addAllVertices(grafo, dao.getArtistsByRole(role));
	System.out.print("vertici:"+this.grafo.vertexSet().size());
	for(Adiacenza a : dao.getAdiacenza(role)) {
		if(grafo.containsVertex(idArtist.get(a.getA1())) && (grafo.containsVertex(idArtist.get(a.getA2()))))
		Graphs.addEdge(grafo, idArtist.get(a.getA1()), idArtist.get(a.getA2()), a.getPeso());
	}
	System.out.print(" archi:"+this.grafo.edgeSet().size());
}
public List<String> allRoles(){
	return this.dao.listRoles();
}
}
