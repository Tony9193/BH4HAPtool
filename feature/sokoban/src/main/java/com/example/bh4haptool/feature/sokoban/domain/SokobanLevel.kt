package com.example.bh4haptool.feature.sokoban.domain

data class SokobanLevel(
    val id: Int,
    val name: String,
    val rows: List<String>
)

object SokobanLevels {
    val DEFAULT_LEVELS: List<SokobanLevel> = listOf(
        SokobanLevel(
            id = 1,
            name = "Procedural-1",
            rows = listOf(
                "####",
                "#@.#",
                "#$ #",
                "#  #",
                "# $#",
                "#. #",
                "#  #",
                "####"
            )
        ),
        SokobanLevel(
            id = 2,
            name = "Procedural-2",
            rows = listOf(
                "#####",
                "#  .#",
                "# $ #",
                "##@##",
                "##$##",
                "#   #",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 3,
            name = "Procedural-3",
            rows = listOf(
                "#######",
                "#. # .#",
                "# $@$ #",
                "#  #  #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 4,
            name = "Procedural-4",
            rows = listOf(
                "######",
                "#.   #",
                "#$$  #",
                "#@.###",
                "####  "
            )
        ),
        SokobanLevel(
            id = 5,
            name = "Procedural-5",
            rows = listOf(
                "####### ",
                "#+.   ##",
                "# # $$ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 6,
            name = "Procedural-6",
            rows = listOf(
                " #####",
                "##  .#",
                "#@$  #",
                "#  * #",
                "######"
            )
        ),
        SokobanLevel(
            id = 7,
            name = "Procedural-7",
            rows = listOf(
                "   #####",
                "####   #",
                "#@$  # #",
                "#. $  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 8,
            name = "Procedural-8",
            rows = listOf(
                "########",
                "#  .#  #",
                "#  $@$ #",
                "#  ## .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 9,
            name = "Procedural-9",
            rows = listOf(
                "#####",
                "#.  #",
                "# $ #",
                "##@##",
                "#.$ #",
                "#   #",
                "#  ##",
                "#### "
            )
        ),
        SokobanLevel(
            id = 10,
            name = "Procedural-10",
            rows = listOf(
                "####### ",
                "#  #  ##",
                "# $@$  #",
                "#. #  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 11,
            name = "Procedural-11",
            rows = listOf(
                "#####",
                "#  .#",
                "# $ #",
                "##@##",
                "#.$ #",
                "#   #",
                "#  ##",
                "#### "
            )
        ),
        SokobanLevel(
            id = 12,
            name = "Procedural-12",
            rows = listOf(
                "#######",
                "#@ #  #",
                "#  $$ #",
                "#  . .#",
                "#######"
            )
        ),
        SokobanLevel(
            id = 13,
            name = "Procedural-13",
            rows = listOf(
                "########",
                "#   #  #",
                "#  $@$ #",
                "#  .# .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 14,
            name = "Procedural-14",
            rows = listOf(
                "########",
                "#@ #   #",
                "#  #.  #",
                "#  # $ #",
                "## $ ###",
                " #   #  ",
                " #.  #  ",
                " #####  "
            )
        ),
        SokobanLevel(
            id = 15,
            name = "Procedural-15",
            rows = listOf(
                " ####",
                "##@ #",
                "# $ #",
                "#   #",
                "##  #",
                " #$.#",
                " # .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 16,
            name = "Procedural-16",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "#.$##",
                "#. # ",
                "#  # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 17,
            name = "Procedural-17",
            rows = listOf(
                "#######",
                "#    .#",
                "#    $#",
                "#  #  #",
                "#### ##",
                "  #@$ #",
                "  #.  #",
                "  #####"
            )
        ),
        SokobanLevel(
            id = 18,
            name = "Procedural-18",
            rows = listOf(
                "#####",
                "#@  #",
                "# $ #",
                "## .#",
                " #$ #",
                " # .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 19,
            name = "Procedural-19",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "#$ ##",
                "#. .#",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 20,
            name = "Procedural-20",
            rows = listOf(
                "########",
                "#+.    #",
                "# $ $  #",
                "####  ##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 21,
            name = "Procedural-21",
            rows = listOf(
                "########",
                "# .#@  #",
                "#   $  #",
                "#  *   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 22,
            name = "Procedural-22",
            rows = listOf(
                "#####",
                "#+  #",
                "#  *#",
                "# $ #",
                "##  #",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 23,
            name = "Procedural-23",
            rows = listOf(
                "#####   ",
                "#   #   ",
                "#   #   ",
                "#  #####",
                "##$.#@ #",
                "#    $ #",
                "#   # .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 24,
            name = "Procedural-24",
            rows = listOf(
                "#### ",
                "#  # ",
                "#. ##",
                "# $@#",
                "# $ #",
                "#. ##",
                "#### "
            )
        ),
        SokobanLevel(
            id = 25,
            name = "Procedural-25",
            rows = listOf(
                "####### ",
                "#@ #..# ",
                "#     # ",
                "# $ $ ##",
                "#   #  #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 26,
            name = "Procedural-26",
            rows = listOf(
                "########",
                "#@ #.  #",
                "#      #",
                "#  #.  #",
                "#  ##  #",
                "# $$   #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 27,
            name = "Procedural-27",
            rows = listOf(
                "    ####",
                "    #  #",
                "##### .#",
                "#@  #$ #",
                "#     $#",
                "#     .#",
                "##  #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 28,
            name = "Procedural-28",
            rows = listOf(
                "#####   ",
                "#@ .#   ",
                "#   #   ",
                "##$.####",
                "#  #   #",
                "#  $   #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 29,
            name = "Procedural-29",
            rows = listOf(
                "#### ",
                "#..# ",
                "# $##",
                "# $@#",
                "# $ #",
                "## .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 30,
            name = "Procedural-30",
            rows = listOf(
                "########",
                "#@ .#  #",
                "#   $ .#",
                "#   #  #",
                "#### $ #",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 31,
            name = "Procedural-31",
            rows = listOf(
                "########",
                "#@     #",
                "#  $ $ #",
                "#   ####",
                "#  #..# ",
                "#     # ",
                "#     # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 32,
            name = "Procedural-32",
            rows = listOf(
                "  ##### ",
                "  #@  # ",
                "  #   # ",
                "###  $##",
                "#..# $ #",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 33,
            name = "Procedural-33",
            rows = listOf(
                " #####  ",
                "##@  #  ",
                "# $  #  ",
                "#  $ ## ",
                "###   ##",
                "  #    #",
                "  #  ..#",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 34,
            name = "Procedural-34",
            rows = listOf(
                "####### ",
                "# .  .# ",
                "# #   # ",
                "#    ## ",
                "#   $@##",
                "#  # $ #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 35,
            name = "Procedural-35",
            rows = listOf(
                "####    ",
                "#@ #####",
                "#  $ $ #",
                "#  ..  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 36,
            name = "Procedural-36",
            rows = listOf(
                "####    ",
                "#@ #####",
                "#   #. #",
                "#   #  #",
                "#  #  ##",
                "#  $$ # ",
                "#    .# ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 37,
            name = "Procedural-37",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "# $ ####",
                "#* #.  #",
                "#      #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 38,
            name = "Procedural-38",
            rows = listOf(
                "########",
                "#@  #  #",
                "# $ #  #",
                "#   #  #",
                "# $#.  #",
                "#      #",
                "#  #.  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 39,
            name = "Procedural-39",
            rows = listOf(
                "########",
                "#@  #..#",
                "#      #",
                "##   $ #",
                "#   ####",
                "#    $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 40,
            name = "Procedural-40",
            rows = listOf(
                "########",
                "#@ . . #",
                "#    # #",
                "####   #",
                "#  $   #",
                "# #$ # #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 41,
            name = "Procedural-41",
            rows = listOf(
                " ###### ",
                " #@.. # ",
                "## #  ##",
                "#  #   #",
                "# $$   #",
                "##     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 42,
            name = "Procedural-42",
            rows = listOf(
                " ####  ",
                "##@.#  ",
                "# $ #  ",
                "# $ ###",
                "# $#  #",
                "#.   .#",
                "##  ###",
                " ####  "
            )
        ),
        SokobanLevel(
            id = 43,
            name = "Procedural-43",
            rows = listOf(
                " #######",
                "##  $@.#",
                "#  $ $.#",
                "#.  ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 44,
            name = "Procedural-44",
            rows = listOf(
                " ####",
                "##@.#",
                "#.  #",
                "# $ #",
                "# $##",
                "#  # ",
                "#  # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 45,
            name = "Procedural-45",
            rows = listOf(
                " #######",
                "##+    #",
                "#  $$  #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 46,
            name = "Procedural-46",
            rows = listOf(
                "  ######",
                "  #@   #",
                "  #    #",
                "  #.. ##",
                "#### $ #",
                "#   $  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 47,
            name = "Procedural-47",
            rows = listOf(
                " #######",
                "##+    #",
                "#  $$  #",
                "#   #  #",
                "## ##  #",
                "#   #  #",
                "#  .#  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 48,
            name = "Procedural-48",
            rows = listOf(
                " ###### ",
                " #@ ..# ",
                " #$   # ",
                " #  ####",
                "##$    #",
                "#      #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 49,
            name = "Procedural-49",
            rows = listOf(
                "########",
                "#@ # . #",
                "#      #",
                "#  # . #",
                "# $ # ##",
                "# $   # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 50,
            name = "Procedural-50",
            rows = listOf(
                "#### ",
                "#+ ##",
                "#   #",
                "# $.#",
                "##$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 51,
            name = "Procedural-51",
            rows = listOf(
                "########",
                "#@     #",
                "#      #",
                "#   #.##",
                "# $# .# ",
                "# $   # ",
                "##  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 52,
            name = "Procedural-52",
            rows = listOf(
                "####### ",
                "#     ##",
                "#    $@#",
                "# $ #  #",
                "#  #   #",
                "#  #   #",
                "####. .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 53,
            name = "Procedural-53",
            rows = listOf(
                "    ####",
                "   ##@ #",
                "####.  #",
                "#      #",
                "# $ #  #",
                "# #$.  #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 54,
            name = "Procedural-54",
            rows = listOf(
                "#####  ",
                "#  .#  ",
                "#.$ #  ",
                "#   ###",
                "#$ #@ #",
                "#   $.#",
                "##  ###",
                " ####  "
            )
        ),
        SokobanLevel(
            id = 55,
            name = "Procedural-55",
            rows = listOf(
                " ####",
                "##@ #",
                "# $ #",
                "#.$.#",
                "##$.#",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 56,
            name = "Procedural-56",
            rows = listOf(
                "#####",
                "#. .#",
                "#  .#",
                "# $ #",
                "##$ #",
                " #@$#",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 57,
            name = "Procedural-57",
            rows = listOf(
                "#####",
                "#. .#",
                "#  .#",
                "# $ #",
                "##$$#",
                " #@ #",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 58,
            name = "Procedural-58",
            rows = listOf(
                "#### ",
                "#@ # ",
                "#  # ",
                "#$$##",
                "#   #",
                "#.$.#",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 59,
            name = "Procedural-59",
            rows = listOf(
                "####### ",
                "#@ #  ##",
                "#      #",
                "#  # ..#",
                "#    $ #",
                "#  $####",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 60,
            name = "Procedural-60",
            rows = listOf(
                "#####",
                "#@  #",
                "# $.#",
                "# $##",
                "# $ #",
                "#.  #",
                "## .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 61,
            name = "Procedural-61",
            rows = listOf(
                "########",
                "#+   ..#",
                "# $$$# #",
                "## #   #",
                "#  #####",
                "#  #    ",
                "#  #    ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 62,
            name = "Procedural-62",
            rows = listOf(
                "#####   ",
                "#+  ####",
                "# . .  #",
                "#  $ $ #",
                "## $####",
                " #  #   ",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 63,
            name = "Procedural-63",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@ $  #",
                "##   $##",
                "# .#  # ",
                "#     # ",
                "##. ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 64,
            name = "Procedural-64",
            rows = listOf(
                "######  ",
                "#@   #  ",
                "# #$ #  ",
                "# $  ###",
                "#  #   #",
                "#  . . #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 65,
            name = "Procedural-65",
            rows = listOf(
                "#####  ",
                "#. .#  ",
                "#.  #  ",
                "# $ ###",
                "# $#@ #",
                "#   $ #",
                "#  ####",
                "####   "
            )
        ),
        SokobanLevel(
            id = 66,
            name = "Procedural-66",
            rows = listOf(
                "########",
                "#+ #   #",
                "# $ $  #",
                "#. #  ##",
                "# $ ### ",
                "#  .#   ",
                "#  ##   ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 67,
            name = "Procedural-67",
            rows = listOf(
                "#####   ",
                "#@  ### ",
                "# $   # ",
                "# $## ##",
                "#  #.  #",
                "#      #",
                "#  #. ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 68,
            name = "Procedural-68",
            rows = listOf(
                "#######",
                "#    .#",
                "#  $$ #",
                "#  # .#",
                "##$@###",
                "#.  #  ",
                "#   #  ",
                "#####  "
            )
        ),
        SokobanLevel(
            id = 69,
            name = "Procedural-69",
            rows = listOf(
                "########",
                "#@   ..#",
                "# $$$  #",
                "#  #  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 70,
            name = "Procedural-70",
            rows = listOf(
                " #######",
                "##  #  #",
                "#  $@$ #",
                "#. ##$ #",
                "#      #",
                "#   .  #",
                "#. #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 71,
            name = "Procedural-71",
            rows = listOf(
                "########",
                "#      #",
                "#    #.#",
                "##$    #",
                "#@ ## .#",
                "# $ #  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 72,
            name = "Procedural-72",
            rows = listOf(
                "########",
                "#@ #  .#",
                "#    #.#",
                "#      #",
                "# $#   #",
                "#* $   #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 73,
            name = "Procedural-73",
            rows = listOf(
                "########",
                "# .#@  #",
                "# .#$$ #",
                "#    ###",
                "#     # ",
                "# #   # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 74,
            name = "Procedural-74",
            rows = listOf(
                "####### ",
                "#@    ##",
                "#      #",
                "#   #  #",
                "##### ##",
                "# $ $  #",
                "#   . .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 75,
            name = "Procedural-75",
            rows = listOf(
                "   #####",
                "   #. .#",
                "#### $ #",
                "#@  #  #",
                "# $ $  #",
                "#. #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 76,
            name = "Procedural-76",
            rows = listOf(
                "########",
                "#@.  . #",
                "# #  # #",
                "#      #",
                "# $ $  #",
                "####.$ #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 77,
            name = "Procedural-77",
            rows = listOf(
                " ###### ",
                "##    ##",
                "#@$  $ #",
                "#  ##  #",
                "#. .#  #",
                "#   #  #",
                "##  ####",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 78,
            name = "Procedural-78",
            rows = listOf(
                "########",
                "#@     #",
                "# $ $  #",
                "## #   #",
                "#.$.####",
                "#   #   ",
                "#  .#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 79,
            name = "Procedural-79",
            rows = listOf(
                "####    ",
                "#@ #    ",
                "#  #    ",
                "# $#####",
                "#  #.  #",
                "#.$ $# #",
                "##    .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 80,
            name = "Procedural-80",
            rows = listOf(
                "########",
                "#@     #",
                "#..  # #",
                "##  $$ #",
                " ##    #",
                " #. $# #",
                " #     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 81,
            name = "Procedural-81",
            rows = listOf(
                "   #####",
                "####   #",
                "#      #",
                "#     ##",
                "##..#$@#",
                " #   $ #",
                " ###  ##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 82,
            name = "Procedural-82",
            rows = listOf(
                "######  ",
                "#@   #  ",
                "# #$$#  ",
                "#  $ ###",
                "# .# ..#",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 83,
            name = "Procedural-83",
            rows = listOf(
                "  #### ",
                "###@ ##",
                "#   $ #",
                "# ##  #",
                "# .#$ #",
                "#    $#",
                "# .  .#",
                "#######"
            )
        ),
        SokobanLevel(
            id = 84,
            name = "Procedural-84",
            rows = listOf(
                " ####  ",
                "##@ ###",
                "#. . .#",
                "#  #  #",
                "##  $$#",
                " # $  #",
                " ###  #",
                "   ####"
            )
        ),
        SokobanLevel(
            id = 85,
            name = "Procedural-85",
            rows = listOf(
                "########",
                "#+  . .#",
                "#$     #",
                "#  ## ##",
                "##$ # # ",
                " #  $ # ",
                " #  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 86,
            name = "Procedural-86",
            rows = listOf(
                "########",
                "#  #+  #",
                "#  #.$ #",
                "#  * ###",
                "#   $ # ",
                "####  # ",
                "   #  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 87,
            name = "Procedural-87",
            rows = listOf(
                " ####   ",
                " #@ #   ",
                " #  #   ",
                "##$ ####",
                "#  #.  #",
                "#    $$#",
                "##  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 88,
            name = "Procedural-88",
            rows = listOf(
                "####### ",
                "#     # ",
                "#.#   # ",
                "#     # ",
                "#. $####",
                "# #@$  #",
                "# .$   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 89,
            name = "Procedural-89",
            rows = listOf(
                "   #####",
                " ###   #",
                " #+$ # #",
                "##.#   #",
                "#  $ . #",
                "#  $####",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 90,
            name = "Procedural-90",
            rows = listOf(
                " #######",
                "##@ ...#",
                "# $    #",
                "#  ## ##",
                "#   #  #",
                "# $$   #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 91,
            name = "Procedural-91",
            rows = listOf(
                "#####   ",
                "#.  ####",
                "#. $#@ #",
                "## $   #",
                "#   *  #",
                "#   #  #",
                "##  #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 92,
            name = "Procedural-92",
            rows = listOf(
                "   #### ",
                " ###@ # ",
                " #    # ",
                "##.$####",
                "#      #",
                "# .$$  #",
                "# . #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 93,
            name = "Procedural-93",
            rows = listOf(
                "#####   ",
                "#@  ### ",
                "#. .  # ",
                "#    $##",
                "#.$##  #",
                "# $    #",
                "##  #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 94,
            name = "Procedural-94",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#  $@  #",
                "##$    #",
                " #  # .#",
                " #$    #",
                " #  #..#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 95,
            name = "Procedural-95",
            rows = listOf(
                "####    ",
                "# .#####",
                "#  $@$ #",
                "# $    #",
                "##  ####",
                " #  ..# ",
                " ###  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 96,
            name = "Procedural-96",
            rows = listOf(
                "   #### ",
                "   #@ # ",
                "   #  # ",
                "####. ##",
                "#  . $ #",
                "# $ $. #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 97,
            name = "Procedural-97",
            rows = listOf(
                "########",
                "#   #@ #",
                "#   #  #",
                "##$$  ##",
                " #     #",
                " ### ..#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 98,
            name = "Procedural-98",
            rows = listOf(
                "#####   ",
                "#.. ### ",
                "#  .  # ",
                "## $  ##",
                "####$$@#",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 99,
            name = "Procedural-99",
            rows = listOf(
                "####    ",
                "#  #####",
                "#      #",
                "# $#   #",
                "#   ####",
                "#.$$@ # ",
                "##  ..# ",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 100,
            name = "Procedural-100",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@$ # #",
                "##$$   #",
                "#  ## ##",
                "#. .   #",
                "##   . #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 101,
            name = "Procedural-101",
            rows = listOf(
                "#### ",
                "#@ ##",
                "# $ #",
                "#*  #",
                "#  .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 102,
            name = "Procedural-102",
            rows = listOf(
                "########",
                "#. #.  #",
                "# $@$  #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 103,
            name = "Procedural-103",
            rows = listOf(
                "#####",
                "#  .#",
                "# $ #",
                "##@##",
                "#.$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 104,
            name = "Procedural-104",
            rows = listOf(
                "    ####",
                "    #@ #",
                "    #  #",
                "#####  #",
                "#   $ ##",
                "#   *.# ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 105,
            name = "Procedural-105",
            rows = listOf(
                "#####",
                "#+  #",
                "#.# #",
                "# $ #",
                "#   #",
                "# $ #",
                "##  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 106,
            name = "Procedural-106",
            rows = listOf(
                "#####",
                "#+  #",
                "#.# #",
                "#   #",
                "# $ #",
                "# $ #",
                "##  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 107,
            name = "Procedural-107",
            rows = listOf(
                "#######",
                "# .  .#",
                "#  $$ #",
                "###@ ##",
                "  #### "
            )
        ),
        SokobanLevel(
            id = 108,
            name = "Procedural-108",
            rows = listOf(
                " ####",
                " #@.#",
                " #$ #",
                "## .#",
                "# $ #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 109,
            name = "Procedural-109",
            rows = listOf(
                "#### ",
                "#  # ",
                "#  ##",
                "# $@#",
                "#*  #",
                "#  .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 110,
            name = "Procedural-110",
            rows = listOf(
                "#####  ",
                "#   ###",
                "#  $@.#",
                "# # $ #",
                "#.  ###",
                "#####  "
            )
        ),
        SokobanLevel(
            id = 111,
            name = "Procedural-111",
            rows = listOf(
                "########",
                "#@ . . #",
                "#  $$  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 112,
            name = "Procedural-112",
            rows = listOf(
                "#####   ",
                "#@  ####",
                "#  $$  #",
                "#   .. #",
                "########"
            )
        ),
        SokobanLevel(
            id = 113,
            name = "Procedural-113",
            rows = listOf(
                "  #####",
                "  #@ .#",
                "  # $ #",
                "#### ##",
                "#  # ##",
                "#  $  #",
                "#    .#",
                "#######"
            )
        ),
        SokobanLevel(
            id = 114,
            name = "Procedural-114",
            rows = listOf(
                "########",
                "# .#@  #",
                "#   $  #",
                "#  *   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 115,
            name = "Procedural-115",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "## $#",
                "#. .#",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 116,
            name = "Procedural-116",
            rows = listOf(
                "  ######",
                "  #@ . #",
                "  #  # #",
                "  #  $ #",
                "###    #",
                "# $ ####",
                "#  .#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 117,
            name = "Procedural-117",
            rows = listOf(
                " #### ",
                " #  # ",
                " #  # ",
                "##  # ",
                "#@$###",
                "# $  #",
                "#  ..#",
                "######"
            )
        ),
        SokobanLevel(
            id = 118,
            name = "Procedural-118",
            rows = listOf(
                "########",
                "#  ## .#",
                "#      #",
                "#. ##$ #",
                "## ##  #",
                "#@$ #  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 119,
            name = "Procedural-119",
            rows = listOf(
                "   #####",
                "   #.  #",
                "   #   #",
                "##### ##",
                "#@  #  #",
                "#  $   #",
                "#   $. #",
                "########"
            )
        ),
        SokobanLevel(
            id = 120,
            name = "Procedural-120",
            rows = listOf(
                "########",
                "#@     #",
                "# #$ $ #",
                "#     ##",
                "#.  ### ",
                "#.    # ",
                "####  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 121,
            name = "Procedural-121",
            rows = listOf(
                "#### ",
                "#@ # ",
                "# $# ",
                "#. # ",
                "#.$##",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 122,
            name = "Procedural-122",
            rows = listOf(
                "####   ",
                "#  ####",
                "# $@$ #",
                "#  ##.#",
                "#.    #",
                "#     #",
                "#   ###",
                "#####  "
            )
        ),
        SokobanLevel(
            id = 123,
            name = "Procedural-123",
            rows = listOf(
                "########",
                "#@ #.  #",
                "#      #",
                "#  #.  #",
                "# $ #  #",
                "# $    #",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 124,
            name = "Procedural-124",
            rows = listOf(
                "########",
                "#   # .#",
                "#   $ .#",
                "#####  #",
                "#@ # $##",
                "#     # ",
                "#  #  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 125,
            name = "Procedural-125",
            rows = listOf(
                "#### ",
                "#+ # ",
                "# $# ",
                "#$ # ",
                "#  ##",
                "# $.#",
                "#  .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 126,
            name = "Procedural-126",
            rows = listOf(
                " ####",
                " #@.#",
                " #$$#",
                " #  #",
                "##$ #",
                "#.  #",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 127,
            name = "Procedural-127",
            rows = listOf(
                "   #####",
                "   #   #",
                "   #   #",
                "#### $ #",
                "#@  #  #",
                "#   $ .#",
                "#  .#  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 128,
            name = "Procedural-128",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "## $####",
                "# $    #",
                "# #  # #",
                "#.  .  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 129,
            name = "Procedural-129",
            rows = listOf(
                "   ####",
                "####  #",
                "# .  .#",
                "#     #",
                "# ##  #",
                "# $@$ #",
                "####  #",
                "   ####"
            )
        ),
        SokobanLevel(
            id = 130,
            name = "Procedural-130",
            rows = listOf(
                "  ##### ",
                "  #@ .# ",
                "  #$ .# ",
                "###  ###",
                "# $    #",
                "# #  # #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 131,
            name = "Procedural-131",
            rows = listOf(
                "########",
                "#+  #  #",
                "#  $$  #",
                "##.    #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 132,
            name = "Procedural-132",
            rows = listOf(
                "#######",
                "#@    #",
                "# #$  #",
                "#  $  #",
                "# .#  #",
                "#     #",
                "##.   #",
                " ######"
            )
        ),
        SokobanLevel(
            id = 133,
            name = "Procedural-133",
            rows = listOf(
                "   #### ",
                " ###@ ##",
                " # ..  #",
                "## ##  #",
                "#   #  #",
                "# $$   #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 134,
            name = "Procedural-134",
            rows = listOf(
                "########",
                "#.     #",
                "#      #",
                "## #  .#",
                "#@$#  ##",
                "# $   # ",
                "##  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 135,
            name = "Procedural-135",
            rows = listOf(
                " #######",
                " #@    #",
                "##     #",
                "#.. #  #",
                "# $ $  #",
                "####  ##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 136,
            name = "Procedural-136",
            rows = listOf(
                " ###### ",
                "##@   # ",
                "# $   # ",
                "#  #####",
                "# $ # .#",
                "#      #",
                "#.     #",
                "########"
            )
        ),
        SokobanLevel(
            id = 137,
            name = "Procedural-137",
            rows = listOf(
                " ####   ",
                "##@ ### ",
                "# $   # ",
                "# $##.# ",
                "#   #.##",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 138,
            name = "Procedural-138",
            rows = listOf(
                " #######",
                "##@    #",
                "# $    #",
                "#  #   #",
                "#  ## ##",
                "# $    #",
                "##  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 139,
            name = "Procedural-139",
            rows = listOf(
                "########",
                "#@  #..#",
                "#      #",
                "##     #",
                "# $$####",
                "#     # ",
                "##    # ",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 140,
            name = "Procedural-140",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@$   #",
                "##  # .#",
                "#  #.$.#",
                "#  # $ #",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 141,
            name = "Procedural-141",
            rows = listOf(
                "#######",
                "#+.#  #",
                "#     #",
                "#  $$ #",
                "###  ##",
                "  #  # ",
                "  #### "
            )
        ),
        SokobanLevel(
            id = 142,
            name = "Procedural-142",
            rows = listOf(
                "#####   ",
                "#   #   ",
                "#   #   ",
                "#.$ ####",
                "#. #@  #",
                "#   $  #",
                "##  ####",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 143,
            name = "Procedural-143",
            rows = listOf(
                "####### ",
                "#@ #. ##",
                "#      #",
                "#.$#   #",
                "##  ####",
                " #  $ # ",
                " #    # ",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 144,
            name = "Procedural-144",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@$ # #",
                "##  $  #",
                "# .#  ##",
                "#  #  # ",
                "# .#  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 145,
            name = "Procedural-145",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "##$ ####",
                "#  #   #",
                "#   $  #",
                "## ..  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 146,
            name = "Procedural-146",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   # $ #",
                "####  ##",
                "#    $# ",
                "#     # ",
                "#.. ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 147,
            name = "Procedural-147",
            rows = listOf(
                "   #####",
                " ###+ .#",
                " #     #",
                "##$$   #",
                "#   ####",
                "# #   # ",
                "#     # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 148,
            name = "Procedural-148",
            rows = listOf(
                "#####   ",
                "#@ .### ",
                "#     # ",
                "#. ## ##",
                "## # $ #",
                "#   $  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 149,
            name = "Procedural-149",
            rows = listOf(
                " ###### ",
                "##@   # ",
                "#     # ",
                "#   ####",
                "#.  #  #",
                "#.#$ $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 150,
            name = "Procedural-150",
            rows = listOf(
                "####### ",
                "#  #@ ##",
                "#   $$ #",
                "#   #  #",
                "#   # ##",
                "# #.  # ",
                "#  .### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 151,
            name = "Procedural-151",
            rows = listOf(
                "####### ",
                "#@    ##",
                "#      #",
                "#  ##$ #",
                "#  #. ##",
                "#  .$ # ",
                "#  #  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 152,
            name = "Procedural-152",
            rows = listOf(
                "########",
                "#@     #",
                "# # .. #",
                "# $ # ##",
                "##  # ##",
                " #$    #",
                " #     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 153,
            name = "Procedural-153",
            rows = listOf(
                "########",
                "#+   . #",
                "#    # #",
                "##     #",
                "#   #  #",
                "# $ $  #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 154,
            name = "Procedural-154",
            rows = listOf(
                "########",
                "#. #@ .#",
                "# $# $.#",
                "#  ##  #",
                "#    $ #",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 155,
            name = "Procedural-155",
            rows = listOf(
                "####### ",
                "#     ##",
                "#    $@#",
                "##$ #  #",
                "#  #   #",
                "#  #. .#",
                "#  #####",
                "####    "
            )
        ),
        SokobanLevel(
            id = 156,
            name = "Procedural-156",
            rows = listOf(
                "   #### ",
                " ###@ ##",
                " #  . .#",
                "##$#   #",
                "#  ##  #",
                "#  $   #",
                "#  #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 157,
            name = "Procedural-157",
            rows = listOf(
                "########",
                "#@  #  #",
                "#   #  #",
                "#. ##$ #",
                "## # $ #",
                "#     .#",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 158,
            name = "Procedural-158",
            rows = listOf(
                "########",
                "#+.    #",
                "#  $$$ #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 159,
            name = "Procedural-159",
            rows = listOf(
                " #######",
                "##     #",
                "#      #",
                "#$ #####",
                "#@$    #",
                "#.     #",
                "#  #. ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 160,
            name = "Procedural-160",
            rows = listOf(
                "########",
                "#@     #",
                "# $  #.#",
                "# $#  .#",
                "#  ##  #",
                "#      #",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 161,
            name = "Procedural-161",
            rows = listOf(
                "########",
                "#    ..#",
                "#  $$  #",
                "## #  .#",
                "#@$#####",
                "#  #    ",
                "#  #    ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 162,
            name = "Procedural-162",
            rows = listOf(
                "####### ",
                "#  #  ##",
                "#.  $  #",
                "#. ##  #",
                "##$@#  #",
                " #  #  #",
                " #  #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 163,
            name = "Procedural-163",
            rows = listOf(
                " #######",
                "##  #@ #",
                "#   $  #",
                "#  #   #",
                "#. .####",
                "# $ #   ",
                "##  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 164,
            name = "Procedural-164",
            rows = listOf(
                "########",
                "#..    #",
                "#  $$  #",
                "#.  # ##",
                "####@$ #",
                "   #   #",
                "   #  ##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 165,
            name = "Procedural-165",
            rows = listOf(
                "########",
                "#  # * #",
                "#  $   #",
                "#      #",
                "##### ##",
                "   #@$ #",
                "   # ..#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 166,
            name = "Procedural-166",
            rows = listOf(
                "########",
                "#   #@ #",
                "#*   $.#",
                "#   # .#",
                "##$ ####",
                "#   #   ",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 167,
            name = "Procedural-167",
            rows = listOf(
                "   #### ",
                "   #@ # ",
                "####$ # ",
                "#    ## ",
                "#    .##",
                "#.# $  #",
                "#   *  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 168,
            name = "Procedural-168",
            rows = listOf(
                "########",
                "#  .#@.#",
                "# #.$$ #",
                "#   $  #",
                "##  ####",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 169,
            name = "Procedural-169",
            rows = listOf(
                "####### ",
                "# . . # ",
                "#  $$.# ",
                "###$@## ",
                " #   ###",
                " #     #",
                " #     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 170,
            name = "Procedural-170",
            rows = listOf(
                "#####   ",
                "#. .### ",
                "#     # ",
                "#    $# ",
                "## #$@##",
                "#    $ #",
                "#   .  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 171,
            name = "Procedural-171",
            rows = listOf(
                "#####   ",
                "#@  ### ",
                "# $ $ # ",
                "#     # ",
                "# $#  ##",
                "#    . #",
                "## .  .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 172,
            name = "Procedural-172",
            rows = listOf(
                "   #####",
                "   #@ .#",
                "   #   #",
                "#####$ #",
                "#      #",
                "#  $$#.#",
                "####  .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 173,
            name = "Procedural-173",
            rows = listOf(
                "    ####",
                "##### .#",
                "#@ ##$.#",
                "# $## .#",
                "## $   #",
                "#    # #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 174,
            name = "Procedural-174",
            rows = listOf(
                " #######",
                " # . $@#",
                "##  $$ #",
                "#  ## ##",
                "#     # ",
                "#..#### ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 175,
            name = "Procedural-175",
            rows = listOf(
                " ###### ",
                " #   .# ",
                " # $  # ",
                "##  ####",
                "#. $#@ #",
                "#    $ #",
                "##### .#",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 176,
            name = "Procedural-176",
            rows = listOf(
                "#####   ",
                "#+  ####",
                "#.#$$  #",
                "#.     #",
                "#   ####",
                "#  $#   ",
                "##  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 177,
            name = "Procedural-177",
            rows = listOf(
                "########",
                "#@  #  #",
                "#      #",
                "###$   #",
                "  #  ###",
                "  #$ .# ",
                "  #  .# ",
                "  ##### "
            )
        ),
        SokobanLevel(
            id = 178,
            name = "Procedural-178",
            rows = listOf(
                "########",
                "#@     #",
                "#  $ $ #",
                "#  ## ##",
                "####.$##",
                " #    .#",
                " #.    #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 179,
            name = "Procedural-179",
            rows = listOf(
                "#######",
                "#+.  .#",
                "#   $$#",
                "####  #",
                "#   $ #",
                "#     #",
                "#  ####",
                "####   "
            )
        ),
        SokobanLevel(
            id = 180,
            name = "Procedural-180",
            rows = listOf(
                "  ######",
                "  #    #",
                "  #    #",
                "### $$##",
                "#@ # $ #",
                "#     .#",
                "#  .# .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 181,
            name = "Procedural-181",
            rows = listOf(
                "########",
                "#@  # .#",
                "#  $ $ #",
                "#  #. *#",
                "#   #  #",
                "#      #",
                "#  #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 182,
            name = "Procedural-182",
            rows = listOf(
                "   ####",
                "#### .#",
                "#     #",
                "#$ #  #",
                "#  #$##",
                "#. *@# ",
                "#  ### ",
                "####   "
            )
        ),
        SokobanLevel(
            id = 183,
            name = "Procedural-183",
            rows = listOf(
                " #######",
                " #@..  #",
                "##$$ $ #",
                "# . #  #",
                "# # #  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 184,
            name = "Procedural-184",
            rows = listOf(
                "#######",
                "#+ #  #",
                "#    .#",
                "#. #$ #",
                "# # $ #",
                "#   $ #",
                "####  #",
                "   ####"
            )
        ),
        SokobanLevel(
            id = 185,
            name = "Procedural-185",
            rows = listOf(
                "########",
                "#.$ #@ #",
                "#   $  #",
                "#  #   #",
                "#. #####",
                "# $    #",
                "#. #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 186,
            name = "Procedural-186",
            rows = listOf(
                "   #####",
                " ###@  #",
                " # $$  #",
                "##     #",
                "#.  #$##",
                "#.  . # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 187,
            name = "Procedural-187",
            rows = listOf(
                "########",
                "#@.$   #",
                "#   $  #",
                "## ##$ #",
                "# .#   #",
                "# .#   #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 188,
            name = "Procedural-188",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   ####",
                "#   $  #",
                "## $   #",
                "#. $####",
                "#.. #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 189,
            name = "Procedural-189",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   # ..#",
                "#### .##",
                "#   $ ##",
                "# $ $  #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 190,
            name = "Procedural-190",
            rows = listOf(
                "#######",
                "#@$ . #",
                "# $$  #",
                "###   #",
                "  # #.#",
                "  #  .#",
                "  #####"
            )
        ),
        SokobanLevel(
            id = 191,
            name = "Procedural-191",
            rows = listOf(
                "   #####",
                "   #+  #",
                "#### . #",
                "#  $$$ #",
                "#    .##",
                "####  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 192,
            name = "Procedural-192",
            rows = listOf(
                " ####   ",
                " #@ #   ",
                " #$ #   ",
                " # .####",
                "##$ .  #",
                "# .$   #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 193,
            name = "Procedural-193",
            rows = listOf(
                " ####   ",
                "##  #   ",
                "# $.#   ",
                "#. .####",
                "#  #@  #",
                "# $ $  #",
                "##     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 194,
            name = "Procedural-194",
            rows = listOf(
                "####### ",
                "#@    # ",
                "#. .#$# ",
                "#  #  ##",
                "# $    #",
                "## *   #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 195,
            name = "Procedural-195",
            rows = listOf(
                " #######",
                "##  # .#",
                "#@$    #",
                "# $##  #",
                "## .#$##",
                " #     #",
                " # .   #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 196,
            name = "Procedural-196",
            rows = listOf(
                "#####   ",
                "#   ### ",
                "#  $@ # ",
                "#   #.##",
                "##$#  .#",
                "#      #",
                "# $  .##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 197,
            name = "Procedural-197",
            rows = listOf(
                "   #####",
                " ###@  #",
                " #  $  #",
                "##  $  #",
                "#. $####",
                "#.  #   ",
                "#. ##   ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 198,
            name = "Procedural-198",
            rows = listOf(
                "########",
                "#@ ##  #",
                "# $ .  #",
                "#. #.  #",
                "##$ # ##",
                "#  $  # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 199,
            name = "Procedural-199",
            rows = listOf(
                "####### ",
                "#..   ##",
                "#  $   #",
                "##. #  #",
                "####@$##",
                "#    $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 200,
            name = "Procedural-200",
            rows = listOf(
                "########",
                "#@ #...#",
                "#  $$  #",
                "# $   ##",
                "#  #### ",
                "#  #    ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 201,
            name = "Procedural-201",
            rows = listOf(
                "######",
                "#@ * #",
                "# $  #",
                "##  .#",
                " #####"
            )
        ),
        SokobanLevel(
            id = 202,
            name = "Procedural-202",
            rows = listOf(
                "########",
                "#  ##  #",
                "# $@$  #",
                "#. #.  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 203,
            name = "Procedural-203",
            rows = listOf(
                "####### ",
                "#+.   ##",
                "# #$ $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 204,
            name = "Procedural-204",
            rows = listOf(
                "########",
                "#@     #",
                "# $$ # #",
                "##   ..#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 205,
            name = "Procedural-205",
            rows = listOf(
                " ####",
                " #  #",
                "## .#",
                "#@$ #",
                "# $ #",
                "## .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 206,
            name = "Procedural-206",
            rows = listOf(
                "#### ",
                "#+ # ",
                "# $# ",
                "#. ##",
                "# $ #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 207,
            name = "Procedural-207",
            rows = listOf(
                "#####",
                "#@  #",
                "# $ #",
                "#  ##",
                "#.$# ",
                "#. # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 208,
            name = "Procedural-208",
            rows = listOf(
                " ####",
                " #  #",
                "##  #",
                "#@$ #",
                "#  *#",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 209,
            name = "Procedural-209",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "##$.#",
                " # .#",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 210,
            name = "Procedural-210",
            rows = listOf(
                "#####",
                "#   #",
                "#.  #",
                "## *#",
                "#@$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 211,
            name = "Procedural-211",
            rows = listOf(
                "########",
                "#@  $. #",
                "#  $   #",
                "#   #. #",
                "########"
            )
        ),
        SokobanLevel(
            id = 212,
            name = "Procedural-212",
            rows = listOf(
                "   #####",
                "   #@  #",
                "####   #",
                "#   # .#",
                "#   $$ #",
                "##### .#",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 213,
            name = "Procedural-213",
            rows = listOf(
                "#### ",
                "#+ ##",
                "#   #",
                "#   #",
                "# $##",
                "#  # ",
                "# $##",
                "#   #",
                "#   #",
                "#. ##",
                "#### "
            )
        ),
        SokobanLevel(
            id = 214,
            name = "Procedural-214",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "# $#####",
                "# $ # .#",
                "#      #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 215,
            name = "Procedural-215",
            rows = listOf(
                "    ####",
                "    #  #",
                "    #  #",
                "#####  #",
                "#   #  #",
                "#   #  #",
                "##  #$ #",
                "#  #@ ##",
                "#  $..# ",
                "##  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 216,
            name = "Procedural-216",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "#   #",
                "##$.#",
                " # .#",
                "##$ #",
                "#   #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 217,
            name = "Procedural-217",
            rows = listOf(
                "#### ",
                "#+ # ",
                "#.$##",
                "#   #",
                "#   #",
                "#   #",
                "# $##",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 218,
            name = "Procedural-218",
            rows = listOf(
                "#####",
                "#   #",
                "#   #",
                "#.$.#",
                "##@##",
                "# $##",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 219,
            name = "Procedural-219",
            rows = listOf(
                "#### ",
                "#@ # ",
                "# $# ",
                "#  ##",
                "#  .#",
                "#.# #",
                "#   #",
                "##  #",
                " #$ #",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 220,
            name = "Procedural-220",
            rows = listOf(
                "#####",
                "#. .#",
                "# # #",
                "#   #",
                "# $ #",
                "##@##",
                "# $ #",
                "#   #",
                "#   #",
                "##  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 221,
            name = "Procedural-221",
            rows = listOf(
                "########",
                "#@ . . #",
                "#    # #",
                "## $ $ #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 222,
            name = "Procedural-222",
            rows = listOf(
                "#### ",
                "#@ # ",
                "#. # ",
                "#.$##",
                "# $ #",
                "# # #",
                "#   #",
                "#   #",
                "# # #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 223,
            name = "Procedural-223",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "#   #",
                "#  ##",
                "#.$# ",
                "#. ##",
                "##$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 224,
            name = "Procedural-224",
            rows = listOf(
                " ####",
                " #@.#",
                " #$$#",
                " #  #",
                "##$ #",
                "#.  #",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 225,
            name = "Procedural-225",
            rows = listOf(
                "########",
                "#@. .  #",
                "# #    #",
                "#   ####",
                "# $ #   ",
                "# $ #   ",
                "#  ##   ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 226,
            name = "Procedural-226",
            rows = listOf(
                "########",
                "# .#@  #",
                "# .# $ #",
                "#  ## ##",
                "##     #",
                " # $   #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 227,
            name = "Procedural-227",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $##",
                "# $ #",
                "#.  #",
                "## .#",
                "#  ##",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 228,
            name = "Procedural-228",
            rows = listOf(
                "########",
                "#@     #",
                "# #  # #",
                "#      #",
                "#####  #",
                "# $ $  #",
                "#  ..  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 229,
            name = "Procedural-229",
            rows = listOf(
                "########",
                "#@ .#  #",
                "#  .#  #",
                "#  #   #",
                "#   $$ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 230,
            name = "Procedural-230",
            rows = listOf(
                " #######",
                "##@    #",
                "# . $# #",
                "#      #",
                "##.# $ #",
                " #     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 231,
            name = "Procedural-231",
            rows = listOf(
                "########",
                "#+.#   #",
                "#      #",
                "# $   ##",
                "####$  #",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 232,
            name = "Procedural-232",
            rows = listOf(
                " #######",
                "##+    #",
                "#  $$  #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 233,
            name = "Procedural-233",
            rows = listOf(
                "#####",
                "# $.#",
                "#   #",
                "#  ##",
                "## # ",
                " #$# ",
                "##@##",
                "#.$.#",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 234,
            name = "Procedural-234",
            rows = listOf(
                " #######",
                "##@.   #",
                "# $    #",
                "#  #  ##",
                "#  ##  #",
                "# $    #",
                "#  #.  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 235,
            name = "Procedural-235",
            rows = listOf(
                "########",
                "#@     #",
                "#   $# #",
                "##     #",
                "#   #$##",
                "#.    # ",
                "#.  ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 236,
            name = "Procedural-236",
            rows = listOf(
                "#######",
                "#@   .#",
                "# $$  #",
                "##. ###",
                " #    #",
                " ###  #",
                "   ####"
            )
        ),
        SokobanLevel(
            id = 237,
            name = "Procedural-237",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "## ##",
                "#. ##",
                "#  .#",
                "# $ #",
                "##$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 238,
            name = "Procedural-238",
            rows = listOf(
                "####### ",
                "#  .. ##",
                "#  $ $@#",
                "#   #  #",
                "#####  #",
                "    #  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 239,
            name = "Procedural-239",
            rows = listOf(
                "#### ",
                "#@ ##",
                "#   #",
                "# $ #",
                "## ##",
                "#. .#",
                "#   #",
                "##$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 240,
            name = "Procedural-240",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "##$.#",
                "#.$.#",
                "# $ #",
                "##  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 241,
            name = "Procedural-241",
            rows = listOf(
                "########",
                "#@ # ..#",
                "#      #",
                "# $ # ##",
                "#  ##  #",
                "# $ #  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 242,
            name = "Procedural-242",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#  .####",
                "#      #",
                "#  #$$ #",
                "#  . # #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 243,
            name = "Procedural-243",
            rows = listOf(
                "#### ",
                "#. # ",
                "#  ##",
                "# $@#",
                "# $ #",
                "# $##",
                "#. # ",
                "#. # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 244,
            name = "Procedural-244",
            rows = listOf(
                " ####   ",
                " #  ### ",
                " #  ..# ",
                "##$$  # ",
                "#@  ####",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 245,
            name = "Procedural-245",
            rows = listOf(
                "########",
                "#      #",
                "# $    #",
                "#   # ##",
                "## #.  #",
                "#  .   #",
                "# $#####",
                "##@ #   ",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 246,
            name = "Procedural-246",
            rows = listOf(
                "   #####",
                "   #+  #",
                "   #.  #",
                " ### $ #",
                "##  .###",
                "# $$ #  ",
                "#    #  ",
                "######  "
            )
        ),
        SokobanLevel(
            id = 247,
            name = "Procedural-247",
            rows = listOf(
                "####    ",
                "#  #    ",
                "# .#####",
                "##  $  #",
                "#@$  # #",
                "#  $ ..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 248,
            name = "Procedural-248",
            rows = listOf(
                "########",
                "#  ..  #",
                "# $$   #",
                "## .#  #",
                " ###@$##",
                " #    # ",
                " #  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 249,
            name = "Procedural-249",
            rows = listOf(
                "  #####",
                "  #@ .#",
                "  #$ .#",
                "  #  ##",
                "  #$  #",
                "  #   #",
                "###   #",
                "#    ##",
                "#     #",
                "###   #",
                "  #####"
            )
        ),
        SokobanLevel(
            id = 250,
            name = "Procedural-250",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "# $##",
                "#  # ",
                "#* ##",
                "#. .#",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 251,
            name = "Procedural-251",
            rows = listOf(
                "#####   ",
                "#@  ####",
                "#   #. #",
                "# $ #. #",
                "##$   ##",
                " #    # ",
                " #  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 252,
            name = "Procedural-252",
            rows = listOf(
                "#### ",
                "#+ # ",
                "#. ##",
                "# $ #",
                "# $ #",
                "## .#",
                "# $##",
                "#  # ",
                "#  # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 253,
            name = "Procedural-253",
            rows = listOf(
                "####### ",
                "#     ##",
                "# $    #",
                "#####  #",
                "    # ##",
                "    #$# ",
                "#####@# ",
                "#. #. ##",
                "#      #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 254,
            name = "Procedural-254",
            rows = listOf(
                "########",
                "#      #",
                "#      #",
                "#####$ #",
                "#@ .#  #",
                "#  $  .#",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 255,
            name = "Procedural-255",
            rows = listOf(
                "####### ",
                "#@    # ",
                "#   . # ",
                "##$#. ##",
                "#  ##  #",
                "#  $   #",
                "#  #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 256,
            name = "Procedural-256",
            rows = listOf(
                "    ####",
                " ####@ #",
                " #   $ #",
                "## # $ #",
                "#  ##  #",
                "#      #",
                "#  .. ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 257,
            name = "Procedural-257",
            rows = listOf(
                "########",
                "#@     #",
                "#      #",
                "##  # ##",
                " ####  #",
                " #     #",
                "##  #  #",
                "#.  ####",
                "#  $  # ",
                "# .$  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 258,
            name = "Procedural-258",
            rows = listOf(
                " #######",
                "##@   .#",
                "#      #",
                "# .#   #",
                "#     ##",
                "# #$$ # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 259,
            name = "Procedural-259",
            rows = listOf(
                "########",
                "#@ #   #",
                "#      #",
                "#  #   #",
                "##$ # ##",
                "#   # # ",
                "#   # ##",
                "##$#   #",
                " #  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 260,
            name = "Procedural-260",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "# # #",
                "#.$ #",
                "##$.#",
                " # .#",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 261,
            name = "Procedural-261",
            rows = listOf(
                "####### ",
                "#@    # ",
                "# #$  # ",
                "#  $  ##",
                "#  #   #",
                "#      #",
                "##     #",
                "## #####",
                "#   #   ",
                "#.. #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 262,
            name = "Procedural-262",
            rows = listOf(
                " ####   ",
                " #  #   ",
                " #  ##  ",
                " # $ #  ",
                " #.  #  ",
                "##   ###",
                "#@$# $ #",
                "#  #.  #",
                "####.  #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 263,
            name = "Procedural-263",
            rows = listOf(
                "####### ",
                "#  #@ ##",
                "#  $ $ #",
                "#   #  #",
                "#      #",
                "# #  # #",
                "# . .  #",
                "#   #  #",
                "#   #  #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 264,
            name = "Procedural-264",
            rows = listOf(
                "  #####",
                "  #  .#",
                "  #  .#",
                "###$ ##",
                "#@ $$# ",
                "#.   # ",
                "###### "
            )
        ),
        SokobanLevel(
            id = 265,
            name = "Procedural-265",
            rows = listOf(
                "#####",
                "#+ .#",
                "#.  #",
                "# $ #",
                "# $##",
                "# $ #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 266,
            name = "Procedural-266",
            rows = listOf(
                "#####",
                "#@$.#",
                "# $.#",
                "# $##",
                "#   #",
                "#.$.#",
                "##  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 267,
            name = "Procedural-267",
            rows = listOf(
                "####    ",
                "#@ #    ",
                "#  #    ",
                "#  #####",
                "#. #   #",
                "#      #",
                "#.$#####",
                "##   #  ",
                "#  $ #  ",
                "#    #  ",
                "######  "
            )
        ),
        SokobanLevel(
            id = 268,
            name = "Procedural-268",
            rows = listOf(
                "#### ",
                "#+ ##",
                "#  .#",
                "# $ #",
                "##$ #",
                "#.$ #",
                "#.$ #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 269,
            name = "Procedural-269",
            rows = listOf(
                "######  ",
                "#+   #  ",
                "#    #  ",
                "## . ###",
                "#  #$$ #",
                "#      #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 270,
            name = "Procedural-270",
            rows = listOf(
                "####    ",
                "#. #    ",
                "#. #    ",
                "#  #####",
                "#   #@ #",
                "#   $  #",
                "##$#   #",
                "#  #####",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 271,
            name = "Procedural-271",
            rows = listOf(
                "########",
                "#@     #",
                "#      #",
                "##  #..#",
                "#   ####",
                "#      #",
                "#  $ $ #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 272,
            name = "Procedural-272",
            rows = listOf(
                "########",
                "#..    #",
                "#  $$  #",
                "#.  # ##",
                "####@$ #",
                " #     #",
                " #  ####",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 273,
            name = "Procedural-273",
            rows = listOf(
                "###### ",
                "#@   # ",
                "#.#$ # ",
                "#    # ",
                "#. ####",
                "# $ $ #",
                "#.    #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 274,
            name = "Procedural-274",
            rows = listOf(
                "####### ",
                "#@    ##",
                "#      #",
                "#   #  #",
                "## #   #",
                " # #   #",
                "## #. .#",
                "# $ # ##",
                "#  $  # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 275,
            name = "Procedural-275",
            rows = listOf(
                "   #####",
                "####@ .#",
                "#  #  .#",
                "#  #  ##",
                "#      #",
                "#      #",
                "# $ # ##",
                "## #  # ",
                "#   $ # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 276,
            name = "Procedural-276",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#  $@  #",
                "##$#   #",
                "#      #",
                "#   # ##",
                "#  #.  #",
                "## #.  #",
                "#      #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 277,
            name = "Procedural-277",
            rows = listOf(
                "########",
                "#@ #.  #",
                "#  ..  #",
                "#  ##  #",
                "#   $  #",
                "#  $$  #",
                "#   #  #",
                "####  ##",
                "   #  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 278,
            name = "Procedural-278",
            rows = listOf(
                "   #### ",
                " ###@.##",
                " #     #",
                "##$ #. #",
                "# $ # ##",
                "# #    #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 279,
            name = "Procedural-279",
            rows = listOf(
                "########",
                "#+. #  #",
                "#  $$  #",
                "##     #",
                " #### ##",
                "    # # ",
                "   ## # ",
                "   #  ##",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 280,
            name = "Procedural-280",
            rows = listOf(
                "#####   ",
                "#@  ### ",
                "# $   # ",
                "# $ # # ",
                "#  ## ##",
                "#      #",
                "##  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 281,
            name = "Procedural-281",
            rows = listOf(
                "####### ",
                "#@    # ",
                "#     # ",
                "## $####",
                " #$    #",
                " #     #",
                " #  #.##",
                " ### .# ",
                "   #  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 282,
            name = "Procedural-282",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "##  ####",
                "#   # .#",
                "#      #",
                "##### .#",
                "#   #  #",
                "# $  $ #",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 283,
            name = "Procedural-283",
            rows = listOf(
                "   #####",
                "   #   #",
                "   #   #",
                "  ###  #",
                "  #@$$##",
                "  #   # ",
                "###   ##",
                "#  #   #",
                "#    # #",
                "#    ..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 284,
            name = "Procedural-284",
            rows = listOf(
                " #######",
                "##@ *  #",
                "# $$   #",
                "#  #  ##",
                "##     #",
                " #    .#",
                " ###  .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 285,
            name = "Procedural-285",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#  $$@ #",
                "# $    #",
                "##  # .#",
                "#      #",
                "#   #..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 286,
            name = "Procedural-286",
            rows = listOf(
                " #######",
                " #+.   #",
                " #   # #",
                " ###   #",
                "##     #",
                "# $ $  #",
                "#  ##  #",
                "#   #  #",
                "# # #  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 287,
            name = "Procedural-287",
            rows = listOf(
                "    ####",
                "    #@.#",
                "#####  #",
                "#  #   #",
                "# $$$  #",
                "#    ..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 288,
            name = "Procedural-288",
            rows = listOf(
                " ####   ",
                "##@ ####",
                "# $ #  #",
                "#  $   #",
                "###    #",
                "  #  # #",
                "  # .. #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 289,
            name = "Procedural-289",
            rows = listOf(
                "########",
                "#      #",
                "# $$  .#",
                "#   #..#",
                "#  #####",
                "# $@  # ",
                "##    # ",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 290,
            name = "Procedural-290",
            rows = listOf(
                " #######",
                " #@    #",
                "## # $ #",
                "#     ##",
                "#    $# ",
                "## #  # ",
                "#. .### ",
                "#   #   ",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 291,
            name = "Procedural-291",
            rows = listOf(
                " #######",
                " #@   .#",
                " #    .#",
                "##$ # .#",
                "# $   ##",
                "#  $  # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 292,
            name = "Procedural-292",
            rows = listOf(
                "   #####",
                " ###@  #",
                " # $$  #",
                "##  $  #",
                "#.  #  #",
                "#      #",
                "#.. #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 293,
            name = "Procedural-293",
            rows = listOf(
                "####### ",
                "#@   .##",
                "# $$$  #",
                "#  # ..#",
                "#   ####",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 294,
            name = "Procedural-294",
            rows = listOf(
                " ####   ",
                " #  #   ",
                " #  ####",
                " # $@$ #",
                "###    #",
                "#    # #",
                "#      #",
                "####  ##",
                " #     #",
                " # . . #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 295,
            name = "Procedural-295",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "##$ ####",
                "#  #.  #",
                "#    $$#",
                "##  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 296,
            name = "Procedural-296",
            rows = listOf(
                "########",
                "#@ .#  #",
                "#      #",
                "#  .#  #",
                "####   #",
                " #     #",
                "##$ #  #",
                "# $ # ##",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 297,
            name = "Procedural-297",
            rows = listOf(
                "####### ",
                "#@    ##",
                "#      #",
                "#. ##. #",
                "#   #.##",
                "#      #",
                "# $ $  #",
                "##$ ####",
                " #  #   ",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 298,
            name = "Procedural-298",
            rows = listOf(
                "########",
                "#      #",
                "#      #",
                "## # $ #",
                "#     ##",
                "#  .$*@#",
                "#### $.#",
                "   # .##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 299,
            name = "Procedural-299",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   #   #",
                "####$  #",
                "#    $##",
                "# #   # ",
                "#.. $.# ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 300,
            name = "Procedural-300",
            rows = listOf(
                "#### ",
                "#  # ",
                "#  # ",
                "# $##",
                "# $ #",
                "#. .#",
                "#.$ #",
                "##@##",
                "# $ #",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 301,
            name = "Procedural-301",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   #   #",
                "#####$ #",
                "#  $ $ #",
                "#.     #",
                "#..#   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 302,
            name = "Procedural-302",
            rows = listOf(
                " #######",
                "##@.#  #",
                "#.$ .  #",
                "# $    #",
                "# $##  #",
                "#  ##  #",
                "#  #####",
                "####    "
            )
        ),
        SokobanLevel(
            id = 303,
            name = "Procedural-303",
            rows = listOf(
                "####### ",
                "#@ #  ##",
                "#   $  #",
                "#  ## $#",
                "#   #  #",
                "#.  .$.#",
                "#  #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 304,
            name = "Procedural-304",
            rows = listOf(
                "#####",
                "#.  #",
                "# $ #",
                "##@##",
                "#. .#",
                "#.  #",
                "# $ #",
                "# $##",
                "# $ #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 305,
            name = "Procedural-305",
            rows = listOf(
                "####### ",
                "#    .##",
                "#  $$$@#",
                "#  # ..#",
                "####  ##",
                " #     #",
                " #     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 306,
            name = "Procedural-306",
            rows = listOf(
                "####   ",
                "#@ ####",
                "#.$. .#",
                "#   $ #",
                "# $####",
                "#  #   ",
                "#  #   ",
                "####   "
            )
        ),
        SokobanLevel(
            id = 307,
            name = "Procedural-307",
            rows = listOf(
                "#####",
                "#@  #",
                "# # #",
                "# $ #",
                "#$  #",
                "# $ #",
                "#. ##",
                "#* ##",
                "#. .#",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 308,
            name = "Procedural-308",
            rows = listOf(
                "########",
                "#+     #",
                "#      #",
                "####   #",
                "#  ## .#",
                "#      #",
                "#  ## ##",
                "##     #",
                " # $$# #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 309,
            name = "Procedural-309",
            rows = listOf(
                " #######",
                "##@ ...#",
                "#      #",
                "#   # ##",
                "## #   #",
                "# $    #",
                "#  $$  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 310,
            name = "Procedural-310",
            rows = listOf(
                " ###### ",
                "##@ ..# ",
                "#     # ",
                "#   ####",
                "#  #   #",
                "#      #",
                "##     #",
                "# $  ###",
                "# #  $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 311,
            name = "Procedural-311",
            rows = listOf(
                "####### ",
                "#. #. ##",
                "#      #",
                "#      #",
                "#. ##$ #",
                "# $@$  #",
                "## #   #",
                " #  ####",
                " #  #   ",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 312,
            name = "Procedural-312",
            rows = listOf(
                " #######",
                "##@ #  #",
                "#  ... #",
                "#  ##  #",
                "# $$   #",
                "# $ $  #",
                "#### . #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 313,
            name = "Procedural-313",
            rows = listOf(
                "#### ",
                "#@ # ",
                "# $# ",
                "#. ##",
                "#.$.#",
                "# $ #",
                "# $ #",
                "#. ##",
                "#  # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 314,
            name = "Procedural-314",
            rows = listOf(
                " ####   ",
                "##@ ### ",
                "# $$ .# ",
                "#.    # ",
                "##### ##",
                "# $  $ #",
                "#..    #",
                "########"
            )
        ),
        SokobanLevel(
            id = 315,
            name = "Procedural-315",
            rows = listOf(
                "    ####",
                "#####@ #",
                "#  ##  #",
                "#  ##  #",
                "##  #  #",
                " #  #  #",
                "### $  #",
                "#   $$ #",
                "#..  # #",
                "####.  #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 316,
            name = "Procedural-316",
            rows = listOf(
                " ###### ",
                "##  . ##",
                "#@$  $ #",
                "# $#.. #",
                "#   ####",
                "#   #   ",
                "#  ##   ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 317,
            name = "Procedural-317",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#  $   #",
                "# $@$  #",
                "###  ###",
                "##     #",
                "#     .#",
                "#.. ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 318,
            name = "Procedural-318",
            rows = listOf(
                " ####   ",
                "##. ### ",
                "#   $ # ",
                "#  #  ##",
                "## #$  #",
                " # #@$ #",
                "## ##  #",
                "#  .####",
                "#  .#   ",
                "#  ##   ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 319,
            name = "Procedural-319",
            rows = listOf(
                "########",
                "#@$  . #",
                "#  $$  #",
                "####   #",
                " #  $ ##",
                " #     #",
                " ###   #",
                "   #.  #",
                "   # #.#",
                "   #  .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 320,
            name = "Procedural-320",
            rows = listOf(
                "#####   ",
                "#@ .#   ",
                "# $.#   ",
                "## #####",
                "#  #.  #",
                "#    # #",
                "#      #",
                "#  ##  #",
                "# $  $ #",
                "##. $ ##",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 321,
            name = "Procedural-321",
            rows = listOf(
                "   #####",
                "####   #",
                "#    $ #",
                "# $$ $ #",
                "##@ # ##",
                " #  # # ",
                "##. # ##",
                "#   .  #",
                "#.     #",
                "##. ####",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 322,
            name = "Procedural-322",
            rows = listOf(
                "####### ",
                "#@ #  # ",
                "#. .  # ",
                "#  #  ##",
                "#      #",
                "#     $#",
                "#   #$.#",
                "## ##  #",
                "# $  $ #",
                "#  .#  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 323,
            name = "Procedural-323",
            rows = listOf(
                " ####   ",
                " #@ ####",
                " #     #",
                "###  ..#",
                "# $$$#.#",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 324,
            name = "Procedural-324",
            rows = listOf(
                "    ####",
                "   ##@.#",
                "   #  .#",
                "#### $.#",
                "#  # $##",
                "#    $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 325,
            name = "Procedural-325",
            rows = listOf(
                " ####   ",
                " #  #   ",
                " #. #   ",
                " # ##   ",
                " # #####",
                "##$#   #",
                "#@ *.$ #",
                "# # $  #",
                "#  .#  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 326,
            name = "Procedural-326",
            rows = listOf(
                "########",
                "#@     #",
                "#    #.#",
                "####$ .#",
                "####  .#",
                "#  $$  #",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 327,
            name = "Procedural-327",
            rows = listOf(
                " ####",
                " #@ #",
                "## .#",
                "# $ #",
                "# $ #",
                "# $##",
                "# $ #",
                "#.  #",
                "#. .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 328,
            name = "Procedural-328",
            rows = listOf(
                "   #### ",
                "####  # ",
                "#.   .# ",
                "# $ ### ",
                "#  #@ ##",
                "# $ $$ #",
                "## .  .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 329,
            name = "Procedural-329",
            rows = listOf(
                "#### ",
                "#+ ##",
                "#.$.#",
                "# $ #",
                "##$ #",
                " #  #",
                "##$ #",
                "#   #",
                "#   #",
                "## .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 330,
            name = "Procedural-330",
            rows = listOf(
                "#####   ",
                "#@  ### ",
                "#     # ",
                "####  ##",
                "#   #  #",
                "#      #",
                "##..# *#",
                "#### $ #",
                "#   $  #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 331,
            name = "Procedural-331",
            rows = listOf(
                "########",
                "#   #  #",
                "#      #",
                "# $ #  #",
                "## #.  #",
                " #   # #",
                " ###   #",
                " #@ $$##",
                " #    # ",
                " #..### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 332,
            name = "Procedural-332",
            rows = listOf(
                "########",
                "#   #@.#",
                "#   # .#",
                "#   #$ #",
                "##  $  #",
                " ##  # #",
                "##  $. #",
                "#  ##  #",
                "#   #  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 333,
            name = "Procedural-333",
            rows = listOf(
                "####### ",
                "#@    ##",
                "#  .  .#",
                "#  #  .#",
                "#     ##",
                "#      #",
                "##  #  #",
                " ## $  #",
                "  #$$# #",
                "  #    #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 334,
            name = "Procedural-334",
            rows = listOf(
                "####### ",
                "#+ #  # ",
                "#.  $ # ",
                "#. #$ ##",
                "#  # $ #",
                "#      #",
                "## #   #",
                "#   # ##",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 335,
            name = "Procedural-335",
            rows = listOf(
                "########",
                "#+ $   #",
                "# # $  #",
                "# $ ####",
                "#  $   #",
                "# #. # #",
                "#      #",
                "#      #",
                "# . #. #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 336,
            name = "Procedural-336",
            rows = listOf(
                "   #####",
                "####   #",
                "#      #",
                "#    $ #",
                "## ##$##",
                "# ..$@# ",
                "#.  ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 337,
            name = "Procedural-337",
            rows = listOf(
                "#######",
                "#. $@.#",
                "#   $ #",
                "#. # ##",
                "#$##$ #",
                "# .   #",
                "####  #",
                "   ####"
            )
        ),
        SokobanLevel(
            id = 338,
            name = "Procedural-338",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   #   #",
                "#####  #",
                "#.. #$ #",
                "# $$ $ #",
                "#..   ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 339,
            name = "Procedural-339",
            rows = listOf(
                " #######",
                " #@ #  #",
                " # *   #",
                "#### $.#",
                "#  ## .#",
                "#    $ #",
                "#  $  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 340,
            name = "Procedural-340",
            rows = listOf(
                "########",
                "#  ## .#",
                "#  $   #",
                "# $##  #",
                "# $ ####",
                "#. .#@ #",
                "## .#  #",
                "# $#   #",
                "#    # #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 341,
            name = "Procedural-341",
            rows = listOf(
                "########",
                "#   #@ #",
                "#    $ #",
                "##  #  #",
                "# $#####",
                "#     # ",
                "##$ # ##",
                "## #  .#",
                "#      #",
                "#    ..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 342,
            name = "Procedural-342",
            rows = listOf(
                "   #### ",
                "   #@ # ",
                "####  # ",
                "#     ##",
                "#      #",
                "#  #$$ #",
                "#  # $##",
                "####  .#",
                "   #  .#",
                "   ## .#",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 343,
            name = "Procedural-343",
            rows = listOf(
                "   #### ",
                "####  ##",
                "#      #",
                "#      #",
                "#   # ##",
                "## ##$# ",
                "#.  #@##",
                "#.  #$ #",
                "#.#  $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 344,
            name = "Procedural-344",
            rows = listOf(
                "  ####  ",
                "  #@ #  ",
                "  #  #  ",
                "  #  ###",
                " ###$  #",
                " # $   #",
                "##   $ #",
                "#.  #  #",
                "#.  #  #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 345,
            name = "Procedural-345",
            rows = listOf(
                "########",
                "#@     #",
                "# $ $  #",
                "## #   #",
                "# $ ####",
                "#$    # ",
                "#. ...# ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 346,
            name = "Procedural-346",
            rows = listOf(
                "########",
                "#@ #   #",
                "#  #   #",
                "#  # $##",
                "#   #  #",
                "#. .#$ #",
                "#.  #  #",
                "## # $ #",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 347,
            name = "Procedural-347",
            rows = listOf(
                "#####   ",
                "#   #   ",
                "#   #   ",
                "# $##   ",
                "##+##   ",
                "# * #   ",
                "#   ####",
                "##     #",
                " # $.# #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 348,
            name = "Procedural-348",
            rows = listOf(
                "########",
                "#+ #. .#",
                "#      #",
                "# $ $  #",
                "## #####",
                " #$#    ",
                "## #    ",
                "#  #### ",
                "#     # ",
                "#  #  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 349,
            name = "Procedural-349",
            rows = listOf(
                " #######",
                "##  # .#",
                "#  .$$ #",
                "#  #   #",
                "#  # $ #",
                "#  ##@##",
                "#### $ #",
                "#      #",
                "#    # #",
                "##..   #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 350,
            name = "Procedural-350",
            rows = listOf(
                "####    ",
                "#@.#####",
                "# .## .#",
                "#      #",
                "#      #",
                "# # # ##",
                "#      #",
                "#  $ $ #",
                "#  #$# #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 351,
            name = "Procedural-351",
            rows = listOf(
                "####    ",
                "#  #    ",
                "#  #####",
                "#  #@  #",
                "##$# $ #",
                "# $  # #",
                "#      #",
                "## ##  #",
                "#. ..  #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 352,
            name = "Procedural-352",
            rows = listOf(
                "####### ",
                "#     ##",
                "#   $$ #",
                "#. ##  #",
                "## #.$.#",
                " # # # #",
                "## #   #",
                "#@$##  #",
                "#  #   #",
                "####  .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 353,
            name = "Procedural-353",
            rows = listOf(
                " #######",
                "##   . #",
                "#      #",
                "#  #   #",
                "#   # ##",
                "#   #.# ",
                "#   # ##",
                "# $#.  #",
                "# $@$ $#",
                "##  # .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 354,
            name = "Procedural-354",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#  $#   ",
                "# $ ### ",
                "##    # ",
                " ##$  # ",
                "##    ##",
                "#..$#  #",
                "#     .#",
                "#.    ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 355,
            name = "Procedural-355",
            rows = listOf(
                "    ####",
                " ####@ #",
                " #  $$ #",
                "## # $ #",
                "# .#  ##",
                "#     # ",
                "#. .### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 356,
            name = "Procedural-356",
            rows = listOf(
                "####### ",
                "#  #  ##",
                "# $$   #",
                "#   #  #",
                "#.  #  #",
                "## ##  #",
                "#@$ ####",
                "#   #   ",
                "# #.#   ",
                "#  .#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 357,
            name = "Procedural-357",
            rows = listOf(
                "####### ",
                "# ..  ##",
                "# #   .#",
                "#   #  #",
                "# $##  #",
                "# $@ $ #",
                "##  #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 358,
            name = "Procedural-358",
            rows = listOf(
                " ######",
                " #@   #",
                " # $$ #",
                " #  ###",
                "## $#  ",
                "#   #  ",
                "#   ###",
                "#. #  #",
                "#.    #",
                "#. #  #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 359,
            name = "Procedural-359",
            rows = listOf(
                "########",
                "#  #   #",
                "#      #",
                "#  #####",
                "#   $@ #",
                "# #$   #",
                "# * *###",
                "#..  ## ",
                "####  # ",
                "   #  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 360,
            name = "Procedural-360",
            rows = listOf(
                "   #####",
                "#### ..#",
                "#@ #$ .#",
                "#  #  .#",
                "#  $ $##",
                "#  $  # ",
                "##  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 361,
            name = "Procedural-361",
            rows = listOf(
                "   #####",
                "   #@  #",
                "####$  #",
                "#   $$ #",
                "#     ##",
                "# #   # ",
                "#   ### ",
                "#    #  ",
                "# . .#  ",
                "##  .#  ",
                " #####  "
            )
        ),
        SokobanLevel(
            id = 362,
            name = "Procedural-362",
            rows = listOf(
                "########",
                "# .    #",
                "#  $$  #",
                "# $#   #",
                "#*@#####",
                "#   ..# ",
                "#  #  # ",
                "##  ### ",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 363,
            name = "Procedural-363",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#.  #  #",
                "#. ##  #",
                "#  #   #",
                "#    # #",
                "##  $  #",
                "#    $##",
                "# .#$*@#",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 364,
            name = "Procedural-364",
            rows = listOf(
                "    ####",
                " ####  #",
                " #  .  #",
                "## ##$ #",
                "#@$ $  #",
                "#    $ #",
                "#  ## ##",
                "#      #",
                "# #.   #",
                "#  .# .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 365,
            name = "Procedural-365",
            rows = listOf(
                "   #####",
                " ###  .#",
                " #     #",
                "##$    #",
                "#.  ####",
                "#.#  .# ",
                "#   # ##",
                "#  ##  #",
                "# $@$$ #",
                "##    ##",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 366,
            name = "Procedural-366",
            rows = listOf(
                "#####",
                "#   #",
                "#   #",
                "##$ #",
                "#. .#",
                "# $ #",
                "#. ##",
                "#$ # ",
                "#@$# ",
                "#. # ",
                "#### "
            )
        ),
        SokobanLevel(
            id = 367,
            name = "Procedural-367",
            rows = listOf(
                "########",
                "#@     #",
                "#      #",
                "####  ##",
                "##  $$ #",
                "#  $   #",
                "#...####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 368,
            name = "Procedural-368",
            rows = listOf(
                "   #####",
                "   #  .#",
                "####$. #",
                "#@$    #",
                "# $$ .##",
                "#. #  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 369,
            name = "Procedural-369",
            rows = listOf(
                " ###### ",
                "##@ . # ",
                "# $ $$# ",
                "#  #* ##",
                "##     #",
                " #  .# #",
                " ###.  #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 370,
            name = "Procedural-370",
            rows = listOf(
                "########",
                "#@   $.#",
                "#    $ #",
                "#####  #",
                "#   # .#",
                "#  $ $.#",
                "##### .#",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 371,
            name = "Procedural-371",
            rows = listOf(
                " #######",
                "##  #  #",
                "#   #$ #",
                "#   #  #",
                "# .##  #",
                "#. #@$ #",
                "#..#$  #",
                "##     #",
                " # $ # #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 372,
            name = "Procedural-372",
            rows = listOf(
                "########",
                "#@     #",
                "#   $$ #",
                "##  #  #",
                "#  . $##",
                "# #.  # ",
                "#.  .$# ",
                "####  # ",
                "   #  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 373,
            name = "Procedural-373",
            rows = listOf(
                "    ####",
                "   ##@ #",
                "   #.  #",
                "####.  #",
                "#   #  #",
                "#*  $  #",
                "#  . $ #",
                "###$ ###",
                "  #    #",
                "  #    #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 374,
            name = "Procedural-374",
            rows = listOf(
                " ####   ",
                " #  ####",
                " # $@. #",
                "## $   #",
                "# $ ####",
                "#.  #   ",
                "#.$.#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 375,
            name = "Procedural-375",
            rows = listOf(
                "   #####",
                " ###   #",
                " #     #",
                "##. ####",
                "#@$$  .#",
                "# $ $  #",
                "#### ..#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 376,
            name = "Procedural-376",
            rows = listOf(
                "########",
                "#@ $.  #",
                "#.# $  #",
                "#. $ $##",
                "#  #   #",
                "#. #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 377,
            name = "Procedural-377",
            rows = listOf(
                "#####   ",
                "#.  #   ",
                "#. .#   ",
                "# $ #   ",
                "#  #### ",
                "#  $  # ",
                "##    ##",
                "####$$@#",
                "#     .#",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 378,
            name = "Procedural-378",
            rows = listOf(
                " ####   ",
                "##  ### ",
                "#@* . # ",
                "#  $$ ##",
                "#   #  #",
                "# #$.  #",
                "#   # .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 379,
            name = "Procedural-379",
            rows = listOf(
                "####   ",
                "#@ ##  ",
                "#. .#  ",
                "#   ###",
                "#  # .#",
                "# $ $.#",
                "##    #",
                "# $####",
                "#   $ #",
                "#     #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 380,
            name = "Procedural-380",
            rows = listOf(
                "####### ",
                "#@ #  # ",
                "#  #  # ",
                "#  #  # ",
                "# $##$##",
                "# $  $ #",
                "##.. ..#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 381,
            name = "Procedural-381",
            rows = listOf(
                "   #####",
                " ###. .#",
                " #@$   #",
                "##$#.  #",
                "#. ##$##",
                "#  $   #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 382,
            name = "Procedural-382",
            rows = listOf(
                "####   ",
                "#@ ####",
                "#     #",
                "#   *$#",
                "### *.#",
                "# $ # #",
                "#   . #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 383,
            name = "Procedural-383",
            rows = listOf(
                " ######",
                " #@.  #",
                " # .  #",
                "## *$##",
                "#  # ##",
                "#  $  #",
                "# $ . #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 384,
            name = "Procedural-384",
            rows = listOf(
                "    ####",
                "   ## .#",
                "   #   #",
                "   #   #",
                "   ## ##",
                "##### # ",
                "#@  # ##",
                "#  $ $ #",
                "# #$  .#",
                "# * . ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 385,
            name = "Procedural-385",
            rows = listOf(
                "   #### ",
                "####  # ",
                "#@ #$ # ",
                "# $   ##",
                "##     #",
                "#   #  #",
                "# $ # .#",
                "#  #   #",
                "#  #. .#",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 386,
            name = "Procedural-386",
            rows = listOf(
                " #######",
                "##+ #  #",
                "# .$$ *#",
                "#      #",
                "#####  #",
                "#      #",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 387,
            name = "Procedural-387",
            rows = listOf(
                "####    ",
                "#@ #####",
                "#. #.  #",
                "#$$    #",
                "#  $.$##",
                "#  #. # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 388,
            name = "Procedural-388",
            rows = listOf(
                "########",
                "#  .#@ #",
                "#  $$  #",
                "#. .#  #",
                "## $# ##",
                " # *  # ",
                " #  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 389,
            name = "Procedural-389",
            rows = listOf(
                " #######",
                "## .#  #",
                "#.  #  #",
                "# $ #  #",
                "#  ##  #",
                "#   $$.#",
                "## .$@##",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 390,
            name = "Procedural-390",
            rows = listOf(
                "########",
                "#  #   #",
                "# $    #",
                "#  $####",
                "##$@#..#",
                " # $   #",
                " #   ..#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 391,
            name = "Procedural-391",
            rows = listOf(
                "   #####",
                "   #   #",
                "####   #",
                "#+  #* #",
                "#  *   #",
                "#   $$.#",
                "#####  #",
                "    #  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 392,
            name = "Procedural-392",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#   #  #",
                "# $ #  #",
                "##    ##",
                " ##$ ## ",
                " #+$* ##",
                " #     #",
                " #. #  #",
                " #. #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 393,
            name = "Procedural-393",
            rows = listOf(
                " #######",
                "##  #  #",
                "#   $  #",
                "#  ##$ #",
                "#    $ #",
                "# #   .#",
                "# ... ##",
                "#####$@#",
                "    #  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 394,
            name = "Procedural-394",
            rows = listOf(
                "########",
                "#@     #",
                "# $ $  #",
                "## ##  #",
                "#.$.####",
                "#   .. #",
                "##$    #",
                " # #  ##",
                " #     #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 395,
            name = "Procedural-395",
            rows = listOf(
                "########",
                "#   #@ #",
                "#  $#  #",
                "##  $$ #",
                "#.    ##",
                "#  # $ #",
                "#  #   #",
                "## #####",
                "#     # ",
                "#...  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 396,
            name = "Procedural-396",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@$   #",
                "## ## ##",
                "#.. # ##",
                "#   $  #",
                "##.## *#",
                "#  # $ #",
                "#  #   #",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 397,
            name = "Procedural-397",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@$ # #",
                " # $  .#",
                "## .# ##",
                "#  $*.# ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 398,
            name = "Procedural-398",
            rows = listOf(
                "######  ",
                "#  . #  ",
                "# # $#  ",
                "#  *@###",
                "### $  #",
                "#  .*  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 399,
            name = "Procedural-399",
            rows = listOf(
                " #######",
                "##@.   #",
                "# $  # #",
                "#  $   #",
                "#  #  ##",
                "# $  ## ",
                "##    ##",
                " #$ # .#",
                " #     #",
                " ### ..#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 400,
            name = "Procedural-400",
            rows = listOf(
                "   #### ",
                " ###@ ##",
                " #  $$ #",
                "## # $ #",
                "# $    #",
                "#   ####",
                "##  ### ",
                "# .   # ",
                "# #   # ",
                "#..  .# ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 401,
            name = "Procedural-401",
            rows = listOf(
                "#####",
                "#   #",
                "#   #",
                "# $.#",
                "##@##",
                "# $ #",
                "#.  #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 402,
            name = "Procedural-402",
            rows = listOf(
                "#####",
                "#.  #",
                "# $ #",
                "##@##",
                "#.$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 403,
            name = "Procedural-403",
            rows = listOf(
                "#####",
                "#@  #",
                "# $ #",
                "##  #",
                " #$.#",
                " # .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 404,
            name = "Procedural-404",
            rows = listOf(
                "#######",
                "#+ .  #",
                "# $ $ #",
                "####  #",
                "   ####"
            )
        ),
        SokobanLevel(
            id = 405,
            name = "Procedural-405",
            rows = listOf(
                "#### ",
                "#  # ",
                "#  ##",
                "# $@#",
                "#*  #",
                "#  .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 406,
            name = "Procedural-406",
            rows = listOf(
                "######  ",
                "#@   #  ",
                "# #  #  ",
                "#  $ ###",
                "####.$ #",
                "   #.  #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 407,
            name = "Procedural-407",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "# $ #",
                "##$.#",
                " # .#",
                " #  #",
                " ####"
            )
        ),
        SokobanLevel(
            id = 408,
            name = "Procedural-408",
            rows = listOf(
                "#####",
                "#   #",
                "#.  #",
                "## *#",
                "#@$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 409,
            name = "Procedural-409",
            rows = listOf(
                "##### ",
                "#   ##",
                "#* $@#",
                "# #  #",
                "# .  #",
                "######"
            )
        ),
        SokobanLevel(
            id = 410,
            name = "Procedural-410",
            rows = listOf(
                "#####",
                "#   #",
                "#   #",
                "# $##",
                "#   #",
                "#   #",
                "#. .#",
                "## ##",
                "#@$ #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 411,
            name = "Procedural-411",
            rows = listOf(
                "########",
                "#  ##@.#",
                "#.   $ #",
                "#  ##  #",
                "# $ ####",
                "#   #   ",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 412,
            name = "Procedural-412",
            rows = listOf(
                "#######",
                "#@..  #",
                "#   $ #",
                "####$ #",
                "  #   #",
                "  #   #",
                "  #   #",
                "  #####"
            )
        ),
        SokobanLevel(
            id = 413,
            name = "Procedural-413",
            rows = listOf(
                "    ####",
                "    #  #",
                "#####$ #",
                "#@  # .#",
                "#  $   #",
                "####  .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 414,
            name = "Procedural-414",
            rows = listOf(
                "########",
                "#  #@  #",
                "#  #   #",
                "#   #  #",
                "#  $.* #",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 415,
            name = "Procedural-415",
            rows = listOf(
                "  #### ",
                "###@ # ",
                "# $ $# ",
                "#    ##",
                "##    #",
                "#.. # #",
                "#     #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 416,
            name = "Procedural-416",
            rows = listOf(
                "   #####",
                "####@ .#",
                "#  $$  #",
                "#    .##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 417,
            name = "Procedural-417",
            rows = listOf(
                " ####",
                "##@.#",
                "#.  #",
                "# $ #",
                "# $##",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 418,
            name = "Procedural-418",
            rows = listOf(
                "#####",
                "#   #",
                "#   #",
                "#. .#",
                "## ##",
                " #$# ",
                "##@# ",
                "# $##",
                "#   #",
                "# $.#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 419,
            name = "Procedural-419",
            rows = listOf(
                "########",
                "#@ #   #",
                "#  $$  #",
                "#      #",
                "#  ##  #",
                "#  #.  #",
                "####.  #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 420,
            name = "Procedural-420",
            rows = listOf(
                " ######",
                "##@   #",
                "# $   #",
                "#  ####",
                "#.  #  ",
                "# $.#  ",
                "#  ##  ",
                "####   "
            )
        ),
        SokobanLevel(
            id = 421,
            name = "Procedural-421",
            rows = listOf(
                "########",
                "#@ #   #",
                "# $#   #",
                "#.*    #",
                "###  ###",
                "  #  #  ",
                "  #  #  ",
                "  ####  "
            )
        ),
        SokobanLevel(
            id = 422,
            name = "Procedural-422",
            rows = listOf(
                "#### ",
                "#@ ##",
                "# $ #",
                "#.$.#",
                "#.$##",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 423,
            name = "Procedural-423",
            rows = listOf(
                "   #####",
                "####@  #",
                "#  #$# #",
                "#. # $ #",
                "#      #",
                "#.   # #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 424,
            name = "Procedural-424",
            rows = listOf(
                " ####",
                " #@ #",
                "##$ #",
                "#   #",
                "# #.#",
                "#.  #",
                "# $##",
                "# $ #",
                "#  .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 425,
            name = "Procedural-425",
            rows = listOf(
                "########",
                "#@  #  #",
                "#  $   #",
                "#   #  #",
                "#  # $##",
                "#   . # ",
                "#  .### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 426,
            name = "Procedural-426",
            rows = listOf(
                "########",
                "#@     #",
                "# $ $  #",
                "####   #",
                "#. .#  #",
                "#      #",
                "##     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 427,
            name = "Procedural-427",
            rows = listOf(
                "   #####",
                "   #  .#",
                "   #   #",
                "##### ##",
                "#@  #$ #",
                "# # $  #",
                "#  *.  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 428,
            name = "Procedural-428",
            rows = listOf(
                "########",
                "#@ #   #",
                "#     .#",
                "#  #  ##",
                "####   #",
                " #  $  #",
                " #  $. #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 429,
            name = "Procedural-429",
            rows = listOf(
                " ####   ",
                "##@ ### ",
                "#  .. # ",
                "#  ##$##",
                "#   #  #",
                "#   $  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 430,
            name = "Procedural-430",
            rows = listOf(
                "#####   ",
                "#+. ### ",
                "#     # ",
                "#    $# ",
                "####  ##",
                "#### $ #",
                "#      #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 431,
            name = "Procedural-431",
            rows = listOf(
                " #######",
                " #  . .#",
                " #  $  #",
                "##### ##",
                "#@ #   #",
                "# $    #",
                "#  #   #",
                "#   ####",
                "#   #   ",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 432,
            name = "Procedural-432",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "# $$####",
                "###  . #",
                "#      #",
                "#   # .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 433,
            name = "Procedural-433",
            rows = listOf(
                "####### ",
                "#@    # ",
                "# $   ##",
                "# $##. #",
                "# $ #  #",
                "#. .####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 434,
            name = "Procedural-434",
            rows = listOf(
                "   #####",
                " ###  .#",
                " #  .  #",
                " # ## ##",
                " #  # ##",
                " ##    #",
                "##     #",
                "#@$ #  #",
                "# #$   #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 435,
            name = "Procedural-435",
            rows = listOf(
                "########",
                "#+ ##  #",
                "# $$   #",
                "#. #   #",
                "#  #   #",
                "#  #####",
                "####    "
            )
        ),
        SokobanLevel(
            id = 436,
            name = "Procedural-436",
            rows = listOf(
                "########",
                "#  #@  #",
                "#  $   #",
                "# $ # ##",
                "## #.  #",
                " #  .  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 437,
            name = "Procedural-437",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   #   ",
                "##  ####",
                "# $#  .#",
                "#  $   #",
                "#  ## ##",
                "####. ##",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 438,
            name = "Procedural-438",
            rows = listOf(
                "#####",
                "#@  #",
                "# $ #",
                "##$ #",
                "# $ #",
                "#  .#",
                "#. .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 439,
            name = "Procedural-439",
            rows = listOf(
                "   #### ",
                "   #  # ",
                "####$ # ",
                "#     # ",
                "#   # # ",
                "##### # ",
                " #### ##",
                " #@  $ #",
                " #  . .#",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 440,
            name = "Procedural-440",
            rows = listOf(
                "####### ",
                "#@   .##",
                "#$$$ $ #",
                "#..# . #",
                "########"
            )
        ),
        SokobanLevel(
            id = 441,
            name = "Procedural-441",
            rows = listOf(
                " ####   ",
                " #  ### ",
                " #    # ",
                "##    ##",
                "#  ##  #",
                "#   $$ #",
                "##  #@##",
                " ###. .#",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 442,
            name = "Procedural-442",
            rows = listOf(
                "#### ",
                "#  # ",
                "#  ##",
                "# $@#",
                "#.$ #",
                "# $ #",
                "##  #",
                " # .#",
                " # .#",
                " ####"
            )
        ),
        SokobanLevel(
            id = 443,
            name = "Procedural-443",
            rows = listOf(
                "#####",
                "#@  #",
                "#   #",
                "## ##",
                "#. .#",
                "#  .#",
                "# $ #",
                "##$ #",
                "# $ #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 444,
            name = "Procedural-444",
            rows = listOf(
                "########",
                "#@     #",
                "#      #",
                "###  ###",
                "# $ $ # ",
                "#     # ",
                "#  #####",
                "## #   #",
                "#      #",
                "#. .   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 445,
            name = "Procedural-445",
            rows = listOf(
                " #######",
                "##@.  .#",
                "#      #",
                "#  #  ##",
                "#  ##  #",
                "#      #",
                "#  #   #",
                "#    $ #",
                "#  $####",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 446,
            name = "Procedural-446",
            rows = listOf(
                "#####",
                "#.  #",
                "# $ #",
                "##@##",
                "# $ #",
                "#   #",
                "#. ##",
                "#.$.#",
                "# $ #",
                "#  ##",
                "#### "
            )
        ),
        SokobanLevel(
            id = 447,
            name = "Procedural-447",
            rows = listOf(
                "#####   ",
                "#   #   ",
                "#   #   ",
                "##$ ####",
                " #  #+.#",
                " # $ $.#",
                " #  #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 448,
            name = "Procedural-448",
            rows = listOf(
                "####### ",
                "#..#  ##",
                "#$$$   #",
                "#@  #  #",
                "#### $ #",
                "   #. .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 449,
            name = "Procedural-449",
            rows = listOf(
                "#####",
                "#   #",
                "#   #",
                "##$ #",
                "#   #",
                "#.$.#",
                "##@##",
                "#.$ #",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 450,
            name = "Procedural-450",
            rows = listOf(
                " ####",
                " #@.#",
                " #$ #",
                "## $#",
                "#   #",
                "# # #",
                "#. .#",
                "# $##",
                "# $ #",
                "#  .#",
                "#####"
            )
        ),
        SokobanLevel(
            id = 451,
            name = "Procedural-451",
            rows = listOf(
                "    ####",
                " ####@.#",
                " #     #",
                "## ## .#",
                "# $ #  #",
                "#  $   #",
                "#  #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 452,
            name = "Procedural-452",
            rows = listOf(
                "########",
                "#@     #",
                "# #$   #",
                "# $ #  #",
                "#      #",
                "####   #",
                "   #   #",
                " ###  ##",
                " #..  # ",
                " #  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 453,
            name = "Procedural-453",
            rows = listOf(
                "####### ",
                "#+ .  ##",
                "# $$ $ #",
                "## # . #",
                " #  ####",
                " #    # ",
                " #    # ",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 454,
            name = "Procedural-454",
            rows = listOf(
                "#####   ",
                "#. .### ",
                "#     # ",
                "#  # $##",
                "####   #",
                "#@ $   #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 455,
            name = "Procedural-455",
            rows = listOf(
                "#####",
                "#. .#",
                "#.  #",
                "# $ #",
                "#$ ##",
                "# $@#",
                "# $ #",
                "#. ##",
                "#### "
            )
        ),
        SokobanLevel(
            id = 456,
            name = "Procedural-456",
            rows = listOf(
                "########",
                "# ..#@ #",
                "#   $$ #",
                "#  ##  #",
                "#  # $ #",
                "#. #   #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 457,
            name = "Procedural-457",
            rows = listOf(
                "    ####",
                " ####. #",
                " #  #. #",
                "##    $#",
                "#@$ $  #",
                "#  # #.#",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 458,
            name = "Procedural-458",
            rows = listOf(
                "    ####",
                "    #@.#",
                "##### .#",
                "#   # .#",
                "#      #",
                "## #   #",
                "#   # ##",
                "#  $$  #",
                "#   $  #",
                "#  #  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 459,
            name = "Procedural-459",
            rows = listOf(
                "   #####",
                "   #@ .#",
                "   # $ #",
                "##### ##",
                "#  #  .#",
                "# $$$  #",
                "#    ..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 460,
            name = "Procedural-460",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   #   #",
                "#### $ #",
                "# ..#  #",
                "# $  $ #",
                "## .  ##",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 461,
            name = "Procedural-461",
            rows = listOf(
                "###### ",
                "#.   ##",
                "# $   #",
                "#  #$ #",
                "#### ##",
                "#+$ $ #",
                "#..   #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 462,
            name = "Procedural-462",
            rows = listOf(
                "########",
                "#+  #. #",
                "#      #",
                "##  #  #",
                "#      #",
                "#    # #",
                "####   #",
                "#      #",
                "# $ $  #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 463,
            name = "Procedural-463",
            rows = listOf(
                "########",
                "#  #  .#",
                "#  #   #",
                "#  ## ##",
                "#   #  #",
                "# # .. #",
                "#      #",
                "# $ #  #",
                "# $$#  #",
                "##@ #  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 464,
            name = "Procedural-464",
            rows = listOf(
                "#######",
                "#@ #  #",
                "#*$.. #",
                "#     #",
                "#  #. #",
                "# $ $ #",
                "#   ###",
                "#####  "
            )
        ),
        SokobanLevel(
            id = 465,
            name = "Procedural-465",
            rows = listOf(
                " #######",
                " #     #",
                " #  $  #",
                " ### $ #",
                "##+.# $#",
                "#     .#",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 466,
            name = "Procedural-466",
            rows = listOf(
                "   #####",
                "####.  #",
                "#@$... #",
                "#  $ $ #",
                "#####$ #",
                "    #  #",
                "   ##  #",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 467,
            name = "Procedural-467",
            rows = listOf(
                " #######",
                " #    .#",
                " #     #",
                "##$ # .#",
                "#   # .#",
                "#  $@$ #",
                "#  .$ ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 468,
            name = "Procedural-468",
            rows = listOf(
                "#####   ",
                "#  .####",
                "# ...$@#",
                "# $ $  #",
                "# $#####",
                "#  #    ",
                "#  ##   ",
                "#   #   ",
                "#   #   ",
                "#   #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 469,
            name = "Procedural-469",
            rows = listOf(
                "########",
                "#@     #",
                "#      #",
                "# $ #  #",
                "#  ##  #",
                "# $ $  #",
                "#  ##  #",
                "##$ .. #",
                " # ..  #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 470,
            name = "Procedural-470",
            rows = listOf(
                " ####",
                "##@ #",
                "# $ #",
                "#.$.#",
                "##$ #",
                " # .#",
                "##$.#",
                "#   #",
                "#   #",
                "#####"
            )
        ),
        SokobanLevel(
            id = 471,
            name = "Procedural-471",
            rows = listOf(
                "   ####",
                "   #@ #",
                "####$ #",
                "# .$ ##",
                "#  $  #",
                "#.# $.#",
                "#    .#",
                "#######"
            )
        ),
        SokobanLevel(
            id = 472,
            name = "Procedural-472",
            rows = listOf(
                "########",
                "#@ #   #",
                "# $$   #",
                "#  #  ##",
                "##$    #",
                " #     #",
                " # .#  #",
                " #### ##",
                " #     #",
                " #  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 473,
            name = "Procedural-473",
            rows = listOf(
                "  #####",
                "  #@ .#",
                "  # ..#",
                "  #   #",
                "###  ##",
                "#  $ # ",
                "# $$ ##",
                "# #   #",
                "#  $  #",
                "### . #",
                "  #####"
            )
        ),
        SokobanLevel(
            id = 474,
            name = "Procedural-474",
            rows = listOf(
                "   #### ",
                "   #. ##",
                "####  .#",
                "# $    #",
                "#   # ##",
                "# $$# # ",
                "##@ # # ",
                " ###. ##",
                "   #   #",
                "   #   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 475,
            name = "Procedural-475",
            rows = listOf(
                "########",
                "#@  #  #",
                "#      #",
                "## ##  #",
                " #    ##",
                " ## $## ",
                "## $  ##",
                "# $ # .#",
                "# . .  #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 476,
            name = "Procedural-476",
            rows = listOf(
                "########",
                "#..    #",
                "#  $$  #",
                "# $ # ##",
                "#. ##$@#",
                "#. ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 477,
            name = "Procedural-477",
            rows = listOf(
                "#####   ",
                "#.. #   ",
                "# . ####",
                "##*#@  #",
                "#   $  #",
                "#  $ $ #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 478,
            name = "Procedural-478",
            rows = listOf(
                "######  ",
                "#    #  ",
                "# #  #  ",
                "#    ###",
                "#.$##  #",
                "#      #",
                "# $ #  #",
                "#.$## ##",
                "#. #@$ #",
                "#. #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 479,
            name = "Procedural-479",
            rows = listOf(
                " #####  ",
                "## . #  ",
                "#    #  ",
                "#  $ ## ",
                "## #$@# ",
                "#   $ # ",
                "#  #####",
                "#      #",
                "#      #",
                "#   #..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 480,
            name = "Procedural-480",
            rows = listOf(
                " #######",
                "##  #  #",
                "#  ..  #",
                "#  ## .#",
                "# $## ##",
                "# $@$  #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 481,
            name = "Procedural-481",
            rows = listOf(
                "########",
                "#   #  #",
                "#   $  #",
                "####@$ #",
                "##    ##",
                "#     # ",
                "#  #####",
                "#      #",
                "#.#    #",
                "#.    ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 482,
            name = "Procedural-482",
            rows = listOf(
                "########",
                "#   .$ #",
                "#  $   #",
                "####  .#",
                "#  .# $#",
                "#      #",
                "#  .# ##",
                "####@$ #",
                "   #   #",
                "   #  ##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 483,
            name = "Procedural-483",
            rows = listOf(
                "########",
                "#   #@ #",
                "# # #  #",
                "#   #$ #",
                "#   # .#",
                "# # $$ #",
                "#.  .$.#",
                "########"
            )
        ),
        SokobanLevel(
            id = 484,
            name = "Procedural-484",
            rows = listOf(
                " #######",
                "## ..  #",
                "#+$  * #",
                "#  ##$ #",
                "# $#   #",
                "#  #   #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 485,
            name = "Procedural-485",
            rows = listOf(
                "#####   ",
                "#   ####",
                "#$  $@ #",
                "# $#   #",
                "#.$ ####",
                "#      #",
                "#.   ..#",
                "########"
            )
        ),
        SokobanLevel(
            id = 486,
            name = "Procedural-486",
            rows = listOf(
                " #######",
                " #@ # .#",
                "##*$ $ #",
                "#      #",
                "# * ####",
                "#. ##   ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 487,
            name = "Procedural-487",
            rows = listOf(
                "########",
                "#  #   #",
                "#  $   #",
                "# $ ####",
                "##@#   #",
                "# $    #",
                "#   # ##",
                "## #   #",
                " #  . .#",
                " ###.  #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 488,
            name = "Procedural-488",
            rows = listOf(
                "   #### ",
                "####  ##",
                "#   $  #",
                "#  $ * #",
                "## #   #",
                " # # #.#",
                "## #  .#",
                "#@$#####",
                "#  #    ",
                "# .#    ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 489,
            name = "Procedural-489",
            rows = listOf(
                "########",
                "#.  .  #",
                "#.$$   #",
                "##@ # ##",
                "##$##.# ",
                "#   $ # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 490,
            name = "Procedural-490",
            rows = listOf(
                "########",
                "#.  #+ #",
                "#.#$$  #",
                "#.  #  #",
                "#  $# ##",
                "#   $ # ",
                "##  ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 491,
            name = "Procedural-491",
            rows = listOf(
                "########",
                "#. #   #",
                "#      #",
                "#. #####",
                "#  #@  #",
                "#  #   #",
                "#.$#   #",
                "## ##  #",
                "# $  $ #",
                "#     ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 492,
            name = "Procedural-492",
            rows = listOf(
                "########",
                "#@  #  #",
                "#      #",
                "##### .#",
                " ###. .#",
                " #.    #",
                "##  # ##",
                "# $$   #",
                "#  $$  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 493,
            name = "Procedural-493",
            rows = listOf(
                "#####   ",
                "#   #   ",
                "# # #   ",
                "#   ####",
                "#. #   #",
                "#.$. # #",
                "## # $ #",
                "## # $##",
                "#   $@# ",
                "#.  ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 494,
            name = "Procedural-494",
            rows = listOf(
                " #######",
                " #     #",
                " #     #",
                "####  ##",
                "#@.##$##",
                "# $$ $ #",
                "#      #",
                "###  ###",
                "  #  .# ",
                "  # ..# ",
                "  ##### "
            )
        ),
        SokobanLevel(
            id = 495,
            name = "Procedural-495",
            rows = listOf(
                "#####   ",
                "#@..#   ",
                "#  .####",
                "# .#   #",
                "#  $ # #",
                "# $$$  #",
                "###    #",
                "  #  # #",
                "  #    #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 496,
            name = "Procedural-496",
            rows = listOf(
                "   #####",
                "####   #",
                "#      #",
                "#  $####",
                "##. .#  ",
                " ##  #  ",
                " #@*.###",
                "## $   #",
                "#   #$ #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 497,
            name = "Procedural-497",
            rows = listOf(
                "#####   ",
                "#+ .####",
                "#  .#  #",
                "##  #  #",
                "#   #  #",
                "#      #",
                "#  ## ##",
                "#  $   #",
                "# #$$  #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 498,
            name = "Procedural-498",
            rows = listOf(
                "########",
                "#+ . . #",
                "#      #",
                "#  #   #",
                "##  ####",
                " # $  # ",
                " # $ $##",
                "####   #",
                "#    $ #",
                "#     .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 499,
            name = "Procedural-499",
            rows = listOf(
                "####### ",
                "#   . ##",
                "#  $. .#",
                "#   #$ #",
                "#  ##@##",
                "#  # * #",
                "####   #",
                "#   #  #",
                "#    $ #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 500,
            name = "Procedural-500",
            rows = listOf(
                "  #####",
                "###+..#",
                "#  $  #",
                "#  $ ##",
                "#  ####",
                "# $$ .#",
                "#     #",
                "#######"
            )
        ),
        SokobanLevel(
            id = 501,
            name = "Procedural-501",
            rows = listOf(
                "  ######",
                "  #    #",
                "  #    #",
                "  #  $##",
                "  ##$$+#",
                "   #  .#",
                "   ## .#",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 502,
            name = "Procedural-502",
            rows = listOf(
                "  ####  ",
                "  # .#  ",
                "  #  #  ",
                "###$$## ",
                "#..  .##",
                "#  #$$@#",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 503,
            name = "Procedural-503",
            rows = listOf(
                "#####   ",
                "#. .#   ",
                "#.$.#   ",
                "# $ ####",
                "#  #@  #",
                "# $$   #",
                "##     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 504,
            name = "Procedural-504",
            rows = listOf(
                " ###### ",
                "##  $+##",
                "#  $ $.#",
                "#.  #$ #",
                "####  ##",
                "   # .# ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 505,
            name = "Procedural-505",
            rows = listOf(
                "########",
                "#+*.   #",
                "# $ $  #",
                "####. ##",
                "#   #$ #",
                "#      #",
                "####  ##",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 506,
            name = "Procedural-506",
            rows = listOf(
                " #######",
                "## .  .#",
                "#      #",
                "#  #  .#",
                "#     ##",
                "# $$$ # ",
                "##@ ### ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 507,
            name = "Procedural-507",
            rows = listOf(
                " #######",
                "##  #  #",
                "#      #",
                "#  ##  #",
                "#* .#$##",
                "# $ $@# ",
                "#. .### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 508,
            name = "Procedural-508",
            rows = listOf(
                "#####   ",
                "#@  ####",
                "# $  $ #",
                "#      #",
                "# $#  ##",
                "#      #",
                "## . ..#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 509,
            name = "Procedural-509",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#   ####",
                "#      #",
                "### $  #",
                "  #.$#.#",
                "  # *  #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 510,
            name = "Procedural-510",
            rows = listOf(
                "########",
                "#@  . .#",
                "# $$$  #",
                "#  #  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 511,
            name = "Procedural-511",
            rows = listOf(
                "########",
                "#   #@ #",
                "#   $  #",
                "##$#   #",
                " # .#  #",
                " #$ .  #",
                " # .   #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 512,
            name = "Procedural-512",
            rows = listOf(
                "########",
                "#@  . .#",
                "# $$ $ #",
                "## ##$##",
                "#  #.  #",
                "# .#   #",
                "#####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 513,
            name = "Procedural-513",
            rows = listOf(
                "####    ",
                "#..#    ",
                "#  #####",
                "#$$#.  #",
                "#@$  $ #",
                "##  . ##",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 514,
            name = "Procedural-514",
            rows = listOf(
                "########",
                "#+ .  .#",
                "# $ $  #",
                "##$#####",
                "##     #",
                "#    # #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 515,
            name = "Procedural-515",
            rows = listOf(
                " #######",
                "##  ...#",
                "#    $ #",
                "#  ##$##",
                "##$@   #",
                " #     #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 516,
            name = "Procedural-516",
            rows = listOf(
                "    ####",
                "   ##@ #",
                "   # $ #",
                " ### $ #",
                " #..  .#",
                " # $####",
                " #  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 517,
            name = "Procedural-517",
            rows = listOf(
                " #######",
                "##  #  #",
                "#@$    #",
                "# $## .#",
                "#. .#$##",
                "#     # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 518,
            name = "Procedural-518",
            rows = listOf(
                "   #### ",
                " ###  # ",
                " #.   # ",
                "##  ### ",
                "#@$#####",
                "# $ $  #",
                "#  ..  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 519,
            name = "Procedural-519",
            rows = listOf(
                " ####   ",
                " #  ####",
                " #.. $@#",
                " ### $ #",
                " # $$# #",
                " #..   #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 520,
            name = "Procedural-520",
            rows = listOf(
                " ####   ",
                " #+ #   ",
                "##$ ### ",
                "#. $  # ",
                "#$  * # ",
                "#.  ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 521,
            name = "Procedural-521",
            rows = listOf(
                "####### ",
                "#  #@ # ",
                "#  #  # ",
                "#  #  # ",
                "##$## ##",
                "# $ $  #",
                "#.  . .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 522,
            name = "Procedural-522",
            rows = listOf(
                "####### ",
                "#@    ##",
                "#      #",
                "#   #  #",
                "# $## ##",
                "# $ $  #",
                "##  ...#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 523,
            name = "Procedural-523",
            rows = listOf(
                " #######",
                "##@ #  #",
                "#   $$ #",
                "#. #   #",
                "#    $##",
                "#     # ",
                "#. .### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 524,
            name = "Procedural-524",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "#...#   ",
                "#$ #####",
                "# $#   #",
                "#  $   #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 525,
            name = "Procedural-525",
            rows = listOf(
                " #######",
                " #  #@ #",
                " #.$#  #",
                " #. #  #",
                " #. $ ##",
                " #  $  #",
                " ###   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 526,
            name = "Procedural-526",
            rows = listOf(
                "########",
                "#  .#  #",
                "#   #  #",
                "#. .#  #",
                "## ##$ #",
                "#@$ $  #",
                "# *    #",
                "########"
            )
        ),
        SokobanLevel(
            id = 527,
            name = "Procedural-527",
            rows = listOf(
                " #######",
                "##@ . .#",
                "#      #",
                "# $##$##",
                "## # . #",
                "#  $$  #",
                "#   . ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 528,
            name = "Procedural-528",
            rows = listOf(
                "########",
                "#@  . .#",
                "#  $ $ #",
                "#####$##",
                "#. #   #",
                "#      #",
                "#  #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 529,
            name = "Procedural-529",
            rows = listOf(
                "   #####",
                "####@  #",
                "#  # # #",
                "#  #  $#",
                "##  #$ #",
                " #    .#",
                " ###.$.#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 530,
            name = "Procedural-530",
            rows = listOf(
                "########",
                "#@ ## .#",
                "#  #  .#",
                "# $ $  #",
                "## $  .#",
                " #  ####",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 531,
            name = "Procedural-531",
            rows = listOf(
                "#####   ",
                "#@  ####",
                "#    $ #",
                "#      #",
                "#  ##$.#",
                "#  $  $#",
                "#  #...#",
                "########"
            )
        ),
        SokobanLevel(
            id = 532,
            name = "Procedural-532",
            rows = listOf(
                "#####   ",
                "#.$.#   ",
                "#.$.####",
                "###    #",
                "#@$    #",
                "# $##  #",
                "#  #####",
                "####    "
            )
        ),
        SokobanLevel(
            id = 533,
            name = "Procedural-533",
            rows = listOf(
                "   #####",
                "   #. .#",
                "   #  .#",
                "#### $ #",
                "#@ ##$ #",
                "# $    #",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 534,
            name = "Procedural-534",
            rows = listOf(
                "####### ",
                "#+ #  ##",
                "#      #",
                "#.$##  #",
                "#   #  #",
                "# $*#  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 535,
            name = "Procedural-535",
            rows = listOf(
                "    ####",
                " ####@ #",
                " #  #  #",
                "## . $ #",
                "# $$ ###",
                "# #   # ",
                "#  . .# ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 536,
            name = "Procedural-536",
            rows = listOf(
                "   #####",
                "   #.$@#",
                "   # $ #",
                "#### $ #",
                "#..# $##",
                "#     # ",
                "# .   # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 537,
            name = "Procedural-537",
            rows = listOf(
                " #####  ",
                "##@  #  ",
                "# $  #  ",
                "#  $ ###",
                "#$ ## .#",
                "#.    .#",
                "#  ##  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 538,
            name = "Procedural-538",
            rows = listOf(
                "   #####",
                " ###   #",
                " #@$$# #",
                "##$#   #",
                "#  . . #",
                "#   ####",
                "## .#   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 539,
            name = "Procedural-539",
            rows = listOf(
                "#####   ",
                "#+  ####",
                "#.  #  #",
                "#   $  #",
                "## $   #",
                "#  $####",
                "#.  #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 540,
            name = "Procedural-540",
            rows = listOf(
                " ####   ",
                " #@ ####",
                " # $.  #",
                " ###   #",
                " ####$ #",
                " #   $ #",
                " #  . .#",
                " #######"
            )
        ),
        SokobanLevel(
            id = 541,
            name = "Procedural-541",
            rows = listOf(
                "   #####",
                " ###   #",
                " #.  # #",
                "## $   #",
                "#..#$$##",
                "#.  $@# ",
                "#  #### ",
                "####    "
            )
        ),
        SokobanLevel(
            id = 542,
            name = "Procedural-542",
            rows = listOf(
                "########",
                "#   #  #",
                "#      #",
                "#### $ #",
                "#@* # ##",
                "#  $ $ #",
                "#.  . .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 543,
            name = "Procedural-543",
            rows = listOf(
                "#####   ",
                "#@ .####",
                "#* .   #",
                "# $ $  #",
                "#  #####",
                "# $   # ",
                "# .#  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 544,
            name = "Procedural-544",
            rows = listOf(
                "   #####",
                "   #.  #",
                "   # $ #",
                " ####  #",
                "##+ #  #",
                "# $$ $.#",
                "#  .  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 545,
            name = "Procedural-545",
            rows = listOf(
                " #######",
                "##     #",
                "#@$$   #",
                "#  #   #",
                "## ##  #",
                " # *. .#",
                " ####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 546,
            name = "Procedural-546",
            rows = listOf(
                "####    ",
                "#.*##   ",
                "#$$.#   ",
                "#@$.#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 547,
            name = "Procedural-547",
            rows = listOf(
                "#####   ",
                "#.  ####",
                "#$ $ $+#",
                "#.   $.#",
                "########"
            )
        ),
        SokobanLevel(
            id = 548,
            name = "Procedural-548",
            rows = listOf(
                "   #####",
                "   #.  #",
                "   #   #",
                "   ## ##",
                "#### $+#",
                "#  $ $ #",
                "#    .##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 549,
            name = "Procedural-549",
            rows = listOf(
                "########",
                "#  .#@ #",
                "#   #  #",
                "## ##$.#",
                "## #.$.#",
                "#   $$ #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 550,
            name = "Procedural-550",
            rows = listOf(
                "########",
                "#  #.. #",
                "# $@$  #",
                "# $##* #",
                "#  # . #",
                "#      #",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 551,
            name = "Procedural-551",
            rows = listOf(
                "   #####",
                "   #@  #",
                "   #.$ #",
                "#####$ #",
                "#. ## .#",
                "#   $$ #",
                "#   # .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 552,
            name = "Procedural-552",
            rows = listOf(
                "####    ",
                "#@ #    ",
                "# $#####",
                "#      #",
                "# # $# #",
                "#.$.  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 553,
            name = "Procedural-553",
            rows = listOf(
                "########",
                "#+ #.  #",
                "# $$.# #",
                "#  $   #",
                "####  ##",
                "   # $# ",
                "   #. # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 554,
            name = "Procedural-554",
            rows = listOf(
                "########",
                "#   .. #",
                "# $    #",
                "# $ #$##",
                "##@# .# ",
                " #    # ",
                " ###### "
            )
        ),
        SokobanLevel(
            id = 555,
            name = "Procedural-555",
            rows = listOf(
                "#####   ",
                "#@  #   ",
                "# $.#   ",
                "# $#### ",
                "#. #. ##",
                "# $$   #",
                "#. #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 556,
            name = "Procedural-556",
            rows = listOf(
                " ###### ",
                "##   .# ",
                "#  $$.# ",
                "#.$ ### ",
                "##@##   ",
                "# $ #   ",
                "#  .#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 557,
            name = "Procedural-557",
            rows = listOf(
                "########",
                "#@ #.  #",
                "#  #   #",
                "#  #   #",
                "# $ #$ #",
                "# $    #",
                "####.. #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 558,
            name = "Procedural-558",
            rows = listOf(
                "########",
                "#@ #  .#",
                "#   .  #",
                "#. # $##",
                "##$ #  #",
                "#  $   #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 559,
            name = "Procedural-559",
            rows = listOf(
                "########",
                "#+ ##  #",
                "#.  #  #",
                "#  $$  #",
                "##     #",
                "#  $#  #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 560,
            name = "Procedural-560",
            rows = listOf(
                "####    ",
                "#  #### ",
                "# $ $@# ",
                "#   $ ##",
                "####  .#",
                "   #  .#",
                "   ## .#",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 561,
            name = "Procedural-561",
            rows = listOf(
                "  ##### ",
                "  #. .# ",
                "  #   # ",
                "### $###",
                "#@$    #",
                "# $.#  #",
                "#  #####",
                "####    "
            )
        ),
        SokobanLevel(
            id = 562,
            name = "Procedural-562",
            rows = listOf(
                "####### ",
                "#. #@ ##",
                "#   $$ #",
                "# ..#  #",
                "## #  ##",
                " #  $  #",
                " ### $.#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 563,
            name = "Procedural-563",
            rows = listOf(
                " ####   ",
                " #@ #   ",
                " # $####",
                " #.    #",
                " ###$$ #",
                "   #.#$#",
                "   #. .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 564,
            name = "Procedural-564",
            rows = listOf(
                " #######",
                " #  * .#",
                " #.. # #",
                " ##    #",
                " #@$$  #",
                " #  #$ #",
                " ####  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 565,
            name = "Procedural-565",
            rows = listOf(
                " ###### ",
                "## .  ##",
                "#  $$  #",
                "#.  #  #",
                "## ##  #",
                "#@$ #  #",
                "#.  #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 566,
            name = "Procedural-566",
            rows = listOf(
                " ###### ",
                " #@   ##",
                " #   $ #",
                " ####  #",
                "##. #  #",
                "#  $ $ #",
                "#..   ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 567,
            name = "Procedural-567",
            rows = listOf(
                "####### ",
                "#+..  ##",
                "# # $$ #",
                "#  .#  #",
                "#   # ##",
                "# $ $  #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 568,
            name = "Procedural-568",
            rows = listOf(
                "########",
                "#   #  #",
                "# # #. #",
                "#   #* #",
                "#      #",
                "#    $*#",
                "# *##@ #",
                "########"
            )
        ),
        SokobanLevel(
            id = 569,
            name = "Procedural-569",
            rows = listOf(
                "####### ",
                "#@ #  ##",
                "#      #",
                "#   # .#",
                "# $#  .#",
                "# $ $# #",
                "#     .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 570,
            name = "Procedural-570",
            rows = listOf(
                "    ####",
                " ####  #",
                " #@$   #",
                " # $#  #",
                " ## * ##",
                "  #   .#",
                "  # .  #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 571,
            name = "Procedural-571",
            rows = listOf(
                " #######",
                "##@    #",
                "# $  # #",
                "#  $   #",
                "###  . #",
                "  #$ # #",
                "  #  ..#",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 572,
            name = "Procedural-572",
            rows = listOf(
                " #######",
                " #  # .#",
                "##  #$.#",
                "#@$ # .#",
                "#  $   #",
                "#   #  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 573,
            name = "Procedural-573",
            rows = listOf(
                "####    ",
                "#. #####",
                "#. $   #",
                "# $#   #",
                "##@ ####",
                "# $ #   ",
                "#.  #   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 574,
            name = "Procedural-574",
            rows = listOf(
                " ####   ",
                "## .####",
                "#  .#@ #",
                "# $ #  #",
                "#.$##  #",
                "# $  $ #",
                "#  .  ##",
                "####### "
            )
        ),
        SokobanLevel(
            id = 575,
            name = "Procedural-575",
            rows = listOf(
                "####    ",
                "# .#    ",
                "#  #    ",
                "# $##   ",
                "#$$.#   ",
                "#+$.#   ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 576,
            name = "Procedural-576",
            rows = listOf(
                "####    ",
                "#@ #    ",
                "#  #    ",
                "#.$#### ",
                "#. #  # ",
                "# $$ .# ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 577,
            name = "Procedural-577",
            rows = listOf(
                " ####   ",
                "##  ####",
                "#.  #@ #",
                "# $ $  #",
                "#$   ###",
                "# #  ..#",
                "#      #",
                "########"
            )
        ),
        SokobanLevel(
            id = 578,
            name = "Procedural-578",
            rows = listOf(
                "   #### ",
                " ###@ ##",
                " #    .#",
                "##$$#. #",
                "# $ #.##",
                "#     # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 579,
            name = "Procedural-579",
            rows = listOf(
                "####    ",
                "#. #    ",
                "#.$#####",
                "# $.#  #",
                "#  $@$ #",
                "#   # .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 580,
            name = "Procedural-580",
            rows = listOf(
                "########",
                "#.    .#",
                "#.     #",
                "# $ ####",
                "# $#### ",
                "#   $@# ",
                "#  #  # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 581,
            name = "Procedural-581",
            rows = listOf(
                "   #####",
                "   #   #",
                "   # $ #",
                "####  ##",
                "#@  $$ #",
                "#      #",
                "#...#  #",
                "########"
            )
        ),
        SokobanLevel(
            id = 582,
            name = "Procedural-582",
            rows = listOf(
                "  ##### ",
                "  #   ##",
                "  #  $+#",
                "  #  $ #",
                "  ###$ #",
                "   # $ #",
                "   #...#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 583,
            name = "Procedural-583",
            rows = listOf(
                "########",
                "#      #",
                "#    . #",
                "#   #*##",
                "# $##+# ",
                "#   $ # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 584,
            name = "Procedural-584",
            rows = listOf(
                " ####   ",
                " #  #   ",
                " #  ####",
                "## $ $@#",
                "#.$ .* #",
                "#.  #  #",
                "#   ####",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 585,
            name = "Procedural-585",
            rows = listOf(
                "########",
                "#..    #",
                "#  $$  #",
                "#.  # ##",
                "#####$@#",
                "    #  #",
                "    #  #",
                "    ####"
            )
        ),
        SokobanLevel(
            id = 586,
            name = "Procedural-586",
            rows = listOf(
                "   #####",
                "####   #",
                "#+.#   #",
                "#. #  ##",
                "#  $   #",
                "# $ $  #",
                "####   #",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 587,
            name = "Procedural-587",
            rows = listOf(
                "########",
                "#+  # .#",
                "# .$$$ #",
                "# $    #",
                "##. ####",
                " #    # ",
                " ###  # ",
                "   #### "
            )
        ),
        SokobanLevel(
            id = 588,
            name = "Procedural-588",
            rows = listOf(
                "   #####",
                " ###   #",
                " # $   #",
                "## ##  #",
                "#@$ $ ##",
                "#.$.   #",
                "#  #. .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 589,
            name = "Procedural-589",
            rows = listOf(
                "   #####",
                "####+$.#",
                "#  $$#*#",
                "#     .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 590,
            name = "Procedural-590",
            rows = listOf(
                "########",
                "#.. .  #",
                "# #    #",
                "#$  ####",
                "#  $#   ",
                "# $@#   ",
                "##  #   ",
                " ####   "
            )
        ),
        SokobanLevel(
            id = 591,
            name = "Procedural-591",
            rows = listOf(
                " ####   ",
                " #@ ####",
                " #  #  #",
                "## .#$ #",
                "# $ $  #",
                "#  * #.#",
                "#  #  .#",
                "########"
            )
        ),
        SokobanLevel(
            id = 592,
            name = "Procedural-592",
            rows = listOf(
                " ###### ",
                "##@   # ",
                "#     # ",
                "#.$#  # ",
                "#. #  # ",
                "#.$$  # ",
                "#     # ",
                "####### "
            )
        ),
        SokobanLevel(
            id = 593,
            name = "Procedural-593",
            rows = listOf(
                "    ####",
                "#####+ #",
                "#      #",
                "# $ $ ##",
                "#  #$ .#",
                "####  .#",
                "   #####"
            )
        ),
        SokobanLevel(
            id = 594,
            name = "Procedural-594",
            rows = listOf(
                "#####   ",
                "#.  ### ",
                "#. .  # ",
                "#* ## # ",
                "#  ## ##",
                "# $$@$ #",
                "##     #",
                " #######"
            )
        ),
        SokobanLevel(
            id = 595,
            name = "Procedural-595",
            rows = listOf(
                "########",
                "#  #@  #",
                "#  #   #",
                "# $#   #",
                "# $  ###",
                "#.$ .#  ",
                "##  .#  ",
                " #####  "
            )
        ),
        SokobanLevel(
            id = 596,
            name = "Procedural-596",
            rows = listOf(
                " ###### ",
                "## $ .##",
                "#    $@#",
                "#  ##  #",
                "##$. .##",
                "#  *  # ",
                "#   ### ",
                "#####   "
            )
        ),
        SokobanLevel(
            id = 597,
            name = "Procedural-597",
            rows = listOf(
                " ###### ",
                " #   .# ",
                " #  . # ",
                " ##$*###",
                "  #@$  #",
                "  #$ # #",
                "  # .  #",
                "  ######"
            )
        ),
        SokobanLevel(
            id = 598,
            name = "Procedural-598",
            rows = listOf(
                "#####   ",
                "#+$.####",
                "#   #  #",
                "# $##  #",
                "#. ##  #",
                "# $$   #",
                "#. #   #",
                "########"
            )
        ),
        SokobanLevel(
            id = 599,
            name = "Procedural-599",
            rows = listOf(
                "   #####",
                "   #+ .#",
                "####.$ #",
                "#      #",
                "# $$ ###",
                "#  #  # ",
                "####  # ",
                "   #### "
            )
        )
    )
}
