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

import java.util.*;

public class LightRabin extends Automata {

	public HashSet<Integer> liveStates;

	public LightRabin() {
		super();
		liveStates = new HashSet<Integer>();
	}

	public LightRabin(HashSet<Integer> s, HashSet<Integer> ls, Integer is) {
		super(s, is);
		liveStates = ls;
	}

	public LightRabin(HashSet<Integer> s, Integer is) {
		super(s,is);
		liveStates = new HashSet<Integer>();
	}

	@Override
	public String toString() {
		return super.toString().concat(" - Live States: " + liveStates);
	}

	@Override
	public boolean equals(Object o) {
		LightRabin tmpLIFRTA = (LightRabin) o;
		return tmpLIFRTA.states.equals(states) && tmpLIFRTA.initialState.equals(initialState) && tmpLIFRTA.liveStates.equals(liveStates);
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 23 * hash + (states != null ? states.hashCode() : 0);
		hash = 23 * hash + (initialState != null ? initialState.hashCode() : 0);
		hash = 23 * hash + (liveStates != null ? liveStates.hashCode() : 0);
		return hash;
	}

	@Override
	public Object clone() {
		return new LightRabin((HashSet<Integer>) states.clone(), (HashSet<Integer>) liveStates.clone(),  new Integer(initialState));
	}
}
