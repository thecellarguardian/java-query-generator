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

#ifndef __STUDENT_ELEMENT_H__
#define __STUDENT_ELEMENT_H__

namespace paql
{
	namespace element
	{
		struct Student
		{
			int code;
			string name;
			string surname;
			int age;
			string university;
			string faculty;
			string course;
			bool operator==(const Student& elementToCompare) const
			{
				return
					(elementToCompare.code == code) &&
					(elementToCompare.name.compare(name) == 0) &&
					(elementToCompare.surname.compare(surname) == 0) &&
					(elementToCompare.age == age) &&
					(elementToCompare.university.compare(university) == 0) &&
					(elementToCompare.faculty.compare(faculty) == 0) &&
					(elementToCompare.course.compare(course) == 0) &&
					(true);
			}
		};
	}

}

#endif