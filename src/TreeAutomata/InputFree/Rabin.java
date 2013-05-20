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

import GraphViz.*;
import java.io.*;
import java.util.*;

public class Rabin extends Automata {

	public HashSet<State> liveStates;
	public HashSet<AcceptingPair> acceptanceCondition;

	public Rabin() {
		super();
		liveStates = new HashSet<State>();
		acceptanceCondition = new HashSet<AcceptingPair>();
	}

	public Rabin(HashSet<State> s, HashSet<State> ls, State is, HashSet<AcceptingPair> ac, HashSet<Transition> tr) {
		super(s, is, tr);
		liveStates = ls;
		acceptanceCondition = ac;
	}

	public Rabin(HashSet<State> s, State is, HashSet<AcceptingPair> ac, HashSet<Transition> tr) {
		super(s, is, tr);
		liveStates = new HashSet<State>();
		acceptanceCondition = ac;
	}

	public Rabin(HashSet<Integer> s, Integer is, HashSet<TreeAutomata.AcceptingPair> ac, HashSet<TreeAutomata.Transition> tr) {
		states = new HashSet<State>();
		liveStates = new HashSet<State>();
		Iterator<TreeAutomata.Transition> transitionIterator = tr.iterator();
		boolean initialStateChoosed = false;
		while (transitionIterator.hasNext()) {
			TreeAutomata.Transition t = transitionIterator.next();
			State state = new State(t.node, t.character);
			states.add(state);
			if (t.node != is && (t.leftChildren != t.node || t.rightChildren != t.node)) {
				liveStates.add(state);
			}
			if (!initialStateChoosed && t.node == is) {
				initialState = state;
				initialStateChoosed = true;
			}
		}

		acceptanceCondition = new HashSet<AcceptingPair>();
		Iterator<TreeAutomata.AcceptingPair> iteratorAcceptingPair = ac.iterator();
		while (iteratorAcceptingPair.hasNext()) {
			TreeAutomata.AcceptingPair ap = iteratorAcceptingPair.next();

			Iterator<Integer> iteratorOnL = ap.L.iterator();
			HashSet<State> InputFreeL = new HashSet<State>();
			while (iteratorOnL.hasNext()) {
				Integer stateOfL = iteratorOnL.next();
				Iterator<State> iteratorInputFreeStates = states.iterator();
				while (iteratorInputFreeStates.hasNext()) {
					State tmpInputFreeState = iteratorInputFreeStates.next();
					if (tmpInputFreeState.state == stateOfL) {
						InputFreeL.add(tmpInputFreeState);
					}
				}
			}

			Iterator<Integer> iteratorOnU = ap.U.iterator();
			HashSet<State> InputFreeU = new HashSet<State>();
			while (iteratorOnU.hasNext()) {
				Integer stateOfU = iteratorOnU.next();
				Iterator<State> iteratorInputFreeStates = states.iterator();
				while (iteratorInputFreeStates.hasNext()) {
					State tmpInputFreeState = iteratorInputFreeStates.next();
					if (tmpInputFreeState.state == stateOfU) {
						InputFreeU.add(tmpInputFreeState);
					}
				}
			}

			acceptanceCondition.add(new AcceptingPair(InputFreeL, InputFreeU));
		}

		transitionRelation = new HashSet<Transition>();
		transitionIterator = tr.iterator();
		while (transitionIterator.hasNext()) {
			TreeAutomata.Transition t = transitionIterator.next();
			Iterator<State> iteratorForNode = states.iterator();
			while (iteratorForNode.hasNext()) {
				State tmpNode = iteratorForNode.next();
				if (tmpNode.state == t.node && tmpNode.character == t.character) {
					Iterator<State> iteratorForLeftChild = states.iterator();
					while (iteratorForLeftChild.hasNext()) {
						State tmpLeftChildren = iteratorForLeftChild.next();
						if (tmpLeftChildren.state == t.leftChildren) {
							Iterator<State> iteratorForRightChild = states.iterator();
							while (iteratorForRightChild.hasNext()) {
								State tmpRightChildren = iteratorForRightChild.next();
								if (tmpRightChildren.state == t.rightChildren) {
									if (t.node.equals(t.leftChildren) && t.node.equals(t.rightChildren)) {
										if (tmpNode.equals(tmpLeftChildren) && tmpNode.equals(tmpRightChildren)) {
											transitionRelation.add(new Transition(tmpNode, tmpLeftChildren, tmpRightChildren));
										}
									} else {
										transitionRelation.add(new Transition(tmpNode, tmpLeftChildren, tmpRightChildren));
									}
								}
							}
						}
					}
				}
			}
		}

	}

