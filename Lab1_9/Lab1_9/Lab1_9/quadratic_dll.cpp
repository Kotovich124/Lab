#include "pch.h"
#include "quadratic_dll.h"

std::vector<double> decision(const int& a, const int& b, const int& c) {
	std::vector<double>v;
	int d = b * b - 4 * a * c;
	if (d > 0) {
		v.push_back((-b - sqrt(d)) / (2. * a));
		v.push_back((-b + sqrt(d)) / (2. * a));
	}
	else if (d == 0) {
		v.push_back(-b / (2. * a));
	}
	return v;
}
