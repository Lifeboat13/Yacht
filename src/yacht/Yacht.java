package yacht;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

/**
 * This is the primary class for the game.
 * <p>
 * Creates the main game window.
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
public class Yacht {

	// Game Images
	private final ImageIcon   yachtIcon = new ImageIcon(this.getClass().getResource("images/yacht.png"));
	private final ImageIcon[] dieFaces  = {
		new ImageIcon(this.getClass().getResource("images/0.png")),
		new ImageIcon(this.getClass().getResource("images/1.png")),
		new ImageIcon(this.getClass().getResource("images/2.png")),
		new ImageIcon(this.getClass().getResource("images/3.png")),
		new ImageIcon(this.getClass().getResource("images/4.png")),
		new ImageIcon(this.getClass().getResource("images/5.png")),
		new ImageIcon(this.getClass().getResource("images/6.png"))
	};
	private final ImageIcon[] sDieFaces = {
		new ImageIcon(this.getClass().getResource("images/0_selected.png")),
		new ImageIcon(this.getClass().getResource("images/1_selected.png")),
		new ImageIcon(this.getClass().getResource("images/2_selected.png")),
		new ImageIcon(this.getClass().getResource("images/3_selected.png")),
		new ImageIcon(this.getClass().getResource("images/4_selected.png")),
		new ImageIcon(this.getClass().getResource("images/5_selected.png")),
		new ImageIcon(this.getClass().getResource("images/6_selected.png"))
	};

	private ButtonGroup          playerOptGroup;
	private JButton              newGameButton;
	private JButton              rollButton;
	private JButton[]            upperScoreButton = new JButton[6];
	private JButton[]            lowerScoreButton = new JButton[7];
	private JFrame               frame;
	private JLabel[]             scoreHeader      = new JLabel[5];
	private JLabel[]             upperScoreLabel  = new JLabel[9];
	private JLabel[]             lowerScoreLabel  = new JLabel[10];
	private JLabel[][]           upperScoreValue  = new JLabel[4][9];
	private JLabel[][]           lowerScoreValue  = new JLabel[4][10];
	private JMenu                gameMenu;
	private JMenu                helpMenu;
	private JMenu                optionsMenu;
	private JMenuBar             menuBar;
	private JMenuItem            aboutItem;
	private JMenuItem            exitItem;
	private JMenuItem            newGameItem;
	private JMenuItem            rulesItem;
	private JPanel               dicePanel;
	private JPanel               scoreButtonPanel;
	private JPanel               scorePanel;
	private JRadioButtonMenuItem optSinglePlayer;
	private JRadioButtonMenuItem optTwoPlayer;
	private JSeparator           gameSeparator;
	private JSeparator           helpSeparator;
	private JToggleButton[]      dice             = new JToggleButton[Game.DICE.getNumDice()];

	private boolean[] diceList;
	private int       rollCount;
	private Player[]  player = {Game.PLAYER, Game.BOT, Game.MIN, Game.MAX};
	private int       currentPlayer;
	private boolean   singlePlayer;
	private int       turnCount;

	private final int      NUM_TURNS         = 13;
	private	final String[] UPPER_BUTTON_TEXT = {
		"Aces", "Twos", "Threes","Fours", "Fives", "Sixes"
	};
	private final String[] LOWER_BUTTON_TEXT = {
		"3 of a Kind", "4 of a Kind", "Full House",
		"Sm. Straight", "Lg. Straight", "Yacht", "Chance"
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Set up the game
		new Game();

		// Set the look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.out.println("System interface not available.");
		}

		// Run the game
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Yacht window = new Yacht();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Yacht() {
		rollCount     = Game.getMaxRolls();
		currentPlayer = 0;
		singlePlayer  = true;
		turnCount     = NUM_TURNS;
		initialize();
		updateScoreSheet(2);
		updateScoreSheet(3);
	}

	private void newGame() {
		newGame(singlePlayer);
	}

	private void newGame(boolean singlePlayer) {
		currentPlayer     = 0;
		this.singlePlayer = singlePlayer;
		turnCount         = NUM_TURNS;
		resetDicePanel();
		resetRollButton();

		scoreHeader[4].setText("");

		for (int i = 0; i < player.length; i++) {
			player[i].resetScores();

			for (int j = 0; j < 10; j++) {
				if (j < 9) {
					upperScoreValue[i][j].setText("");
				}

				lowerScoreValue[i][j].setText("");
			}
		}

		updateScoreSheet(2);
		updateScoreSheet(3);

		if (singlePlayer) {
			scoreHeader[1].setEnabled(false);
			scoreHeader[2].setEnabled(true);
			scoreHeader[3].setEnabled(true);

			for (int i = 0; i < 10; i++) {
				if (i < 9) {
					upperScoreValue[1][i].setEnabled(false);
					upperScoreValue[2][i].setEnabled(true);
					upperScoreValue[3][i].setEnabled(true);
				}

				lowerScoreValue[1][i].setEnabled(false);
				lowerScoreValue[2][i].setEnabled(true);
				lowerScoreValue[3][i].setEnabled(true);
			}
		} else {
			scoreHeader[1].setEnabled(true);
			scoreHeader[2].setEnabled(false);
			scoreHeader[3].setEnabled(false);

			for (int i = 0; i < 10; i++) {
				if (i < 9) {
					upperScoreValue[1][i].setEnabled(true);
					upperScoreValue[2][i].setEnabled(false);
					upperScoreValue[3][i].setEnabled(false);
				}

				lowerScoreValue[1][i].setEnabled(true);
				lowerScoreValue[2][i].setEnabled(false);
				lowerScoreValue[3][i].setEnabled(false);
			}
		}

		resetScoreButtons();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Yacht");
		frame.setResizable(false);
		frame.setBounds(100, 100, 475, 522);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(yachtIcon.getImage());
		frame.getContentPane().setLayout(null);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('g');
		menuBar.add(gameMenu);

		newGameItem = new JMenuItem("New Game");
		newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		newGameItem.setMnemonic('n');
		newGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		gameMenu.add(newGameItem);

		gameSeparator = new JSeparator();
		gameMenu.add(gameSeparator);

		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		gameMenu.add(exitItem);

		optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic('o');
		menuBar.add(optionsMenu);

		playerOptGroup = new ButtonGroup();

		optSinglePlayer = new JRadioButtonMenuItem("Solitaire");
		optSinglePlayer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK));
		optSinglePlayer.setMnemonic('s');
		optSinglePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame(true);
			}
		});
		optionsMenu.add(optSinglePlayer);
		playerOptGroup.add(optSinglePlayer);

		optTwoPlayer = new JRadioButtonMenuItem("Human vs. PC");
		optTwoPlayer.setMnemonic('h');
		optTwoPlayer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_MASK));
		optTwoPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame(false);
			}
		});
		optionsMenu.add(optTwoPlayer);
		playerOptGroup.add(optTwoPlayer);

		optSinglePlayer.setSelected(true);

		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		rulesItem = new JMenuItem("Rules");
		rulesItem.setMnemonic('r');
		rulesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		rulesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame.getRootPane(), "<html><h1>Rules</h1><ul><li>Roll the dice up to 3 times per turn to get the best hand possible.</li><li>Click the button for where you want to score your turn.</li><li>Once turns are complete, your final score is evaluated.<ul><li><b>Solitaire:</b> Your final score is expressed as a negative number if<br>you score less than the minimum hand, represented as Even<br>if you score the same as the minimum hand, and shown as a<br>percentage of the maximum if you score above the minimum hand.</li><li><b>Human vs. PC</b>: The player (You or Bot) with the higher score wins.</li></ul></li></ul>", "Yacht Rules", JOptionPane.PLAIN_MESSAGE);
			}
		});
		helpMenu.add(rulesItem);

		helpSeparator = new JSeparator();
		helpMenu.add(helpSeparator);

		aboutItem = new JMenuItem("About");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
		aboutItem.setMnemonic('a');
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame.getRootPane(), "<html><center><h1>Yacht</h1><h3>Alpha Release</h3><h3>Author: PCGuyIV</h3><br><p>Licensed under GNU General Public License ver. 3</p>", "About Yacht", JOptionPane.PLAIN_MESSAGE);
			}
		});
		helpMenu.add(aboutItem);

		int x      =  0;
		int y      =  0;
		int width  = 64;
		int height = 64;
		int gap    =  5;
		int pWidth = width * Game.DICE.getNumDice() + gap * (Game.DICE.getNumDice() - 1);

		dicePanel = new JPanel();
		dicePanel.setBounds(10, 10, pWidth, height);
		dicePanel.setLayout(null);
		frame.getContentPane().add(dicePanel);

		for (int i = 0; i < Game.DICE.getNumDice(); i++) {
			dice[i] = new JToggleButton("");
			dice[i].setIcon(dieFaces[Game.DICE.getResult(i)]);
			dice[i].setBounds(x, y, width, height);
			dice[i].setEnabled(false);
			switch (i) {
				case 0:
					dice[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent ev) {
							if (ev.getStateChange() == ItemEvent.SELECTED) {
								dice[0].setIcon(sDieFaces[Game.DICE.getResult(0)]);
							} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
								dice[0].setIcon(dieFaces[Game.DICE.getResult(0)]);
							}
						}
					});
					break;
				case 1:
					dice[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent ev) {
							if (ev.getStateChange() == ItemEvent.SELECTED) {
								dice[1].setIcon(sDieFaces[Game.DICE.getResult(1)]);
							} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
								dice[1].setIcon(dieFaces[Game.DICE.getResult(1)]);
							}
						}
					});
					break;
				case 2:
					dice[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent ev) {
							if (ev.getStateChange() == ItemEvent.SELECTED) {
								dice[2].setIcon(sDieFaces[Game.DICE.getResult(2)]);
							} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
								dice[2].setIcon(dieFaces[Game.DICE.getResult(2)]);
							}
						}
					});
					break;
				case 3:
					dice[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent ev) {
							if (ev.getStateChange() == ItemEvent.SELECTED) {
								dice[3].setIcon(sDieFaces[Game.DICE.getResult(3)]);
							} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
								dice[3].setIcon(dieFaces[Game.DICE.getResult(3)]);
							}
						}
					});
					break;
				case 4:
					dice[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent ev) {
							if (ev.getStateChange() == ItemEvent.SELECTED) {
								dice[4].setIcon(sDieFaces[Game.DICE.getResult(4)]);
							} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
								dice[4].setIcon(dieFaces[Game.DICE.getResult(4)]);
							}
						}
					});
					break;
				default:
					dice[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent ev) {
							if (ev.getStateChange() == ItemEvent.SELECTED) {
								dice[0].setIcon(sDieFaces[0]);
							} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
								dice[0].setIcon(dieFaces[0]);
							}
						}
					});
					break;
			}
			dicePanel.add(dice[i]);
			x += width + gap;
		}

		rollButton = new JButton(Integer.toString(rollCount) + " Rolls");
		rollButton.setToolTipText("Roll them bones!");
		rollButton.setBounds(355, 10, 101, 64);
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Vector<Integer> diceList = new Vector<Integer>();
				boolean[]       rollList = updateDicePanel();

				diceList.removeAllElements();

				for (int i = 0; i < Game.DICE.getNumDice(); i++) {
					if (rollList[i]) {
						diceList.addElement(i);
					}
				}

				int[] dieList = new int[diceList.size()];

				for (int i = 0; i < diceList.size(); i++) {
					dieList[i] = diceList.elementAt(i);
				}

				Game.DICE.rollDice(dieList);
				rollList = updateDicePanel();
				Game.evaluateRoll();
				rollCount--;
				if (rollCount > 1 || rollCount < 1) {
					rollButton.setText(Integer.toString(rollCount) + " Rolls");
					if (rollCount < 1) {
						rollButton.setEnabled(false);
					}
				} else if (rollCount == 1) {
					rollButton.setText(Integer.toString(rollCount) + " Roll");
				}

				updateScoreButtons();
			}
		});
		frame.getContentPane().add(rollButton);

		scoreButtonPanel = new JPanel();
		scoreButtonPanel.setBounds(10, 80, 190, 340);
		frame.getContentPane().add(scoreButtonPanel);
		scoreButtonPanel.setLayout(null);

		x      =   0;
		y      =   0;
		width  = 190;
		height =  25;

		for (int i = 0; i < 6; i++) {
			upperScoreButton[i] = new JButton(UPPER_BUTTON_TEXT[i]);
			upperScoreButton[i].setEnabled(false);
			upperScoreButton[i].setBounds(x, y, width, height);
			scoreButtonPanel.add(upperScoreButton[i]);
			y += height;
		}

		y += 5;
		for (int i = 0; i < 7; i++) {
			lowerScoreButton[i] = new JButton(LOWER_BUTTON_TEXT[i]);
			lowerScoreButton[i].setEnabled(false);
			lowerScoreButton[i].setBounds(x, y, width, height);
			scoreButtonPanel.add(lowerScoreButton[i]);
			y += height;
		}

		upperScoreButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setAces(Game.getAcesVal());
				nextTurn();
			}
		});
		upperScoreButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setTwos(Game.getTwosVal());
				nextTurn();
			}
		});
		upperScoreButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setThrees(Game.getThreesVal());
				nextTurn();
			}
		});
		upperScoreButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setFours(Game.getFoursVal());
				nextTurn();
			}
		});
		upperScoreButton[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setFives(Game.getFivesVal());
				nextTurn();
			}
		});
		upperScoreButton[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setSixes(Game.getSixesVal());
				nextTurn();
			}
		});

		lowerScoreButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setThreeOfAKind(Game.getTOAKVal());
				nextTurn();
			}
		});
		lowerScoreButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setFourOfAKind(Game.getFOAKVal());
				nextTurn();
			}
		});
		lowerScoreButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setFullHouse(Game.hasFullHouse());
				nextTurn();
			}
		});
		lowerScoreButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setSmStraight(Game.hasSmStraight());
				nextTurn();
			}
		});
		lowerScoreButton[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setLgStraight(Game.hasLgStraight());
				nextTurn();
			}
		});
		lowerScoreButton[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setYacht(Game.hasYacht());
				nextTurn();
			}
		});
		lowerScoreButton[6].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player[currentPlayer].getScore().setChance(Game.getChanceVal());
				nextTurn();
			}
		});

		scorePanel = new JPanel();
		scorePanel.setBounds(210, 80, 250, 395);
		frame.getContentPane().add(scorePanel);
		scorePanel.setLayout(null);

		x      = 94;
		y      =  0;
		width  = 39;
		height = 20;
		scoreHeader[0] = new JLabel("You");
		scoreHeader[0].setBounds(x, y, width, height);
		scoreHeader[0].setBorder(new LineBorder(new Color(0, 0, 0)));
		scoreHeader[0].setHorizontalAlignment(SwingConstants.CENTER);
		scoreHeader[0].setVerticalAlignment(SwingConstants.CENTER);
		scorePanel.add(scoreHeader[0]);

		x += width - 1;
		scoreHeader[1] = new JLabel("Bot");
		if (singlePlayer) {
			scoreHeader[1].setEnabled(false);
		}
		scoreHeader[1].setBounds(x, y, width, height);
		scoreHeader[1].setBorder(new LineBorder(new Color(0, 0, 0)));
		scoreHeader[1].setHorizontalAlignment(SwingConstants.CENTER);
		scoreHeader[1].setVerticalAlignment(SwingConstants.CENTER);
		scorePanel.add(scoreHeader[1]);

		x += width - 1;
		scoreHeader[2] = new JLabel("Min");
		if (!singlePlayer) {
			scoreHeader[2].setEnabled(false);
		}
		scoreHeader[2].setBounds(x, y, width, height);
		scoreHeader[2].setBorder(new LineBorder(new Color(0, 0, 0)));
		scoreHeader[2].setHorizontalAlignment(SwingConstants.CENTER);
		scoreHeader[2].setVerticalAlignment(SwingConstants.CENTER);
		scorePanel.add(scoreHeader[2]);

		x += width - 1;
		scoreHeader[3] = new JLabel("Max");
		if (!singlePlayer) {
			scoreHeader[3].setEnabled(false);
		}
		scoreHeader[3].setBounds(x, y, width, height);
		scoreHeader[3].setBorder(new LineBorder(new Color(0, 0, 0)));
		scoreHeader[3].setHorizontalAlignment(SwingConstants.CENTER);
		scoreHeader[3].setVerticalAlignment(SwingConstants.CENTER);
		scorePanel.add(scoreHeader[3]);

		x      =  0;
		width  = 95;
		scoreHeader[4] = new JLabel();
		scoreHeader[4].setBackground(new Color(255, 255, 255));
		scoreHeader[4].setOpaque(true);
		scoreHeader[4].setForeground(new Color(255, 0, 0));
		scoreHeader[4].setBorder(new LineBorder(new Color(0, 0, 0)));
		scoreHeader[4].setHorizontalAlignment(SwingConstants.CENTER);
		scoreHeader[4].setVerticalAlignment(SwingConstants.CENTER);
		scoreHeader[4].setBounds(x, y, width, height);
		scorePanel.add(scoreHeader[4]);

		y      = 19;
		height = 20;

		for (int i = 0; i < 9; i++) {
			switch (i) {
				case 6:
					upperScoreLabel[i] = new JLabel(" Upper Subtotal:");
					break;
				case 7:
					upperScoreLabel[i] = new JLabel(" 63+ BONUS (" + Integer.toString(Score.getBonusValue()) + "):");
					break;
				case 8:
					upperScoreLabel[i] = new JLabel(" Upper Total:");
					break;
				default:
					upperScoreLabel[i] = new JLabel(" " + UPPER_BUTTON_TEXT[i] + ":");
					break;
			}
			upperScoreLabel[i].setFont(new Font("Dialog", Font.PLAIN, 10));
			upperScoreLabel[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			upperScoreLabel[i].setBounds(x, y, width, height);
			upperScoreLabel[i].setVerticalAlignment(SwingConstants.CENTER);
			scorePanel.add(upperScoreLabel[i]);
			y += height - 1;
		}

		y += 5;

		for (int i = 0; i < 10; i++) {
			switch (i) {
				case 7:
					lowerScoreLabel[i] = new JLabel(" Lower Total:");
					break;
				case 8:
					lowerScoreLabel[i] = new JLabel(" Upper Total:");
					break;
				case 9:
					lowerScoreLabel[i] = new JLabel(" Grand Total:");
					break;
				default:
					lowerScoreLabel[i] = new JLabel(" " + LOWER_BUTTON_TEXT[i] + ":");
			}
			lowerScoreLabel[i].setFont(new Font("Dialog", Font.PLAIN, 10));
			lowerScoreLabel[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			lowerScoreLabel[i].setBounds(x, y, width, height);
			lowerScoreLabel[i].setVerticalAlignment(SwingConstants.CENTER);
			scorePanel.add(lowerScoreLabel[i]);
			y += height - 1;
		}

		x      = 94;
		width  = 39;
		height = 20;

		for (int i = 0; i < 4; i++) {
			y = 19;

			for (int j = 0; j < 9; j++) {
				upperScoreValue[i][j] = new JLabel();
				upperScoreValue[i][j].setBorder(new LineBorder(new Color(0, 0, 0)));
				upperScoreValue[i][j].setBackground(new Color(255, 255, 255));
				upperScoreValue[i][j].setOpaque(true);
				upperScoreValue[i][j].setBounds(x, y, width, height);
				upperScoreValue[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				upperScoreValue[i][j].setVerticalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					upperScoreValue[i][j].setEnabled(false);
				}
				scorePanel.add(upperScoreValue[i][j]);
				y += height - 1;
			}

			y += 5;

			for (int j = 0; j < 10; j++) {
				lowerScoreValue[i][j] = new JLabel();
				lowerScoreValue[i][j].setBorder(new LineBorder(new Color(0, 0, 0)));
				lowerScoreValue[i][j].setBackground(new Color(255, 255, 255));
				lowerScoreValue[i][j].setOpaque(true);
				lowerScoreValue[i][j].setBounds(x, y, width, height);
				lowerScoreValue[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				lowerScoreValue[i][j].setVerticalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					lowerScoreValue[i][j].setEnabled(false);
				}
				scorePanel.add(lowerScoreValue[i][j]);
				y += height - 1;
			}

			x += width - 1;
		}

		newGameButton = new JButton("New Game");
		newGameButton.setBounds(10, 420, 190, 45);
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		frame.getContentPane().add(newGameButton);

	}

	private boolean[] updateDicePanel() {
		diceList = new boolean[Game.DICE.getNumDice()];

		for (int i = 0; i < Game.DICE.getNumDice(); i++) {
			if (!dice[i].isEnabled()) {
				dice[i].setEnabled(true);
			}

			if (dice[i].isSelected()) {
				diceList[i] = false;
				dice[i].setIcon(sDieFaces[Game.DICE.getResult(i)]);
			} else {
				diceList[i] = true;
				dice[i].setIcon(dieFaces[Game.DICE.getResult(i)]);
			}
		}

		return diceList;
	}

	private void resetDicePanel() {
		for (int i = 0; i < Game.DICE.getNumDice(); i++) {
			if (dice[i].isSelected()) {
				dice[i].doClick();
			}

			dice[i].setEnabled(false);
		}
	}

	private void resetRollButton() {
		rollCount = Game.getMaxRolls();
		rollButton.setEnabled(true);
		rollButton.setText(Integer.toString(rollCount) + " Rolls");
	}

	private void nextTurn() {
		player[currentPlayer].getScore().updateTotals();
		updateScoreSheet();

		if (!singlePlayer && currentPlayer == 0) {
			currentPlayer = 1;
		} else if (!singlePlayer) {
			currentPlayer = 0;
			turnCount--;
		} else {
			turnCount--;
		}

		for (int i = 0; i < Game.DICE.getNumDice(); i++) {

			if (dice[i].isSelected()) {
				dice[i].doClick();
			}

			dice[i].setEnabled(false);
		}

		resetScoreButtons();

		if (turnCount < 1) {
			showGameResults();
		} else {
			resetRollButton();
		}
	}

	private void resetScoreButtons() {
		for (int i = 0; i < 6; i++) {
			upperScoreButton[i].setEnabled(false);
			upperScoreButton[i].setText(UPPER_BUTTON_TEXT[i]);
			lowerScoreButton[i].setEnabled(false);
			lowerScoreButton[i].setText(LOWER_BUTTON_TEXT[i]);
		}

		lowerScoreButton[6].setEnabled(false);
		lowerScoreButton[6].setText(LOWER_BUTTON_TEXT[6]);
	}

	private void updateScoreButtons() {
		if (player[currentPlayer].getScore().getAces() < 0) {
			upperScoreButton[0].setEnabled(true);
			upperScoreButton[0].setText(UPPER_BUTTON_TEXT[0] + ": " + Integer.toString(Game.getAcesVal()));
		} else {
			upperScoreButton[0].setEnabled(false);
			upperScoreButton[0].setText(UPPER_BUTTON_TEXT[0]);
		}

		if (player[currentPlayer].getScore().getTwos() < 0) {
			upperScoreButton[1].setEnabled(true);
			upperScoreButton[1].setText(UPPER_BUTTON_TEXT[1] + ": " + Integer.toString(Game.getTwosVal()));
		} else {
			upperScoreButton[1].setEnabled(false);
			upperScoreButton[1].setText(UPPER_BUTTON_TEXT[1]);
		}

		if (player[currentPlayer].getScore().getThrees() < 0) {
			upperScoreButton[2].setEnabled(true);
			upperScoreButton[2].setText(UPPER_BUTTON_TEXT[2] + ": " + Integer.toString(Game.getThreesVal()));
		} else {
			upperScoreButton[2].setEnabled(false);
			upperScoreButton[2].setText(UPPER_BUTTON_TEXT[2]);
		}

		if (player[currentPlayer].getScore().getFours() < 0) {
			upperScoreButton[3].setEnabled(true);
			upperScoreButton[3].setText(UPPER_BUTTON_TEXT[3] + ": " + Integer.toString(Game.getFoursVal()));
		} else {
			upperScoreButton[3].setEnabled(false);
			upperScoreButton[3].setText(UPPER_BUTTON_TEXT[3]);
		}

		if (player[currentPlayer].getScore().getFives() < 0) {
			upperScoreButton[4].setEnabled(true);
			upperScoreButton[4].setText(UPPER_BUTTON_TEXT[4] + ": " + Integer.toString(Game.getFivesVal()));
		} else {
			upperScoreButton[4].setEnabled(false);
			upperScoreButton[4].setText(UPPER_BUTTON_TEXT[4]);
		}

		if (player[currentPlayer].getScore().getSixes() < 0) {
			upperScoreButton[5].setEnabled(true);
			upperScoreButton[5].setText(UPPER_BUTTON_TEXT[5] + ": " + Integer.toString(Game.getSixesVal()));
		} else {
			upperScoreButton[5].setEnabled(false);
			upperScoreButton[5].setText(UPPER_BUTTON_TEXT[5]);
		}

		if (player[currentPlayer].getScore().getThreeOfAKind() < 0) {
			lowerScoreButton[0].setEnabled(true);
			lowerScoreButton[0].setText(LOWER_BUTTON_TEXT[0] + ": " + Integer.toString(Game.getTOAKVal()));
		} else {
			lowerScoreButton[0].setEnabled(false);
			lowerScoreButton[0].setText(LOWER_BUTTON_TEXT[0]);
		}

		if (player[currentPlayer].getScore().getFourOfAKind() < 0) {
			lowerScoreButton[1].setEnabled(true);
			lowerScoreButton[1].setText(LOWER_BUTTON_TEXT[1] + ": " + Integer.toString(Game.getFOAKVal()));
		} else {
			lowerScoreButton[1].setEnabled(false);
			lowerScoreButton[1].setText(LOWER_BUTTON_TEXT[1]);
		}

		if (player[currentPlayer].getScore().getFullHouse() < 0) {
			lowerScoreButton[2].setEnabled(true);
			lowerScoreButton[2].setText(LOWER_BUTTON_TEXT[2] + ": " + Integer.toString(Game.getFHVal()));
		} else {
			lowerScoreButton[2].setEnabled(false);
			lowerScoreButton[2].setText(LOWER_BUTTON_TEXT[2]);
		}

		if (player[currentPlayer].getScore().getSmStraight() < 0) {
			lowerScoreButton[3].setEnabled(true);
			lowerScoreButton[3].setText(LOWER_BUTTON_TEXT[3] + ": " + Integer.toString(Game.getSSVal()));
		} else {
			lowerScoreButton[3].setEnabled(false);
			lowerScoreButton[3].setText(LOWER_BUTTON_TEXT[3]);
		}

		if (player[currentPlayer].getScore().getLgStraight() < 0) {
			lowerScoreButton[4].setEnabled(true);
			lowerScoreButton[4].setText(LOWER_BUTTON_TEXT[4] + ": " + Integer.toString(Game.getLSVal()));
		} else {
			lowerScoreButton[4].setEnabled(false);
			lowerScoreButton[4].setText(LOWER_BUTTON_TEXT[4]);
		}

		if (player[currentPlayer].getScore().getYacht() < 0) {
			lowerScoreButton[5].setEnabled(true);
			lowerScoreButton[5].setText(LOWER_BUTTON_TEXT[5] + ": " + Integer.toString(Game.getYachtVal()));
		} else {
			lowerScoreButton[5].setEnabled(false);
			lowerScoreButton[5].setText(LOWER_BUTTON_TEXT[5]);
		}

		if (player[currentPlayer].getScore().getChance() < 0) {
			lowerScoreButton[6].setEnabled(true);
			lowerScoreButton[6].setText(LOWER_BUTTON_TEXT[6] + ": " + Integer.toString(Game.getChanceVal()));
		} else {
			lowerScoreButton[6].setEnabled(false);
			lowerScoreButton[6].setText(LOWER_BUTTON_TEXT[6]);
		}
	}

	private void updateScoreSheet() {
		updateScoreSheet(currentPlayer);
	}

	private void updateScoreSheet(int playerNum) {
		player[playerNum].getScore().updateTotals();

		int[] uScore = {
			player[playerNum].getScore().getAces(),
			player[playerNum].getScore().getTwos(),
			player[playerNum].getScore().getThrees(),
			player[playerNum].getScore().getFours(),
			player[playerNum].getScore().getFives(),
			player[playerNum].getScore().getSixes(),
			player[playerNum].getScore().getUpperSubtotal(),
			player[playerNum].getScore().getBonus(),
			player[playerNum].getScore().getUpperTotal()
		};
		int[] lScore = {
			player[playerNum].getScore().getThreeOfAKind(),
			player[playerNum].getScore().getFourOfAKind(),
			player[playerNum].getScore().getFullHouse(),
			player[playerNum].getScore().getSmStraight(),
			player[playerNum].getScore().getLgStraight(),
			player[playerNum].getScore().getYacht(),
			player[playerNum].getScore().getChance(),
			player[playerNum].getScore().getLowerTotal(),
			player[playerNum].getScore().getUpperTotal(),
			player[playerNum].getScore().getGrandTotal()
		};

		for (int i = 0; i < 10; i++) {
			if (i < 9) {
				if (uScore[i] >= 0) {
					upperScoreValue[playerNum][i].setText(Integer.toString(uScore[i]));
				} else {
					upperScoreValue[playerNum][i].setText("");
				}
			}

			if (lScore[i] >= 0) {
				lowerScoreValue[playerNum][i].setText(Integer.toString(lScore[i]));
			} else {
				lowerScoreValue[playerNum][i].setText("");
			}
		}

	}

	private void showGameResults() {
		if (singlePlayer) {
			if (player[0].getScore().getGrandTotal() < player[2].getScore().getGrandTotal()) {
				scoreHeader[4].setText(Integer.toString(player[0].getScore().getGrandTotal() - player[2].getScore().getGrandTotal()));
			} else if (player[0].getScore().getGrandTotal() > player[2].getScore().getGrandTotal()) {
				scoreHeader[4].setText(Integer.toString(Math.round(100 * (player[0].getScore().getGrandTotal() / player[3].getScore().getGrandTotal()))));
			} else {
				scoreHeader[4].setText("Even");
			}
		} else if (player[0].getScore().getGrandTotal() > player[1].getScore().getGrandTotal()) {
			scoreHeader[4].setText("You win!");
		} else {
			scoreHeader[4].setText("Bot won.");
		}

		JOptionPane.showMessageDialog(frame.getRootPane(), "<html><center><h3>Final Result</h3></center><center><h1>" + scoreHeader[4].getText() + "</h1></center>", "Game Over", JOptionPane.PLAIN_MESSAGE);

	}
}
