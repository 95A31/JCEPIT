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
package TreeAutomata.InputFree;

public class State {

	public Integer state;
	public Character character;
	
	public State() {
		state = Integer.MIN_VALUE;
		character = Character.UNASSIGNED;
	}

	public State(Integer s, Character c) {
		state = s;
		character = c;
	}

	@Override
	public Object clone() {
		return new State(new Integer(state), new Character(character));
	}

	@Override
	public String toString() {
		return "(" + state + " " + character + ")";

	}

	@Override
	public boolean equals(Object o) {
		State tmpIFS = (State) o;
		return tmpIFS.state.equals(state) && tmpIFS.character.equals(character);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + (this.state != null ? this.state.hashCode() : 0);
		hash = 89 * hash + (this.character != null ? this.character.hashCode() : 0);
		return hash;
	}
}
