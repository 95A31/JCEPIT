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
import java.io.*;
import java.util.*;

public class Buchi extends Automata {

	private HashSet<Integer> acceptanceCondition;

	public Buchi(HashSet<Character> a, HashSet<Integer> s, Integer is, HashSet<Integer> ac, HashSet<Transition> tr) {
		super(a, s, is, tr);
		acceptanceCondition = ac;
	}

	public Buchi() {
		super();
		acceptanceCondition = new HashSet<Integer>();
		transitionRelation = new HashSet<Transition>();
	}

	public static Buchi fromFile(String fileName) throws FileNotFoundException, IOException {

		System.out.print("Parsing input file...\r");

		HashSet<Character> a = new HashSet<Character>();
		HashSet<Integer> s = new HashSet<Integer>();
		Integer is = Integer.MIN_VALUE;
		HashSet<Transition> tr = new HashSet<Transition>();
		HashSet<Integer> ac = new HashSet<Integer>();

		FileInputStream fis = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		long readedFile = 0;
		int state = Integer.MIN_VALUE;
		String line;
		while ((line = br.readLine()) != null) {

			System.out.print("Parsing input file... [" + (int) (((float) (readedFile = readedFile + line.length()) / fis.getChannel().size()) * 100) + "%]\r");

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
					ss = line.split(" ");
					for (String str : ss) {
						ac.add(Integer.parseInt(str));
					}
					break;
				}
			}
		}
		br.close();
		System.out.println("Parsing input file... [OK]   ");
		return new Buchi(a, s, is, ac, tr);
	}

	public Buchi recognizeEmptyLanguage() {
		System.out.print("Checking for empty language...\r");
		HashSet<Transition> tr = (HashSet<Transition>) transitionRelation.clone();
		HashSet<Integer> ac = (HashSet<Integer>) acceptanceCondition.clone();
		Integer numberOfStates = states.size();

		FiniteTreesTable ftt = new FiniteTreesTable(tr, ac);

		for (int i = 0; i <= states.size(); i++) {
			System.out.print("Checking for empty language... [" + (int) ((float) i / (float) (states.size() + 1) * 100) + "%]\r");
			ftt.removeIncoerentTrees();
			if (!ftt.containsBadStates() && !ftt.hasEmptyTreesSet(initialState)) {
				Buchi tmpBTA = (Buchi) clone();
				tmpBTA.states = ftt.getStates();				
				removeIncoerentTransition(tmpBTA.transitionRelation, tmpBTA.states);
				tmpBTA.states.remove(initialState);
				System.out.println("Checking for empty language... [OK]   ");
				return tmpBTA;
			}
		}
		System.out.println("Checking for empty language... [OK]   ");
		return new Buchi();
	}

	@Override
	public Object clone() {
		return new Buchi((HashSet<Character>) alphabet.clone(), (HashSet<Integer>) states.clone(), initialState, (HashSet<Integer>) acceptanceCondition.clone(), (HashSet<Transition>) transitionRelation.clone());
	}

	private static void removeIncoerentTransition(HashSet<Transition> tr, Set<Integer> s) {
		HashSet<Transition> transitionToEliminate = new HashSet<Transition>();
		for (Transition t : tr) {
			if (!s.contains(t.node) || !s.contains(t.leftChildren) || !s.contains(t.rightChildren)) {
				transitionToEliminate.add(t);
			}
		}
		tr.removeAll(transitionToEliminate);
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
							if (trans.node.equals(trans.leftChildren) && trans.node.equals(trans.rightChildren) && !acceptanceCondition.contains(trans.node)) {
								continue;
							}
							if (acceptanceCondition.contains(trans.node)) {
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
