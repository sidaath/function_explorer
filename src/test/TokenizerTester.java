import java.util.Deque;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.io.*;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Path;

class TokenizerTester {
	public static void main (String[] args){
		//extend later to get a full queue of directories to search
		try{
			System.out.println("SOurce File : "+args[0]);
			System.out.println("Result File : "+args[1]);
			File file = new File(args[0]);
			Map<String, List<String>> res2 = Tokenizer.tokenizeFile(file);	
			InputStream inStream = new FileInputStream(file);
			Reader readerRaw = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(readerRaw);
			List<String> compare = Files.readAllLines(Path.of(args[1]));
			System.out.println("Pass");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}

