package it.polito.tdp.itunes.model;

import java.util.*;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model=new Model();
		model.creaGrafo(20);
		List<Album> vertici=new ArrayList<>(model.getVertici());
		model.calcolaPercorso(vertici.get(1), vertici.get(6), 14.5);
	}

}
