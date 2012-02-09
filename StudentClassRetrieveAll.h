/**
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

#include "Student.h"
#include "StudentClass.h"
#include <list>
#include <boost/ref.hpp>

#ifndef __STUDENTCLASSRETRIEVEALL_STUDENTCLASS_QUERY_H__
#define __STUDENTCLASSRETRIEVEALL_STUDENTCLASS_QUERY_H__

namespace paql
{
	namespace query
	{
		class StudentClassRetrieveAll
		{
			private:
				paql::container::StudentClass& container;
			public:
				StudentClassRetrieveAll(paql::container::StudentClass& containerToSet) : container(containerToSet){}
				std::list<boost::reference_wrapper<paql::element::Student> > execute()
				{
					std::list<boost::reference_wrapper<paql::element::Student> > listToReturn;
					for(std::list< boost::reference_wrapper<paql::element::Student> >::iterator i = container.begin(); i != container.end(); i++)
					{
						listToReturn.push_back((*i));
					}
					return listToReturn;
				}
		};
	}
}

#endif