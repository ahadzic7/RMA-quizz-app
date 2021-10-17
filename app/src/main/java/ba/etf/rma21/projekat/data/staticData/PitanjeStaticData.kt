package ba.etf.rma21.projekat.data.staticData

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.old.PitanjeKviz

fun listaPitanja(): List<Pitanje> {
    return listOf(
//            //TP
//            Pitanje( "tp1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "tp2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "tp3", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "tp4", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "tp5", "5 + 2 = ", listOf("1", "2", "7"), 2),
//            Pitanje( "tp6", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "tp7", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "tp8", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "tp9", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "tp10", "5 + 2 = ", listOf("1", "2", "7"), 2),
//            Pitanje( "tp11", "5 + 2 = ", listOf("1", "2", "7"), 2),
//
//            //RMA
//            Pitanje( "rma1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "rma2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "rma3", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "rma4", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "rma5", "5 + 2 = ", listOf("1", "2", "7"), 2),
//            Pitanje( "rma6", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "rma7", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "rma8", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "rma9", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "rma10", "5 + 2 = ", listOf("1", "2", "7"), 2),
//            Pitanje( "rma11", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "rma12", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "rma13", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "rma14", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "rma15", "5 + 2 = ", listOf("1", "2", "7"), 2),
//            Pitanje( "rma16", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "rma17", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "rma18", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "rma19", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "rma20", "5 + 2 = ", listOf("1", "2", "7"), 2),
//
//            //IM
//            Pitanje( "im1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "im2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "im3", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "im4", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "im5", "5 + 2 = ", listOf("1", "2", "7"), 2),
//            Pitanje( "im6", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "im7", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "im8", "5 + 2 = ", listOf("1", "2", "7"), 2),
//
//            //DM
//            Pitanje( "dm1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "dm2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "dm3", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "dm4", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "dm5", "5 + 2 = ", listOf("1", "2", "7"), 2),
//
//            //NA
//            Pitanje( "na1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "na2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "na3", "3 + 2 = ", listOf("1", "2", "5"), 2),
//            Pitanje( "na4", "4 + 2 = ", listOf("1", "2", "6"), 2),
//            Pitanje( "na5", "5 + 2 = ", listOf("1", "2", "7"), 2),
//
//            //OE
//            Pitanje( "oe1", "1 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "oe2", "2 + 2 = ", listOf("1", "2", "4"), 3),
//            Pitanje( "oe3", "3 + 2 = ", listOf("1", "2", "5"), 3),
//
//            //OPI
//            Pitanje( "opi1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "opi2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "opi3", "3 + 2 = ", listOf("1", "2","5"), 2),
//
//            //SI
//            Pitanje( "si1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "si2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "si3", "3 + 2 = ", listOf("1", "2", "5"), 2),
//
//            //NBP
//            Pitanje( "nbp1", "1 + 2 = ", listOf("1", "2", "3"), 2),
//            Pitanje( "nbp2", "2 + 2 = ", listOf("1", "2", "4"), 2),
//            Pitanje( "nbp3", "3 + 2 = ", listOf("1", "2", "5"), 2)
    )
}

