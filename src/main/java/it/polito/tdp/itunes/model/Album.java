package it.polito.tdp.itunes.model;

import java.util.*;

public class Album implements Comparable<Album>{
	private Integer albumId;
	private String title;
	private Set<Track> canzoni;
	private double costo;
	private double bilancio;
	public Album(Integer albumId, String title) {
		super();
		canzoni=new HashSet<>();
		this.albumId = albumId;
		this.title = title;
	}
	
	public double getBilancio() {
		return bilancio;
	}

	public void setBilancio(double bilancio) {
		this.bilancio = bilancio;
	}

	public Set<Track> getCanzoni() {
		return canzoni;
	}

	public void setCanzoni(Set<Track> canzoni) {
		this.canzoni = canzoni;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(albumId, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		return Objects.equals(albumId, other.albumId) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Album o) {
		
		return title.compareTo(o.getTitle());
	}
	
	
	
}
