package main

import (
	"bufio"
	"fmt"
	"os"
)

func score_round(player1 int, player2 int) (int, int, int) {
	var outcome int
	//1 = rock
	//2 = paper
	//3 = scissors
	if player1 == player2 {
		outcome = 2
	} else if (player1 % 3) == ((player2 + 1) % 3) {
		outcome = 1
	} else {
		outcome = 3
	}
	if outcome == 2 {
		return outcome, 3 + player1, 3 + player2
	} else if outcome == 1 {
		return outcome, 6 + player1, 0 + player2
	} else {
		return outcome, 0 + player1, 6 + player2
	}
}

func score_rps_strategy_part1(strategy []string) int {
	total_score_p2 := 0
	for _, line := range strategy {
		player1 := int(line[0]-'A') + 1
		player2 := int(line[2]-'X') + 1
		//fmt.Printf("p1 %v p2 %v\n", player1, player2)
		_, _, score2 := score_round(player1, player2)
		//fmt.Printf("w: %v p1 %v p2 %v\n", winner, score1, score2)
		total_score_p2 += score2
	}
	return total_score_p2
}

func follow_strategy_part2(strategy []string) int {
	total_score_p2 := 0
	for _, line := range strategy {
		player1 := int(line[0]-'A') + 1
		outcome := int(line[2]-'X') + 1
		var player2 int
		// player 1 needs to win
		if outcome == 1 {
			player2 = ((player1 + 1) % 3) + 1
		}
		// draw
		if outcome == 2 {
			player2 = player1
		}
		// player 2 needs to win
		if outcome == 3 {
			player2 = (player1 % 3) + 1
		}
		result, _, score2 := score_round(player1, player2)
		if result != outcome {
			panic("!!!!")
		}
		total_score_p2 += score2
	}
	return total_score_p2
}

func main() {
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	result := score_rps_strategy_part1(data)
	fmt.Printf("Total score I: %v\n", result)
	result = follow_strategy_part2(data)
	fmt.Printf("Total score II: %v\n", result)
}
