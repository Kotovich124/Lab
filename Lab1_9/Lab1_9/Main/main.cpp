#include <iostream>
#include <vector>
#include "quadratic_dll.h"
using namespace std;

string before_message = "This program calculates the roots of the quadratic equation";
string no_roots_message = "no roots";
string one_root_message = "one root: ";
string two_roots_message = "two roots: ";
string after_message = "End of work.";

void main() {
    cout << before_message << endl;
    int a, b, c;
    cout << "Enter an integer a, b, c separated by space> ";
    cin >> a >> b >> c;
    vector<double> s = decision(a,b,c);
    if (s.size() == 0) cout << no_roots_message << endl;
    else if (s.size() == 1) cout << one_root_message << s[0] << endl;
    else cout << two_roots_message << s[0] << ", " << s[1] << endl;
    cout << after_message << endl;
    system("pause");
}