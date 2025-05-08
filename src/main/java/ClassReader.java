import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;

class ClassReader{

	static List<String> processClass(Reader reader){
		ArrayList<String> tokenList = new ArrayList();
		StringBuilder charHolder = new StringBuilder();
		int r;
		try{
			while((char)(r = reader.read()) != '}'){
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
			return tokenList;

		}catch(Exception e){
			System.out.println(e);
		}
		return tokenList;
		
	}

	//use the reader to skip over commented lines
	//at end of execution, position reader at the first non-commented char ready for reading
	static void skipCommentedLines(Reader reader, char commentStarter){
		if(reader.markSupported()){
			skipWithResetBuffer(reader, '/');
		}
		else{
			skipWithoutResetBuffer(reader, '/');
		}
		return;
        }


	private static int skipWithResetBuffer(Reader reader, char commentStarter){
		int count = 0;
		try{
                    	if(commentStarter == '/'){
                                char nextChar = (char)reader.read();
				count++;
                                if(nextChar == '/'){
                                        String x = readLine(reader);
					count = count + x.length();
                                        count = count + skipWithResetBuffer(reader, 'x');
                                        return count;
                                }
                                if(nextChar == '*'){
                                        count = count + skipWithResetBuffer(reader, '*');
                                        return count;
                                }
                                return count;
                        }

                        if(commentStarter == 'x'){
                                reader.mark(2);
                                char nextChar = (char)reader.read();
				count++;
                                if(nextChar == '/'){
                                        count = count + skipWithResetBuffer(reader, '/');
                                        return count;
                                }else{
                                      	reader.reset();
					count--;
                                        return count;
                                }
                        }

                        if(commentStarter == '*'){
                                String line = readLine(reader);
				count = count + line.length();
                                if(line.length() < 2){
                                        count = count + skipWithResetBuffer(reader, '*');
                                        return count ;
                                }
                                String last2Chars = line.substring(line.length() - 2);
                                if(last2Chars.equals("*/")){
                                        return count;
                                }else{
                                      	count = count + skipWithResetBuffer(reader, '*');
                                        return count;
                                }
                        }
                }catch(Exception e){
                        System.out.println(e);
                }
                return count;
	}

	private static String readLine(Reader reader){
		StringBuilder charHolder = new StringBuilder();

		try{
			int r;
			while((r = reader.read()) != 10 && r != 13){
				charHolder.append((char)r);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return charHolder.toString();
	}

	private static void skipWithoutResetBuffer(Reader unresettableReader, char commentStarter){
		int skippedChars = 0;
		try{
 			BufferedReader reader = new BufferedReader(unresettableReader);

			if(commentStarter == '/'){
				reader.mark(2);
                                char nextChar = (char)reader.read();
                                skippedChars++;
				if(nextChar == '/'){	
                                        String x = reader.readLine();
					skippedChars = x.length();
                                        skippedChars = skippedChars + skipWithResetBuffer(reader, 'x');
					unresettableReader.skip(skippedChars);
                                        return;
                                }
                                if(nextChar == '*'){
                                        skippedChars = skippedChars + skipWithResetBuffer(reader, '*');
					unresettableReader.skip(skippedChars);
                                        return;
                                }
				reader.reset();
                                return;
                	}
                }catch(Exception e){
                        System.out.println(e);
                }

                return;
	}

	//position the reader at the closing position of scope declared by '(' or '{'
	private static void readScope(Reader reader, char openBracket, char closeBracket){
		int bracketCount = 1;
		int r;
		try{
			while(bracketCount != 0){
				r = reader.read();
				if((char)r == closeBracket){
					bracketCount--;
				}
				if((char)r == openBracket){
					bracketCount++;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	

}
