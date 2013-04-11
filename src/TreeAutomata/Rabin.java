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

import java.io.*;
import java.util.*;

public class Rabin extends Automata {

	private HashSet<AcceptingPair> acceptanceCondition;

	public Rabin(HashSet<Character> a, HashSet<Integer> s, Integer is, HashSet<AcceptingPair> ac, HashSet<Transition> tr) {
		super(a, s, is, tr);
		acceptanceCondition = ac;
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
		return new Rabin(a, s, is, ac, tr);		
	}
	
	public TreeAutomata.InputFree.Rabin toInputFree(){
		return new TreeAutomata.InputFree.Rabin(states, initialState, acceptanceCondition, transitionRelation);
	}
}
