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

import GraphViz.*;
import TreeAutomata.Rabin;

import java.io.*;
import java.util.*;

public class Rabin extends Automata {

	public HashSet<Integer> liveStates;
	public HashSet<AcceptingPair> acceptanceCondition;

	public Rabin() {
		super();
		liveStates = new HashSet<Integer>();
		acceptanceCondition = new HashSet<AcceptingPair>();
	}
	

	public Rabin(HashSet<Character> a,  Integer is, HashSet<Integer> s, HashSet<AcceptingPair> ac, HashSet<Transition> tr) {
		super(s, is, tr);
		acceptanceCondition = ac;
		liveStates = new HashSet<Integer>();
		for (Transition t : transitionRelation){
			if (t.node != is && (t.leftChildren != t.node || t.rightChildren != t.node)) {
				liveStates.add(t.node);
			}
		}
	}
	

	public Rabin(HashSet<Integer> s, HashSet<Integer> ls, Integer is, HashSet<AcceptingPair> ac, HashSet<Transition> tr) {
		super(s, is, tr);
		liveStates = ls;
		acceptanceCondition = ac;
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
					for (Integer ls1 : liveStates) {
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
						for (Integer ls2 : liveStates) {
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
		if (!lr.initialState.equals(initialState)) {
			return false;
		}

		for (Integer s : lr.states) {
			if (!lr.liveStates.contains(s) && !isInSomeUSet(s)) {
				return false;
			}
		}

		HashSet<Integer> stss = (HashSet<Integer>) lr.states.clone();
		stss.add(lr.initialState);

		stateIter:
		for (Integer s : stss) {
			for (Transition t : transitionRelation) {
				if (t.node.equals(s) && lr.states.contains(t.leftChildren) && lr.states.contains(t.rightChildren)) {
					continue stateIter;
				}
			}
			return false;
		}
		return true;
	}

	private boolean notInSameLUPair(Integer s) {
		for (AcceptingPair pair : acceptanceCondition) {
			if (pair.U.contains(s)) {
				HashSet<Integer> tmpSs = (HashSet<Integer>) liveStates.clone();
				tmpSs.retainAll(pair.L);
				if (tmpSs.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isInSomeUSet(Integer s) {
		for (AcceptingPair pair : acceptanceCondition) {
			if (pair.U.contains(s)) {
				return true;
			}
		}
		return false;
	}
	
	public static Rabin fromFile(String fileName) throws FileNotFoundException, IOException {

		System.out.print("Parsing input file...\r");

		HashSet<Character> a = new HashSet<Character>();
		HashSet<Integer> s = new HashSet<Integer>();
		Integer is = Integer.MIN_VALUE;
		HashSet<Transition> tr = new HashSet<Transition>();
		HashSet<AcceptingPair> ac = new HashSet<AcceptingPair>();

		FileInputStream fis = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		long readedFile = 0;
		int state = Integer.MIN_VALUE;
		String line;
		while ((line = br.readLine()) != null) {
			
			System.out.print("Parsing input file... [" + (int)(((float) (readedFile = readedFile + line.length())/fis.getChannel().size()) * 100) + "%]\r");
			
			if (line.length() <= 0 || line.charAt(0) == '#') {
				continue;
			} else if (line.equals("[Alphabet]")) {
				state = 0;
			} else if (line.equals("[States]")) {
				state = 1;
			} else if (line.equals("[Initial State]")) {
				state = 2;
			} else if (line.equals("[Transitions]")) {
				state = 3;
			} else if (line.equals("[Acceptance Conditions]")) {
				state = 4;
			} else {
				switch (state) {
					case 0:
						a.add(line.charAt(0));
						break;

					case 1:
						s.add(Integer.parseInt(line));
						break;

					case 2:
						is = Integer.parseInt(line);
						break;

					case 3:
						String[] ss = line.split(" ");
						Integer node = Integer.parseInt(ss[0]);
						Character c = ss[1].charAt(0);
						Integer leftChildren = Integer.parseInt(ss[3]);
						Integer rightChildren = Integer.parseInt(ss[4]);

						tr.add(new Transition(node, c, leftChildren, rightChildren));
						break;

					case 4:
						String[] pair = line.split("-");
						String[] L = pair[0].split(" ");
						String[] U = pair[1].split(" ");

						AcceptingPair ap = new AcceptingPair();

						for (int i = 0; i < L.length; i++) {
							if (!L[i].isEmpty()) {
								ap.L.add(Integer.parseInt(L[i]));
							}
						}

						for (int i = 0; i < U.length; i++) {
							if (!U[i].isEmpty()) {
								ap.U.add(Integer.parseInt(U[i]));
							}
						}

						ac.add(ap);
						break;
				}
			}
		}
		br.close();
		System.out.println("Parsing input file... [OK]   ");
		return new Rabin(a,is,s, ac, tr);		
	}

	@Override
	public Object clone() {
		return new Rabin((HashSet<Integer>) states.clone(), (HashSet<Integer>) liveStates.clone(), new Integer(initialState), (HashSet<AcceptingPair>) acceptanceCondition.clone(), (HashSet<Transition>) transitionRelation.clone());
	}

	@Override
	public String toString() {
		return super.toString().concat(" - Live States: " + liveStates);
	}

	public void toImageFile(String fileName) throws IOException {
		System.out.print("Saving a successful run to image file...\r");

		HashSet<Integer> notVistitedStates = (HashSet<Integer>) states.clone();
		LinkedList<Integer> currentBFSStates;
		LinkedList<Integer> nextBFSStates = new LinkedList<Integer>();
		nextBFSStates.add(initialState);
		HashSet<Transition> transRelWorkCopy = (HashSet<Transition>) transitionRelation.clone();
		HashSet<Transition> transToEliminate = new HashSet<Transition>();

		GraphViz g = new GraphViz();
		g.addln(g.start_graph());

		g.addln("\tnode [shape = circle];");

		while (!nextBFSStates.isEmpty()) {
			System.out.print("Saving a successful run to image file... [" + (int) ((float) (states.size() - notVistitedStates.size()) / (float) states.size() * 100) + "%]\r");
			currentBFSStates = nextBFSStates;
			nextBFSStates = new LinkedList<Integer>();
			System.gc();

			g.add("\n\t{rank = same; ");
			for (Integer s : currentBFSStates) {
				g.add(s + " ");
			}
			g.addln("}");

			while (!currentBFSStates.isEmpty()) {
				Integer tmpState = currentBFSStates.pop();

				transRelWorkCopy.removeAll(transToEliminate);
				transToEliminate.clear();

				for (Transition trans : transRelWorkCopy) {
					if (trans.node.equals(tmpState)) {
						if (trans.node == tmpState && (states.contains(trans.node) || trans.node.equals(initialState)) && states.contains(trans.leftChildren) && states.contains(trans.rightChildren)) {
							if ((trans.node.equals(trans.leftChildren) || trans.node.equals(trans.rightChildren)) && !isInSomeUSet(trans.node)) {
								continue;
							}
							if (isInSomeUSet(trans.node)) {
								g.addln("\t" + tmpState + " [style=\"filled\", fillcolor=\"lawngreen\"];");
							}
							if (notVistitedStates.contains(trans.leftChildren)) {
								g.addln("\t" + tmpState + " -> " + trans.leftChildren + " [label=" + trans.character + "];");
								nextBFSStates.add(trans.leftChildren);
								notVistitedStates.remove(trans.leftChildren);
							} else {
								double newState = Math.random() + trans.leftChildren;
								g.addln("\t" + newState + " [label=\"" + trans.leftChildren + "\", style=\"dashed\"];");
								g.addln("\t" + tmpState + " -> " + newState + " [label=" + trans.character + "];");
							}
							if (notVistitedStates.contains(trans.rightChildren)) {
								g.addln("\t" + tmpState + " -> " + trans.rightChildren + " [label=" + trans.character + "];");
								nextBFSStates.add(trans.rightChildren);
								notVistitedStates.remove(trans.rightChildren);
							} else {
								double newState = Math.random() + trans.rightChildren;
								g.addln("\t" + newState + " [label=\"" + trans.rightChildren + "\", style=\"dashed\"];");
								g.addln("\t" + tmpState + " -> " + newState + " [label=" + trans.character + "];");
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
