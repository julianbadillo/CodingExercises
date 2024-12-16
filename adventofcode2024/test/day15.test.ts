import assert from "assert";
import { Robot, solve, move, DIRS, D } from "../src/day15-part1";
import { findBoxes, moveBetter, solve as solve2 } from "../src/day15-part2";

describe('day15', () => {

    it('move', () => {
        const map1 = `########
#..O.O.#
##..O..#
#...O..#
#.#.O..#
#...O..#
#......#
########`.split('\n').map(l => l.split(''));
        const inst = '<^^>>>vv<v>>v<<'.split('');
        const robot: Robot = { r: 2, c: 2 };
        move(map1, robot, inst[0]);
        assert.equal(robot.r, 2);
        assert.equal(robot.c, 2);

        move(map1, robot, inst[1]);
        assert.equal(robot.r, 1);
        assert.equal(robot.c, 2);

        move(map1, robot, inst[2]);
        assert.equal(robot.r, 1);
        assert.equal(robot.c, 2);

        move(map1, robot, inst[3]);
        assert.equal(robot.r, 1);
        assert.equal(robot.c, 3);

        // asert map chagnged
        assert.equal(map1.map(row => row.join('')).join('\n'), `########
#...OO.#
##..O..#
#...O..#
#.#.O..#
#...O..#
#......#
########`);
    });

    it('findBoxes', () => {
        const map1 = `##############
##......##..##
##..........##
##....[][]..##
##.....[]...##
##..........##
##############`.split('\n').map(m=> m.split(''));
        // wall
        let boxes = findBoxes(map1, {r: 1, c: 2}, DIRS.get('<') as D);
        assert.equal(boxes, undefined);
        
        // push two boxes - horizontaly
        boxes = findBoxes(map1, {r: 3, c: 10}, DIRS.get('<') as D) as Robot[];
        assert.equal(boxes.length, 4);
        assert.equal(boxes[0].r, 3);
        assert.equal(boxes[0].c, 9);
        assert.equal(boxes[1].c, 8);
        assert.equal(boxes[2].c, 7);
        assert.equal(boxes[3].c, 6);

        // push two boxes - horizontally
        boxes = findBoxes(map1, {r: 3, c: 5}, DIRS.get('>') as D) as Robot[];
        assert.equal(boxes.length, 4);
        assert.equal(boxes[0].c, 6);
        assert.equal(boxes[1].c, 7);
        assert.equal(boxes[2].c, 8);
        assert.equal(boxes[3].c, 9);

        //one box - vertically
        boxes = findBoxes(map1, {r: 4, c: 6}, DIRS.get('^') as D);
        assert.equal(boxes?.length, 2);
        boxes = findBoxes(map1, {r: 4, c: 9}, DIRS.get('^') as D);
        assert.equal(boxes?.length, 2);

        // three boxes vertically
        boxes = findBoxes(map1, {r: 5, c: 7}, DIRS.get('^') as D);
        assert.notEqual(boxes, undefined);
        assert.equal(boxes?.length, 6);

        boxes = findBoxes(map1, {r: 5, c: 8}, DIRS.get('^') as D);
        assert.notEqual(boxes, undefined);
        assert.equal(boxes?.length, 6);

        // two boxes vertically
        boxes = findBoxes(map1, {r: 2, c: 7}, DIRS.get('v') as D);
        assert.notEqual(boxes, undefined);
        assert.equal(boxes?.length, 4, boxes?.map(b => `[${b.r}, ${b.c}]`).join());
        boxes = findBoxes(map1, {r: 2, c: 8}, DIRS.get('v') as D);
        assert.notEqual(boxes, undefined);
        assert.equal(boxes?.length, 4, boxes?.map(b => `[${b.r}, ${b.c}]`).join());

        const map2 = `##############
##......##..##
##..........##
##....[][]..##
##....[]....##
##..........##
##############`.split('\n').map(m=> m.split(''));

        // two boxes stacked vertically
        boxes = findBoxes(map2, {r: 2, c: 7}, DIRS.get('v') as D);
        assert.notEqual(boxes, undefined);
        assert.equal(boxes?.length, 4, boxes?.map(b => `[${b.r}, ${b.c}]`).join());
    });

    it('moveBetter', () => {
        const map1 = `##############
##......##..##
##..........##
##....[][]..##
##....[]....##
##..........##
##############`.split('\n').map(m=> m.split('')); 
        
        const robot = {r: 3, c: 10};
        moveBetter(map1, robot, '<');
        assert.equal(robot.r, 3);
        assert.equal(robot.c, 9);        
        assert.equal(map1.map(row => row.join('')).join('\n'),`##############
##......##..##
##..........##
##...[][]...##
##....[]....##
##..........##
##############`);
        
        moveBetter(map1, robot, 'v');
        assert.equal(robot.r, 4);
        assert.equal(robot.c, 9);

        moveBetter(map1, robot, 'v');
        assert.equal(robot.r, 5);
        assert.equal(robot.c, 9);

        moveBetter(map1, robot, '<');
        assert.equal(robot.r, 5);
        assert.equal(robot.c, 8);

        moveBetter(map1, robot, '<');
        assert.equal(robot.r, 5);
        assert.equal(robot.c, 7);

        moveBetter(map1, robot, '^');
        assert.equal(robot.r, 4);
        assert.equal(robot.c, 7);

        assert.equal(map1.map(row => row.join('')).join('\n'),`##############
##......##..##
##...[][]...##
##....[]....##
##..........##
##..........##
##############`);

        moveBetter(map1, robot, '^');
        assert.equal(robot.r, 4);
        assert.equal(robot.c, 7);
    });


    const s1 = `########
#..O.O.#
##@.O..#
#...O..#
#.#.O..#
#...O..#
#......#
########

<^^>>>vv<v>>v<<`;
    const s2 = `##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^`;
    it('solve', () => {
        
        assert.equal(solve(s1), 2028);
        assert.equal(solve(s2), 10092);
    });

    it('solve-part2', () => {
        assert.equal(solve2(s2), 9021);
    })

});