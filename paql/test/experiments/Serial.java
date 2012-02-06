import java.io.Serializable;
import java.io.ObjectOutputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
class Obj implements Serializable
{
	transient public final String fileName;
	public final String data;
	public Obj(String f, String d)
	{
		fileName = f;
		data = d;
	}
}

public class Serial
{
	public static void main(String[] args)
	{
		Obj o = new Obj("prova", "<xml>");
		FileOutputStream fis = null;
		ObjectOutputStream out = null;
		try
		{
			fis = new FileOutputStream(o.fileName);
			out = new ObjectOutputStream(fis);
			out.writeObject(0);
			out.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
