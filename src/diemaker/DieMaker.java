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
package diemaker;

import java.util.Random;

/**
 * Creates an object used to simulate a single die.
 *
 * @version Alpha 2
 * @author PCGuyIV
 */
public class DieMaker {

	private static final int DEFAULT_SIDES     = 6;
	private static final int DEFAULT_MIN_VAL   = 1;
	private static final int DEFAULT_START_VAL = DEFAULT_SIDES;

	private final Random RNG = new Random(System.nanoTime());

	private int sides, min, max, result;

	public DieMaker() {
		initDie();
	}

	public DieMaker(int sides) {
		initDie(sides);
	}

	public DieMaker(int sides, int min) {
		initDie(sides, min);
	}

	public DieMaker(int sides, int min, int start) {
		initDie(sides, min, start);
	}

	public void initDie() {
		initDie(DEFAULT_SIDES, DEFAULT_MIN_VAL, DEFAULT_START_VAL);
	}

	public void initDie(int sides) {
		initDie(sides, DEFAULT_MIN_VAL, DEFAULT_START_VAL);
	}

	public void initDie(int sides, int min) {
		initDie(sides, min, DEFAULT_START_VAL);
	}

	public void initDie(int sides, int min, int start) {
		this.sides = sides;
		this.min   = min;
		max   = sides + min - 1;
		result     = start;
	}

	public static int getDefaultSides() {
		return DEFAULT_SIDES;
	}

	public static int getDefaultMinVal() {
		return DEFAULT_MIN_VAL;
	}

	public static int getDefaultStartVal() {
		return DEFAULT_START_VAL;
	}

	public int getSides() {
		return sides;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getResult() {
		return result;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	public void setMinAndMax(int min) {
		this.min = min;
		max      = sides + min - 1;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int roll() {
		result = RNG.nextInt(sides) + min;
		return result;
	}

	@Override
	public String toString() {
		return Integer.toString(result);
	}

}
