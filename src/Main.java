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

import TreeAutomata.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		if(args.length < 2){
			System.out.println("One or more argument(s) missing.");
			System.out.println("EXAMPLE: java -jar JCEPIT.jar -R RabinExampleAutomata.txt");
			System.out.println("EXAMPLE: java -jar JCEPIT.jar -B BuchiExampleAutomata.txt");
			return;
		}

		if (args[0].matches("-B|-b")) {
			Buchi BTA = Buchi.fromFile(args[1]);
			Buchi tmpBTA = BTA.recognizeEmptyLanguage();
			System.out.println("Language recognized by automata " + (tmpBTA.states.isEmpty() ? "is " : "isn't ") + "empty.");
			if (!tmpBTA.states.isEmpty()) {
				tmpBTA.toImageFile(args[1].substring(0, args[1].lastIndexOf('.')) + ".png");
			}
		} else if (args[0].matches("-R|-r")) {
			Rabin RA = Rabin.fromFile(args[1]);
			Rabin tmpRA = RA.recognizeEmptyLanguage();
			System.out.println("Language recognized by automata " + (tmpRA.states.isEmpty() ? "is " : "isn't ") + "empty.");
			if (!tmpRA.states.isEmpty()) {
				tmpRA.toImageFile(args[1].substring(0, args[1].lastIndexOf('.')) + ".png");
			}
		} else {
			System.out.println("Unknow option: " + args[0]);
		}
	}
}
