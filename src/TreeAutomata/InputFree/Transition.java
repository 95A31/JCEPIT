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
