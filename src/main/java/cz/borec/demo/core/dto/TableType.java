package cz.borec.demo.core.dto;

public enum TableType {
   // using the constructor defined below
   STANDARD("Standard"),
   BIG("Velk\u00FD"),
   SMALL("\u017Didle"),
   HORIZONTAL("Horizont\u00E1ln\u00ED"),
   VERTICAL("Vertik\u00E1ln\u00ED");

   // Member to hold the name
   private String string;

   // constructor to set the string
   TableType(String name){string = name;}

   // the toString just returns the given name
   @Override
   public String toString() {
       return string;
   }
}