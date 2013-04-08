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

public class Transition {

	public State node;
	public State leftChildren;
	public State rightChildren;

	public Transition(State n, State lc, State rc) {
		node = n;
		leftChildren = lc;
		rightChildren = rc;
	}

	@Override
	public Object clone() {
		return new Transition((State) node.clone(), (State) leftChildren.clone(), (State) rightChildren.clone());
	}

	@Override
	public String toString() {
		return node + " " + " -> " + leftChildren + " " + rightChildren;
	}
	
	@Override
	public boolean equals(Object o) {
		Transition tmpIFT = (Transition) o;
		return tmpIFT.node.equals(node) && tmpIFT.leftChildren.equals(leftChildren) && tmpIFT.rightChildren.equals(rightChildren);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + (this.node != null ? this.node.hashCode() : 0);
		hash = 17 * hash + (this.leftChildren != null ? this.leftChildren.hashCode() : 0);
		hash = 17 * hash + (this.rightChildren != null ? this.rightChildren.hashCode() : 0);
		return hash;
	}
}
