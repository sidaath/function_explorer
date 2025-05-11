package tokenize;

import java.util.Map;
import java.util.List;
import java.util.Deque;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.io.*;

class Tokenizer {

	public static Map<String, List<String>> tokenizeFile(File file){
		return getTokensFromFile(file);
	}

	private static Map<String,List<String>> getTokensFromFile(File file){
		Map<String, List<String>> tokenMap = new HashMap();
		
		try{
			InputStream inputStream = new FileInputStream(file);
			Reader streamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(streamReader);
			tokenMap = parseText(reader);
		}catch(Exception e){
			System.out.println(e);
		}
		return tokenMap;
	}

	//read classes contained in a source file recursively
	//each recursive iteration tokenizes one class/interface
	//final return is a joined map of all iterations
	private static Map<String, List<String>> parseText(BufferedReader reader){
		Map<String, List<String>> tokensInClass = new HashMap();
		String className = "";
		try{
			int r = reader.read();
			if(r == -1){
				return tokensInClass;
			}

			if(r == 10){
				return parseText(reader);
			}

			else{
				char character = (char)r;
				StringBuilder charHolder = new StringBuilder();
				if(character == '/'){
					ClassReader.skipCommentedLines(reader, '/');
				}else{
					charHolder.append(character);
				}
				while ((r = reader.read()) != -1 && (character = (char)r) != '{'){
					if(character == ' ' || character == ';' || character =='\n'){
						className = charHolder.toString();
						charHolder.setLength(0);
					}else{
						charHolder.append((char)r);
					}
				}
				
				List<String> tokenList = ClassReader.processClass(reader);
				tokensInClass.put(className, tokenList);
			}

			Map<String, List<String>> nextClassTokens = parseText(reader);
			if(nextClassTokens.isEmpty()){
				return tokensInClass;
			}else{
				for(Map.Entry<String, List<String>> pair : nextClassTokens.entrySet()){
					tokensInClass.put(pair.getKey(), pair.getValue());
				}
			}
			return tokensInClass;
		}catch(Exception e){
			System.out.println(e);
		}
		return null;
	}

//	//reads text inside a class/interface while skipping contents within brackets
//	//returns a list of "tokens" - token = identifiers, (), {}, keywords
//	private static List<String> processClassText(BufferedReader reader){
//		ArrayList<String> tokenList = new ArrayList();
//		StringBuilder charHolder = new StringBuilder();
//		int r;
//		try{
//			while((char)(r = reader.read()) != '}'){
//				char character = (char)r;
//				
//				if(character == '/'){
//					skipCommentedLines(reader, character);
//				}
//
//				else if(character == '{'){
//					if(charHolder.length() > 0){
//						tokenList.add(charHolder.toString());
//						charHolder.setLength(0);
//					}
//					readScope(reader, '{', '}');
//					tokenList.add("{}");
//				}
//
//				else if(character == ' ' || character == '	'){
//					if(charHolder.length() > 0){
//						tokenList.add(charHolder.toString());
//						charHolder.setLength(0);
//					}
//				}
//
//				else if(character == '('){
//					if(charHolder.length() > 0){
//						tokenList.add(charHolder.toString());
//						charHolder.setLength(0);
//					}
//					readScope(reader, '(', ')');
//					tokenList.add("()");
//				}
//
//				else if(character != '\n'){
//					charHolder.append(character);
//				}
//			}
//			return tokenList;
//
//		}catch(Exception e){
//			System.out.println(e);
//		}
//		return tokenList;
//	}
//	
//	//use the reader to skip over commented lines
//	//at end of execution, position reader at the first non-commented char ready for reading
//	private static void skipCommentedLines(BufferedReader reader, char commentStarter){
//		try{
//                    	if(commentStarter == '/'){
//                                char nextChar = (char)reader.read();
//                                if(nextChar == '/'){
//                                        String x = reader.readLine();
//                                        skipCommentedLines(reader, 'x');
//                                        return;
//                                }
//                                if(nextChar == '*'){
//                                        skipCommentedLines(reader, '*');
//                                        return;
//                                }
//                                return;
//                        }
//
//                        if(commentStarter == 'x'){
//                                reader.mark(2);
//                                char nextChar = (char)reader.read();
//                                if(nextChar == '/'){
//                                        skipCommentedLines(reader, '/');
//                                        return;
//                                }else{
//                                      	reader.reset();
//                                        return;
//                                }
//                        }
//
//                        if(commentStarter == '*'){
//                                String line = reader.readLine();
//                                if(line.length() < 2){
//                                        skipCommentedLines(reader, '*');
//                                        return;
//                                }
//                                String last2Chars = line.substring(line.length() - 2);
//                                if(last2Chars.equals("*/")){
//                                        return;
//                                }else{
//                                      	skipCommentedLines(reader, '*');
//                                        return;
//                                }
//                        }
//                }catch(Exception e){
//                        System.out.println(e);
//                }
//                return;
//        }
//	
//	//position the reader at the closing position of scope declared by '(' or '{'
//	private static void readScope(BufferedReader reader, char openBracket, char closeBracket){
//		Deque<Character> stack = new ArrayDeque();
//		int r;
//		stack.addFirst (openBracket);
//		try{
//			while(stack.size() != 0){
//				r = reader.read();
//				if((char)r == closeBracket){
//					stack.removeFirst();
//				}
//				if((char)r == openBracket){
//					stack.addFirst(openBracket);
//				}
//			}
//		}
//		catch(Exception e){
//			System.out.println(e);
//		}
//	}
	
}
