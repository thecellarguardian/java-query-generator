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
#include "PAQLContainerLibrary.h"
#include <string>
using namespace std;

#ifndef __STUDENTCLASS_STUDENT_CONTAINER_H__
#define __STUDENTCLASS_STUDENT_CONTAINER_H__

namespace paql
{
	namespace container
	{
		class StudentClass :
		public std::list< boost::reference_wrapper<paql::element::Student> >,
		public virtual boost::fusion::map
		<
			boost::fusion::pair
			<
				MetaKey<boost::fusion::vector<int>, 0>,
				std::map
				<
					boost::fusion::vector<int>,
					boost::reference_wrapper<paql::element::Student>,
					paql::FusionVectorComparator< boost::fusion::vector<int> >
				>
			>
		>
		{
			public:
			template<int keyIndex> paql::element::Student& get(int a0)
			{
				boost::fusion::vector<int>key(a0);
				return (boost::unwrap_reference<paql::element::Student>::type&)(((boost::fusion::at_key<MetaKey<boost::fusion::vector<int>, 0> >(*this)).find(key))->second);
			}
			bool insert(paql::element::Student& element)
			{
				boost::reference_wrapper<paql::element::Student> elementToInsert = boost::ref<paql::element::Student>(element);
				{
					boost::fusion::vector<int> key(element.code);
					if((boost::fusion::at_key< MetaKey<boost::fusion::vector<int>, 0> >(*this)).count(key)) return false;
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<int>, 0> >(*this)).insert
					(
						std::map< boost::fusion::vector<int>, boost::reference_wrapper<paql::element::Student>, FusionVectorComparator< boost::fusion::vector<int> > >::value_type(key, elementToInsert)
					);
				}
				this->push_back(boost::ref(element));
				return true;
			}
			void remove(paql::element::Student& element)
			{
				{
					boost::fusion::vector<int> key(element.code);
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<int>, 0> >(*this)).erase(key);
				}
				this->remove_if(ContentComparator<paql::element::Student>(boost::ref(element)));
			}
		};
	}
}

#endif