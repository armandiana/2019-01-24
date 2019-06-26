package it.polito.tdp.extflightdelays.model;

public class StatoPeso implements Comparable<StatoPeso>{

	private String nomeStato;
	private Integer pesoArco;
	
	public StatoPeso(String nomeStato, int pesoArco) {
		super();
		this.nomeStato = nomeStato;
		this.pesoArco = pesoArco;
	}
	public String getNomeStato() {
		return nomeStato;
	}
	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}
	public int getPesoArco() {
		return pesoArco;
	}
	public void setPesoArco(int pesoArco) {
		this.pesoArco = pesoArco;
	}
	@Override
	public String toString() {
		return nomeStato + "-" + pesoArco;
	}
	@Override
	public int compareTo(StatoPeso other) {
		return -(this.pesoArco.compareTo(other.pesoArco));
	}
	
	
}
