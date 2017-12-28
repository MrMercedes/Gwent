package com.example.hp0331.gwent;

/**
 * Created by Aaron.zhang
 * at  2017/12/22.
 */

public class Gwent {
    public int attack;
    public int guard;

    public Gwent(int attack, int guard) {
        this.attack = attack;
        this.guard = guard;
    }

    public void attacked(Gwent gwent){
        if (guard<gwent.attack){
            attack=attack+guard-gwent.attack;
            guard=0;
        }else {
            guard=guard-gwent.attack;
        }

        if (attack<0){
            attack=0;
        }
    }
}
