package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JABANTO
 *
 */
public class AutorizadorCortesia extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Personal personal;
	private MotivoCortesia motivoCortesia;
	private String password;
	private List<MotivoCortesia> listaMotivoCortesia;


	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}

	public void setPersonal(Personal personal){
		this.personal=personal;
	}
	public Personal getPersonal(){
		return this.personal;
	}

	public void setMotivoCortesia(MotivoCortesia  motivoCortesia){
		this.motivoCortesia=motivoCortesia;
	}
	public MotivoCortesia getMotivoCortesia(){
		return this.motivoCortesia;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the listaMotivoCortesia
	 */
	public List<MotivoCortesia> getListaMotivoCortesia() {
		return listaMotivoCortesia;
	}
	/**
	 * @param listaMotivoCortesia the listaMotivoCortesia to set
	 */
	public void setListaMotivoCortesia(List<MotivoCortesia> listaMotivoCortesia) {
		this.listaMotivoCortesia = listaMotivoCortesia;
	}
}
