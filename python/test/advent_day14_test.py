from advent_day14 import *

def test_process_template():
    cp, cc = process_template("NCNBCHB")

    assert cp["NC"] == 1
    assert cp["CN"]== 1 
    assert cp["NB"]== 1
    assert cp["CH"]== 1
    assert cp["HB"]== 1

    assert cc['N']== 2
    assert cc['C']== 2
    assert cc['B']== 2
    assert cc['H']== 1

def test_transform_count():
    template, rules = read_file("test/advent_day14_test.txt")
    cp, cc = process_template(template)
    # NNCB
    print(cp)
    print(cc)
    
    cp = transform_count_maps(cp, cc, rules)
    #NCNBCHB
    print(cp)
    print(cc)
    assert cp["NC"] == 1
    assert cp["CN"]== 1 
    assert cp["NB"]== 1
    assert cp["CH"]== 1
    assert cp["HB"]== 1

    assert cc['N']== 2
    assert cc['C']== 2
    assert cc['B']== 2
    assert cc['H']== 1
    
    cp = transform_count_maps(cp, cc, rules)
    # step 2 NBCCNBBBCBHCB
    assert cp["CN"]== 1 
    assert cp["NB"]== 2
    assert cp["CC"]== 1
    assert cp["BB"]== 2
    assert cp["CB"]== 2
    
    assert cc['N']== 2
    assert cc['C']== 4
    assert cc['B']== 6
    assert cc['H']== 1
    assert sum(v for v in cc.values()) == 13
    cp = transform_count_maps(cp, cc, rules)
    
    # Step 3 NBBBCNCCNBBNBNBBCHBHHBCHB
    assert sum(v for v in cc.values()) == 25
    
    cp = transform_count_maps(cp, cc, rules)
    # Step 4
    cp = transform_count_maps(cp, cc, rules)
    assert sum(v for v in cc.values()) == 97


def test_transform_count2():
    template, rules = read_file("test/advent_day14_test.txt")
    cp, cc = process_template(template)
    # NNCB
    cp = transform_count_maps(cp, cc, rules)
    #NCNBCHB
    cp2, cc2 = process_template("NCNBCHB")
    assert cp == cp2
    assert cc == cc2
    #NBCCNBBBCBHCB
    cp = transform_count_maps(cp, cc, rules)
    cp3, cc3 = process_template("NBCCNBBBCBHCB")
    assert cp == cp3
    assert cc == cc3
    
    # NBBBCNCCNBBNBNBBCHBHHBCHB
    cp = transform_count_maps(cp, cc, rules)
    cp4, cc4 = process_template("NBBBCNCCNBBNBNBBCHBHHBCHB")
    assert cp == cp4
    assert cc == cc4

def test_all_test():
    template, rules = read_file("test/advent_day14_test.txt")
    cp, cc = process_template(template)
    for i in range(40):
        cp = transform_count_maps(cp, cc, rules)
    mx = max(cc.values())
    mn = min(cc.values())
    assert mx == 2192039569602
    assert mn == 3849876073

def test_all_test():
    template, rules = read_file("test/advent_day14.txt")
    cp, cc = process_template(template)
    for i in range(40):
        cp = transform_count_maps(cp, cc, rules)
    
    mx = max(cc.values())
    mn = min(cc.values())
    print("Max ", mx, " Min ", mn, " - ", (mx - mn))
    