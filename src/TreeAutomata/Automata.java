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

import java.util.*;

public abstract class Automata {

	protected HashSet<Character> alphabet;
	public HashSet<Integer> states;
	protected Integer initialState;
	protected HashSet<Transition> transitionRelation;

	public Automata() {
		alphabet = new HashSet<Character>();
		states = new HashSet<Integer>();
		transitionRelation = new HashSet<Transition>();
	}

	public Automata(HashSet<Character> a, HashSet<Integer> s, Integer is,  HashSet<Transition> tr) {
		alphabet = a;
		states = s;
		initialState = is;
		transitionRelation = tr;
	}
}
