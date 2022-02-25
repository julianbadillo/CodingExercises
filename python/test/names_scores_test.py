import pytest
from names_scores import *

class TestNameScores:

    def test_nameScore(self):
        s = name_score('COLIN')
        assert s == 53

    def test_nameScore2(self):
        s = name_score('ANA')
        assert s == 16, 'Wrong score!'

    def test_name_total_score_small(self):
        total = total_name_score('data/adventofcode2021/small_test.txt')
        assert total == 30

    def test_name_total_score(self):
        total = total_name_score('data/adventofcode2021/names_test.txt')
        assert total == 1290869
    