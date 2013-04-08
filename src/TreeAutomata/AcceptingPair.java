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

public class AcceptingPair {

	public HashSet<Integer> L;
	public HashSet<Integer> U;

	public AcceptingPair() {
		L = new HashSet<Integer>();
		U = new HashSet<Integer>();
	}

	public AcceptingPair(HashSet<Integer> l, HashSet<Integer> u) {
		L = l;
		U = u;
	}
}
