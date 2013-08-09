package com.nfc_mifareclassic.api;


/**
 * Created with IntelliJ IDEA.
 * User: cbriand
 * Date: 05/08/13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public enum AccessBits {

        // Il faut appeler l'un des constructeurs déclarés :

        READ_WRITE_INC_DEC_WITH_A_OR_B("READ_WRITE_INC_DEC_WITH_A_OR_B","0","0","0",1),
        
        READ_WITH_A_OR_B("READ_WITH_A_OR_B","0","1","0",2),
        
        READ_WITH_A_OR_B_AND_WRITE_WITH_B("READ_WITH_A_OR_B_AND_WRITE_WITH_B", "1","0","0",3),
        
        READ_WRITE_INC_DEC_WITH_B_AND_READ_DEC_WITH_A("READ_WRITE_INC_DEC_WITH_B_AND_READ_DEC_WITH_A","1", "1","0",4),
        
        READ_DEC_WITH_A_OR_B("READ_DEC_WITH_A_OR_B", "0","0","1",5),
        
        READ_WRITE_WITH_B("READ_WRITE_WITH_B","0","1","1",6),
        
        READ_WITH_B("READ_WITH_B","1","0","1",7),
        
        NOTHING("NOTHING","1","1","1",8);    // <- NB: le point-virgule pour mettre fin à la liste des constantes !


        // Membres :

        private final String nom;
        private final String bit1;
        private final String bit2;
        private final String bit3;
        private final int index;
        AccessBits(String nom, String bit1, String bit2,String bit3, int index)
        {
            this.nom = nom;
            this.bit1 = bit1;
            this.bit2 = bit2;
            this.bit3 = bit3;
            this.index =index;
        }

        public String getNom(){ return this.nom; }
        public String getBit1() { return this.bit1; }
        public String getBit2() { return this.bit2; }
        public String getBit3() { return this.bit3; }
        public int getIndex() { return this.index; }
}
