/**
 * The following code has been produced by the stackoverflow user arturh,
 * in this discussion:
 * http://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java
 **/

package paql.lib.Pair;

public class Pair<A, B>
{
    private final A first;
    private final B second;
    public Pair(A first, B second)
    {
        this.first = first;
        this.second = second;
    }
    public int hashCode()
    {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;
        return (hashFirst + hashSecond)*hashSecond + hashFirst;
    }
    public boolean equals(Object other)
    {
        if(other instanceof Pair)
        {
            @SuppressWarnings("unchecked") Pair<A, B> otherPair = (Pair<A, B>)other;
            return
                (
                    (
                        this.first == otherPair.first
                        ||
                        (
                            this.first != null
                            &&
                            otherPair.first != null
                            &&
                            this.first.equals(otherPair.first)
                        )
                    )
                    &&
                    (
                        this.second == otherPair.second
                        ||
                        (
                            this.second != null
                            &&
                            otherPair.second != null
                            &&
                            this.second.equals(otherPair.second)
                        )
                    )
                );
        }
        return false;
    }
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }
    public A getFirst()
    {
        return first;
    }
    public B getSecond()
    {
        return second;
    }
}
