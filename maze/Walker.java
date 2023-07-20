package gad.maze;

import java.awt.*;
import java.util.stream.IntStream;

public class Walker {
	private boolean[][] maze;
	private Result result;

	private boolean y;
	private int direction;
	private Point location;

	public Walker(boolean[][] maze, Result result) {
       this.maze = maze;
		this.result = result;
		y = true;
		this.direction = -1;
		location = new Point(1,0);
	}

	public boolean walk() {
		if(maze[1][0] || maze[maze.length-1][maze[0].length-2]){
			return false;
		}
		Point goal = new Point(maze.length-1,maze[0].length-2);
		result.addLocation(1,0);
        while(!location.equals(goal)){
			if(y){
				if(!maze[location.x+direction][location.y]){
					location.setLocation(location.x+direction,location.y);
					result.addLocation(location.x, location.y);
					y = false;
				}else if(direction == 1 && location.y-1<0){
				return false;
			}else if(!maze[location.x][location.y-direction]){
					location.setLocation(location.x,location.y-direction);
					result.addLocation(location.x, location.y);
				}else if(!maze[location.x-direction][location.y]) {
					location.setLocation(location.x - direction, location.y);
					result.addLocation(location.x, location.y);
					direction = -direction;
					y = false;
				}else{
					location.setLocation(location.x,location.y+direction);
					result.addLocation(location.x, location.y);
					direction = -direction;
				}
			}else {
				if(!maze[location.x][location.y+direction]){
					location.setLocation(location.x,location.y+direction);
					result.addLocation(location.x, location.y);
					y = true;
					direction = -direction;
				}else if(!maze[location.x+direction][location.y]){
					location.setLocation(location.x+direction,location.y);
					result.addLocation(location.x, location.y);
				}else if(!maze[location.x][location.y-direction]) {
					location.setLocation(location.x, location.y -direction);
					result.addLocation(location.x, location.y);
					y = true;
				}else{
					location.setLocation(location.x-direction,location.y);
					result.addLocation(location.x, location.y);
					direction = -direction;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		boolean[][] maze = Maze.generateStandardMaze(50000,50000);
		StudentResult result = new StudentResult();

		Walker walker = new Walker(maze, result);
		System.out.println(walker.walk());

	}
}
