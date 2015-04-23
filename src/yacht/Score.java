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
package src.yacht;

public class Score {

	private static final int MIN_UPPER_TOTAL = 63;
	private static final int BONUS           = 35;
	private static final int FULL_HOUSE      = 25;
	private static final int SM_STRAIGHT     = 30;
	private static final int LG_STRAIGHT     = 40;
	private static final int YACHT           = 50;

	private int[] upper = new int[6];
	private int[] lower = new int[7];

	private int upperSubtotal, bonus, upperTotal, lowerTotal, grandTotal;

	public Score() {
		initScores();
	}

	public Score(String minOrMax) {
		if (minOrMax.equalsIgnoreCase("min")) {
			setScores(3, 1);
		} else if (minOrMax.equalsIgnoreCase("max")) {
			setScores(5, 6);
		} else {
			initScores();
		}
	}

	public void initScores() {
		for (int i = 0; i < 6; i++) {
			upper[i] = -1;
			lower[i] = -1;
		}

		lower[6] = -1;
		updateTotals();
	}

	public void setScores(int count, int val) {
		for (int i = 0; i < 6; i++) {
			upper[i] = (i + 1) * count;
		}

		for (int i = 0; i < 7; i++) {
			switch (i) {
				case 2:
					lower[i] = FULL_HOUSE;
					break;
				case 3:
					lower[i] = SM_STRAIGHT;
					break;
				case 4:
					lower[i] = LG_STRAIGHT;
					break;
				case 5:
					lower[i] = YACHT;
					break;
				default:
					lower[i] = 5 * val;
			}

			updateTotals();
		}
	}

	public static int getMinUpperTotal() {
		return MIN_UPPER_TOTAL;
	}

	public static int getBonusValue() {
		return BONUS;
	}

	public static int getFullHouseValue() {
		return FULL_HOUSE;
	}

	public static int getSmStraightValue() {
		return SM_STRAIGHT;
	}

	public static int getLgStraightValue() {
		return LG_STRAIGHT;
	}

	public static int getYachtValue() {
		return YACHT;
	}

	public int getAces() {
		return upper[0];
	}

	public int getTwos() {
		return upper[1];
	}

	public int getThrees() {
		return upper[2];
	}

	public int getFours() {
		return upper[3];
	}

	public int getFives() {
		return upper[4];
	}

	public int getSixes() {
		return upper[5];
	}

	public int getThreeOfAKind() {
		return lower[0];
	}

	public int getFourOfAKind() {
		return lower[1];
	}

	public int getFullHouse() {
		return lower[2];
	}

	public int getSmStraight() {
		return lower[3];
	}

	public int getLgStraight() {
		return lower[4];
	}

	public int getYacht() {
		return lower[5];
	}

	public int getChance() {
		return lower[6];
	}

	public int getUpperSubtotal() {
		return upperSubtotal;
	}

	public int getBonus() {
		return bonus;
	}

	public int getUpperTotal() {
		return upperTotal;
	}

	public int getLowerTotal() {
		return lowerTotal;
	}

	public int getGrandTotal() {
		return grandTotal;
	}

	public void setAces(int score) {
		upper[0] = score;
	}

	public void setTwos(int score) {
		upper[1] = score;
	}

	public void setThrees(int score) {
		upper[2] = score;
	}

	public void setFours(int score) {
		upper[3] = score;
	}

	public void setFives(int score) {
		upper[4] = score;
	}

	public void setSixes(int score) {
		upper[5] = score;
	}

	public void setThreeOfAKind(int score) {
		lower[0] = score;
	}

	public void setFourOfAKind(int score) {
		lower[1] = score;
	}

	public void setFullHouse(boolean success) {
		if (success) {
			lower[2] = FULL_HOUSE;
		} else {
			lower[2] = 0;
		}
	}

	public void setSmStraight(boolean success) {
		if (success) {
			lower[3] = SM_STRAIGHT;
		} else {
			lower[3] = 0;
		}
	}

	public void setLgStraight(boolean success) {
		if (success) {
			lower[4] = LG_STRAIGHT;
		} else {
			lower[4] = 0;
		}
	}

	public void setYacht(boolean success) {
		if (success) {
			lower[5] = YACHT;
		} else {
			lower[5] = 0;
		}
	}

	public void setChance(int score) {
		lower[6] = score;
	}

	public void updateTotals() {
		upperSubtotal = upperTotal = lowerTotal = grandTotal = 0;

		for (int score : upper) {
			if (score >= 0) {
				upperSubtotal += score;
			}
		}

		if (upperSubtotal < MIN_UPPER_TOTAL) {
			bonus = 0;
		} else {
			bonus = BONUS;
		}

		upperTotal = upperSubtotal + bonus;

		for (int score : lower) {
			if (score >= 0) {
				lowerTotal += score;
			}
		}

		grandTotal = upperTotal + lowerTotal;
	}
}
