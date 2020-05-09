package com.example.springjpa.model;

public enum RatingStar {
    ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5);
    private int value;

    RatingStar(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static RatingStar getRatingStar(int value){
        switch (value){
            case 2: return RatingStar.TWO;
            case 3: return RatingStar.THREE;
            case 4: return RatingStar.FOUR;
            case 5: return RatingStar.FIVE;
            default: return RatingStar.ONE;
        }
    }

}
