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

#include "Element.h"
#include "PAQLContainerLibrary.h"
#include <string>
#include <stdexcept>
using namespace std;

#ifndef __ELEMENTCONTAINER_ELEMENT_CONTAINER_H__
#define __ELEMENTCONTAINER_ELEMENT_CONTAINER_H__

namespace paql
{
	namespace container
	{
		class ElementContainer :
		public std::list< boost::reference_wrapper<paql::element::Element> >,
		public virtual boost::fusion::map
		<
			boost::fusion::pair
			<
				MetaKey<boost::fusion::vector<char>, 0>,
				std::map
				<
					boost::fusion::vector<char>,
					boost::reference_wrapper<paql::element::Element>,
					paql::FusionVectorComparator< boost::fusion::vector<char> >
				>
			>,
			boost::fusion::pair
			<
				MetaKey<boost::fusion::vector<int, char>, 1>,
				std::map
				<
					boost::fusion::vector<int, char>,
					boost::reference_wrapper<paql::element::Element>,
					paql::FusionVectorComparator< boost::fusion::vector<int, char> >
				>
			>,
			boost::fusion::pair
			<
				MetaKey<boost::fusion::vector<string, int>, 2>,
				std::map
				<
					boost::fusion::vector<string, int>,
					boost::reference_wrapper<paql::element::Element>,
					paql::FusionVectorComparator< boost::fusion::vector<string, int> >
				>
			>
		>
		{
			public:
			template<int keyIndex> paql::element::Element& get(char a0)
			{
				boost::fusion::vector<char>key(a0);
				if(((boost::fusion::at_key<MetaKey<boost::fusion::vector<char>, 0> >(*this)).find(key)) == ((boost::fusion::at_key<MetaKey<boost::fusion::vector<char>, 0> >(*this)).end())){std::runtime_error notFound("Element not found"); throw notFound;};
				return (boost::unwrap_reference<paql::element::Element>::type&)(((boost::fusion::at_key<MetaKey<boost::fusion::vector<char>, 0> >(*this)).find(key))->second);
			}
			template<int keyIndex> paql::element::Element& get(int a0, char a1)
			{
				boost::fusion::vector<int, char>key(a0, a1);
				if(((boost::fusion::at_key<MetaKey<boost::fusion::vector<int, char>, 1> >(*this)).find(key)) == ((boost::fusion::at_key<MetaKey<boost::fusion::vector<int, char>, 1> >(*this)).end())){std::runtime_error notFound("Element not found"); throw notFound;};
				return (boost::unwrap_reference<paql::element::Element>::type&)(((boost::fusion::at_key<MetaKey<boost::fusion::vector<int, char>, 1> >(*this)).find(key))->second);
			}
			template<int keyIndex> paql::element::Element& get(string a0, int a1)
			{
				boost::fusion::vector<string, int>key(a0, a1);
				if(((boost::fusion::at_key<MetaKey<boost::fusion::vector<string, int>, 2> >(*this)).find(key)) == ((boost::fusion::at_key<MetaKey<boost::fusion::vector<string, int>, 2> >(*this)).end())){std::runtime_error notFound("Element not found"); throw notFound;};
				return (boost::unwrap_reference<paql::element::Element>::type&)(((boost::fusion::at_key<MetaKey<boost::fusion::vector<string, int>, 2> >(*this)).find(key))->second);
			}
			bool insert(paql::element::Element& element)
			{
				boost::reference_wrapper<paql::element::Element> elementToInsert = boost::ref<paql::element::Element>(element);
				{
					boost::fusion::vector<char> key(element.c);
					if((boost::fusion::at_key< MetaKey<boost::fusion::vector<char>, 0> >(*this)).count(key)) return false;
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<char>, 0> >(*this)).insert
					(
						std::map< boost::fusion::vector<char>, boost::reference_wrapper<paql::element::Element>, FusionVectorComparator< boost::fusion::vector<char> > >::value_type(key, elementToInsert)
					);
				}
				{
					boost::fusion::vector<int, char> key(element.i, element.c);
					if((boost::fusion::at_key< MetaKey<boost::fusion::vector<int, char>, 1> >(*this)).count(key)) return false;
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<int, char>, 1> >(*this)).insert
					(
						std::map< boost::fusion::vector<int, char>, boost::reference_wrapper<paql::element::Element>, FusionVectorComparator< boost::fusion::vector<int, char> > >::value_type(key, elementToInsert)
					);
				}
				{
					boost::fusion::vector<string, int> key(element.s1, element.i);
					if((boost::fusion::at_key< MetaKey<boost::fusion::vector<string, int>, 2> >(*this)).count(key)) return false;
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<string, int>, 2> >(*this)).insert
					(
						std::map< boost::fusion::vector<string, int>, boost::reference_wrapper<paql::element::Element>, FusionVectorComparator< boost::fusion::vector<string, int> > >::value_type(key, elementToInsert)
					);
				}
				this->push_back(boost::ref(element));
				return true;
			}
			void remove(paql::element::Element& element)
			{
				{
					boost::fusion::vector<char> key(element.c);
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<char>, 0> >(*this)).erase(key);
				}
				{
					boost::fusion::vector<int, char> key(element.i, element.c);
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<int, char>, 1> >(*this)).erase(key);
				}
				{
					boost::fusion::vector<string, int> key(element.s1, element.i);
					(boost::fusion::at_key< MetaKey<boost::fusion::vector<string, int>, 2> >(*this)).erase(key);
				}
				this->remove_if(ContentComparator<paql::element::Element>(boost::ref(element)));
			}
		};
	}
}

#endif