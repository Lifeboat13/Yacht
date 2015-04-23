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

public class Player {

	private Score   score;
	private boolean bot;
	private String  minOrMax;

	public Player() {
		initPlayer();
	}

	public Player(String type) {
		initPlayer(type);
	}

	public void initPlayer() {
		initPlayer("");
	}

	public void initPlayer(String type) {
		switch (type) {
			case "bot":
				score    = new Score();
				bot      = true;
				minOrMax = "";
				break;
			case "min":
			case "max":
				score    = new Score(type);
				bot      = false;
				minOrMax = type;
				break;
			default:
				score    = new Score();
				bot      = false;
				minOrMax = "";
				break;
		}
	}

	public Score getScore() {
		return score;
	}

	public boolean isBot() {
		return bot;
	}

	public String getScoreType() {
		return minOrMax;
	}

	public void resetScores() {
		if (minOrMax.equalsIgnoreCase("min")) {
			score.setScores(3, 1);
		} else if (minOrMax.equalsIgnoreCase("max")) {
			score.setScores(5, 6);
		} else {
			score.initScores();
		}
	}

	public void setBot(boolean bot) {
		this.bot = bot;
	}

	public void changeBot() {
		bot = !bot;
	}

	public void makeBot() {
		bot = true;
	}

	public void makeUser() {
		bot = false;
	}
}
