package it.polimi.seiiexamples.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;

		@Stateful
		@Remote
		public class ListElement {
		
			List<Integer> values = new ArrayList<Integer>();
		
			public void addElement(int i) {
				values.add(i);
			}
		
			public void removeElement(int i) {
				values.remove(new Integer(i));
			}
		
			public List<Integer> getElements() {
				return values;
			}
		
		}
		