fun listaPitanjeKviz(): List<PitanjeKviz> {
    return listOf(
//            //TP pitanja
//            PitanjeKviz("tp1", "TP1", "Tehnike programiranja"),
//            PitanjeKviz("tp2", "TP1", "Tehnike programiranja"),
//            PitanjeKviz("tp3", "TP1", "Tehnike programiranja"),
//            PitanjeKviz("tp4", "TP1", "Tehnike programiranja"),
//            PitanjeKviz("tp5", "TP1", "Tehnike programiranja"),
//
//            PitanjeKviz("tp6", "TP2", "Tehnike programiranja"),
//            PitanjeKviz("tp7", "TP2", "Tehnike programiranja"),
//            PitanjeKviz("tp8", "TP2", "Tehnike programiranja"),
//
//            PitanjeKviz("tp9", "TP3", "Tehnike programiranja"),
//            PitanjeKviz("tp10", "TP3", "Tehnike programiranja"),
//            PitanjeKviz("tp11", "TP3", "Tehnike programiranja"),
//
//            //RMA pitanja
//            PitanjeKviz("rma1", "RMA1", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma2", "RMA1", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma3", "RMA1", "Razvoj mobilnih aplikacija"),
//
//            PitanjeKviz("rma4", "RMA2", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma5", "RMA2", "Razvoj mobilnih aplikacija"),
//
//            PitanjeKviz("rma6", "RMA3", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma7", "RMA3", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma8", "RMA3", "Razvoj mobilnih aplikacija"),
//
//            PitanjeKviz("rma9", "RMA4", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma10", "RMA4", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma11", "RMA4", "Razvoj mobilnih aplikacija"),
//
//            PitanjeKviz("rma12", "RMA5", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma13", "RMA5", "Razvoj mobilnih aplikacija"),
//
//            PitanjeKviz("rm14", "RMA6", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma15", "RMA6", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma16", "RMA6", "Razvoj mobilnih aplikacija"),
//
//            PitanjeKviz("rma17", "RMA7", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma18", "RMA7", "Razvoj mobilnih aplikacija"),
//            PitanjeKviz("rma19", "RMA7", "Razvoj mobilnih aplikacija"),
//
//            //IM pitanja
//            PitanjeKviz("im1", "IM1", "Inzenjerska matematika"),
//            PitanjeKviz("im2", "IM1", "Inzenjerska matematika"),
//            PitanjeKviz("im3", "IM1", "Inzenjerska matematika"),
//
//            PitanjeKviz("im4", "IM2", "Inzenjerska matematika"),
//            PitanjeKviz("im5", "IM2", "Inzenjerska matematika"),
//
//            PitanjeKviz("im6", "IM3", "Inzenjerska matematika"),
//            PitanjeKviz("im7", "IM3", "Inzenjerska matematika"),
//            PitanjeKviz("im8", "IM3", "Inzenjerska matematika"),
//
//            //DM pitanja
//            PitanjeKviz("dm1", "DM1", "Diskretna matematika"),
//            PitanjeKviz("dm2", "DM1", "Diskretna matematika"),
//            PitanjeKviz("dm3", "DM1", "Diskretna matematika"),
//
//            PitanjeKviz("dm4", "DM2", "Diskretna matematika"),
//            PitanjeKviz("dm5", "DM2", "Diskretna matematika"),
//
//            //NA pitanja
//            PitanjeKviz("na1", "NA1", "Numericki algoritmi"),
//            PitanjeKviz("na2", "NA1", "Numericki algoritmi"),
//            PitanjeKviz("na3", "NA1", "Numericki algoritmi"),
//            PitanjeKviz("na4", "NA1", "Numericki algoritmi"),
//            PitanjeKviz("na5", "NA1", "Numericki algoritmi"),
//
//            //OE pitanja
//            PitanjeKviz("oe1", "OE1", "Osnove elektrotehnike"),
//            PitanjeKviz("oe2", "OE1", "Osnove elektrotehnike"),
//            PitanjeKviz("oe3", "OE1", "Osnove elektrotehnike"),
//
//            //OPI pitanja
//            PitanjeKviz("opi1", "OPI1", "Operaciona istrazivanja"),
//            PitanjeKviz("opi2", "OPI1", "Operaciona istrazivanja"),
//            PitanjeKviz("opi3", "OPI1", "Operaciona istrazivanja"),
//
//            //SI pitanja
//            PitanjeKviz("si1", "SI", "Softver inzinjering"),
//            PitanjeKviz("si2", "SI", "Softver inzinjering"),
//            PitanjeKviz("si3", "SI", "Softver inzinjering"),
//
//            //NBP pitanja
//            PitanjeKviz("nbp1", "NBP", "Baze podataka"),
//            PitanjeKviz("nbp2", "NBP", "Baze podataka"),
//            PitanjeKviz("nbp3", "NBP", "Baze podataka")
    )
}