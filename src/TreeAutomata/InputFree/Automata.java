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

public abstract class Automata {

	public HashSet<State> states;
	public State initialState;
	public HashSet<Transition> transitionRelation;

	public Automata() {
		states = new HashSet<State>();
		initialState = new State();
		transitionRelation = new HashSet<Transition>();
	}

	public Automata(HashSet<State> s, State is) {
		this(s, is, new HashSet<Transition>());
	}

	public Automata(HashSet<State> s, State is, HashSet<Transition> tr) {
		states = s;
		initialState = is;
		transitionRelation = tr;
	}

	@Override
	public String toString() {
		return "Init State: " + initialState + " - States: " + states;
	}
}
