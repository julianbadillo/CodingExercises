use core::panic;
use std::fs;


pub fn read_file(file_name: &str) -> String {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    return data;
}

pub fn to_binary(input: &str) -> String {
    let mut stream= String::new();
    for c in input.chars().into_iter() {
        let x = u8::from_str_radix( c.to_string().as_str(), 16).unwrap();
        stream.push_str(&format!("{:0>4b}", x));
    }
    return stream;
}

pub fn parse_packet(binput: &str) -> (usize, u32, u32, u64) {
    // packet version
    let mut i: usize = 0;
    let version = u32::from_str_radix(&binput[i..i+3], 2).unwrap();
    let mut total_version = version;
    let mut value: u64 = 0;
    i += 3;
    let type_id = u8::from_str_radix(&binput[i..i+3], 2).unwrap();
    i += 3;
    // literal
    if type_id == 4 {
        let mut literal_s = String::new(); 
        loop {
            let last = &binput[i..i+1] != "1";
            i += 1;
            literal_s.push_str(&binput[i..i+4]);
            i += 4;
            if last {
                break;
            }
        }
        // parse literal
        value = u64::from_str_radix(literal_s.as_str(), 2).unwrap();
        println!("Literal = {}", value);
    }
    else {
        let bit_lt_id = &binput[i..i+1] == "0";
        let mut values: Vec<u64> = vec![];
        i += 1;
        if bit_lt_id {
            let total_bits = usize::from_str_radix(&binput[i..i+15], 2).unwrap();
            i += 15;
            let mut bits: usize = 0;
            println!("Operator = {}, on {} bits", type_id, total_bits);
            loop {
                let (read, v2, tv2, value2) = parse_packet(&binput[i..]);
                // accumulate version
                total_version += tv2;
                bits += read;
                i += read;
                values.push(value2);
                if bits == total_bits {
                    break;
                }
                else if bits > total_bits {
                    panic!("Here!!!");
                }
            }
        } else {
            let number =u32::from_str_radix(&binput[i..i+11], 2).unwrap();
            i += 11;
            let mut pack = 0;
            println!("Operator = {}, on {} packets", type_id, number);
            loop {
                let (read, v2, tv2, value2) = parse_packet(&binput[i..]);
                // accumulate version
                total_version += tv2;
                i += read;
                pack += 1;
                values.push(value2);
                if pack == number {
                    break;
                }
            }
        }
        //sum
        if type_id == 0 {
            value = values.iter().sum();
        }
        //prod
        else if type_id == 1 {
            value = values.iter().product();
        }
        //min
        else if type_id == 2 {
            value = values.into_iter().reduce(u64::min)
                                        .unwrap();
        }
        //max
        else if type_id == 3 {
            value = values.into_iter().reduce(u64::max)
                                        .unwrap();
        }
        // > 
        else if type_id == 5 {
            value = if values[0] > values[1] { 1 } else { 0 };
        }
        // < 
        else if type_id == 6 {
            value = if values[0] < values[1] { 1 } else { 0 };
        }
        // == 
        else if type_id == 7 {
            value = if values[0] == values[1] { 1 } else { 0 };
        }

    }

    return (i, version, total_version, value);
}


#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_read_file() {
        let x = read_file("advent_day16_test.txt");
        assert_eq!(x, "A0016C880162017C3686B18A3D4780");
        // assert_eq!(x[0].len(), 10);
    }
    
    #[test]
    fn test_convert_bin(){
        assert_eq!(format!("{:0>4b}", 1), "0001");
        assert_eq!(format!("{:0>4b}", 4), "0100");
    }

    #[test]
    fn test_to_binary(){
        assert_eq!(to_binary("D2FE28"), "110100101111111000101000");
    }

    #[test]
    fn test_parse_packet_version(){
        let b= to_binary("D2FE28");
        let (_i, v, _tv, val)= parse_packet(b.as_str()); 
        assert_eq!(v, 6);
        assert_eq!(val, 2021);
    }

    #[test]
    fn test_parse_packet_version_operator1(){
        let b= to_binary("38006F45291200");
        let (_i, v, _tv, val) = parse_packet(b.as_str()); 
        assert_eq!(v, 1);
    }
    
    #[test]
    fn test_parse_packet_version_operator2(){
        let b= to_binary("EE00D40C823060");
        let (_i, v, _tv, val) = parse_packet(b.as_str()); 
        assert_eq!(v, 7);
    }

    #[test]
    fn test_parse_packet_version_operator_totalv(){
        let b= to_binary("8A004A801A8002F478");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(tv, 16);
    }

    #[test]
    fn test_parse_packet_version_operator_totalv2(){
        let b= to_binary("620080001611562C8802118E34");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(tv, 12);
    }

    #[test]
    fn test_parse_packet_version_operator_totalv3(){
        let b= to_binary("C0015000016115A2E0802F182340");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(tv, 23);
    }

    #[test]
    fn test_all_test(){
        let str = read_file("advent_day16_test.txt");
        let b= to_binary(str.as_str());
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(tv, 31);
    }

    #[test]
    fn test_all(){
        let str = read_file("advent_day16.txt");
        assert_eq!(str.len(), 1344);
        let b= to_binary(str.as_str());
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        println!("Total version = {}", tv);
    }

    #[test]
    fn test_value_sum(){
        let b= to_binary("C200B40A82");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 3);
    }

    #[test]
    fn test_value_prod(){
        let b= to_binary("04005AC33890");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 54);
    }

    #[test]
    fn test_value_min(){
        let b= to_binary("880086C3E88112");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 7);
    }

    #[test]
    fn test_value_max(){
        let b= to_binary("CE00C43D881120");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 9);
    }

    #[test]
    fn test_value_ge(){
        let b= to_binary("D8005AC2A8F0");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 1);
    }

    #[test]
    fn test_value_le(){
        let b= to_binary("F600BC2D8F");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 0);
    }

    #[test]
    fn test_value_neq(){
        let b= to_binary("9C005AC2F8F0");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 0);
    }
    
    #[test]
    fn test_value_eq(){
        let b= to_binary("9C0141080250320F1802104A08");
        let (_i, _v, tv, val) = parse_packet(b.as_str()); 
        assert_eq!(val, 1);
    }

    #[test]
    fn test_all_value(){
        let str = read_file("advent_day16.txt");
        assert_eq!(str.len(), 1344);
        let b= to_binary(str.as_str());
        let (_i, _v, _tv, val) = parse_packet(b.as_str()); 
        println!("Total value = {}", val);
    }
}