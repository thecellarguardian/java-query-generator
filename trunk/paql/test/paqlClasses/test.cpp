#include <iostream>
#include <typeinfo>
#include "Element.h"
#include "Container.h"
#include "Query.h"


int main()
{
    Container c;
    Element e1 = {"n1", true, 'a', 25, 2.1};
    Element e2 = {"n2", false, 'b', 30, 2.2};
    Element e3 = {"n3", true, 'c', 35, 2.3};
    Element e4 = {"n4", false, 'd', 40, 2.4};
    Element e5 = {"n5", true, 'e', 45, 2.5};
    c.insert(e1);
    c.insert(e2);
    c.insert(e3);
    c.insert(e4);
    c.insert(e5);
    Element ee = c.get<1>("n1", true, 2.1);
    //ee.i = -1;
    //Element ef = c.get<1>(true, 2.1, "ciao1");
    ee.i++;
    Element& ef = c.get<1>("n1", true, 2.1);
    std::cout << ef.i << std::endl;
    c.remove(ef);
    Query query(c);
    std::list<boost::reference_wrapper<Element> > queryList = query.execute();
    for(std::list<boost::reference_wrapper<Element> >::iterator i = queryList.begin(); i != queryList.end(); i++)
    {
        boost::unwrap_reference<Element>::type& j((*i));
        //std::cout << (*i).s << (*i).b << (*i).c << (*i).i << (*i).d << std::endl;
        std::cout << (++j.i) << std::endl;
    }
    Element& eg = c.get<1>("n3", true, 2.3);
    std::cout << eg.i << std::endl;
    return 0;
}
