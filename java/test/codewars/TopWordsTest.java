import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import java.util.stream.*;
import java.util.Arrays;

public class TopWordsTest {

    @Test
    public void sampleTests() {
        ///*
        assertEquals(Arrays.asList("e", "d", "a"),    TopWords.top3("a a a  b  c c  d d d d  e e e e e"));
        assertEquals(Arrays.asList("e", "ddd", "aa"), TopWords.top3("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e"));
        assertEquals(Arrays.asList("won't", "wont"),  TopWords.top3("  //wont won't won't "));
        assertEquals(Arrays.asList("e"),              TopWords.top3("  , e   .. "));
        assertEquals(Arrays.asList(),                 TopWords.top3("  ...  "));
        assertEquals(Arrays.asList(),                 TopWords.top3("  '  "));
        assertEquals(Arrays.asList(),                 TopWords.top3("  '''  "));
        assertEquals(Arrays.asList("a", "of", "on"),  TopWords.top3(Stream
                .of("In a village of La Mancha, the name of which I have no desire to call to",
                        "mind, there lived not long since one of those gentlemen that keep a lance",
                        "in the lance-rack, an old buckler, a lean hack, and a greyhound for",
                        "coursing. An olla of rather more beef than mutton, a salad on most",
                        "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra",
                        "on Sundays, made away with three-quarters of his income.")
                .collect(Collectors.joining("\n")) ));
        //*/
        var res = TopWords.top3("fcDNhCykX fcDNhCykX fcDNhCykX!fcDNhCykX!fcDNhCykX fcDNhCykX.fcDNhCykX?fcDNhCykX fcDNhCykX fcDNhCykX fcDNhCykX;\n" +
                "DSMGDniq DSMGDniq DSMGDniq DSMGDniq;\n" +
                "mgqUdN_zMPBuSI JbAC!Wou/zMPBuSI,FdQGHz?XdRg-Wou,owESaX URxWu-zMPBuSI Wou!FdQGHz XdRg_zMPBuSI JbAC JbAC-XdRg JbAC XdRg FdQGHz/JbAC.JbAC_URxWu XdRg " +
                "JbAC XdRg Wou FdQGHz_Wou XdRg,JbAC_XdRg,URxWu?JbAC JbAC/mgqUdN URxWu Wou;Wou zMPBuSI!URxWu:URxWu:Wou.zMPBuSI.URxWu-zMPBuSI.Wou FdQGHz zMPBuSI Wou XdRg " +
                "XdRg owESaX JbAC!XdRg;JbAC?mgqUdN_FdQGHz FdQGHz_Wou_Wou?Wou.mgqUdN Wou URxWu?Wou!owESaX,Wou_URxWu URxWu-Wou!Wou!zMPBuSI JbAC:Wou;XdRg zMPBuSI FdQGHz Wou " +
                "FdQGHz Wou/FdQGHz_zMPBuSI!XdRg Wou?FdQGHz owESaX XdRg_URxWu;moDiIsZQYi;owESaX_mgqUdN XdRg;Wou XdRg URxWu.XdRg XdRg!URxWu XdRg JbAC!zMPBuSI/JbAC FdQGHz XdRg " +
                "XdRg;mgqUdN mgqUdN Wou zMPBuSI/Wou.URxWu URxWu/JbAC?mgqUdN XdRg_XdRg,JbAC XdRg/XdRg XdRg-Wou Wou URxWu!FdQGHz_zMPBuSI XdRg/XdRg XdRg XdRg \n" +
                "owwzp-pHHNOibYu/lzV''Z:PVVuuE NFsYlbHFPf pHHNOibYu?owwzp:lzV''Z owwzp PVVuuE!BPsDZwlN BPsDZwlN owwzp pzwcxcq pzwcxcq_owwzp:jtsa QKuAmRxrr!owwzp " +
                "BPsDZwlN BiWoCAXNe PVVuuE.lzV''Z pHHNOibYu BPsDZwlN pzwcxcq!BiWoCAXNe pzwcxcq pzwcxcq IaTOXldH pzwcxcq:BiWoCAXNe jtsa BPsDZwlN lzV''Z,owwzp-pzwcxcq " +
                "uhjBiChD',lzV''Z-LkcRJtJ?pzwcxcq.jtsa/pHHNOibYu/owwzp,RDDBiGaSw uhjBiChD':BiWoCAXNe.KyNzku.jtsa pzwcxcq pzwcxcq?RDDBiGaSw pzwcxcq!pzwcxcq BPsDZwlN!RDDBiGaSw " +
                "QKuAmRxrr BiWoCAXNe PVVuuE uhjBiChD' BPsDZwlN pHHNOibYu BiWoCAXNe:BiWoCAXNe owwzp?RDDBiGaSw NFsYlbHFPf!owwzp!RDDBiGaSw RDDBiGaSw lzV''Z " +
                "BiWoCAXNe-KyNzku:LkcRJtJ!LNRqu pzwcxcq?LkcRJtJ-LNRqu,BiWoCAXNe pzwcxcq:RDDBiGaSw!lzV''Z jtsa QKuAmRxrr.jtsa LkcRJtJ KyNzku owwzp BiWoCAXNe " +
                "NFsYlbHFPf PVVuuE_owwzp/LNRqu?jtsa;jtsa RDDBiGaSw owwzp pHHNOibYu RDDBiGaSw uhjBiChD':RDDBiGaSw jtsa.NFsYlbHFPf RDDBiGaSw jtsa-pHHNOibYu?lzV''Z " +
                "RDDBiGaSw-lzV''Z RDDBiGaSw pHHNOibYu RDDBiGaSw PVVuuE,lzV''Z,RDDBiGaSw IaTOXldH?RDDBiGaSw LkcRJtJ IaTOXldH.LNRqu uhjBiChD';jtsa BPsDZwlN/owwzp owwzp " +
                "RDDBiGaSw KyNzku BiWoCAXNe-KyNzku NFsYlbHFPf,LkcRJtJ;RDDBiGaSw,pzwcxcq LNRqu BiWoCAXNe owwzp;BiWoCAXNe:owwzp/PVVuuE lzV''Z pzwcxcq-BiWoCAXNe LkcRJtJ " +
                "BiWoCAXNe pzwcxcq BiWoCAXNe owwzp.lzV''Z BiWoCAXNe-LNRqu:pHHNOibYu!owwzp pzwcxcq!owwzp IaTOXldH.RDDBiGaSw RDDBiGaSw;RDDBiGaSw RDDBiGaSw-uhjBiChD'_LNRqu,jtsa?RDDBiGaSw " +
                "pHHNOibYu_owwzp.lzV''Z PVVuuE:BiWoCAXNe QKuAmRxrr?NFsYlbHFPf lzV''Z BPsDZwlN pHHNOibYu/LkcRJtJ;pHHNOibYu.jtsa_RDDBiGaSw;LkcRJtJ?lzV''Z PVVuuE-BPsDZwlN.lzV''Z " +
                "NFsYlbHFPf BPsDZwlN-BPsDZwlN!KyNzku BiWoCAXNe-jtsa.pzwcxcq BiWoCAXNe.NFsYlbHFPf_LkcRJtJ BPsDZwlN.owwzp/KyNzku RDDBiGaSw BiWoCAXNe " +
                "lzV''Z,jtsa lzV''Z_lzV''Z?BiWoCAXNe_owwzp.IaTOXldH KyNzku/QKuAmRxrr:RDDBiGaSw pHHNOibYu jtsa?pzwcxcq IaTOXldH,uhjBiChD' uhjBiChD'_NFsYlbHFPf " +
                "KyNzku/lzV''Z.lzV''Z pzwcxcq KyNzku pzwcxcq;BPsDZwlN?lzV''Z:LNRqu-jtsa PVVuuE owwzp lzV''Z-KyNzku owwzp pzwcxcq LNRqu,NFsYlbHFPf?pHHNOibYu;owwzp-LkcRJtJ " +
                "BPsDZwlN_owwzp!pzwcxcq owwzp/lzV''Z BPsDZwlN;pzwcxcq!pzwcxcq pzwcxcq?IaTOXldH,pzwcxcq RDDBiGaSw,IaTOXldH jtsa_RDDBiGaSw-LkcRJtJ QKuAmRxrr.BPsDZwlN;");
        assertEquals(Arrays.asList("xdrg", "owwzp", "rddbigasw"), res);
    }

}