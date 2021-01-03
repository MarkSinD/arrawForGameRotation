package com.gamecodeschool.simplegameloop;

public class PhysicsEngine {
    ArrowSpec mArrowSpec;

    PhysicsEngine(ArrowSpec arrowSpec){

        mArrowSpec = arrowSpec;

    }

    boolean update( ){

        mArrowSpec.move();

        return true;
    }
}
