import java.util.Map;
import java.util.List;
import java.util.Deque;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.io.*;
import java.lang.Character;

class Tokenizer {

	public static Map<String, List<String>> tokenizeFile(File file){
		return getTokensFromFile(file);
	}

	private static Map<String,List<String>> getTokensFromFile(File file){


		StringBuilder charHolder = new StringBuilder();
		Map<String, List<String>> tokenMap = new HashMap();
		
		int r;
		String className = "";

		try{
			InputStream inputStream = new FileInputStream(file);
			Reader streamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(streamReader);
			while((r = reader.read()) != -1){
				char character = (char)r;
				switch(character){
					case '/':
						skipCommentedLines(reader, character);
						break;
					case '{':
						List<String> tokens = processClassText(reader);
						tokenMap.put(className, tokens);
						charHolder.setLength(0);
						break;
					case ' ':
						className = charHolder.toString();
						charHolder.setLength(0);
						break;
					case '\n':
						className = charHolder.toString();
						charHolder.setLength(0);
						break;
					default:
						charHolder.append(character);
						break;
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return tokenMap;
	}


	private static List<String> processClassText(BufferedReader reader){
		ArrayList<String> tokenList = new ArrayList();
		StringBuilder charHolder = new StringBuilder();
		int r;
		try{
			while((r = reader.read()) != -1){
				char character = (char)r;
				
				if(character == '/'){
					skipCommentedLines(reader, character);
				}

				else if(character == '{'){
					if(charHolder.length() > 0){
						tokenList.add(charHolder.toString());
						charHolder.setLength(0);
					}
					readScope(reader, '{', '}');
					tokenList.add("{}");
				}

				else if(character == '}'){
					return tokenList;
				}

				else if(character == '}'){
					charHolder.setLength(0);
				}

				else if(character == ' ' || character == '	'){
					if(charHolder.length() > 0){
						tokenList.add(charHolder.toString());
						charHolder.setLength(0);
					}
				}

				else if(character == '('){
					if(charHolder.length() > 0){
						tokenList.add(charHolder.toString());
						charHolder.setLength(0);
					}
					readScope(reader, '(', ')');
					tokenList.add("()");
				}

				else if(character != '\n'){
					charHolder.append(character);
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return tokenList;
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
