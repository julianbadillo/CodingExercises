use std::{fs, vec, collections::HashMap, ops::Add};

pub fn read_file(file_name: &str) -> Vec<String> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<String> = data.split("\r\n")
                            .map(|x| String::from(x))
                            .collect();
    return input;
}

pub fn get_rules(lines: &[String]) -> HashMap<String, String> {
    let mut map:HashMap<String, String> = HashMap::new();
    for line in lines.iter() {
        let parts: Vec<&str> = line.split(" -> ").collect();
        map.insert(parts[0].to_string(), parts[1].to_string());
    }
    return map;
}

pub fn process_template(template: String) -> (HashMap<String, u64>, HashMap<char, u64>){
    let mut count_pairs: HashMap<String, u64> = HashMap::new();
    let mut count_chars: HashMap<char, u64> = HashMap::new();
    let n = template.len();
    let chrs: Vec<char> = template.chars().collect();
    for i in 0..n {
        count_chars.insert(chrs[i], match count_chars.get(&chrs[i])
                                                                {
                                                                    Some(x) => x + 1,
                                                                    None => 1
                                                                });
        if i < n - 1 {
            let pair = &template[i..i+2].to_owned();
            count_pairs.insert(pair.to_owned(), match count_pairs.get(pair)
                                                            {
                                                                Some(x) => x + 1,
                                                                None => 1
                                                            });
        }
    }

    // return
    (count_pairs, count_chars)
}

pub fn transform(template: &mut String, rules: &HashMap<String, String>) {
    let mut n: usize = template.len();
    let mut i: usize = 0;
    loop {
        if i + 1 >= n {
            break;
        }
        let pat = &template[i..i+2];
        // find rule
        if rules.contains_key(pat) {
            // insert and advance index
            let insert = rules.get(pat);
            template.insert_str(i + 1, insert.unwrap());
            i += 2;
            n += 1;
        }
    }
}

pub fn count_freq(template: &str) -> HashMap<char, u32>{
    let mut count = HashMap::new();
    for c in template.chars().into_iter() {
        if !count.contains_key(&c) {
            count.insert(c, 0);
        }
        count.insert(c, count.get(&c).unwrap() + 1);
    }
    return count;
}

pub fn tranform_map(count_pairs: &mut HashMap<String, u64>, count_chars: &mut HashMap<char, u64>, rules: &HashMap<String, String>){
    // TODO
}

#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_get_rules() {
        let lines = read_file("advent_day14_test.txt");
        let rules = get_rules(&lines[2..]);

        assert!(rules.contains_key("CH"));
        assert!(rules.contains_key("HC"));
        assert!(rules.contains_key("CN"));
        assert_eq!(rules["CH"], "B");
        let s = String::from("CN");
        assert!(rules.contains_key(&s));
    }

    #[test]
    fn test_transform(){
        let lines = read_file("advent_day14_test.txt");
        let mut template = String::from(&lines[0]);
        let rules = get_rules(&lines[2..]);
        transform(&mut template, &rules);
        assert_eq!(template, "NCNBCHB");
        transform(&mut template, &rules);
        assert_eq!(template, "NBCCNBBBCBHCB");
        transform(&mut template, &rules);
        assert_eq!(template, "NBBBCNCCNBBNBNBBCHBHHBCHB");

    }

    #[test]
    fn test_count_freq(){
        let count = count_freq("NBBBCNCCNBBNBNBBCHBHHBCHB");
        assert_eq!(count[&'N'], 5);
        assert_eq!(count[&'B'], 11);
        let x = count.iter()
            .max_by(|k1, k2| k1.1.cmp(k2.1))
            .unwrap();
        assert_eq!(x, (&'B', &11));
    }

    #[test]
    fn test_transform_count(){
        let lines = read_file("advent_day14_test.txt");
        let mut template = String::from(&lines[0]);
        let rules = get_rules(&lines[2..]);
        for _i in 0..10 {
            transform(&mut template, &rules);
        }
        assert_eq!(template.len(), 3073);
        let count = count_freq(&template);
        assert_eq!(count[&'B'], 1749);
        assert_eq!(count[&'C'], 298);
        assert_eq!(count[&'H'], 161);
    }

    #[test]
    fn test_all(){
        let lines = read_file("advent_day14.txt");
        let mut template = String::from(&lines[0]);
        let rules = get_rules(&lines[2..]);
        for _i in 0..10 {
            transform(&mut template, &rules);
        }
        let count = count_freq(&template);
        let max = count.values().into_iter().max().unwrap();
        let min = count.values().into_iter().min().unwrap();
        println!("max {} min {}, dif = {}", max, min, max-min);
    }

    #[test]
    fn test_process_template(){
        let (cp, cc) = process_template(String::from("NCNBCHB"));
        assert!(cp.contains_key("NC"));
        assert!(cp.contains_key("CN"));
        assert!(cp.contains_key("NB"));
        
        assert_eq!(cp["NC"], 1);
        assert_eq!(cp["CN"], 1); 
        assert_eq!(cp["NB"], 1);
        assert_eq!(cp["CH"], 1);
        assert_eq!(cp["HB"], 1);

        assert_eq!(cc[&'N'], 2);
        assert_eq!(cc[&'C'], 2);
        assert_eq!(cc[&'B'], 2);
        assert_eq!(cc[&'H'], 1);
    }

    #[test]
    fn test_transform_map(){
        let lines = read_file("advent_day14_test.txt");
        let template = String::from(&lines[0]);
        let rules = get_rules(&lines[2..]);
        let (mut cp, mut cc) = process_template(template);
        tranform_map(&mut cp, &mut cc, &rules);
        println!("{:?}", cp);
        println!("{:?}", cc);
        // assert_eq!(template, "NCNBCHB");
        // transform(&mut template, &rules);
        // assert_eq!(template, "NBCCNBBBCBHCB");
        // transform(&mut template, &rules);
        // assert_eq!(template, "NBBBCNCCNBBNBNBBCHBHHBCHB");

    }


}