package org.ybw.nb;

import java.awt.*;

public class TriangleNode {
	int[][] setP, nowP;
	double[] moveSpeed = {1, 1, 1};
	Color bg;

	public TriangleNode(int[][] setP, Color bg) {
		this.setP = setP;
		this.nowP = setP;
		this.bg = bg;
	}

	public void setLocation(int[][] setP, Color bg) {
		this.setP = setP;
		this.bg = bg;
	}

	public void setBg(Color bg) {
		this.bg = bg;
	}

	public double[] getVector(int dotIndex, int[][] nowP, int[][] setP) {
		double dx = setP[0][dotIndex] - nowP[0][dotIndex], dy = setP[1][dotIndex] - nowP[1][dotIndex];
		if (dx == 0 && dy == 0) {
			return new double[]{0, 0, 0, 0};
		}
		double dist = Math.sqrt(dx * dx + dy * dy);
		return new double[]{dx / dist, dy / dist, dx, dy};
	}

	public void movePoint(int dotIndex, int[][] nowP, int[][] setP) {
		double[] vector = getVector(dotIndex, nowP, setP);
		double dx = (vector[0] < 0 ? -1 : 1) * Math.min(Math.abs(vector[0] * moveSpeed[dotIndex]), Math.abs(vector[2]));
		double dy = (vector[1] < 0 ? -1 : 1) * Math.min(Math.abs(vector[1] * moveSpeed[dotIndex]), Math.abs(vector[3]));
		nowP[0][dotIndex] += (dx > 0 && dx < 1) ? 1 : dx;
		nowP[1][dotIndex] += (dy > 0 && dy < 1) ? 1 : dy;

		if (Math.sqrt(dx * dx + dy * dy) > 300) {
			moveSpeed[dotIndex] += 0.1;
		} else {
			moveSpeed[dotIndex] = 0.5;
		}
		if (moveSpeed[dotIndex] > 5) {
			moveSpeed[dotIndex] = 5;
		} else if (moveSpeed[dotIndex] < 1) {
			moveSpeed[dotIndex] = 1;
		}
	}

	public void render(Graphics2D graphics2D) {
		for (int i = 0; i < 3; i++) {
			movePoint(i, nowP, setP);
		}
		graphics2D.setColor(bg);
		graphics2D.fillPolygon(new Polygon(nowP[0], nowP[1], 3));
	}
}
