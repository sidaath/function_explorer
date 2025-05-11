package tokenize;

import java.util.Deque;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Arrays;
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

			Map<String, List<String>> result = Tokenizer.tokenizeFile(file);	
			Map<String, List<String>> expected = new HashMap();


			InputStream inStream = new FileInputStream(file);
			Reader readerRaw = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(readerRaw);
			List<String> lines = Files.readAllLines(Path.of(args[1]));

			for(String line : lines){
				String[] lineContent = line.split("/");
				String className = lineContent[0];
				List<String> tokens = Arrays.asList(lineContent[1].split(";"));
				expected.put(className, tokens);
			}

			for(Map.Entry<String, List<String>> entry : expected.entrySet()){
				if(!result.containsKey(entry.getKey())){
					System.out.println("Key not available in result map :"+entry.getKey());
					return;
				}
				List<String> expectedTokens = entry.getValue();
				List<String> resultTokens = result.get(entry.getKey());

				for(int i = 0; i < expectedTokens.size(); i++){
					if(!expectedTokens.get(i).equals(resultTokens.get(i))){
						System.out.println(expectedTokens.get(i)+" != "+resultTokens.get(i));
					}
				}
			}

			System.out.println("Pass");

		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}

