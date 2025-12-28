require 'minitest/autorun'
require_relative '../lib/day11'

class Day11Test < Minitest::Test

  def test_hash()
    h = Hash.new
    h["one"] = 1
    assert h.key?("one")
  end

  def test_push()
    l = [1,2,3,4]
    l.unshift(5)
    assert_equal [5,1,2,3,4], l
    assert l.all?{|x| x > 0}
  end

  def test_parse_input()
    lines = "aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out".split("\n")
    devs = Day11.parse_input(lines)
    assert devs.key?("you")
    assert devs.key?("aaa")
    assert devs.key?("out")
    # outputs of you
    assert_equal 2, devs["you"].outs.length
    assert devs["you"].outs.key?("bbb")
    assert devs["you"].outs.key?("ccc")
    assert devs["iii"].outs.key?("out")
    # Inputs to "out"
    assert_equal 4, devs["out"].ins.length
    assert devs["out"].ins.key?("iii")
    assert devs["out"].ins.key?("ggg")
    assert devs["out"].ins.key?("fff")
    assert devs["out"].ins.key?("eee")
  end

  def test_count_paths_dfs()
    lines = "aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out".split("\n")
    devs = Day11.parse_input(lines)

    assert_equal 1, Day11.count_paths_dfs(devs["eee"])
    assert_equal 1, Day11.count_paths_dfs(devs["fff"])
    assert_equal 1, Day11.count_paths_dfs(devs["ggg"])
    assert_equal 1, Day11.count_paths_dfs(devs["ddd"])
    assert_equal 3, Day11.count_paths_dfs(devs["ccc"])
    assert_equal 5, Day11.count_paths_dfs(devs["you"])
    
  end


  def test_part1()
    lines = "aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out".split("\n")
    assert_equal 5, Day11.part1(lines)
  end

  def test_count_paths_inverse_bfs()
    lines = "aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out".split("\n")
    devs = Day11.parse_input(lines)

    Day11.count_paths_inverse_bfs(devs["out"])
    assert_equal 5, devs["you"].paths
    assert_equal 10, devs["aaa"].paths
    # print("#{paths.map{|k, v| "#{k}: #{v}"}.join("\n")}")

    lines = "svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out
".split("\n")
    devs = Day11.parse_input(lines)
    Day11.count_paths_inverse_bfs(devs["out"])
    # print("#{paths.map{|k, v| "#{k}: #{v}"}.join("\n")}")
    assert_equal 8, devs["svr"].paths
  end

  def test_count_dac_fft_inverse_bfs()
    lines = "svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out
".split("\n")
    devs = Day11.parse_input(lines)
    Day11.count_dac_fft_inverse_bfs(devs["out"])
    # devs.values.each{|d| print("#{d.to_s}\n")}
    assert_equal 2, devs["svr"].dac
    assert_equal 2, devs["svr"].fft
    assert_equal 2, devs["svr"].both
    assert_equal 2, devs["svr"].none
  end

  def test_part2()
    lines = "svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out
".split("\n")
    assert_equal 2, Day11.part2(lines)
  end
end