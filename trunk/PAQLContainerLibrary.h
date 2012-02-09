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

#include <boost/fusion/container/map.hpp>
#include <boost/fusion/include/map.hpp>
#include <boost/fusion/container/vector.hpp>
#include <boost/fusion/include/vector.hpp>
#include <boost/fusion/sequence/intrinsic/at_key.hpp>
#include <boost/fusion/include/at_key.hpp>
#include <boost/fusion/sequence/intrinsic/at_c.hpp>
#include <boost/fusion/include/at_c.hpp>
#include <boost/fusion/support/pair.hpp>
#include <boost/fusion/include/pair.hpp>
#include <boost/ref.hpp>
#include <map>
#include <list>
#include <string>
#include <algorithm>

#ifndef __PAQL_CONTAINER_LIBRARY_H__
#define __PAQL_CONTAINER_LIBRARY_H__

namespace paql
{
	template <typename KeyType, int i> class MetaKey{};
	template <typename KeyType> class FusionVectorComparator
	{
		public:
			bool operator()(const KeyType& a, const KeyType& b) const
			{
				return boost::fusion::at_c<0>(a) < boost::fusion::at_c<0>(b);
			}
	};
	template <typename T> class ContentComparator
	{
		private:
			boost::reference_wrapper<T> first;		public:
			ContentComparator(boost::reference_wrapper<T> firstToSet)
			: first(firstToSet){}
			bool operator()(boost::reference_wrapper<T> elementToCheck) const
			{
				return
					((typename boost::unwrap_reference<T>::type)elementToCheck)
					==
					((typename boost::unwrap_reference<T>::type)first);
			}
	};
}

#endif