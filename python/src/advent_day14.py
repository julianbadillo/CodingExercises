
def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, 'r') as file:
        template = ''
        rules = {}
        for l in file.readlines():
            l = l.strip()
            if ' -> ' in l:
                p = l.split(' -> ')
                rules[p[0]] = p[1]
            elif l:
                template = l
        
        return template, rules

def process_template(template):
    count_pairs = {}
    count_chars = {}
    for i in range(len(template)):
        if template[i] not in count_chars:
            count_chars[template[i]] = 0
        count_chars[template[i]] += 1
        if i < len(template) - 1:
            pair = template[i:i+2]
            if pair not in count_pairs:
                count_pairs[pair] = 0
            count_pairs[pair] += 1
    return (count_pairs, count_chars)


def transform_count_maps(count_pairs, count_chars, rules):
    keys = [k for k in count_pairs.keys()]
    count_pairs_r = {}
    for pair in keys:
        if pair in rules:
            # split and make two pairs
            c = rules[pair]
            pair1 = pair[:1] + c
            # add the count to each group of pairs
            if pair1 not in count_pairs_r:
                count_pairs_r[pair1] = 0
            count_pairs_r[pair1] += count_pairs[pair]
            
            pair2 = c + pair[1:]
            if pair2 not in count_pairs_r:
                count_pairs_r[pair2] = 0
            count_pairs_r[pair2] += count_pairs[pair]
            
            # update count chars
            if c not in count_chars:
                count_chars[c] = 0
            count_chars[c] += count_pairs[pair]
    return count_pairs_r