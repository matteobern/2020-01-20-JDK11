package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
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
private List<Artist> percorsoMigliore;
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
		Graphs.addEdgeWithVertices(grafo, idArtist.get(a.getA1()),idArtist.get(a.getA2()),a.getPeso() );
	}
	System.out.print(" archi:"+this.grafo.edgeSet().size());
}






public List<String> allRoles(){
	return this.dao.listRoles();
}
public List<Adiacenza> getAdiacenze(String role){
	List<Adiacenza> result=this.dao.getAdiacenza(role);
	Collections.sort(result);
	return result;
}
public List<Artist> percorsoMigliore(String codice){
	Integer id=Integer.parseInt(codice);
	Artist partenza=this.idArtist.get(id);
	this.percorsoMigliore=null;
	List<Artist> parziale=new ArrayList<>();
	parziale.add(partenza);
	cerca(parziale,0);
	return this.percorsoMigliore;
}
public void cerca(List<Artist> parziale,int peso) {

	
	
	Artist ultimo=parziale.get(parziale.size()-1);
	for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(ultimo) ) {
		Artist vicino=Graphs.getOppositeVertex(grafo, e, ultimo);
		if(peso==0) {
		int pesoGiusto = (int)grafo.getEdgeWeight(e);
		parziale.add(vicino);
		cerca(parziale,pesoGiusto);
		parziale.remove(vicino);
		}
		else {
			if(!parziale.contains(vicino) &&  (int)grafo.getEdgeWeight(e)==peso) {
				parziale.add(vicino);
				cerca(parziale,peso);
				parziale.remove(vicino);
																				}
			
		}
		if(this.percorsoMigliore==null || parziale.size()>this.percorsoMigliore.size()) {
			this.percorsoMigliore=parziale;
			return;
		}
		
	}
}

}
