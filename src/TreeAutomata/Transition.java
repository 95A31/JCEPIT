/*******************************************************************************
 * JCEPIT: Java Checker for Emptiness Problem on Infinite Trees
 *    
 * Copyright (C) 2013 95A31
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

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


