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
package yacht;

import diemaker.Dice;

public class Game {

	public static final Player PLAYER = new Player();
	public static final Player BOT    = new Player("bot");
	public static final Player MIN    = new Player("min");
	public static final Player MAX    = new Player("max");
	public static final Dice   DICE   = new Dice(true);

	private static final int MAX_ROLLS = 3;

	private static boolean threeOfAKind, fourOfAKind, fullHouse;
	private static boolean smStraight,   lgStraight,  yacht;

	private static int acesVal,  twosVal,  threesVal;
	private static int foursVal, fivesVal, sixesVal;
	private static int toakVal,  foakVal,  fhVal;
	private static int ssVal,    lsVal,    yachtVal;
	private static int chanceVal;

	public static void evaluateRoll() {
		int[] valCount = new int[DICE.getSides()];
		int[] diceVals = DICE.getResults();

		boolean hasPair = false;

		threeOfAKind = fourOfAKind = fullHouse = yacht = false;
		smStraight   = lgStraight  = true;

		toakVal = foakVal = fhVal = ssVal = lsVal = yachtVal = 0;

		for (int i = 0; i < DICE.getSides(); i++) {
			valCount[i] = 0;
		}

		for (int dieVal : diceVals) {
			valCount[dieVal - 1]++;
		}

		acesVal   = valCount[0];
		twosVal   = valCount[1] * 2;
		threesVal = valCount[2] * 3;
		foursVal  = valCount[3] * 4;
		fivesVal  = valCount[4] * 5;
		sixesVal  = valCount[5] * 6;
		chanceVal = DICE.totalDice();

		for (int i = 0; i < DICE.getSides(); i++) {
			if (valCount[i] == 2 || valCount[i] == 5) {
				hasPair = true;
			}

			if (valCount[i] >= 3) {
				threeOfAKind = true;
				toakVal      = DICE.totalDice();
			}

			if (valCount[i] >= 4) {
				fourOfAKind = true;
				foakVal     = DICE.totalDice();
			}

			if (valCount[i] == 5) {
				yacht    = true;
				yachtVal = Score.getYachtValue();
			}
		}

		if (hasPair && threeOfAKind) {
			fullHouse = true;
			fhVal     = Score.getFullHouseValue();
		} else {
			fullHouse = false;
			fhVal     = 0;
		}

		if (threeOfAKind || fourOfAKind || yacht) {
			lgStraight = false;
			lsVal      = 0;
			smStraight = false;
			ssVal      = 0;
		} else {
			int zeroCount = 0;
			int pairCount = 0;

			for (int i = 0; i < DICE.getSides(); i++) {
				if (valCount[i] == 0) {
					zeroCount++;

					if (i > 0 && i < DICE.getSides() - 1) {
						lgStraight = false;

						if (i > 1 && i < DICE.getSides() - 2) {
							smStraight = false;
						}
					}
				} else if (valCount[i] == 2) {
					pairCount++;
				}
			}

			if (zeroCount > 1 || pairCount > 1 || !lgStraight) {
				lsVal = 0;
			} else {
				lsVal = Score.getLgStraightValue();
			}

			if (zeroCount > 2 || pairCount > 2 || !smStraight) {
				ssVal = 0;
			} else {
				ssVal = Score.getSmStraightValue();
			}
		}
	}

	public static int getMaxRolls() {
		return MAX_ROLLS;
	}

	public static int getAcesVal() {
		return acesVal;
	}

	public static int getTwosVal() {
		return twosVal;
	}

	public static int getThreesVal() {
		return threesVal;
	}

	public static int getFoursVal() {
		return foursVal;
	}

	public static int getFivesVal() {
		return fivesVal;
	}

	public static int getSixesVal() {
		return sixesVal;
	}

	public static int getChanceVal() {
		return chanceVal;
	}

	public static boolean hasThreeOfAKind() {
		return threeOfAKind;
	}

	public static boolean hasFourOfAKind() {
		return fourOfAKind;
	}

	public static boolean hasFullHouse() {
		return fullHouse;
	}

	public static boolean hasSmStraight() {
		return smStraight;
	}

	public static boolean hasLgStraight() {
		return lgStraight;
	}

	public static boolean hasYacht() {
		return yacht;
	}

	public static int getTOAKVal() {
		return toakVal;
	}

	public static int getFOAKVal() {
		return foakVal;
	}

	public static int getFHVal() {
		return fhVal;
	}

	public static int getSSVal() {
		return ssVal;
	}

	public static int getLSVal() {
		return lsVal;
	}

	public static int getYachtVal() {
		return yachtVal;
	}

}
