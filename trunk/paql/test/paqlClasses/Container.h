/**
 * @file Container.h
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

#ifndef CONTAINER_H
#define CONTAINER_H

/*----------------------------------------------------------------------------*/
//Gli elementi qui racchiusi stanno per conto loro in una libreria

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
    boost::reference_wrapper<T> first;
    public:
        ContentComparator(boost::reference_wrapper<T> firstToSet) : first(firstToSet){}
        bool operator()(boost::reference_wrapper<T> elementToCheck) const
        {
            return
                ((typename boost::unwrap_reference<T>::type)elementToCheck)
                ==
                ((typename boost::unwrap_reference<T>::type)first);
        }
};
/*----------------------------------------------------------------------------*/
//Il codice seguente è generato
class Container : public std::list< boost::reference_wrapper<Element> >, //</GENERATOR: ci mette Element e il nome classe>
public virtual boost::fusion::map
<
    /* <GENERATOR: per ogni chiave dichiara una entry di una meta-mappa,
     * con la relativa meta-chiave e valore (una mappa che indicizza secondo la chiave).> */
    boost::fusion::pair
    <
        MetaKey<boost::fusion::vector<char>,0>, //Chiave metamappa
        std::map //Valore metamappa
        <
            boost::fusion::vector<char>, //Chiave mappa
            boost::reference_wrapper<Element>, //Valore mappa
            FusionVectorComparator< boost::fusion::vector<char> > //Comparator
        >
    >,
    boost::fusion::pair
    <
        MetaKey<boost::fusion::vector<std::string, bool, double>, 1>, //Chiave metamappa
        std::map //Valore metamappa
        <
            boost::fusion::vector<std::string, bool, double>, //Chiave mappa
            boost::reference_wrapper<Element>, //Valore mappa
            FusionVectorComparator<boost::fusion::vector<std::string, bool, double> > //Comparatore
        >
    >
    /* </GENERATOR> */
>
{
    public:
        template<int keyIndex> Element& get(const char& a0)
        {
            typedef boost::fusion::vector<char> KeyType;
            typedef MetaKey<KeyType, keyIndex> MetaKeyType;
            KeyType key(a0);
            return (boost::unwrap_reference<Element>::type&)(((boost::fusion::at_key<MetaKeyType>(*this)).find(key))->second);
        }
        template<int keyIndex> Element& get(const std::string& a0, const bool& a1, const double& a2)
        {
            typedef boost::fusion::vector<std::string, bool, double> KeyType;
            typedef MetaKey<KeyType, keyIndex> MetaKeyType;
            KeyType key(a0, a1, a2);
            return (boost::unwrap_reference<Element>::type&)(((boost::fusion::at_key<MetaKeyType>(*this)).find(key))->second);
        }
        bool insert(Element& element) //nella derivata!
        {
            //per ogni chiave genero il fusion vector adeguato, la metakey
            //adeguata e inserisco l' elemento
            boost::reference_wrapper<Element> elementToInsert = boost::ref<Element>(element);
            {
                typedef boost::fusion::vector<char> KeyType;
                typedef MetaKey<KeyType, 0> MetaKeyType;
                KeyType key(element.c);
                if((boost::fusion::at_key<MetaKeyType>(*this)).count(key)) return false;
                (boost::fusion::at_key<MetaKeyType>(*this)).insert
                (
                    std::map< KeyType, boost::reference_wrapper<Element>, FusionVectorComparator< KeyType > >::value_type(key, elementToInsert)
                );
            }
            {
                typedef boost::fusion::vector<std::string, bool, double> KeyType;
                typedef MetaKey<KeyType, 1> MetaKeyType;
                KeyType key(element.s, element.b, element.d);
                if((boost::fusion::at_key<MetaKeyType>(*this)).count(key)) return false;
                (boost::fusion::at_key<MetaKeyType>(*this)).insert
                (
                    std::map< KeyType, boost::reference_wrapper<Element>, FusionVectorComparator< KeyType > >::value_type(key, elementToInsert)
                );
            }
            this->push_back(boost::ref(element));
            return true;
            //Infine
        }
        void remove(Element& element)
        {
            {
                typedef boost::fusion::vector<char> KeyType;
                typedef MetaKey<KeyType, 0> MetaKeyType;
                KeyType key(element.c);
                (boost::fusion::at_key<MetaKeyType>(*this)).erase(key);
            }
            {
                typedef boost::fusion::vector<std::string, bool, double> KeyType;
                typedef MetaKey<KeyType, 1> MetaKeyType;
                KeyType key(element.s, element.b, element.d);
                (boost::fusion::at_key<MetaKeyType>(*this)).erase(key);
            }
            this->remove_if(ContentComparator<Element>(boost::ref(element)));
        }
};

#endif
