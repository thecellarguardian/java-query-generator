#include "E.h"
#include "EC.h"
#include "ECQ.h"
#include <iostream>

int main()
{
	paql::element::E e1 = {1, "a", "b"};
	paql::element::E e2 = {2, "b", "b"};
	paql::element::E e3 = {3, "c", "b"};
	paql::element::E e4 = {4, "d", "b"};
	paql::element::E e5 = {5, "e", "b"};
	paql::container::EC c;
	c.insert(e1);
	c.insert(e2);
	c.insert(e3);
	c.insert(e4);
	c.insert(e5);
	paql::element::E& eref = c.get<1>("b", 2);
	eref.b = "c";
	std::cout << e2.b << std::endl;
	c.remove(eref);
	paql::element::E& eref1 = c.get<0>(2);
	return 0;
}
