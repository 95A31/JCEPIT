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
	
	public Automata(HashSet<Integer> s, Integer is,  HashSet<Transition> tr) {
		alphabet = new HashSet<Character>();
		states = s;
		initialState = is;
		transitionRelation = tr;
	}	
	
	public Automata(HashSet<Integer> s, Integer is) {
		alphabet = new HashSet<Character>();
		states = s;
		initialState = is;
		transitionRelation = new HashSet<Transition>();
	}
	
}
