import java.util.Deque;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.io.*;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Path;

class Main
{
	public static void main (String[] args){
		//extend later to get a full queue of directories to search
		int offset = Integer.parseInt(args[1]);
		System.out.println("offset = "+offset);

		try{
			String fileContent = Files.readString(Path.of(args[0]));
			System.out.println(fileContent);
			System.out.println("\n\n");
			File file = new File(args[0]);
			ArrayList<String> res = tokenize(file);
			int index = 0;
			for(String s : res){
				System.out.println(index+" = "+s);
				index++;
			}
		}
		catch(Exception ex){
			System.out.println("exception at read 1");
		}
		//String content = readBracketContent()
		System.out.println("done");
	}

	private static ArrayList<String> tokenize(File file){
		ArrayList<String> list = new ArrayList();

		try{
			InputStream inStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inStream);
			Reader buffered = new BufferedReader(reader);
			int r ;
			StringBuilder charHolder = new StringBuilder();
			readTopStatement(reader);
			while( (r = reader.read()) != -1){
				if((char)r == ' ' || (char)r == '	'){
					if(charHolder.length() > 0){
						list.add(charHolder.toString());
						charHolder.setLength(0);
					}
				}
				else if((char)r == '('){
					if(charHolder.length() > 0){
						list.add(charHolder.toString());
						charHolder.setLength(0);
					}
					readScope(reader, '(', ')');
					list.add("()");
				}
				else if((char)r == '{'){
					if(charHolder.length() > 0){
						list.add(charHolder.toString());
						charHolder.setLength(0);
					}
					readScope(reader, '{', '}');
					list.add("{}");
				}
				else if((char)r != '\n'){
					charHolder.append((char)r);
				}
			}
		}
		catch(Exception ex){
			System.out.println("exception");
		}

		return list;
	}

	private static void readTopStatement(Reader reader){
		StringBuilder charHolder = new StringBuilder();
		String[] words = new String[2];
		int r;
		try{
			while( (r = reader.read()) != -1){
				//skip any leading whitespace
				while((char)r == ' ' || (char)r == '	'){
					r = reader.read();
				}

				//check if first letter in file is /, if it is not return
				if((char)r != '/'){
					return;
				}
				else{
					r = reader.read();
				}
				//check if first two letters are // or /\*, if not return
				if((char)r != '/' || (char)r != '*'){
					return;
				}
				
			}
		}
		catch(Exception ex){
			System.out.println("ex 2");
		}
	}

	private static void skipCommentedLines(Reader reader, char commentStarter){
		if(commentStarter == '/'){
			char nextChar = (char)reader.read();
			if(nextChar == '/'){
				reader.readLine();
				skipCommentedLines(reader, 'x');
				return;
			}
			if(nextChar == '*'){
				skipCommentedLines(reader, '*');
				return;
			}
		}

		if(commentStarter == 'x'){
			char nextChar = (char)reader.read();
			if(nextChar == '/'){
				skipCommentedLines(reader, '/');
				return;
			}
		}

		if(commentStarter == '*'){
			String line = reader.readLine();
			String last2Chars = line.substring(line.length() - 2);
			if(last2Chars.equals("*/")){
				return;
			}else{
				skipCommentedLines(reader, '*');
				return;
			}
		}

		return;
	}

	private static void readScope(Reader reader, char openBracket, char closeBracket){
		Deque<Character> stack = new ArrayDeque();
		int r;
		stack.add(openBracket);
		try{
			while(stack.size() != 0){
				r = reader.read();
				if((char)r == closeBracket){
					stack.removeFirst();
				}
				if((char)r == openBracket){
					stack.addFirst(openBracket);
				}
			}
		}
		catch(Exception e){System.out.println("ex3");}
	}
}

