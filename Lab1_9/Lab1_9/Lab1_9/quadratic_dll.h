#pragma once
#ifdef QUADRATIC_DLL_EXPORTS
#define QUADRATIC_DLL_API __declspec(dllexport)
#else
#define QUADRATIC_DLL_API __declspec(dllimport)
#endif

QUADRATIC_DLL_API std::vector<double> decision(const int& a, const int& b, const int& c);