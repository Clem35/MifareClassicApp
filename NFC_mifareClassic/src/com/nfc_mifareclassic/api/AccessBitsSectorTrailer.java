package com.nfc_mifareclassic.api;

/**
 * Created with IntelliJ IDEA.
 * User: cbriand
 * Date: 05/08/13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public enum AccessBitsSectorTrailer {
    /*
                           <li>9 : Write KEY A, read access Bits, read KEY B and write KEY B only with KEY A</li>
            *              <li>10 : Read Access Bits and Read Key B only with KEY A</li>
            *              <li>11 : write KEY A, read Access Bits and Write KEY B with key B or B AND read Access bits with KEY A</li>
            *              <li>12 : Read Access bits with KEY A or KEY B</li>
            *              <li>13 : Write KEY A and B, Read/Write Access bits and read KEY B with "KEY A"</li>
            *              <li>14 : Write KEY A and B, Read/Write Access bits with "KEY B" and read Acces Bits with "KEY A" too</li>
            *              <li>15 : Read/Write Access Bits with KEY B and read Access Bits with KEY A too</li>
            *              <li>16 : Read Access Bits with KEY A or B</li>                            */

    WRITE_KEYA_AND_KEYB_READ_ACCESSBITS_AND_KEYB_WITH_KEYA("WRITE_KEYA_AND_KEYB_READ_ACCESSBITS_AND_KEYB_WITH_KEYA","0","0","0",9),

    READ_ACCESSBITS_AND_KEYB_WITH_KEYA("READ_ACCESSBITS_AND_KEYB_WITH_KEYA","0","1","0",10),

    WRITE_KEYA_KEYB_READ_ACCESSBITS_WITH_KEYB_AND_READ_ACCESSBITS_WITH_KEYA("WRITE_KEYA_KEYB_READ_ACCESSBITS_WITH_KEYB_AND_READ_ACCESSBITS_WITH_KEYA", "1","0","0",11),

    READ_ACCESSBITS_WITH__KEYA_OR_KEYB("READ_ACCESSBITS_WITH__KEYA_OR_KEYB","1", "1","0",12),

    READ_AND_WRITE_ACCESSBITS_AND_KEYB_AND_WRITE_KEYA_WITH_KEYA("READ_AND_WRITE_ACCESSBITS_AND_KEYB_AND_WRITE_KEYA_WITH_KEYA", "0","0","1",13),

    WRITE_KEYA_KEYB_AND_ACCESBITS_WITHKEYB_AND_READ_ACCESSBITS_WITH_KEYA_OR_KEYB("WRITE_KEYA_KEYB_AND_ACCESBITS_WITHKEYB_AND_READ_ACCESSBITS_WITH_KEYA_OR_KEYB","0","1","1",14),

    WRITE_ACCESSBITS_WITH_KEYB_AND_READ_ACCESSBITS_WITH_KEYA_OR_KEYB("WRITE_ACCESSBITS_WITH_KEYB_AND_READ_ACCESSBITS_WITH_KEYA_OR_KEYB","1","0","1",15),

    READ_ACCESSBITS_WITH_KEYA_OR_KEYB("READ_ACCESSBITS_WITH_KEYA_OR_KEYB","1","1","1",16);


    // Membres :

    private final String nom;
    private final String bit1;
    private final String bit2;
    private final String bit3;
    private final int index;
    
    AccessBitsSectorTrailer(String nom, String bit1, String bit2, String bit3, int index)
    {
        this.nom = nom;
        this.bit1 = bit1;
        this.bit2 = bit2;
        this.bit3 = bit3;
        this.index = index;
    }

    public String getNom(){ return this.nom; }
    public String getBit1() { return this.bit1; }
    public String getBit2() { return this.bit2; }
    public String getBit3() { return this.bit3; }
    public int getIndex() { return this.index; }

}
