//package tech.makers.demo.levelManagement.levels;
//
//import tech.makers.demo.assets.Door;
//import tech.makers.demo.levelManagement.*;
//import tech.makers.demo.levelManagement.ComputerInteraction;
//import tech.makers.demo.player.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Level1 extends Level {
//
//    public Level1() {
//        super(
//                new Player(100, 100),
//                createPuzzles(),
//                new Door(600, 400, "/sprites/door.png"),
//                createInteractions()
//        );
//    }
//
//    private static List<Puzzle> createPuzzles() {
//        List<Puzzle> puzzles = new ArrayList<>();
//        // Add any initial puzzles here if needed
//        return puzzles;
//    }
//
//    private static List<Interaction> createInteractions() {
//        List<Interaction> interactions = new ArrayList<>();
//        Door door = new Door(600, 400, "/sprites/door.png");
//        ComputerInteraction computerInteraction = new ComputerInteraction(300, 300, "/sprites/Computer.png", door);
//        EddieInteraction eddieInteraction = new EddieInteraction(700, 50, "/sprites/Eddie_idle_anim.png", computerInteraction);
//        interactions.add(computerInteraction);
//        interactions.add(new RouterInteraction(100, 500, "/sprites/Router.png", computerInteraction));
//        interactions.add(eddieInteraction);
//        return interactions;
//    }
//}
