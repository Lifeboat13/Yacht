/*
 * This file is part of Yacht.
 *
 * Yacht is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Yacht is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Yacht.  If not, see <http://www.gnu.org/licenses/>.
 */
package src.diemaker;

/**
 * Creates random number generators that will mimic a set of dice.
 *
 * @version Alpha 3
 * @author  PCGuyIV
 */
public class Dice {

	private static final int DEFAULT_NUM_DICE = 5;

	private DieMaker[] dice;
	private int[]      results;

	private int     numDice, sides, minVal, total;
	private boolean sequential;

	public Dice() {
		initDice();
	}

	public Dice(boolean sequential) {
		initDice(sequential);
	}

	public Dice(int numDice) {
		initDice(numDice);
	}

	public Dice(int numDice, boolean sequential) {
		initDice(numDice, sequential);
	}

	public Dice(int numDice, int sides) {
		initDice(numDice, sides);
	}

	public Dice(int numDice, int sides, boolean sequential) {
		initDice(numDice, sides, sequential);
	}

	public Dice(int numDice, int sides, int minVal) {
		initDice(numDice, sides, minVal);
	}

	public Dice(int numDice, int sides, int minVal, boolean sequential) {
		initDice(numDice, sides, minVal, sequential);
	}

	public void initDice() {
		initDice(DEFAULT_NUM_DICE, DieMaker.getDefaultSides(), DieMaker.getDefaultMinVal(), false);
	}

	public void initDice(boolean sequential) {
		initDice(DEFAULT_NUM_DICE, DieMaker.getDefaultSides(), DieMaker.getDefaultMinVal(), sequential);
	}

	public void initDice(int numDice) {
		initDice(numDice, DieMaker.getDefaultSides(), DieMaker.getDefaultMinVal(), false);
	}

	public void initDice(int numDice, boolean sequential) {
		initDice(numDice, DieMaker.getDefaultSides(), DieMaker.getDefaultMinVal(), sequential);
	}

	public void initDice(int numDice, int sides) {
		initDice(numDice, sides, DieMaker.getDefaultMinVal(), false);
	}

	public void initDice(int numDice, int sides, boolean sequential) {
		initDice(numDice, sides, DieMaker.getDefaultMinVal(), sequential);
	}

	public void initDice(int numDice, int sides, int minVal) {
		initDice(numDice, sides, minVal, false);
	}

	public void initDice(int numDice, int sides, int minVal, boolean sequential) {
		dice            = new DieMaker[numDice];
		results         = new int[numDice];
		this.numDice    = numDice;
		this.sides      = sides;
		this.minVal     = minVal;
		this.sequential = sequential;

		for (int i = 0; i < numDice; i++) {
			if (sequential && (numDice <= sides || i < sides)) {
				dice[i] = new DieMaker(sides, minVal, i + minVal);
			} else if (sequential && i >= sides) {
				dice[i] = new DieMaker(sides, minVal, sides + minVal - 1);
			} else {
				dice[i]    = new DieMaker(sides, minVal);
			}
			results[i] = dice[i].getResult();
		}

		totalDice();
	}

	public int totalDice() {
		total = 0;

		for (DieMaker die : dice) {
			total += die.getResult();
		}

		return total;
	}

	public DieMaker[] getDice() {
		return dice;
	}

	public DieMaker getDie(int dieNum) {
		return dice[dieNum];
	}

	public int[] getResults() {
		return results;
	}

	public int getResult(int dieNum) {
		return results[dieNum];
	}

	public int getTotal() {
		return total;
	}

	public int getNumDice() {
		return numDice;
	}

	public int getSides() {
		return sides;
	}

	public int getMinVal() {
		return minVal;
	}

	public boolean isSequential() {
		return sequential;
	}

	public void setNumDice(int numDice) {
		if (numDice != this.numDice) {
			initDice(numDice, sides, minVal, sequential);
			this.numDice = numDice;
		}
	}

	public void setSides(int sides) {
		if (sides != this.sides) {
			initDice(numDice, sides, minVal, sequential);
			this.sides = sides;
		}
	}

	public void setMinVal(int minVal) {
		if (minVal != this.minVal) {
			initDice(numDice, sides, minVal, sequential);
			this.minVal = minVal;
		}
	}

	public void setSequential(boolean sequential) {
		if (sequential != this.sequential) {
			initDice(numDice, sides, minVal, sequential);
			this.sequential = sequential;
		}
	}

	public void changeSequential() {
		sequential = !sequential;
		initDice(numDice, sides, minVal, sequential);
	}

	public void rollDice() {
		int[] rollList = new int[numDice];

		for (int i = 0; i < numDice; i++) {
			rollList[i] = i;
		}

		rollDice(rollList);
	}

	public void rollDice(int[] rollList) {
		for (int die : rollList) {
			dice[die].roll();
			results[die] = dice[die].getResult();
		}

		totalDice();
	}

	public void updateDice() {
		int[] updateList = new int[numDice];

		for (int i = 0; i < numDice; i++) {
			updateList[i] = i;
		}

		updateDice(updateList);
	}

	public void updateDice(int[] updateList) {
		for (int die : updateList) {
			results[die] = dice[die].getResult();
		}

		totalDice();
	}

	@Override
	public String toString() {
		String[] resultList = new String[numDice];

		for (int i = 0; i < numDice; i++) {
			resultList[i] = dice[i].toString();
		}

		return String.join(", ", resultList);
	}
}
