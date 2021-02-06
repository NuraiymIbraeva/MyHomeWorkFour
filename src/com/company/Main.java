package com.company;

import java.util.Random;

public class Main {
    public static int[] heroesHealth = {270, 280, 250, 265,300,250,300,500};
    public static int[] heroesDamage = {20, 15, 25, 0,0,50,15,10};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic", "Medic","Lucky", "Thor" ,"Berserk" +
            "golem"};
    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int round_number = 0;

    public static void main(String[] args) {
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        round_number++;
        bossDefenceType = changeBossDefence();
        System.out.println("Boss choose: " + bossDefenceType);
        luckydown();
        thorBeatBoss();
        blockBerserk();
        golemGolem();
        bossHits();
        healheroes();
        heroesHit();
        printStatistics();
    }

    public static void golemGolem() {
        if (heroesHealth[7] > 0) {
            double gKoef = bossDamage / 5;
            for (int i = 0; i < heroesHealth.length; i++) {
                heroesHealth[i] += gKoef;
            }
            heroesHealth[7] -= (gKoef * 2);
        }
    }

    public static void blockBerserk(){
        if(heroesHealth[6]> 0){
            Random blockBerserk = new Random();
            int ber = blockBerserk.nextInt(5) +1;
            heroesDamage[6] = bossDamage / ber;
            System.out.println("berserk block some damage" + bossDamage / ber);

        }
    }

    public static void thorBeatBoss() {
        if(heroesHealth[5]> 0){
            Random thorWillBeat = new Random();
            int thorBeat = thorWillBeat.nextInt(5) +1;
            if (thorBeat == 4) {
                bossDamage =0;
                System.out.println("Boss failed");
            }
            else bossDamage = 50;
        }
    }

    public static void luckydown() {
        if(heroesHealth[4]> 0) {
            int luk = new Random().nextInt(3) +1;
            if (luk == 2) {
                heroesHealth[4] += bossDamage;
                System.out.println("Lucky chance");
            }
        }
    }

    public static void healheroes() {
        if (heroesHealth[3] > 0) {
            Random choose = new Random();
            int k = choose.nextInt(30) + 15;
            int min = heroesHealth[0];
            int index = 0;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] < min) {
                    min = heroesHealth[i];
                    index = i;
                }
            }

            if (min <100  && min != 0) {
                heroesHealth[index] += k;
                System.out.println("Medic hill " + heroesAttackType[index] + " for " + k);
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static String changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt( heroesAttackType.length); // 0,1,2
        return heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
            }
        }
    }

    public static void heroesHit() {
        Random random = new Random();
        int coeff = random.nextInt(8) + 2; //2,3,4,5,6,7,8,9
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (coeff == 3) {
                    bossHealth = bossHealth - heroesDamage[i] * coeff;
                    System.out.println("Critical damage: ["
                            + coeff + "] = " +
                            heroesDamage[i] * coeff);
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("________ ROUND [" + round_number + "]");
        System.out.println("Boss health: " + bossHealth
                + " [" + bossDamage + "]");
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " [" +
                    heroesDamage[i] + "]");
            // System.out.println("medicHealth:" + medicHealth
            // + " [" + medicDamage + "]");
        }
        System.out.println("________________");
    }
}
