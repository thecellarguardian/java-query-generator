#include "Element.h"
#include "ElementContainer.h"
#include "ElementContainerQuery.h"
#include <iostream>
using namespace paql::element;
using namespace paql::container;
using namespace paql::query;
using namespace std;
int main()
{
	Element e1 = {'a', 1, "stringA1", "stringB1"};
	Element e2 = {'a', 2, "stringA2", "stringB2"};
	ElementContainer c;
	c.insert(e1);
	c.insert(e2);
	Element& ref1 = c.get<0>('a');
	Element& ref2 = c.get<1>(2, 'a');
	cout << ref1.i << endl;
	cout << ref2.i << endl;
	return 0;
}
