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

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JToggleButton;

public class Bot {

	private static boolean         needsSingleVal,   needsPairVal, needsLowPairVal;
	private static boolean         needsHighPairVal, needsTOAKVal, needsFOAKVal;
	private static boolean         needsYachtVal,    scored;
	private static boolean[]       needsDiceVals;
	private static int             single, pair, lowPair, highPair;
	private static int             toak,   foak, yacht,   cp;
	private static int[]           diceVals;
	private static JButton[]       lowerBtn = Yacht.window.getLowerScoreButtons();
	private static JButton[]       upperBtn = Yacht.window.getUpperScoreButtons();
	private static JToggleButton[] dice     = Yacht.window.getDiceButtons();
	private static Player[]        player   = Yacht.window.getPlayers();

	public static void play() {
		scored = false;
		cp     = Yacht.window.getCurrentPlayer();

		do {
			repaintButtons();
			pause();
			Yacht.window.getRollButton().doClick(250);
			pause();

			single = lowPair = highPair = toak = foak = yacht = 0;
			needsSingleVal = needsLowPairVal = needsHighPairVal = false;
			needsTOAKVal = needsFOAKVal = needsYachtVal = false;

			needsDiceVals = new boolean[] {false, false, false, false, false};

			diceVals = Arrays.copyOf(Game.DICE.getResults(), Game.DICE.getNumDice());

			Arrays.sort(diceVals);

			for (int i = 0; i < Game.DICE.getNumDice(); i++) {
				needsDiceVals[i] = needsVal(diceVals[i]);
			}


			for (int i = 0; i < Game.DICE.getSides(); i++) {
				switch (Game.getValCount()[i]) {
					case 5:
						single = lowPair = highPair = toak = foak = yacht = i + 1;
						needsYachtVal = needsVal(single);
						break;
					case 4:
						lowPair = toak = foak = i + 1;
						needsFOAKVal = needsVal(foak);
						break;
					case 3:
						if (lowPair == 0) {
							lowPair = toak = i + 1;
						} else {
							highPair = toak = i + 1;
						}

						needsTOAKVal = needsVal(toak);
						break;
					case 2:
						if (lowPair == 0) {
							lowPair = i + 1;
							needsLowPairVal = needsVal(lowPair);
						} else {
							highPair = i + 1;
							needsHighPairVal = needsVal(highPair);
						}
					case 1:
						single = i + 1;
						needsSingleVal = needsVal(single);
						break;
					default:
						break;
				}
			}

			if (lowPair > 0 && highPair == 0) {
				pair         = lowPair;
				needsPairVal = needsVal(pair);
			} else if (highPair > 0 && needsVal(highPair)) {
				pair         = highPair;
				needsPairVal = true;
			} else if (needsVal(lowPair)) {
				pair         = lowPair;
				needsPairVal = true;
			} else {
				pair         = 0;
				needsPairVal = false;
			}

			if (Game.hasYacht()) {
				if (player[cp].getScore().getYacht() < 0) {
					scored = true;
					lowerBtn[5].doClick(250);
					pause();
				} else if (needsYachtVal) {
					scored = true;
					pushUpperButton(yacht);
				} else if (Game.DICE.getTotal() > 25) {
					if (player[cp].getScore().getFourOfAKind() < 0) {
						scored = true;
						lowerBtn[1].doClick(250);
						pause();
					} else if (player[cp].getScore().getThreeOfAKind() < 0) {
						scored = true;
						lowerBtn[0].doClick(250);
						pause();
					} else if (player[cp].getScore().getChance() < 0) {
						scored = true;
						lowerBtn[6].doClick(250);
						pause();
					} else if (player[cp].getScore().getFullHouse() < 0) {
						scored = true;
						lowerBtn[2].doClick(250);
						pause();
					} else if (Yacht.window.getRollCount() > 0) {
						selectDice();
					} else {
						scored = true;
						zeroScore();
					}
				} else if (Game.DICE.getTotal() == 25) {
					if (player[cp].getScore().getFourOfAKind() < 0) {
						scored = true;
						lowerBtn[1].doClick(250);
						pause();
					} else if (player[cp].getScore().getFullHouse() < 0) {
						scored = true;
						lowerBtn[2].doClick(250);
						pause();
					} else if (player[cp].getScore().getThreeOfAKind() < 0) {
						scored = true;
						lowerBtn[0].doClick(250);
						pause();
					} else if (player[cp].getScore().getChance() < 0) {
						scored = true;
						lowerBtn[6].doClick(250);
						pause();
					} else if (Yacht.window.getRollCount() > 0) {
						selectDice();
					} else {
						scored = true;
						zeroScore();
					}
				} else {
					if (player[cp].getScore().getFullHouse() < 0) {
						scored = true;
						lowerBtn[2].doClick(250);
						pause();
					} else if (player[cp].getScore().getFourOfAKind() < 0) {
						scored = true;
						lowerBtn[1].doClick(250);
						pause();
					} else if (player[cp].getScore().getThreeOfAKind() < 0) {
						scored = true;
						lowerBtn[0].doClick(250);
						pause();
					} else if (Yacht.window.getRollCount() > 0) {
						selectDice();
					} else if (player[cp].getScore().getChance() < 0) {
						scored = true;
						lowerBtn[6].doClick(250);
						pause();
					} else {
						scored = true;
						zeroScore();
					}
				}
			} else if (Game.hasFourOfAKind()) {
				if (Yacht.window.getRollCount() > 0) {
					if (player[cp].getScore().getYacht()     < 0 || needsFOAKVal ||
						player[cp].getScore().getFullHouse() < 0) {
						selectDice("fourOfAKind");
					} else if (player[cp].getScore().getFourOfAKind() < 0) {
						if (single == 6) {
							scored = true;
							lowerBtn[1].doClick(250);
							pause();
						} else {
							selectDice("fourOfAKind");
						}
					} else if (needsSingleVal) {
						selectDice();
					}
				} else if (needsFOAKVal) {
					scored = true;
					pushUpperButton(foak);
				} else if (player[cp].getScore().getFourOfAKind() < 0) {
					scored = true;
					lowerBtn[1].doClick(250);
					pause();
				} else if (player[cp].getScore().getThreeOfAKind() < 0) {
					scored = true;
					lowerBtn[0].doClick(250);
					pause();
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[1].doClick(250);
					pause();
				} else if (needsSingleVal) {
					scored = true;
					pushUpperButton(single);
				} else {
					scored = true;
					zeroScore();
				}
			} else if (Game.hasFullHouse()) {
				needsPairVal = false;
				pair         = 0;

				if (toak > lowPair) {
					pair = lowPair;
				} else {
					pair = highPair;
				}

				needsPairVal = needsVal(pair);

				if (player[cp].getScore().getFullHouse() < 0) {
					scored = true;
					lowerBtn[2].doClick(250);
					pause();
				} else if (Yacht.window.getRollCount() > 0) {
					if (player[cp].getScore().getYacht()       < 0 || needsTOAKVal ||
						player[cp].getScore().getFourOfAKind() < 0) {
						selectDice("threeOfAKind");
					} else if (player[cp].getScore().getThreeOfAKind() < 0) {
						if (pair == 6) {
							scored = true;
							lowerBtn[0].doClick(250);
							pause();
						} else {
							selectDice("threeOfAKind");
						}
					} else if (needsPairVal) {
						selectDice("pair");
					} else {
						selectDice();
					}
				} else if (needsTOAKVal) {
					scored = true;
					pushUpperButton(toak);
				} else if (player[cp].getScore().getThreeOfAKind() < 0) {
					scored = true;
					lowerBtn[0].doClick(250);
					pause();
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
					pause();
				} else if (needsPairVal) {
					scored = true;
					pushUpperButton(pair);
				} else {
					scored = true;
					zeroScore();
				}
			} else if (Game.hasThreeOfAKind()) {
				int     lowSingle         = 0,     highSingle         = 0;
				boolean needsLowSingleVal = false, needsHighSingleVal = false;

				for (int i = 0; i < Game.DICE.getSides(); i++) {
					if (Game.getValCount()[i] == 1) {
						if (lowSingle == 0) {
							lowSingle         = i + 1;
							needsLowSingleVal = needsVal(lowSingle);
						} else {
							highSingle         = i + 1;
							needsHighSingleVal = needsVal(highSingle);
						}
					}
				}

				if (Yacht.window.getRollCount() > 0) {
					if (player[cp].getScore().getYacht()        < 0 || needsTOAKVal ||
						player[cp].getScore().getFourOfAKind()  < 0 ||
						player[cp].getScore().getFullHouse()    < 0 ||
						player[cp].getScore().getThreeOfAKind() < 0) {
						selectDice("threeOfAKind");
					} else {
						selectDice();
					}
				} else if (needsTOAKVal) {
					scored = true;
					pushUpperButton(toak);
				} else if (player[cp].getScore().getThreeOfAKind() < 0) {
					scored = true;
					lowerBtn[0].doClick(250);
					pause();
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
					pause();
				} else if (needsLowSingleVal) {
					scored = true;
					pushUpperButton(lowSingle);
				} else if (needsHighSingleVal) {
					scored = true;
					pushUpperButton(highSingle);
				} else {
					scored = true;
					zeroScore();
				}
			} else if (Game.hasLgStraight()) {
				if (player[cp].getScore().getLgStraight() < 0) {
					scored = true;
					lowerBtn[4].doClick(250);
					pause();
				} else if (player[cp].getScore().getSmStraight() < 0) {
					scored = true;
					lowerBtn[3].doClick(250);
					pause();
				} else if (Yacht.window.getRollCount() > 0) {
					selectDice();
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
					pause();
				} else {
					for (int i = 0; i < Game.DICE.getNumDice(); i++) {
						if (needsDiceVals[i]) {
							scored = true;
							pushUpperButton(diceVals[i]);
							break;
						}
					}

					if (!scored) {
						scored = true;
						zeroScore();
					}
				}
			} else if (Game.hasSmStraight()) {
				if (Yacht.window.getRollCount() > 0) {
					if (player[cp].getScore().getLgStraight() < 0) {
						selectDice("smStraight");
					} else if (player[cp].getScore().getSmStraight() < 0) {
						scored = true;
						lowerBtn[3].doClick(250);
						pause();
					} else if (needsPairVal) {
						selectDice("pair");
					} else {
						selectDice();
					}
				} else if (player[cp].getScore().getSmStraight() < 0) {
					scored = true;
					lowerBtn[3].doClick(250);
					pause();
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
					pause();
				} else if (needsPairVal) {
					scored = true;
					pushUpperButton(pair);
				} else {
					for (int i = 0; i < Game.DICE.getNumDice(); i++) {
						if (needsDiceVals[i]) {
							scored = true;
							pushUpperButton(diceVals[i]);
							break;
						}
					}

					if (!scored) {
						scored = true;
						zeroScore();
					}
				}
			} else if (highPair > 0) {
				if (Yacht.window.getRollCount() > 0) {
					if (player[cp].getScore().getYacht()        < 0 || needsHighPairVal ||
						player[cp].getScore().getFourOfAKind()  < 0 ||
						player[cp].getScore().getThreeOfAKind() < 0) {
						selectDice("highPair");
					} else if (player[cp].getScore().getFullHouse() < 0) {
						selectDice("twoPair");
					} else if (needsLowPairVal) {
						selectDice("lowPair");
					} else {
						selectDice();
					}
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
				} else if (needsLowPairVal) {
					scored = true;
					pushUpperButton(lowPair);
				} else if (needsHighPairVal) {
					pushUpperButton(highPair);
				} else if (needsSingleVal) {
					scored = true;
					pushUpperButton(single);
				} else {
					scored = true;
					zeroScore();
				}
			} else if (pair > 0) {
				if (Yacht.window.getRollCount() > 0) {
					if (player[cp].getScore().getYacht()        < 0 || needsPairVal ||
						player[cp].getScore().getFourOfAKind()  < 0 ||
						player[cp].getScore().getThreeOfAKind() < 0 ||
						player[cp].getScore().getFullHouse()    < 0) {
						selectDice("pair");
					} else {
						selectDice();
					}
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
				} else if (needsPairVal) {
					scored = true;
					pushUpperButton(pair);
				} else {
					for (int i = 0; i < Game.DICE.getNumDice(); i++) {
						if (needsDiceVals[i]) {
							scored = true;
							pushUpperButton(diceVals[i]);
							break;
						}
					}

					if (!scored) {
						scored = true;
						zeroScore();
					}
				}
			} else {
				if (Yacht.window.getRollCount() > 0) {
					selectDice();
				} else if (player[cp].getScore().getChance() < 0) {
					scored = true;
					lowerBtn[6].doClick(250);
				} else {
					for (int i = 0; i < Game.DICE.getNumDice(); i++) {
						if (needsDiceVals[i]) {
							scored = true;
							pushUpperButton(diceVals[i]);
							break;
						}
					}

					if (!scored) {
						scored = true;
						zeroScore();
					}
				}
			}

			repaintButtons();
		} while (Yacht.window.getRollCount() > 0 && !scored);

		repaintButtons();
	}

