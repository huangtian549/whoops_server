package com.whoops.util;

public class Test {

	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 3959000; // 地球的半径

	public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
		double x, y, distance;
		x = (longt2 - longt1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
		y = (lat2 - lat1) * PI * R / 180;
		// System.out.println((Math.hypot(x, y) / 1000));
		// distance = Double.parseDouble(String.format("%.0f", Math.hypot(x, y)
		// / 1000));
		distance = (Math.hypot(x, y) / 1000);
		return distance;
	}

	private static double EARTH_RADIUS = 3959;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double distance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}
	public static void main(String[] args) {
		System.out.println(getDistance(37.9, -122.41, 37.787357, -122.408226));
		System.out.println(distance(37.9, -122.41, 37.787357, -122.408226));
	}

}
