package paql.lib.Triple;

public class Triple<A, B, C>
{
    private final A first;
    private final B second;
    private final C third;
    public Triple(A first, B second, C third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    public int hashCode()
    {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;
        int hashThird = third != null ? third.hashCode() : 0;
        int partialHash = (hashFirst + hashSecond)*hashSecond + hashFirst;
        return (partialHash + hashThird)*hashThird + partialHash;
    }
    public boolean equals(Object other)
    {
        if(other instanceof Triple)
        {
            @SuppressWarnings("unchecked") Triple<A, B, C> otherPair = (Triple<A, B, C>)other;
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
                    &&
                    (
                        this.third == otherPair.third
                        ||
                        (
                            this.third != null
                            &&
                            otherPair.third != null
                            &&
                            this.third.equals(otherPair.third)
                        )
                    )
                );
        }
        return false;
    }
    public String toString()
    {
        return "(" + first + ", " + second + ", " + third + ")";
    }
    public A getFirst()
    {
        return first;
    }
    public B getSecond()
    {
        return second;
    }
    public C getThird()
    {
        return third;
    }
}

