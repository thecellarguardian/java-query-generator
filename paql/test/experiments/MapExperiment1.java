import java.util.*;

class Pair<T,V>
{
	public final T first;
	public final V second;
	public Pair(T f, V s)
	{
		first = f;
		second = s;
	}
	public int hashCode() {
	   int hashFirst = first != null ? first.hashCode() : 0;
	                int hashSecond = second != null ? second.hashCode() : 0;
	                
	                        return (hashFirst + hashSecond) * hashSecond + hashFirst;
	                            }
public boolean equals(Object other) {
        if (other instanceof Pair) {
                        Pair otherPair = (Pair) other;
                                        return 
                                                        ((  this.first == otherPair.first ||
                                                                                ( this.first != null && otherPair.first 
                                                                                != null &&
                                                                                                          this.first.equals(otherPair.first))) &&
                                                                                                                           (      this.second == otherPair.second ||
                                                                                                                                                   ( this.second != null && otherPair.second != null &&
                                                                                                                                                                             this.second.equals(otherPair.second))) );
                                                                                                                                                                                     }
                                                                                                                                                                                     
                                                                                                                                                                                             return false;
                                                                                                                                                                                                 }
}

class MapExperiment
{
	public static void main(String[] args)
	{
		HashMap<Pair<String, String>, Integer> m = new HashMap<Pair<String, String>, Integer>();
		String k1 = new String("prova");
		String k2 = new String("prova");
		m.put(new Pair<String,String>(k1, k2), 1);
		String k3 = new String("prova");
		String k4 = new String("prova");
		System.out.println((new Pair<String, String>(k1, k2)).equals(new Pair<String, String>(k3, k4)));
		System.out.println((new Pair<String, String>(k1, k2)).hashCode() == (new Pair<String, String>(k3, 
		k4)).hashCode());
		System.out.println(m.containsKey(new Pair<String, String>(k3,k4)));
		LinkedHashSet<Pair<String, String> > set = new LinkedHashSet<Pair<String, String> >();
		set.add(new Pair<String, String>(k1, k2));
		set.add(new Pair<String, String>(k1, k3));
		set.add(new Pair<String, String>(k2, k3));
		set.add(new Pair<String, String>(k2, k2));
		set.add(new Pair<String, String>(k3, k1));
		set.add(new Pair<String, String>(k1, k1));
		set.add(new Pair<String, String>(k3, "diverso"));
		Iterator<Pair<String, String> > i = set.iterator();
		while(i.hasNext())
		{
			System.out.println(i.next());
		}
		}
}
