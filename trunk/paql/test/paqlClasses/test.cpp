#include <iostream>
#include "Element.h"
#include "Container.h"
#include "Query.h"

int main()
{
    Container c;
    Element e1 = {"ciao1", true, 'a', 25, 2.1};
    Element e2 = {"ciao2", false, 'b', 30, 2.2};
    Element e3 = {"ciao3", true, 'c', 35, 2.3};
    Element e4 = {"ciao4", false, 'd', 40, 2.4};
    Element e5 = {"ciao5", true, 'e', 45, 2.5};
    c.insert(e1);
    c.insert(e2);
    c.insert(e3);
    c.insert(e4);
    c.insert(e5);
    Element ee = c.get<1>("ciao1", true, 2.1);
    //ee.i = -1;
    //Element ef = c.get<1>(true, 2.1, "ciao1");
    ee.i++;
    Element& ef = c.get<1>("ciao1", true, 2.1);
    std::cout << ef.i << std::endl;
    c.remove(ef);
    //std::cout << ee.s << ee.b << ee.c <<  ee.i << ee.d << std::endl;
    return 0;
}
