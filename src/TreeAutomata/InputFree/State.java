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