	public Rabin recognizeEmptyLanguage() {
		System.out.print("Checking for empty language...\r");
		HashSet<LightRabin> previousLevel = new HashSet<LightRabin>();
		HashSet<LightRabin> currentLevel = new HashSet<LightRabin>();

		for (Transition t : transitionRelation) {
			if (isInSomeUSet(t.leftChildren) && isInSomeUSet(t.rightChildren)) {
				LightRabin tmpLR = new LightRabin();
				tmpLR.initialState = t.node;
				tmpLR.states.add(t.leftChildren);
				tmpLR.states.add(t.rightChildren);
				previousLevel.add(tmpLR);
			}
		}

		LightRabin lightSolution = new LightRabin();

		mainIter:
		for (int i = 0; i < liveStates.size(); i++) {
			System.out.print("Checking for empty language... [" + (int) ((float) i / (float) liveStates.size() * 100) + "%]\r");
			for (LightRabin LR1 : previousLevel) {
				for (Transition t : transitionRelation) {
					if (LR1.initialState.equals(t.leftChildren)) {
						LightRabin tmpLR = (LightRabin) LR1.clone();
						tmpLR.states.add(tmpLR.initialState);
						tmpLR.liveStates.add(tmpLR.initialState);
						tmpLR.states.add(t.rightChildren);
						tmpLR.initialState = t.node;
						currentLevel.add(tmpLR);
						continue;
					} else if (LR1.initialState.equals(t.rightChildren)) {
						LightRabin tmpLR = (LightRabin) LR1.clone();
						tmpLR.states.add(tmpLR.initialState);
						tmpLR.liveStates.add(tmpLR.initialState);
						tmpLR.states.add(t.leftChildren);
						tmpLR.initialState = t.node;
						currentLevel.add(tmpLR);
					}
				}
				for (LightRabin LR2 : previousLevel) {
					for (State ls1 : liveStates) {
						//Case 3
						if (notInSameLUPair(ls1) && LR1.liveStates.equals(LR2.liveStates) && LR1.states.contains(ls1) && LR2.initialState.equals(ls1) && LR2.states.contains(ls1)) {
							LightRabin tmpLR = (LightRabin) LR1.clone();
							tmpLR.states.add(LR2.initialState);
							tmpLR.states.addAll(LR2.states);
							tmpLR.liveStates.add(LR2.initialState);
							tmpLR.liveStates.addAll(LR2.liveStates);
							if (isGoodSolution(tmpLR)) {
								lightSolution = tmpLR;
								break mainIter;
							}
							currentLevel.add(tmpLR);
						}
						//Case 2
						for (State ls2 : liveStates) {
							if (!ls1.equals(ls2) && LR1.states.contains(ls1) && LR2.initialState.equals(ls1) && !LR2.states.contains(ls2) && !LR2.initialState.equals(ls2)) {
								LightRabin tmpLR = (LightRabin) LR1.clone();
								tmpLR.states.add(LR2.initialState);
								tmpLR.states.addAll(LR2.states);
								tmpLR.liveStates.add(LR2.initialState);
								tmpLR.liveStates.addAll(LR2.liveStates);
								if (isGoodSolution(tmpLR)) {
									lightSolution = tmpLR;
									break mainIter;
								}
								currentLevel.add(tmpLR);
							}
						}
					}
				}
			}

			previousLevel = currentLevel;
			currentLevel = new HashSet<LightRabin>();
			System.gc();
		}


		System.out.println("Checking for empty language... [OK]   ");
		if (lightSolution.states.isEmpty()) {
			return new Rabin();
		} else {
			Rabin solution = new Rabin();
			solution.initialState = initialState;
			solution.states = lightSolution.states;
			solution.liveStates = lightSolution.liveStates;
			solution.acceptanceCondition = acceptanceCondition;
			solution.transitionRelation = transitionRelation;
			return solution;
		}
	}

	private boolean isGoodSolution(LightRabin lr) {
		if (!lr.initialState.state.equals(initialState.state)) {
			return false;
		}

		for (State s : lr.states) {
			if (!lr.liveStates.contains(s) && !isInSomeUSet(s)) {
				return false;
			}
		}

		HashSet<State> stss = (HashSet<State>) lr.states.clone();
		stss.add(lr.initialState);

		stateIter:
		for (State s : stss) {
			for (Transition t : transitionRelation) {
				if (t.node.equals(s) && lr.states.contains(t.leftChildren) && lr.states.contains(t.rightChildren)) {
					continue stateIter;
				}
			}
			return false;
		}
		return true;
	}

