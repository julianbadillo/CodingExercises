use std::fs;

pub struct Packet {
    version: u8, type_id: u8, value: u64, len: usize, total_version: u32
}
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

pub fn parse_packet(binput: &str) -> Packet {
    // packet version
    let mut packet = Packet {version: 0, len: 0, total_version: 0, type_id: 0, value: 0};
    let mut i: usize = 0;
    packet.version = u8::from_str_radix(&binput[i..i+3], 2).unwrap();
    packet.total_version = packet.version as u32;
    i += 3;

    packet.type_id = u8::from_str_radix(&binput[i..i+3], 2).unwrap();
    i += 3;
    // literal
    if packet.type_id == 4 {
        
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
        packet.value = u64::from_str_radix(literal_s.as_str(), 2).unwrap();
        //println!("Literal = {}", packet.value);
    }
    else {
        let bit_lt_id = &binput[i..i+1] == "0";
        let mut values = vec![];
        i += 1;
        let mut total_bits: usize = 0;
        let mut total_count: u32 = 0;
        let mut bits: usize = 0;
        let mut count: u32 = 0;

        if bit_lt_id {
            total_bits = usize::from_str_radix(&binput[i..i+15], 2).unwrap();
            i += 15;
        } else {
            total_count =u32::from_str_radix(&binput[i..i+11], 2).unwrap();
            i += 11;
        }
        
        //println!("Operator = {}, on {} bits", packet.type_id, total_bits);
        loop {
            let packet2 = parse_packet(&binput[i..]);
            // accumulate version
            packet.total_version += packet2.total_version;
            bits += packet2.len;
            i += packet2.len;
            count += 1;
            
            values.push(packet2.value);
            if bits == total_bits || count == total_count {
                break;
            }
        }
        
        //sum
        if packet.type_id == 0 {
            packet.value = values.iter().sum();
        }
        //prod
        else if packet.type_id == 1 {
            packet.value = values.iter().product();
        }
        //min
        else if packet.type_id == 2 {
            packet.value = values.into_iter().reduce(u64::min)
                                        .unwrap();
        }
        //max
        else if packet.type_id == 3 {
            packet.value = values.into_iter().reduce(u64::max)
                                        .unwrap();
        }
        // > 
        else if packet.type_id == 5 {
            packet.value = if values[0] > values[1] { 1 } else { 0 };
        }
        // < 
        else if packet.type_id == 6 {
            packet.value = if values[0] < values[1] { 1 } else { 0 };
        }
        // == 
        else if packet.type_id == 7 {
            packet.value = if values[0] == values[1] { 1 } else { 0 };
        }

    }
    packet.len = i;
    return packet;
    //return (i, version, total_version, value);
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
        let p= parse_packet(b.as_str()); 
        assert_eq!(p.version, 6);
        assert_eq!(p.value, 2021);
    }

    #[test]
    fn test_parse_packet_version_operator1(){
        let b= to_binary("38006F45291200");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.version, 1);
    }
    
    #[test]
    fn test_parse_packet_version_operator2(){
        let b= to_binary("EE00D40C823060");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.version, 7);
    }

    #[test]
    fn test_parse_packet_version_operator_totalv(){
        let b= to_binary("8A004A801A8002F478");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.total_version, 16);
    }

    #[test]
    fn test_parse_packet_version_operator_totalv2(){
        let b= to_binary("620080001611562C8802118E34");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.total_version, 12);
    }

    #[test]
    fn test_parse_packet_version_operator_totalv3(){
        let b= to_binary("C0015000016115A2E0802F182340");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.total_version, 23);
    }

    #[test]
    fn test_all_test(){
        let str = read_file("advent_day16_test.txt");
        let b= to_binary(str.as_str());
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.total_version, 31);
    }

    #[test]
    fn test_all(){
        let str = read_file("advent_day16.txt");
        assert_eq!(str.len(), 1344);
        let b= to_binary(str.as_str());
        let p = parse_packet(b.as_str()); 
        println!("Total version = {}", p.total_version);
    }

    #[test]
    fn test_value_sum(){
        let b= to_binary("C200B40A82");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 3);
    }

    #[test]
    fn test_value_prod(){
        let b= to_binary("04005AC33890");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 54);
    }

    #[test]
    fn test_value_min(){
        let b= to_binary("880086C3E88112");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 7);
    }

    #[test]
    fn test_value_max(){
        let b= to_binary("CE00C43D881120");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 9);
    }

    #[test]
    fn test_value_ge(){
        let b= to_binary("D8005AC2A8F0");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 1);
    }

    #[test]
    fn test_value_le(){
        let b= to_binary("F600BC2D8F");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 0);
    }

    #[test]
    fn test_value_neq(){
        let b= to_binary("9C005AC2F8F0");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 0);
    }
    
    #[test]
    fn test_value_eq(){
        let b= to_binary("9C0141080250320F1802104A08");
        let p = parse_packet(b.as_str()); 
        assert_eq!(p.value, 1);
    }

    #[test]
    fn test_all_value(){
        let str = read_file("advent_day16.txt");
        assert_eq!(str.len(), 1344);
        let b= to_binary(str.as_str());
        let p = parse_packet(b.as_str()); 
        println!("Total value = {}", p.value);
    }
}