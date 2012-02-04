/**
 * @file Query.h
 * @author Cosimo Sacco <cosimosacco@gmail.com>
 *
 * @section LICENSE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

#include "Element.h"
#include "Container.h"

#include <list>
#include <boost/ref.hpp>

#ifndef QUERY_H
#define QUERY_H

class Query
{
    private:
        const Container& container;
    public:
        Query(const Container& containerToSet) : container(containerToSet){}
        /*std::list<boost::reference_wrapper<Element const> > execute()
        {
            return listToReturn;
        }*/
};

#endif
