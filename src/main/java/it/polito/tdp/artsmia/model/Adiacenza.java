package it.polito.tdp.artsmia.model;

public class Adiacenza {
Integer a1;
Integer a2;
Integer peso;
public Adiacenza(Integer a1, Integer a2, Integer peso) {
	this.a1 = a1;
	this.a2 = a2;
	this.peso = peso;
}
public Integer getA1() {
	return a1;
}
public void setA1(Integer a1) {
	this.a1 = a1;
}
public Integer getA2() {
	return a2;
}
public void setA2(Integer a2) {
	this.a2 = a2;
}
public Integer getPeso() {
	return peso;
}
public void setPeso(Integer peso) {
	this.peso = peso;
}

}
