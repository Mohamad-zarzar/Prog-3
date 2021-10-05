package model.interfaces;


import model.implementation.Projekt;

public interface UrlInterface {

	public String getName();

	public void setName(String name);

	public long getId();

	public void setId(long id);

	public String getUrl();

	public Projekt getProject();

	public void setUrl(String url);

	public void setProject(Projekt project);

}
