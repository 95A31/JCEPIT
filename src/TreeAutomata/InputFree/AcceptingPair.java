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

public class AcceptingPair {

	public HashSet<State> L;
	public HashSet<State> U;

	public AcceptingPair() {
		L = new HashSet<State>();
		U = new HashSet<State>();
	}

	public AcceptingPair(HashSet<State> l, HashSet<State> u) {
		L = l;
		U = u;
	}
}
