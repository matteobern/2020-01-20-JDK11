package it.polito.tdp.artsmia.model;

public class Artist {
Integer artist_id;
String name;
public Artist(Integer artist_id, String name) {
	this.artist_id = artist_id;
	this.name = name;
}
public Integer getArtist_id() {
	return artist_id;
}
public void setArtist_id(Integer artist_id) {
	this.artist_id = artist_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}
