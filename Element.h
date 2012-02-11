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

#include <string>
using namespace std;

#ifndef __ELEMENT_ELEMENT_H__
#define __ELEMENT_ELEMENT_H__

namespace paql
{
	namespace element
	{
		struct Element
		{
			char c;
			int i;
			string s1;
			string s2;
			bool operator==(const Element& elementToCompare) const
			{
				return
					(elementToCompare.c == c) &&
					(elementToCompare.i == i) &&
					(elementToCompare.s1.compare(s1) == 0) &&
					(elementToCompare.s2.compare(s2) == 0) &&
					(true);
			}
		};
	}
}

#endif