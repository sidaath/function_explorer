import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
class Test
{
	public static void main(String[] args){
                try{
                    	String fileContent = Files.readString(Path.of(args[0]));
                        System.out.println(fileContent);
			System.out.println("char at string = "+args[1]);
                        System.out.println("\n\n");
                        File file = new File(args[0]);
                        InputStream inStream = new FileInputStream(file);
                        Reader reader = new InputStreamReader(inStream);
                        BufferedReader buffered = new BufferedReader(reader);
			skipCommentedLines(buffered, args[1].charAt(0));
			System.out.println("result = " + buffered.readLine());
                }
                catch(Exception ex){
                        System.out.println("exception at read 1");
                }
                //String content = readBracketContent()
                System.out.println("done");

	}

	private static void skipCommentedLines(BufferedReader reader, char commentStarter){
		try{
	                if(commentStarter == '/'){
				reader.mark(2);
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
				reader.reset();
				return;
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
		}catch(Exception e){
			System.out.println("Exception");
		}

                return;
        }
}
