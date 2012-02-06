import java.util.*;

class VariableInformation
{
    public final String variableType;
    public final String variableName;
    VariableInformation(String variableTypeToSet, String variableNameToSet)
    {
        variableType = variableTypeToSet;
        variableName = variableNameToSet;
    }
    public boolean equals(Object other)
    {
        if(other instanceof VariableInformation)
        {
            VariableInformation otherPair = (VariableInformation)other;
            return
                (
                    (
                        this.variableType == otherPair.variableType
                        ||
                        (
                            this.variableType != null
                            &&
                            otherPair.variableType != null
                            &&
                            this.variableType.equals(otherPair.variableType)
                        )
                    )
                    &&
                    (
                        this.variableName == otherPair.variableName
                        ||
                        (
                            this.variableName != null
                            &&
                            otherPair.variableName != null
                            &&
                            this.variableName.equals(otherPair.variableName)
                        )
                    )
                );
        }
        return false;
    }
    public int hashCode()
    {
        int hashFirst = variableType != null ? variableType.hashCode() : 0;
        int hashSecond = variableName != null ? variableName.hashCode() : 0;
        return (hashFirst + hashSecond)*hashSecond + hashFirst;
    }
}
class MapExperiment
{
    public static void main(String[] args)
    {
        LinkedHashSet<VariableInformation> set = new LinkedHashSet<VariableInformation>();
        set.add(new VariableInformation("a","b"));
        set.add(new VariableInformation("a","b"));
        set.add(new VariableInformation("a","b"));
        set.add(new VariableInformation("a","b"));
        set.add(new VariableInformation("a","b"));
        System.out.println(set.size());
    }
}
