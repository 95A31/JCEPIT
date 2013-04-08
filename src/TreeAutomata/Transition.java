/*******************************************************************************
 * Copyright (c) 2013 95A31.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     95A31 - initial API and implementation
 ******************************************************************************/
package TreeAutomata;

public class Transition {	
	public Integer node;
	public Character character;
	public Integer leftChildren;
	public Integer rightChildren;
	
	public Transition(Integer n, Character c, Integer lc, Integer rc){
		node = n;
		character = c;
		leftChildren = lc;
		rightChildren = rc;
	}
	
	@Override
	public String toString(){
		return node + " " + character + " -> " + leftChildren + " " +rightChildren ;		
	}
	
		@Override
	public boolean equals(Object o) {
		Transition tmpT = (Transition) o;
		return tmpT.node.equals(node) && tmpT.character.equals(character) && tmpT.leftChildren.equals(leftChildren) && tmpT.rightChildren.equals(rightChildren);
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + (this.node != null ? this.node.hashCode() : 0);
		hash = 79 * hash + (this.character != null ? this.character.hashCode() : 0);
		hash = 79 * hash + (this.leftChildren != null ? this.leftChildren.hashCode() : 0);
		hash = 79 * hash + (this.rightChildren != null ? this.rightChildren.hashCode() : 0);
		return hash;
	}

}


