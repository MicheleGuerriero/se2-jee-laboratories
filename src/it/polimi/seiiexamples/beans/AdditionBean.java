package it.polimi.seiiexamples.beans;

import javax.ejb.Stateless;

@Stateless
public class AdditionBean {

	private int i,j,k;
	
    public int getI() {
		return i;
	}
    
	public void setI(int i) {
		this.i = i;
	}
	
	public int getJ() {
		return j;
	}
	
	public void setJ(int j) {
		this.j = j;
	}
	
	public int getK() {
		return k;
	}

	public void add(){
		k=i+j;
	}
}

