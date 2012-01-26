/**
 * @file PAQLTokenClass.java
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

package paql.src.PAQLCompilationStep.Utilities.PAQLTokenClass;

public enum PAQLTokenClass
{
    NUMERIC_CONSTANT,
    CHAR_CONSTANT,
    STRING_CONSTANT,
    IDENTIFIER,
    ELEMENT_KEYWORD,
    CONTAINER_KEYWORD,
    QUERY_KEYWORD,
    KEY_KEYWORD,
    SEMICOLON,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    LEFT_CURLY_BRACKET,
    RIGHT_CURLY_BRACKET,
    LEFT_ANGULAR_BRACKET,
    RIGHT_ANGULAR_BRACKET
}