	private boolean notInSameLUPair(State s) {
		for (AcceptingPair pair : acceptanceCondition) {
			if (pair.U.contains(s)) {
				HashSet<State> tmpSs = (HashSet<State>) liveStates.clone();
				tmpSs.retainAll(pair.L);
				if (tmpSs.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isInSomeUSet(State s) {
		for (AcceptingPair pair : acceptanceCondition) {
			if (pair.U.contains(s)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object clone() {
		return new Rabin((HashSet<State>) states.clone(), (HashSet<State>) liveStates.clone(), (State) initialState.clone(), (HashSet<AcceptingPair>) acceptanceCondition.clone(), (HashSet<Transition>) transitionRelation.clone());
	}

	@Override
	public String toString() {
		return super.toString().concat(" - Live States: " + liveStates);
	}

	public void toImageFile(String fileName) throws IOException {
		System.out.print("Saving a successful run to image file...\r");

		HashSet<State> notVistitedStates = (HashSet<State>) states.clone();
		LinkedList<State> currentBFSStates;
		LinkedList<State> nextBFSStates = new LinkedList<State>();
		nextBFSStates.add(initialState);
		HashSet<Transition> transRelWorkCopy = (HashSet<Transition>) transitionRelation.clone();
		HashSet<Transition> transToEliminate = new HashSet<Transition>();

		GraphViz g = new GraphViz();
		g.addln(g.start_graph());

		g.addln("\tnode [shape = circle];");

		while (!nextBFSStates.isEmpty()) {
			System.out.print("Saving a successful run to image file... [" + (int) ((float) (states.size() - notVistitedStates.size()) / (float) states.size() * 100) + "%]\r");
			currentBFSStates = nextBFSStates;
			nextBFSStates = new LinkedList<State>();
			System.gc();

			g.add("\n\t{rank = same; ");
			for (State s : currentBFSStates) {
				g.add(s.state + " ");
			}
			g.addln("}");

			while (!currentBFSStates.isEmpty()) {
				State tmpState = currentBFSStates.pop();

				transRelWorkCopy.removeAll(transToEliminate);
				transToEliminate.clear();

				for (Transition trans : transRelWorkCopy) {
					if (trans.node.equals(tmpState)) {
						if (trans.node == tmpState && (states.contains(trans.node) || trans.node.equals(initialState)) && states.contains(trans.leftChildren) && states.contains(trans.rightChildren)) {
							if ((trans.node.equals(trans.leftChildren) || trans.node.equals(trans.rightChildren)) && !isInSomeUSet(trans.node)) {
								continue;
							}
							if (isInSomeUSet(trans.node)) {
								g.addln("\t" + tmpState.state + " [style=\"filled\", fillcolor=\"lawngreen\"];");
							}
							if (notVistitedStates.contains(trans.leftChildren)) {
								g.addln("\t" + tmpState.state + " -> " + trans.leftChildren.state + " [label=" + tmpState.character + "];");
								nextBFSStates.add(trans.leftChildren);
								notVistitedStates.remove(trans.leftChildren);
							} else {
								double newState = Math.random() + trans.leftChildren.state;
								g.addln("\t" + newState + " [label=\"" + trans.leftChildren.state + "\", style=\"dashed\"];");
								g.addln("\t" + tmpState.state + " -> " + newState + " [label=" + tmpState.character + "];");
							}
							if (notVistitedStates.contains(trans.rightChildren)) {
								g.addln("\t" + tmpState.state + " -> " + trans.rightChildren.state + " [label=" + tmpState.character + "];");
								nextBFSStates.add(trans.rightChildren);
								notVistitedStates.remove(trans.rightChildren);
							} else {
								double newState = Math.random() + trans.rightChildren.state;
								g.addln("\t" + newState + " [label=\"" + trans.rightChildren.state + "\", style=\"dashed\"];");
								g.addln("\t" + tmpState.state + " -> " + newState + " [label=" + tmpState.character + "];");
							}
							notVistitedStates.remove(tmpState);
							transToEliminate.add(trans);
							break;
						}
					}
				}
				transRelWorkCopy.removeAll(transToEliminate);
				transToEliminate.clear();
			}
		}

		g.addln(g.end_graph());
		g.writeGraphToFile(g.getGraph(g.getDotSource(), "png"), fileName);
		System.out.println("Saving a successful run to image file... [OK]   ");
	}
}
