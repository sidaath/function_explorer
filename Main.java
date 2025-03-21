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
		try{
			String fileContent = Files.readString(Path.of(args[0]));
			System.out.println(fileContent);
			System.out.println("**** end of file *******\n");
			File file = new File(args[0]);
			ArrayList<String> res = tokenize(file);
			System.out.println("done tokenizing");
			int index = 0;
			for(String s : res){
				System.out.println(index+" = "+s);
				index++;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		System.out.println("done");
	}

	private static ArrayList<String> tokenize(File file){
		ArrayList<String> list = new ArrayList();
		boolean inClass = false;
		try{
			InputStream inStream = new FileInputStream(file);
			Reader readerRaw = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(readerRaw);
			int r ;
			StringBuilder charHolder = new StringBuilder();
			while( (r = reader.read()) != -1){
				if((char)r == '/'){
					skipCommentedLines(reader, (char)r);
				}
				else if((char)r == ' ' || (char)r == '	'){
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
				else if ((char)r == '{' && !inClass){
					inClass = true;
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
		catch(Exception e){
			System.out.println(e);
		}

		return list;
	}

	private static void skipCommentedLines(BufferedReader reader, char commentStarter){
		try{
                    	if(commentStarter == '/'){
                                char nextChar = (char)reader.read();
                                if(nextChar == '/'){
                                        String x = reader.readLine();
                                        skipCommentedLines(reader, 'x');
                                        return;
                                }
                                if(nextChar == '*'){
                                        skipCommentedLines(reader, '*');
                                        return;
                                }
                                return;
                        }

                        if(commentStarter == 'x'){
                                reader.mark(2);
                                char nextChar = (char)reader.read();
                                if(nextChar == '/'){
                                        skipCommentedLines(reader, '/');
                                        return;
                                }else{
                                      	reader.reset();
                                        return;
                                }
                        }

                        if(commentStarter == '*'){
                                String line = reader.readLine();
                                if(line.length() < 2){
                                        skipCommentedLines(reader, '*');
                                        return;
                                }
                                String last2Chars = line.substring(line.length() - 2);
                                if(last2Chars.equals("*/")){
                                        return;
                                }else{
                                      	skipCommentedLines(reader, '*');
                                        return;
                                }
                        }
			return;
                }catch(Exception e){
                        System.out.println(e);
                }

                return;
        }


	private static void readScope(BufferedReader reader, char openBracket, char closeBracket){
		Deque<Character> stack = new ArrayDeque();
		int r;
		stack.add(openBracket);
		try{
			while(stack.size() != 0 && (r = reader.read()) != -1){
				if((char)r == closeBracket){
					stack.removeFirst();
				}
				if((char)r == openBracket){
					stack.addFirst(openBracket);
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}

