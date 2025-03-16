import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
class SkipOverCommentedLinesTester
{
	public static void main(String[] args){
		String message = "To use this method, the reader has to be position at the first found instance of the \'/\' character."
				+"eg : // this is a line - the reader should have read the first / and then call the skip method.";
		System.out.println(message);
		System.out.println("Directory with test files : "+args[0]);
                try{
			File directory = new File(args[0]);
			File[] files = directory.listFiles();
			for(File file : files){
				System.out.println("File : "+ file.getName());
	                        InputStream inStream = new FileInputStream(file);
	                        Reader reader = new InputStreamReader(inStream);
	                        BufferedReader buffered = new BufferedReader(reader);
				skipCommentedLines(buffered, (char)buffered.read());
				System.out.println(buffered.readLine());
			}
                }
                catch(Exception ex){
                        System.out.println("exception at read 1");
                }
                System.out.println("done");

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
				if(line.length() == 0){
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
			System.out.println("Exception");
			System.out.println(e);
		}

                return;
        }
}
