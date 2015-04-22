ABOUT YACHT:
------------------------------------------------------------------------
Yacht is a dice game derived from poker dice, and is the basis for the
commercial game, Yahtzee. This game takes it's name from the original
game, but has scoring that more closely resembles that of it's proginy.

This version of yacht has two modes: Solitaire and Human vs. PC. Both
versions are intended for a single user (human player).

In Solitaire mode, the objective is to get as high a score as possible.
The minimum hand shows the smallest score possible that still fills in
every blank, including the bonus score for achieving at least 63 points
in the top half. The maximum score shows the highest possible score. If
your score falls short of the minimum score, your result is expresses as
a negative number that shows how far away from the minimum score you
came. If you score exactly the minimum score, your hand is counted as
"Even," but if your score is above the minimum score, your score is
expressed as a percentage of the maximum score.

In Human vs. PC mode, the objective is to score higher than the computer
controlled opponent. The winner is the one who scores highest.

CURRENT VERSION: Alpha 3
------------------------------------------------------------------------
Technically, this game is still in development. The AI is in place and
functional; however, there are issues with the GUI during the bot's
turn. There is still some debugging code in place that produces output
to the console, if you run it from the command line.

Hopefully this is the last alpha release. If it had not been for the
discovery of the major logic error, I wouldn't have bothered with this
release.

Due to the issues with the GUI, my attention is going to be focused more
on that. Barring any other discoveries, the next release should contain
the new GUI.

CHANGES SINCE ALPHA 2
------------------------------------------------------------------------
- Added AI
- Corrected major logic error that was producing false positives on
  small & large straights.

CHANGES SINCE ALPHA 1
------------------------------------------------------------------------
- Corrected some spelling errors.
- Corrected issue with scoring buttons not resetting when starting a new
  game.
- Added pop-up results display at the end of a game.

PACKAGE CONTENTS: yacht_alpha_1.zip
------------------------------------------------------------------------
source     : A folder containing all source code and images for the game
README.txt : This file
yacht.ico  : Shortcut icon
yacht.jar  : The executable JAR file.

HOW TO PLAY:
------------------------------------------------------------------------
After starting the program or a new game, click on the "3 Rolls" button.

Click on any of the dice you want to keep (they will turn green), and
click the "2 Rolls" button to roll the remaining red dice; or if you are
happy with your hand, go ahead and click on one of the score buttons.

If you chose to roll again, you may now select any additional dice you
wish to keep and click on the "1 Roll" button, or score your hand.

After clicking the "1 Roll" button, you must score your hand for the
game to continue.

If you are playing in Solitaire mode, after you score, the "0 Rolls"
button will reset to "3 Rolls" and become active again. If you are
playing the Human vs. PC mode, the computer has its turn, then the board
resets for you to begin your next turn.

Repeat these steps until all 13 buttons have been used. At the end of
the game, the final result will display in the top left corner of the
score card.