	public static void pause() {
		pause(1000);
	}

	private static void pause(long time) {
		long t1, t2;
		t2 = System.currentTimeMillis();

		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t2 < time);
	}

	private static void selectDice() {
		selectDice("");
	}

	private static void selectDice(String currHand) {
		repaintButtons();

		for (int i = 0; i < Game.DICE.getNumDice(); i++) {
			if (dice[i].isSelected()) {
				dice[i].doClick();
			}

			dice[i].setIcon(Yacht.window.getDieFace(Game.DICE.getResult(i)));
		}

		repaintButtons();

		switch (currHand) {
			case "smStraight":
				boolean ace   = false;
				boolean two   = false;
				boolean three = false;
				boolean four  = false;
				boolean five  = false;
				boolean six   = false;

				for (int i = 0; i < Game.DICE.getNumDice(); i++) {
					switch (Game.DICE.getResult(i)) {
						case 1:
							if (diceVals[0] == 1 && !ace) {
								ace = true;
								if (!dice[i].isSelected()) {
									dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
									dice[i].doClick(250);
									pause();
								}
							}

							break;
						case 2:
							if (diceVals[0] <= 2 && !two) {
								two = true;
								if (!dice[i].isSelected()) {
									dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
									dice[i].doClick(250);
									pause();
								}
							}

							break;
						case 3:
							if (!three) {
								three = true;
								if (!dice[i].isSelected()) {
									dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
									dice[i].doClick(250);
									pause();
								}
							}

							break;
						case 4:
							if (!four) {
								four = true;
								if (!dice[i].isSelected()) {
									dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
									dice[i].doClick(250);
									pause();
								}
							}

							break;
						case 5:
							if (diceVals[4] >= 5 && !five) {
								five = true;
								if (!dice[i].isSelected()) {
									dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
									dice[i].doClick(250);
									pause();
								}
							}
							break;
						case 6:
							if (diceVals[4] == 6 && !six) {
								six = true;
								if (!dice[i].isSelected()) {
									dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
									dice[i].doClick(250);
									pause();
								}
							}
							break;
						default:
							break;
					}
				}

				break;
			case "threeOfAKind":
			case "fourOfAKind":
			case "highPair":
			case "lowPair":
			case "pair":
				int val;

				switch (currHand) {
					case "threeOfAKind":
						val = toak;
						break;
					case "fourOfAKind":
						val = foak;
						break;
					case "highPair":
						val = highPair;
						break;
					case "lowPair":
						val = lowPair;
						break;
					default:
						val = pair;
						break;
				}

				for (int i = 0; i < Game.DICE.getNumDice(); i++) {
					if (Game.DICE.getResult(i) == val && !dice[i].isSelected()) {
						dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
						dice[i].doClick(250);
						pause();
					}
				}

				break;
			case "twoPair":
				for (int i = 0; i < Game.DICE.getNumDice(); i++) {
					if ((Game.DICE.getResult(i) == highPair || Game.DICE.getResult(i) == lowPair) && !dice[i].isSelected()) {
						dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
						dice[i].doClick(250);
						pause();
					}
				}

				break;
			default:
				int die = Game.DICE.getNumDice() - 1;

				single         = 0;
				needsSingleVal = false;

				for (int i = 0; i < Game.DICE.getNumDice(); i++) {
					if (needsDiceVals[die - i]) {
						single = diceVals[die - i];
						needsSingleVal = true;
						break;
					}
				}

				if (needsSingleVal) {
					for (int i = 0; i < Game.DICE.getNumDice(); i++) {
						if (Game.DICE.getResult(i) == single && !dice[i].isSelected()) {
							dice[i].setIcon(Yacht.window.getSDieFace(Game.DICE.getResult(i)));
							dice[i].doClick(250);
							pause();
						}
					}
				} else {
					for (int i = 0; i < Game.DICE.getNumDice(); i++) {
						if (dice[i].isSelected()) {
							dice[i].setIcon(Yacht.window.getDieFace(Game.DICE.getResult(i)));
							dice[i].doClick(250);
							pause();
						}
					}
				}

				break;
		}

		repaintButtons();
	}

	private static boolean needsVal(int val) {
		switch (val) {
			case 1:
				if (player[cp].getScore().getAces() < 0) {
					return true;
				} else {
					return false;
				}
			case 2:
				if (player[cp].getScore().getTwos() < 0) {
					return true;
				} else {
					return false;
				}
			case 3:
				if (player[cp].getScore().getThrees() < 0) {
					return true;
				} else {
					return false;
				}
			case 4:
				if (player[cp].getScore().getFours() < 0) {
					return true;
				} else {
					return false;
				}
			case 5:
				if (player[cp].getScore().getFives() < 0) {
					return true;
				} else {
					return false;
				}
			case 6:
				if (player[cp].getScore().getSixes() < 0) {
					return true;
				} else {
					return false;
				}
			default:
				return false;
		}
	}

	private static void pushUpperButton(int btnNum) {
		switch (btnNum) {
			case 1:
				upperBtn[0].doClick(250);
				pause();
				break;
			case 2:
				upperBtn[1].doClick(250);
				pause();
				break;
			case 3:
				upperBtn[2].doClick(250);
				pause();
				break;
			case 4:
				upperBtn[3].doClick(250);
				pause();
				break;
			case 5:
				upperBtn[4].doClick(250);
				pause();
				break;
			case 6:
				upperBtn[5].doClick(250);
				pause();
				break;
			default:
				break;
		}

		repaintButtons();
	}

	private static void zeroScore() {
		if (player[cp].getScore().getChance() < 0) {
			lowerBtn[6].doClick();
			pause();
		} else if (player[cp].getScore().getLgStraight() < 0) {
			lowerBtn[4].doClick(250);
			pause();
		} else if (player[cp].getScore().getYacht() < 0) {
			lowerBtn[5].doClick(250);
			pause();
		} else if (player[cp].getScore().getSmStraight() < 0) {
			lowerBtn[3].doClick(250);
			pause();
		} else if (player[cp].getScore().getFullHouse() < 0) {
			lowerBtn[2].doClick(250);
			pause();
		} else if (player[cp].getScore().getFourOfAKind() < 0) {
			lowerBtn[1].doClick(250);
			pause();
		} else if (player[cp].getScore().getThreeOfAKind() < 0) {
			lowerBtn[0].doClick(250);
			pause();
		} else if (player[cp].getScore().getAces() < 0) {
			upperBtn[0].doClick(250);
			pause();
		} else if (player[cp].getScore().getTwos() < 0) {
			upperBtn[1].doClick(250);
			pause();
		} else if (player[cp].getScore().getThrees() < 0) {
			upperBtn[1].doClick(250);
			pause();
		} else if (player[cp].getScore().getFours() < 0) {
			upperBtn[1].doClick(250);
			pause();
		} else if (player[cp].getScore().getFives() < 0) {
			upperBtn[1].doClick(250);
			pause();
		} else {
			upperBtn[1].doClick(250);
			pause();
		}

		repaintButtons();
	}

	public static void repaintButtons() {
		Yacht.window.getFrame().repaint();

		for (int i = 0; i < lowerBtn.length; i++) {
			if (i < upperBtn.length) {
				upperBtn[i].repaint();
			}

			if (i < dice.length) {
				dice[i].repaint();
			}

			lowerBtn[i].repaint();
		}
	}
}
