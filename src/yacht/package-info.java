/**
 * <h1>Yacht</h1>
 * <p>
 * A variant on the traditional game that spawned Yahtzee. The rules of this
 * version are closer to those of Yahtzee than the original game of Yacht.
 * <p>
 * This is a one-player game, and can be played solitaire or two-player against
 * a computer opponent. The object of the two-player version is to beat the
 * computer's score. The object of a solitaire game is to get as close to a
 * perfect game as you can (375 points), while still scoring better than the
 * minimum game (258 points).
 * <p>
 * The minimum game score assumes scoring 63 points in the top half (minimum
 * points required to earn the 35-point bonus), and achieving the smallest score
 * above zero for each of the options in the bottom half (5 points for 3 of a
 * kind, 4 of a kind, and chance, and the specified value for full house, small
 * straight, large straight, and yacht). The maximum game assumes scoring 140
 * points (the most points possible, including the 35-point bonus) in the top
 * half, and the highest possible score for each option in the bottom half (30
 * points for 3 of a kind, 4 of a kind, and chance, and the specified value for
 * full house, large straight, and yacht).
 * <p>
 * Scores in the two-player version are evaluated against each other, and the
 * highest score wins. Scores in the solitaire version are expressed as a value
 * from 0 (258 points) to 117 (375) for games that meet or exceed the minimum,
 * and a value from -258 (0 points) to -1 (257 points) for games that fall short
 * of the minimum.
 * <p>
 * @version Alpha 2
 * @author PCGuyIV
 * @copyright Copyright (C) 2014 - PCGuyIV
 * @license GNU General Public License, Version 3 (GPLv3)
 * <p>
 * This file is part of Yacht.
 * <p>
 * Yacht is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * Yacht is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * Yacht.  If not, see {@link http://www.gnu.org/licenses/}.
 */
package src.yacht;