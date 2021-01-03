package com.theluckydev.fst.objet;

import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;

public class Objet implements Serializable, SalleBatimentInterface {

    public int indice;


    @Override
    public String getStandardName() {
        return "Standard";
    }

    @Override
    public String getType(){return "object";}

    @Override
    public boolean hasForAlias(String newTextU) {
        return false;
    }

}
