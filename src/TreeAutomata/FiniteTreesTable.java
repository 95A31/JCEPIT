package TreeAutomata;

import java.util.*;
import java.util.Map.Entry;

public class FiniteTreesTable {

	public HashMap<Integer, HashSet<HashSet<Integer>>> FiniteTreesTable;

	public FiniteTreesTable(HashSet<Transition> tr, HashSet<Integer> ac) {

		FiniteTreesTable = new HashMap<Integer, HashSet<HashSet<Integer>>>();

		HashSet<Transition> transRelWorkCopy = (HashSet<Transition>) tr.clone();
		HashSet<Transition> transToEliminate = new HashSet<Transition>();
		boolean transitionAdded;

		do {
			transitionAdded = false;

			transRelWorkCopy.removeAll(transToEliminate);
			transToEliminate.clear();

			for (Transition trans : transRelWorkCopy) {
				HashSet<HashSet<Integer>> tmpSetOfStates;

				if (FiniteTreesTable.containsKey(trans.node)) {
					tmpSetOfStates = FiniteTreesTable.get(trans.node);
				} else {
					tmpSetOfStates = new HashSet<HashSet<Integer>>();
				}
				if ((ac.contains(trans.rightChildren) && ac.contains(trans.leftChildren)) || FiniteTreesTable.containsKey(trans.leftChildren) && ac.contains(trans.rightChildren) || FiniteTreesTable.containsKey(trans.rightChildren)
						&& ac.contains(trans.leftChildren) || FiniteTreesTable.containsKey(trans.leftChildren) && FiniteTreesTable.containsKey(trans.rightChildren)) {
					HashSet<Integer> tmpStates = new HashSet<Integer>();
					tmpStates.add(trans.rightChildren);
					tmpStates.add(trans.leftChildren);
					tmpSetOfStates.add(tmpStates);
					transToEliminate.add(trans);
				}
				if (!tmpSetOfStates.isEmpty()) {
					FiniteTreesTable.put(trans.node, tmpSetOfStates);
					transitionAdded = true;
				}
			}
		} while (transitionAdded);
	}

	public boolean containsBadStates() {
		for (Entry<Integer, HashSet<HashSet<Integer>>> entryOfTable : FiniteTreesTable.entrySet()) {
			for (HashSet<Integer> tree : entryOfTable.getValue()) {
				if (!FiniteTreesTable.keySet().containsAll(tree))
					return true;
			}
		}
		return false;
	}

	public boolean hasEmptyTreesSet(Integer state) {
		return !FiniteTreesTable.containsKey(state);
	}

	public HashSet<Integer> getStates() {
		return new HashSet(FiniteTreesTable.keySet());
	}

	public void removeIncoerentTrees() {
		HashSet<Integer> keyToRemove = new HashSet<Integer>();
		for (Entry<Integer, HashSet<HashSet<Integer>>> entryOfTable : FiniteTreesTable.entrySet()) {
			for (HashSet<Integer> tree : entryOfTable.getValue()) {
				if (!FiniteTreesTable.keySet().containsAll(tree)) {
					entryOfTable.getValue().remove(tree);
					if (entryOfTable.getValue().isEmpty()) {
						keyToRemove.add(entryOfTable.getKey());
					}
				}
			}
		}
		for (Integer key : keyToRemove)
			FiniteTreesTable.remove(key);
		System.gc();
	}
}
