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

import java.util.*;

public class LightRabin extends Automata {

	public HashSet<State> liveStates;

	public LightRabin() {
		super();
		liveStates = new HashSet<State>();
	}

	public LightRabin(HashSet<State> s, HashSet<State> ls, State is) {
		super(s, is);
		liveStates = ls;
	}

	public LightRabin(HashSet<State> s, State is) {
		super(s, is);
		liveStates = new HashSet<State>();
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
		return new LightRabin((HashSet<State>) states.clone(), (HashSet<State>) liveStates.clone(), (State) initialState.clone());
	}
}
