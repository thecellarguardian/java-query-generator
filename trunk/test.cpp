#include "StudentClass.h"
#include "Student.h"
#include "StudentClassRetrieveAll.h"
#include <iostream>

int main()
{
	paql::element::Student a =
		{
			152030,
			"Carlo",
			"Rossi",
			25,
			"Università di Pisa",
			"Facoltà di Ingegneria",
			"Corso di Laurea in Ingegneria Informatica"
		};
	paql::element::Student b =
		{
			152031,
			"Carla",
			"Bruni",
			32,
			"Università di Mantova",
			"Facoltà di Lettere",
			"Corso di Laurea in Filologia"
		};
	paql::element::Student c =
		{
			152032,
			"Diego",
			"Rosi",
			22,
			"Università di Foggia",
			"Facoltà di Agraria",
			"Corso di Laurea in Politiche Agrarie e del Territorio"
		};
	paql::element::Student d =
		{
			152033,
			"Antonio",
			"Verdi",
			21,
			"Università di Siena",
			"Facoltà di Scienze NN.MM.FF",
			"Corso di Laurea in Fisica"
		};
	paql::container::StudentClass mixedClass;
	mixedClass.insert(a);
	mixedClass.insert(b);
	mixedClass.insert(c);
	mixedClass.insert(d);
	paql::query::StudentClassRetrieveAll query(mixedClass);
	std::list< boost::reference_wrapper<paql::element::Student> > results = query.execute();
	for
	(
		std::list< boost::reference_wrapper<paql::element::Student> >::iterator i = results.begin();
		i != results.end();
		i++
	)
	{
		std:: cout << ((boost::unwrap_reference<paql::element::Student>::type&)(*i)).age << std::endl;
		a.age++;
		std:: cout << "\t" << ((boost::unwrap_reference<paql::element::Student>::type&)(*i)).age << std::endl;
	}
}
