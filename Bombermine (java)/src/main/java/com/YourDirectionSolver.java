package com;

import java.io.IOException;
import com.utils.Board;
import com.utils.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * User: your name
 */


public class YourDirectionSolver implements DirectionSolver {
    final String AddFire = Direction.ACT.toString() + ",";
    static Boolean NextFire = false;
    static Random r = new Random();
    static Direction dir = Direction.LEFT;

    // REALIZATION
    String ReadAction()
    {
        Direction dir = Direction.STOP;
        try
        {
            int q = System.in.read();
            switch (q)
            {
                case 'w':
                    return dir.UP.toString();
                case 'a':
                    return dir.LEFT.toString();
                case 's':
                    return dir.DOWN.toString();
                case 'd':
                    return dir.RIGHT.toString();
                case ' ':
                    return dir.ACT.toString();
                default:
                    return dir.STOP.toString();
            }
        }
        catch (IOException e){
            System.out.println("Error reading from user");
        }
        return "";
    }
    boolean CanGo(Board board, Direction dir)
    {
        List<Point> list = board.getBarriers();
        Point pt = new Point(board.getBomberman().getX() + dir.getX(),
                             board.getBomberman().getY() + dir.getY());
        return !(list.contains(pt));
    }
    boolean CommingNearMan(Board board, Direction dir)
    {
        Point pt_l, pt_r, pt_u, pt_d;
        List<Point> list = board.getMeatChoppers();
        list.addAll(board.getOtherBombermans());

        for(int i = 1; i < 3; i++)
        {
            pt_l = new Point(board.getBomberman().getX() - i,
                             board.getBomberman().getY() + i );
            pt_r = new Point(board.getBomberman().getX() + i,
                             board.getBomberman().getY() - i );
            pt_u = new Point(board.getBomberman().getX() + i,
                             board.getBomberman().getY() + i );
            pt_d = new Point(board.getBomberman().getX() - i,
                             board.getBomberman().getY() - i );

            if (list.contains(pt_l) || list.contains(pt_r) || list.contains(pt_u) || list.contains(pt_d)) {
                return true;
            }
        }
        return false;
    }
    Boolean CommingToWall(Board board, Direction dir)
    {
        Point pt = new Point(board.getBomberman().getX() + dir.getX()*2,
                             board.getBomberman().getY() + dir.getY()*2);
        board.isBarrierAt(pt.getX(), pt.getY());
        return false;
    }
    Boolean CommingToBomb(Board board, Direction dir)
    {
        Point pt;
        List<Point> list = board.getBombs();
        for (int i = 1; i < 4; i++)
        {
            pt = new Point(board.getBomberman().getX() + dir.getX() * i,
                           board.getBomberman().getY() + dir.getY() * i);
            if (list.contains(pt))
            {
                return true;
            }
        }
        return false;
    }
    Boolean IsWeLocked(Board board)
    {
        return  !CanGo(board, Direction.LEFT)  &&
                !CanGo(board, Direction.RIGHT) &&
                !CanGo(board, Direction.UP)    &&
                !CanGo(board, Direction.DOWN);
    }
    Direction GetDirection(int ChooseDir)
    {
        System.out.println(ChooseDir);
        switch(ChooseDir)
        {
            case 1: return Direction.LEFT;
            case 2: return Direction.RIGHT;
            case 3: return Direction.UP;
            case 4: return Direction.DOWN;
            default:
                //System.out.println("Even here");
                return Direction.LEFT;  //never goes here
        }
    }
    @Override
    public String get(Board board)
    {
        String fire = "";

        int randomInt = 0;


        while( true )
        {
            // check if we are locked
            if (IsWeLocked(board))
            {
                fire = AddFire;
                break;
            }
            // we are at the Barrier
            else if (CanGo(board, dir))
            {
                break;
            }
            // choose another direction
            else
            {
                //System.out.println("ReGenerate: " + dir.toString());
                fire = AddFire;
                randomInt = r.nextInt(5);
                if (randomInt > 0 && randomInt < 5) // fix for generating strange numbers
                {
                    dir = GetDirection(randomInt % 5);
                }
            }
        }


        //! First fire !!!
        if (NextFire == true)
        {
            fire = AddFire;
            NextFire = false;
        }
        // check if we are comming to a wall
        if (CommingToWall(board, dir))

        {
            fire = AddFire;
        }
        if (CommingNearMan(board, dir))
        {
            NextFire = true;
        }




        System.out.println("Move: " + fire + dir.toString());
        return fire + dir.toString();
        //return ReadAction();
    }
}
