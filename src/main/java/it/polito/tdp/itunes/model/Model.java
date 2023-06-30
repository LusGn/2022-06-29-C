package it.polito.tdp.itunes.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;


public class Model {
	ItunesDAO dao;
	Map<Integer, Album> idA;
	Graph<Album, DefaultWeightedEdge> grafo;
	List<Album> best;
	Map<Integer, AlbumBilancio> idAB;
	int max=0;
	public Model() {
		dao=new ItunesDAO();
		idA=new HashMap<>();
		dao.getAllAlbums(idA);
		dao.getAllTracks(idA);
		idAB=new HashMap<>();
	}
	
	public String calcolaPercorso(Album a1, Album a2, double x) {
		String result="";
		best=new ArrayList<>();
		List<Album> parziale=new ArrayList<>();
		parziale.add(a1);
		double bilancio=idAB.get(a1.getAlbumId()).getBilancio();
		ricorsione(a2, x,bilancio, parziale);
		for(Album a: best) {
			result+=a.getTitle()+"\n";
		}
		return result;
	}
	
	private void ricorsione(Album a2, double x, double bilancio, List<Album> parziale) {
		Album ultimo=parziale.get(parziale.size()-1);
		if(ultimo.equals(a2) ) {
			if(parziale.size()>max) {
		
				best=new ArrayList<>(parziale);
				max=parziale.size();
			
			}
			return;
		}
		List<Album> vicini=new ArrayList<>(Graphs.neighborListOf(this.grafo, ultimo));
		for(Album a: vicini) {
			double bbb=a.getBilancio();
			if(a.getBilancio()>bilancio && !parziale.contains(a)) {
				parziale.add(a);
				ricorsione(a2, x, bilancio, parziale);
				parziale.remove(a);
			}
		}
	}

	public void creaGrafo(double price) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//veritii: album con somma presso >=n
		List<Album> vertici=new ArrayList<>(vertici(price));
		Graphs.addAllVertices(this.grafo, vertici);
		
		//archi: differenza di presso!=0
		for(Album a1: this.grafo.vertexSet()) {
			for(Album a2: this.grafo.vertexSet()) {
				if(!a1.equals(a2)) {
					if(calcolaDifferenza(a1, a2)!=0)
						Graphs.addEdgeWithVertices(this.grafo, a1, a2, calcolaDifferenza(a1, a2));
				}
			}
		}
		idAB=new HashMap<>(this.setAllBilancio());
	}

	private double calcolaDifferenza(Album a1, Album a2) {
		double result=a1.getCosto()-a2.getCosto();
		if(result<0)
			result=result*-1;
		return result;
	}

	private List<Album> vertici(double price) {
		List<Album> result=new ArrayList<>();
		for(Album a: idA.values()) {
			double r=0;
			for(Track t: a.getCanzoni()) {
				r+=t.getUnitPrice();
			}
			a.setCosto(r);
			if(a.getCosto()>=price) {
				result.add(a);
			}
		}
		
		return result;
	}
	public int getVerticiSize() {
		return this.grafo.vertexSet().size();
	}
	public int getArcoSize() {
		return this.grafo.edgeSet().size();
	}
	public List<Album> getVertici(){
		List<Album> result=new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(result);
		return result;
	}

	public Map<Integer, AlbumBilancio> setAllBilancio() {
		List<Album> vertici=new ArrayList<>(this.getVertici());
		Map<Integer, AlbumBilancio> result=new HashMap<>();
		for(Album a: vertici) {
			List<DefaultWeightedEdge> e=new ArrayList<>(this.grafo.incomingEdgesOf(a));
			double sum=0;
			for(DefaultWeightedEdge archi: e) {
				sum+=this.grafo.getEdgeWeight(archi);
			}
			double media=sum/e.size();
			a.setBilancio(media);
			AlbumBilancio ab=new AlbumBilancio(a, media);
			result.put(a.getAlbumId(), ab);
		}
		return result;
		
	}

	public String calcolaAdiacenza(Album a1){
		String result="";
		List<Album> vicini=new ArrayList<>(Graphs.neighborListOf(this.grafo, a1));
		List<AlbumBilancio> r=new ArrayList<>();
		for(Album a: vicini) {
			r.add(idAB.get(a.getAlbumId()));
		}
		Collections.sort(r);
		for(AlbumBilancio a: r) {
			result+=a.getAlbum().getTitle()+", bilancio: "+a.getBilancio()+"\n";
		}
		return result;
		
	}
}
